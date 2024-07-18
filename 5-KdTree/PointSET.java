import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;

import java.util.ArrayList;
import java.util.List;

public class PointSET {
    private final SET<Point2D> set;
    private int size;

    // construct an empty set of points
    public PointSET() {
        set = new SET<>();
        size = 0;
    }

    // check if the set is empty
    public boolean isEmpty() {
        return size == 0;
    }

    // number of points in the set
    public int size() {
        return size;
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException("Point should not be null.");
        }

        if (size == 0 || !set.contains(p)) {
            set.add(p);
            size++;
        }
    }

    // check if the set contain point p
    public boolean contains(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException("Point should not be null.");
        }

        return size != 0 && set.contains(p);
    }

    // draw all points to standard draw
    public void draw() {
        if (size != 0) {
            for (Point2D p : set) {
                p.draw();
            }
        }
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) {
            throw new IllegalArgumentException("Rectangle should not be null.");
        }

        List<Point2D> pointsInside = new ArrayList<>();
        for (Point2D p : set) {
            if (rect.contains(p)) {
                pointsInside.add(p);
            }
        }
        return pointsInside;
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException("Point should not be null.");
        }

        if (size == 0) return null;

        double shortestDis = 0;
        double x = p.x();
        double y = p.y();
        for (Point2D q : set) {
            double currDis = p.distanceSquaredTo(q);
            if (shortestDis == 0 || currDis < shortestDis) {
                shortestDis = currDis;
                x = q.x();
                y = q.y();
            }
        }
        return new Point2D(x, y);
    }

    public static void main(String[] args) {

    }
}
