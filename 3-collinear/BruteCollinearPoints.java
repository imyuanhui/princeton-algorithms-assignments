import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BruteCollinearPoints {
    private final List<LineSegment> segments = new ArrayList<>();

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
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

        for (int i = 0; i < copy.length; i++) {
            for (int j = i + 1; j < copy.length; j++) {
                for (int k = j + 1; k < copy.length; k++) {
                    for (int l = k + 1; l < copy.length; l++) {
                        Point p = copy[i];
                        Point q = copy[j];
                        Point r = copy[k];
                        Point s = copy[l];

                        if (p.slopeTo(q) == p.slopeTo(r) && p.slopeTo(r) == p.slopeTo(s)) {
                            segments.add(new LineSegment(p, s));
                        }
                    }
                }
            }
        }

    }
    // the number of line segments
    public int numberOfSegments() {
        return segments.size();
    }

    // the line segments
    public LineSegment[] segments() {
        return segments.toArray(new LineSegment[0]);
    }
    public static void main(String[] args) {
        // test
        Point[] points = {
                new Point(10000, 0), new Point(0, 10000), new Point(3000, 7000),
                new Point(7000, 3000), new Point(20000, 21000), new Point(3000, 4000),
                new Point(14000, 15000), new Point(6000, 7000)
        };

        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            System.out.println(segment.toString());
            segment.draw();
        }
    }
}
