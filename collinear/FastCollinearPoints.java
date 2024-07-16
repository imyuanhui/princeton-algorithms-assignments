import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FastCollinearPoints {
    private final List<LineSegment> segments = new ArrayList<>();

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {
        if (points == null) throw new IllegalArgumentException("Argument cannot be null");
        for (Point point : points) {
            if (point == null) throw new IllegalArgumentException("Point cannot be null");
        }
        for (int i = 0; i < points.length - 1; i++) {
            for (int j = i + 1; j < points.length; j++) {
                if (points[i].equals(points[j])) throw new IllegalArgumentException("Argument should not contain same points");
            }
        }

        Point [] copy = points.clone();
        Arrays.sort(copy);
        Arrays.sort(copy, copy[0].slopeOrder());

        for (int i = 0; i < copy.length - 1; i++) {
            Point origin = copy[i];
            List<Point> collinearPoints = new ArrayList<>();
            collinearPoints.add(origin);
            collinearPoints.add(copy[i + 1]);
            double previousSlope = origin.slopeTo(copy[i + 1]);
            for (int j = i + 2; j < copy.length; j++) {
                double currentSlope = origin.slopeTo(copy[j]);
                if (currentSlope == previousSlope) {
                    collinearPoints.add(copy[j]);
                } else {
                    if (collinearPoints.size() >= 4) {
                        addSegment(collinearPoints);
                    }
                    collinearPoints.clear();
                    collinearPoints.add(origin);
                    collinearPoints.add(copy[j]);
                    previousSlope = currentSlope;
                }
            }
        }
    }

    private void addSegment(List<Point> collinearPoints) {
        if (collinearPoints.size() >= 4) {
            Point[] collinearArray = collinearPoints.toArray(new Point[0]);
            Arrays.sort(collinearArray);
            segments.add(new LineSegment(collinearArray[0], collinearArray[collinearArray.length - 1]));
        }
    }

    public int numberOfSegments() {
        return segments.size();
    }
    public LineSegment[] segments() {
        return segments.toArray(new LineSegment[0]);
    }
    public static void main(String[] args) {
        Point[] points = {
                new Point(10000, 0), new Point(0, 10000), new Point(3000, 7000),
                new Point(7000, 3000), new Point(20000, 21000), new Point(3000, 4000),
                new Point(14000, 15000), new Point(6000, 7000)
        };

        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            System.out.println(segment);
            segment.draw();
        }
    }
}
