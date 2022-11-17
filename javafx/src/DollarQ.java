import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;

public class DollarQ {
    private int cloudSize = 64;
    private int lutSize = 64;
    private HashMap<String, PointCloud> templates = new HashMap<>();
    private int templateCnt = 2;

    public DollarQ() {
        List<double[]> temp = new ArrayList<>();
        temp.add(new double[] { 30, 7, 1 });
        temp.add(new double[] { 103, 7, 1 });
        temp.add(new double[] { 66, 7, 2 });
        temp.add(new double[] { 66, 87, 2 });
        PointCloud t = new PointCloud("T", temp);
        templates.put("T", t);

        temp = new ArrayList<>();
        temp.add(new double[] { 177, 2, 1 });
        temp.add(new double[] { 177, 95, 1 });
        temp.add(new double[] { 182, 1, 2 });
        temp.add(new double[] { 246, 95, 2 });
        temp.add(new double[] { 247, 87, 3 });
        temp.add(new double[] { 247, 1, 3 });
        PointCloud n = new PointCloud("N", temp);
        templates.put("N", n);

        temp = new ArrayList<>();
        temp.add(new double[] { 0, 0, 1 });
        temp.add(new double[] { 75, 0, 1 });
        temp.add(new double[] { 75, 1, 2 });
        temp.add(new double[] { 75, 50, 2 });
        temp.add(new double[] { 74, 50, 3 });
        temp.add(new double[] { 0, 50, 3 });
        temp.add(new double[] { 0, 49, 3 });
        temp.add(new double[] { 0, 1, 3 });
        PointCloud square = new PointCloud("Square", temp);
        templates.put("Square", square);

    }

    private class PointCloud {
        private String name;
        private List<double[]> points;
        private int[][] lut;

        public PointCloud(String name, List<double[]> points) {
            this.name = name;
            this.points = normalize(points);
            this.lut = computeLUT(this.points);

        }

        public String getName() {
            return this.name;
        }

        public List<double[]> getPoints() {
            return this.points;
        }

        public int[][] getLut() {
            return this.lut;
        }

    }

    public String recognize(List<double[]> points) {
        PointCloud candidate = new PointCloud("", points);
        String result = "";
        boolean found = false;
        double score = Double.MAX_VALUE;
        for (PointCloud template : templates.values()) {
            double d = cloudMatch(candidate, template, score);
            if (d < score) {
                score = d;
                result = template.getName();
                found = true;
            }
        }
        return found ? result : "Not found";
    }

    public void addTemplate(List<double[]> points) {
        templateCnt++;
        PointCloud newTemplate = new PointCloud("" + templateCnt, points);
        templates.put(newTemplate.getName(), newTemplate);
    }

    private double cloudMatch(PointCloud candidate, PointCloud template, double minSoFar) {
        int n = candidate.getPoints().size();
        int step = (int) Math.sqrt(n);

        double[] lb1 = computeLb(candidate.getPoints(), template.getPoints(), step, template.getLut());
        double[] lb2 = computeLb(template.getPoints(), candidate.getPoints(), step, candidate.getLut());

        for (int i = 0, j = 0; i < n; i += step, j++) {
            if (lb1[j] < minSoFar) {
                minSoFar = Math.min(minSoFar, cloudDistance(candidate.getPoints(), template.getPoints(), i, minSoFar));
            }
            if (lb2[j] < minSoFar) {
                minSoFar = Math.min(minSoFar, cloudDistance(template.getPoints(), candidate.getPoints(), i, minSoFar));
            }
        }

        return minSoFar;
    }

    private double cloudDistance(List<double[]> points, List<double[]> template, int start, double minSoFar) {
        int n = points.size();
        List<Integer> unmatched = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            unmatched.add(i);
        }
        int i = start;
        int weight = n;
        double sum = 0;

