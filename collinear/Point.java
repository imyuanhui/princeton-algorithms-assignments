/******************************************************************************
 *  Compilation:  javac Point.java
 *  Execution:    java Point
 *  Dependencies: none
 *  An immutable data type for points in the plane.
 *  For use on Coursera, Algorithms Part I programming assignment.
 *
 ******************************************************************************/

import edu.princeton.cs.algs4.StdDraw;

import java.util.Arrays;
import java.util.Comparator;

public class Point implements Comparable<Point> {

    private final int x;     // x-coordinate of this point
    private final int y;     // y-coordinate of this point

    /**
     * Initializes a new point.
     *
     * @param  x the <em>x</em>-coordinate of the point
     * @param  y the <em>y</em>-coordinate of the point
     */
    public Point(int x, int y) {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
    }

    /**
     * Draws this point to standard draw.
     */
    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    /**
     * Draws the line segment between this point and the specified point
     * to standard draw.
     *
     * @param that the other point
     */
    public void drawTo(Point that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    /**
     * Returns the slope between this point and the specified point.
     * Formally, if the two points are (x0, y0) and (x1, y1), then the slope
     * is (y1 - y0) / (x1 - x0). For completeness, the slope is defined to be
     * +0.0 if the line segment connecting the two points is horizontal;
     * Double.POSITIVE_INFINITY if the line segment is vertical;
     * and Double.NEGATIVE_INFINITY if (x0, y0) and (x1, y1) are equal.
     *
     * @param  that the other point
     * @return the slope between this point and the specified point
     */
    public double slopeTo(Point that) {
        /* YOUR CODE HERE */
        if (that.x == this.x) {
            if (that.y != this.y) {
                return Double.POSITIVE_INFINITY;
            } else {
                return Double.NEGATIVE_INFINITY;
            }
        } else if (that.y == this.y) {
            return 0.0;
        } else {
            return (double) (that.y - this.y) / (that.x - this.x);
        }
    }

    /**
     * Compares two points by y-coordinate, breaking ties by x-coordinate.
     * Formally, the invoking point (x0, y0) is less than the argument point
     * (x1, y1) if and only if either y0 < y1 or if y0 = y1 and x0 < x1.
     *
     * @param  that the other point
     * @return the value <tt>0</tt> if this point is equal to the argument
     *         point (x0 = x1 and y0 = y1);
     *         a negative integer if this point is less than the argument
     *         point; and a positive integer if this point is greater than the
     *         argument point
     */
    public int compareTo(Point that) {
        /* YOUR CODE HERE */
        if (this.y == that.y) {
            return Integer.compare(this.x, that.x);
        } else {
            return Integer.compare(this.y, that.y);
        }
    }

    /**
     * Compares two points by the slope they make with this point.
     * The slope is defined as in the slopeTo() method.
     *
     * @return the Comparator that defines this ordering on points
     */
    public Comparator<Point> slopeOrder() {
        /* YOUR CODE HERE */
        class BySlope implements Comparator<Point> {
            public int compare(Point p1, Point p2) {
                double slope1 = slopeTo(p1);
                double slope2 = slopeTo(p2);
                return Double.compare(slope1, slope2);
            }
        }
        return new BySlope();
    }

    /**
     * Returns a string representation of this point.
     * This method is provide for debugging;
     * your program should not rely on the format of the string representation.
     *
     * @return a string representation of this point
     */
    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }

    /**
     * Unit tests the Point data type.
     */
    public static void main(String[] args) {
        /* YOUR CODE HERE */
        // Initialize different points
        Point p1 = new Point(1, 2);
        Point p2 = new Point(1, 3);
        Point p3 = new Point(2, 2);
        Point p4 = new Point(1, 2);
        Point edgePoint1 = new Point(0, 0);
        Point edgePoint2 = new Point(32767, 32767);

        // Test drawTo()
        p1.drawTo(p2); // vertical line
        p1.drawTo(p3); // horizontal line
        p1.drawTo(p4); // same point
        p2.drawTo(p3); // common case
        p1.drawTo(edgePoint1); // corner case
        p1.drawTo(edgePoint2); // corner case

        // Test compareTo()
        assert p3.compareTo(p2) < 0 : "Error in compareTo()";
        assert p1.compareTo(p3) < 0 : "Error in compareTo()";
        assert p1.compareTo(p4) == 0 : "Error in compareTo()";
        assert p1.compareTo(edgePoint1) > 0 : "Error in compareTo()";
        assert p1.compareTo(edgePoint2) < 0 : "Error in compareTo()";

        // Test slopeTo()
        double slope1_2 = p1.slopeTo(p2);
        double slope1_3 = p1.slopeTo(p3);
        double slope1_4 = p1.slopeTo(p4);
        double slope3_2 = p3.slopeTo(p2);
        double slope1_0 = p1.slopeTo(edgePoint1);

        assert slope1_2 == Double.POSITIVE_INFINITY : "Error in slopeTo() with vertical line";
        assert slope1_3 == 0.0 : "Error in slopeTo() with horizontal line";
        assert slope1_4 == Double.NEGATIVE_INFINITY : "Error in slopeTo() with same point";
        assert slope3_2 == -1.0 : "Error in slopeTo()";
        assert slope1_0 == 2.0 : "Error in slopeTo()";

        // Test slopeOrder()
        Point[] points = {edgePoint1, p2, p3, p4, edgePoint2};
        Arrays.sort(points, p1.slopeOrder());
        System.out.println("Points sorted by slopeOrder relative to p1:");
        for (Point p : points) {
            System.out.println("Point" + p.toString() + "slope: " + p1.slopeTo(p));
        }
    }
}
