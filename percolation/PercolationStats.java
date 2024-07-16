import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    // initialize instance variables
    private final double mean;
    private final double stddev;
    private final int t;
    private final static double confidence95 = 1.96;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException();
        }

        this.t = trials;
        double[] ratio = new double[t];

        for (int i = 0; i < t; i++) {
            Percolation p = new Percolation(n);
            while (!p.percolates()) {
                int randomRow = StdRandom.uniformInt(n) + 1;
                int randomCol = StdRandom.uniformInt(n) + 1;
                p.open(randomRow, randomCol);
            }

            // calculate threshold for the ith trial
            ratio[i] = ((double) p.numberOfOpenSites()) / (n * n);
        }

        this.mean = StdStats.mean(ratio);
        this.stddev = StdStats.stddev(ratio);
    }

    // sample mean of percolation threshold
    public double mean() {
        return mean;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return stddev;
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean - confidence95 * stddev / Math.sqrt(t);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean + confidence95 * stddev / Math.sqrt(t);
    }

    // test client (see below)
    public static void main(String[] args) {
        PercolationStats test = new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
        System.out.println("mean                    = " + test.mean());
        System.out.println("stddev                  = " + test.stddev());
        System.out.println("95% confidence interval = " + "[" + test.confidenceLo() + ", " + test.confidenceHi() + "]");
    }
}
