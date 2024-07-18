import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.util.ArrayList;
import java.util.List;

public class KdTree {
    private static class Node {
        private final Point2D point;
        private Node left;
        private Node right;
        private final boolean xCompare;

        public Node(Point2D p, boolean xCompare) {
            this.point = p;
            this.xCompare = xCompare;
        }
    }

    private Node root;
    private int size;

    public KdTree() {
        root = null;
        size = 0;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void insert(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException("Point should not be null.");
        }

        root = insert(root, p, true);
        size++;
    }

    private Node insert(Node node, Point2D p, boolean xCompare) {
        if (node == null) return new Node(p, xCompare);

        if (node.point.equals(p)) return node;

        if (node.xCompare) {
            if (p.x() < node.point.x()) {
                node.left = insert(node.left, p, !xCompare);
            } else {
                node.right = insert(node.right, p, !xCompare);
            }
        } else {
            if (p.y() < node.point.y()) {
                node.left = insert(node.left, p, !xCompare);
            } else {
                node.right = insert(node.right, p, !xCompare);
            }
        }
        return node;
    }

    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException("Point should not be null.");
        if (size == 0) return false;
        return compare(root, p);
    }

    private boolean compare(Node node, Point2D p) {
        if (node == null) return false;
        if (node.point.equals(p)) return true;

        if (node.xCompare) {
            if (p.x() < node.point.x()) {
                return compare(node.left, p);
            } else {
                return compare(node.right, p);
            }
        } else {
            if (p.y() < node.point.y()) {
                return compare(node.left, p);
            } else {
                return compare(node.right, p);
            }
        }
    }

    public void draw() {
        RectHV unitSquare = new RectHV(0.0, 0.0, 1.0, 1.0);
        unitSquare.draw();
        draw(root, 0.0, 0.0, 1.0, 1.0);
    }

    private void draw(Node node, double xmin, double ymin, double xmax, double ymax) {
        if (node == null) return;

        node.point.draw();

        if (node.xCompare) {
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.line(node.point.x(), ymin, node.point.x(), ymax);
            draw(node.left, xmin, ymin, node.point.x(), ymax);
            draw(node.right, node.point.x(), ymin, xmax, ymax);
        } else {
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.line(xmin, node.point.y(), xmax, node.point.y());
            draw(node.left, xmin, ymin, xmax, node.point.y());
            draw(node.right, xmin, node.point.y(), xmax, ymax);
        }
    }

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) {
            throw new IllegalArgumentException("Rectangle should not be null.");
        }

        List<Point2D> pointsInside = new ArrayList<>();
        range(root, rect, pointsInside);

        return pointsInside;
    }

    private void range(Node node, RectHV rect, List<Point2D> result) {
        if (node == null) return;

        if (rect.contains(node.point)) {
            result.add(node.point);
        }

        if (node.xCompare) {
            if (rect.xmin() <= node.point.x()) {
                range(node.left, rect, result);
            }
            if (rect.xmax() >= node.point.x()) {
                range(node.right, rect, result);
            }
        } else {
            if (rect.ymin() <= node.point.y()) {
                range(node.left, rect, result);
            }
            if (rect.ymax() >= node.point.y()) {
                range(node.right, rect, result);
            }
        }
    }

    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException("Point should not be null.");
        return nearest(root, p, root.point);
    }

    private Point2D nearest(Node node, Point2D p, Point2D nearest) {
        if (node == null) return nearest;

        if (node.point.distanceSquaredTo(p) < nearest.distanceSquaredTo(p)) {
            nearest = node.point;
        }

        if (node.xCompare) {
            if (p.x() < node.point.x()) {
                nearest = nearest(node.left, p, nearest);
                if (node.right != null && Math.abs(node.point.x() - p.x()) < nearest.distanceSquaredTo(p)) {
                    nearest = nearest(node.right, p, nearest);
                }
            } else {
                nearest = nearest(node.right, p, nearest);
                if (node.left != null && Math.abs(node.point.x() - p.x()) < nearest.distanceSquaredTo(p)) {
                    nearest = nearest(node.left, p, nearest);
                }
            }
        } else {
            if (p.y() < node.point.y()) {
                nearest = nearest(node.left, p, nearest);
                if (node.right != null && Math.abs(node.point.y() - p.y()) < nearest.distanceSquaredTo(p)) {
                    nearest = nearest(node.right, p, nearest);
                }
            } else {
                nearest = nearest(node.right, p, nearest);
                if (node.left != null && Math.abs(node.point.y() - p.y()) < nearest.distanceSquaredTo(p)) {
                    nearest = nearest(node.left, p, nearest);
                }
            }
        }
        return nearest;
    }

    public static void main(String[] args) {
    }
}
