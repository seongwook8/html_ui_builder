
/**
 * The $Q Super-Quick Recognizer (JavaScript version)
 *
 * Javascript version:
 *
 *  Nathan Magrofuoco
 *  Universite Catholique de Louvain
 *  Louvain-la-Neuve, Belgium
 *  nathan.magrofuoco@uclouvain.be
 *
 * Original $Q authors (C# version):
 *
 *  Radu-Daniel Vatavu, Ph.D.
 *  University Stefan cel Mare of Suceava
 *  Suceava 720229, Romania
 *  radu.vatavu@usm.ro
 *
 *  Lisa Anthony, Ph.D.
 *  Department of CISE
 *  University of Florida
 *  Gainesville, FL, USA 32611
 *  lanthony@cise.ufl.edu
 *
 *  Jacob O. Wobbrock, Ph.D.
 *  The Information School | DUB Group
 *  University of Washington
 *  Seattle, WA, USA 98195-2840
 *  wobbrock@uw.edu
 *
 * The academic publication for the $Q recognizer, and what should be
 * used to cite it, is:
 *
 *    Vatavu, R.-D., Anthony, L. and Wobbrock, J.O. (2018). $Q: A super-quick,
 *    articulation-invariant stroke-gesture recognizer for low-resource devices.
 *    Proceedings of the ACM Conference on Human-Computer Interaction with Mobile
 *    Devices and Services (MobileHCI '18). Barcelona, Spain (September 3-6, 2018).
 *    New York: ACM Press. Article No. 23.
 *    https://dl.acm.org/citation.cfm?id=3229434.3229465
 *
 * This software is distributed under the "New BSD License" agreement:
 *
 * Copyright (c) 2018-2019, Nathan Magrofuoco, Jacob O. Wobbrock, Radu-Daniel Vatavu,
 * and Lisa Anthony. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *    * Redistributions of source code must retain the above copyright
 *      notice, this list of conditions and the following disclaimer.
 *    * Redistributions in binary form must reproduce the above copyright
 *      notice, this list of conditions and the following disclaimer in the
 *      documentation and/or other materials provided with the distribution.
 *    * Neither the names of the University Stefan cel Mare of Suceava,
 *      University of Washington, nor University of Florida, nor the names of its
 *      contributors may be used to endorse or promote products derived from this
 *      software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL Radu-Daniel Vatavu OR Lisa Anthony
 * OR Jacob O. Wobbrock BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT
 * OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 * STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY
 * OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
**/

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;

public class DollarQ {
    private int cloudSize = 128;
    private int lutSize = 128;
    private HashMap<String, PointCloud> templates = new HashMap<>();
    private int templateCnt = 8;

    public DollarQ() {
        List<double[]> temp = new ArrayList<>();
        temp = new ArrayList<>();
        temp.add(new double[] { 10, 10, 1 });
        temp.add(new double[] { 30, 10, 1 });
        temp.add(new double[] { 20, 10, 2 });
        temp.add(new double[] { 20, 40, 2 });
        temp.add(new double[] { 10, 40, 3 });
        temp.add(new double[] { 30, 40, 3 });
        PointCloud I = new PointCloud("img", temp);
        templates.put("img", I);

        temp = new ArrayList<>();
        temp.add(new double[] { 10, 10, 1 });
        temp.add(new double[] { 10, 40, 1 });
        temp.add(new double[] { 10, 30, 1 });
        temp.add(new double[] { 13, 25, 1 });
        temp.add(new double[] { 20, 22, 1 });
        temp.add(new double[] { 25, 25, 1 });
        temp.add(new double[] { 28, 30, 1 });
        temp.add(new double[] { 25, 35, 1 });
        temp.add(new double[] { 20, 40, 1 });
        temp.add(new double[] { 10, 40, 1 });
        PointCloud B = new PointCloud("button", temp);
        templates.put("button", B);

        temp = new ArrayList<>();
        temp.add(new double[] { 10, 10, 1 });
        temp.add(new double[] { 10, 50, 1 });
        temp.add(new double[] { 10, 10, 2 });
        temp.add(new double[] { 20, 10, 2 });
        temp.add(new double[] { 30, 20, 2 });
        temp.add(new double[] { 20, 30, 2 });
        temp.add(new double[] { 10, 30, 2 });
        PointCloud P = new PointCloud("p", temp);
        templates.put("p", P);

        temp = new ArrayList<>();
        temp.add(new double[] { 10, 0, 1 });
        temp.add(new double[] { 10, 40, 1 });
        temp.add(new double[] { 10, 20, 2 });
        temp.add(new double[] { 30, 20, 2 });
        temp.add(new double[] { 30, 0, 3 });
        temp.add(new double[] { 30, 40, 3 });
        PointCloud h = new PointCloud("h", temp);
        templates.put("h", h);

        temp = new ArrayList<>();
        temp.add(new double[] { 10, 10, 1 });
        temp.add(new double[] { 10, 40, 1 });
        temp.add(new double[] { 10, 20, 1 });
        temp.add(new double[] { 18, 12, 1 });
        temp.add(new double[] { 30, 10, 1 });
        PointCloud r = new PointCloud("radio", temp);
        templates.put("radio", r);

        temp = new ArrayList<>();
        temp.add(new double[] { 10, 10, 1 });
        temp.add(new double[] { 10, 40, 1 });
        temp.add(new double[] { 40, 40, 1 });
        temp.add(new double[] { 40, 10, 1 });
        temp.add(new double[] { 10, 10, 1 });
        PointCloud c = new PointCloud("checkbox", temp);
        templates.put("checkbox", c);

        temp = new ArrayList<>();
        temp.add(new double[] { 10, 30, 1 });
        temp.add(new double[] { 10, 40, 1 });
        temp.add(new double[] { 20, 40, 1 });
        temp.add(new double[] { 20, 30, 1 });
        temp.add(new double[] { 10, 30, 1 });
        temp.add(new double[] { 30, 10, 2 });
        temp.add(new double[] { 30, 40, 2 });
        PointCloud o = new PointCloud("ol", temp);
        templates.put("ol", o);

        temp = new ArrayList<>();
        temp.add(new double[] { 10, 10, 1 });
        temp.add(new double[] { 10, 40, 1 });
        temp.add(new double[] { 30, 40, 1 });
        temp.add(new double[] { 30, 10, 1 });
        PointCloud u = new PointCloud("ul", temp);
        templates.put("ul", u);

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

    // private void printList(List<double[]> list) {
    // System.out.println("---------" + list.size() + " points -------------");
    // for (double[] point : list) {
    // System.out.print(point[0] + " ,");
    // System.out.print(point[1] + " ,");
    // System.out.print(point[2] + "\n");
    // }
    // }

    // private void testResample() {
    // List<double[]> list = new ArrayList<>();
    // for (int i = 0; i < 2000; i++) {
    // list.add(new double[] { 0, i, 1 });
    // }
    // List<double[]> sampled = resample(list);
    // printList(list);
    // printList(sampled);
    // }

}
