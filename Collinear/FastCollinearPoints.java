/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {
    private LineSegment[] lineSegments;

    public FastCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException("points is null");
        }

        for (int i = 0; i < points.length; ++i) {
            if (points[i] == null) {
                throw new IllegalArgumentException("point is null");
            }
            for (int j = i + 1; j < points.length; ++j) {
                if (points[i].equals(points[j])) {
                    throw new IllegalArgumentException("point is expect");
                }
            }
        }

        if (points.length < 4) {
            lineSegments = new LineSegment[0];
            return;
        }

        ArrayList<LineSegment> lineList = new ArrayList<>();
        Point[] tmpPoints = Arrays.copyOf(points, points.length);
        for (int i = 0; i < points.length; ++i) {
            // 以当前点为原点，根据斜率进行排序
            Arrays.sort(tmpPoints, points[i].slopeOrder());

            // 查找与p点斜率相同的点
            int count = 0;
            // 定义相同的点斜率为负无穷，因此从第二个开始
            double tmpSlope = points[i].slopeTo(tmpPoints[1]);
            for (int j = 1; j < points.length; ++j) {
                if (tmpSlope == tmpPoints[j].slopeTo(points[i])) {
                    // 斜率连续相等时计数器增加
                    count++;
                }
                else {
                    if (count >= 3) {
                        Arrays.sort(tmpPoints, j - count, j);
                        // 若当前点不在最左下角，不添加，运行到最左下角的点才会添加
                        if (points[i].compareTo(tmpPoints[j - count]) < 0) {
                            lineList.add(new LineSegment(points[i], tmpPoints[j - 1]));
                        }
                    }
                    // 若是出现斜率不相等，则重新计数
                    tmpSlope = points[i].slopeTo(tmpPoints[j]);
                    count = 1;
                }
            }
            if (count >= 3) {
                Arrays.sort(tmpPoints, tmpPoints.length - count, tmpPoints.length);
                if (points[i].compareTo(tmpPoints[tmpPoints.length - count]) < 0) {
                    lineList.add(new LineSegment(points[i], tmpPoints[tmpPoints.length - 1]));
                }
            }
        }

        lineSegments = lineList.toArray(new LineSegment[0]);
    }

    public int numberOfSegments() {
        return lineSegments.length;
    }

    public LineSegment[] segments() {
        return lineSegments.clone();
    }

    public static void main(String[] args) {
        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
