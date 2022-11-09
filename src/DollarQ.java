import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;

public class DollarQ {
    private int cloudSize = 32;
    private int lutSize = 64;

    private HashMap<String, pointCloud> pointClouds = new HashMap<>();

    public DollarQ() {
        int a = 0;
    }

    private class pointCloud {
        private String name;
        private List<double[]> points;
        private int[][] lut;

        public pointCloud(String name, List<double[]> points) {
            this.name = name;
            this.points = normalize(points);
            this.lut = computeLUT(points);
        }

    }

    private List<double[]> normalize(List<double[]> points) {
        List<double[]> newPoints = resample(points);
        translateToOrigin(newPoints);
        scale(newPoints);

        return newPoints;

    }

    private List<double[]> resample(List<double[]> points) {
        double I = pathLength(points) / (this.cloudSize - 1);
        double D = 0;
        List<double[]> newPoints = new ArrayList<>();
        double[] prevPoint = points.get(0);
        newPoints.add(prevPoint);

        for (int i = 1; i < points.size(); i++) {
            double[] curPoint = points.get(i);
            if (prevPoint[2] == curPoint[2]) {
                double d = ecDistance(prevPoint, curPoint);
                if ((D + d) >= I) {
                    double qx = prevPoint[0] + (I - D) / d * (curPoint[0] - prevPoint[0]);
                    double qy = prevPoint[1] + (I - D) / d * (curPoint[1] - prevPoint[1]);
                    newPoints.add(new double[] { qx, qy, curPoint[2] });
                    points.add(i, new double[] { qx, qy, curPoint[2] });
                    D = 0;
                } else {
                    D += d;
                }
            }
            prevPoint = curPoint;
        }
        if (newPoints.size() == cloudSize - 1) {
            double[] lastPoint = points.get(points.size() - 1);
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

        double scale = Math.max(xmax - xmin, ymax - ymin);
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

}