        do {
            double minVal = Double.MAX_VALUE;
            int index = -1;
            for (int j = 0; j < unmatched.size(); j++) {
                double d = sqrEcDistance(points.get(i), template.get(unmatched.get(j)));
                if (d < minVal) {
                    minVal = d;
                    index = j;
                }
            }
            unmatched.remove(index);
            sum += weight * minVal;
            if (sum >= minSoFar) {
                return sum;
            }
            weight--;
            i = (i + 1) % n;
        } while (i != start);
        return sum;
    }

    private double[] computeLb(List<double[]> points, List<double[]> template, int step, int[][] lut) {
        int n = points.size();
        double[] lb = new double[n / step + 1];
        double[] sat = new double[n];
        lb[0] = 0;

        for (int i = 0; i < n; i++) {
            int x = (int) Math.round(points.get(i)[0]);
            int y = (int) Math.round(points.get(i)[1]);
            int index = lut[x][y];
            double d = sqrEcDistance(points.get(i), template.get(index));
            sat[i] = (i == 0) ? d : sat[i - 1] + d;
            lb[0] += (n - i) * d;
        }
        for (int i = step, j = 1; i < n; j++, i += step) {
            lb[j] = lb[0] + i * sat[n - 1] - n * sat[i - 1];
        }
        return lb;
    }

    private List<double[]> normalize(List<double[]> points) {
        List<double[]> newPoints = resample(points);
        translateToOrigin(newPoints);
        scale(newPoints);

        return newPoints;

    }

    private List<double[]> resample(List<double[]> points) {
        List<double[]> clone = new ArrayList<>(points);
        double I = pathLength(clone) / (this.cloudSize - 1);
        double D = 0;
        List<double[]> newPoints = new ArrayList<>();
        newPoints.add(clone.get(0));

        for (int i = 1; i < clone.size(); i++) {
            if (clone.get(i)[2] == clone.get(i - 1)[2]) {
                double d = ecDistance(clone.get(i - 1), clone.get(i));
                if ((D + d) >= I) {
                    double qx = clone.get(i - 1)[0] + ((I - D) / d) * (clone.get(i)[0] - clone.get(i - 1)[0]);
                    double qy = clone.get(i - 1)[1] + ((I - D) / d) * (clone.get(i)[1] - clone.get(i - 1)[1]);
                    newPoints.add(new double[] { qx, qy, clone.get(i)[2] });
                    clone.add(i, new double[] { qx, qy, clone.get(i)[2] });
                    D = 0;
                } else {
                    D += d;
                }
            }
        }
        if (newPoints.size() == cloudSize - 1) {
            double[] lastPoint = clone.get(clone.size() - 1);
            newPoints.add(lastPoint);
        }

        return newPoints;
    }

    private void translateToOrigin(List<double[]> points) {
        double[] centroid = new double[2];

        for (double[] point : points) {
            centroid[0] += point[0];
            centroid[1] += point[1];
        }
        centroid[0] /= points.size();
        centroid[1] /= points.size();

        for (double[] point : points) {
            point[0] -= centroid[0];
            point[1] -= centroid[1];
        }
    }

    private void scale(List<double[]> points) {
        double xmin = Double.MAX_VALUE;
        double xmax = Double.MIN_VALUE;
        double ymin = Double.MAX_VALUE;
        double ymax = Double.MIN_VALUE;

        for (double[] point : points) {
            xmin = Math.min(xmin, point[0]);
            xmax = Math.max(xmax, point[0]);
            ymin = Math.min(ymin, point[1]);
            ymax = Math.max(ymax, point[1]);
        }

        double scale = Math.max(xmax - xmin, ymax - ymin) / (this.cloudSize - 1);
        for (double[] point : points) {
            point[0] = (point[0] - xmin) / scale;
            point[1] = (point[1] - ymin) / scale;
        }

    }

    private int[][] computeLUT(List<double[]> points) {
        int[][] lut = new int[this.lutSize][this.lutSize];
        for (int i = 0; i < this.lutSize; i++) {
            for (int j = 0; j < this.lutSize; j++) {
                double minVal = Double.MAX_VALUE;
                int index = -1;
                for (int k = 0; k < points.size(); k++) {
                    double d = sqrEcDistance(points.get(k), new double[] { i, j });
                    if (d < minVal) {
                        minVal = d;
                        index = k;
                    }
                }
                lut[i][j] = index;
            }
        }

        return lut;
    }

    private double pathLength(List<double[]> points) {
        double length = 0;
        double[] prevPoint = points.get(0);
        for (int i = 1; i < points.size(); i++) {
            double[] curPoint = points.get(i);
            if (prevPoint[2] == curPoint[2]) {
                length += ecDistance(prevPoint, curPoint);
            }
            prevPoint = curPoint;
        }

        return length;
    }

    private double sqrEcDistance(double[] a, double[] b) {
        return (a[0] - b[0]) * (a[0] - b[0]) + (a[1] - b[1]) * (a[1] - b[1]);
    }

    private double ecDistance(double[] a, double[] b) {
        return Math.sqrt(sqrEcDistance(a, b));
    }

    // for debugging

    private void printList(List<double[]> list) {
        System.out.println("---------" + list.size() + " points -------------");
        for (double[] point : list) {
            System.out.print(point[0] + " ,");
            System.out.print(point[1] + " ,");
            System.out.print(point[2] + "\n");
        }
    }

    private void testResample() {
        List<double[]> list = new ArrayList<>();
        for (int i = 0; i < 2000; i++) {
            list.add(new double[] { 0, i, 1 });
        }
        List<double[]> sampled = resample(list);
        printList(list);
        printList(sampled);
    }

}
