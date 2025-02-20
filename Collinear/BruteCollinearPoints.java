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

public class BruteCollinearPoints {
    private LineSegment[] lineSegments;

    // finds all line segments containing 4 points
    // Throw an IllegalArgumentException if the argument to the constructor is null,
    // if any point in the array is null,
    // or if the argument to the constructor contains a repeated point
    public BruteCollinearPoints(Point[] points) {
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

        Point[] tmpPoints = new Point[4];
        ArrayList<LineSegment> lineList = new ArrayList<>();
        for (int i = 0; i < points.length - 3; ++i) {
            for (int j = i + 1; j < points.length - 2; ++j) {
                for (int k = j + 1; k < points.length - 1; ++k) {
                    for (int m = k + 1; m < points.length; ++m) {
                        double slope0 = points[i].slopeTo(points[j]);
                        if (slope0 == points[i].slopeTo(points[k]) &&
                                slope0 == points[i].slopeTo(points[m])) {
                            tmpPoints[0] = points[i];
                            tmpPoints[1] = points[j];
                            tmpPoints[2] = points[k];
                            tmpPoints[3] = points[m];
                            Arrays.sort(tmpPoints);
                            lineList.add(new LineSegment(tmpPoints[0], tmpPoints[3]));
                        }
                    }
                }
            }
        }

        lineSegments = lineList.toArray(new LineSegment[0]);
    }

    // the number of line segments
    public int numberOfSegments() {
        return lineSegments.length;
    }

    // the line segments
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
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
