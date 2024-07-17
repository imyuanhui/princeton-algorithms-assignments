import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    // initialize instance variables
    private final int n;
    private final boolean[] grid;
    private final WeightedQuickUnionUF uf;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("n must be greater than 0");
        } else {
            this.n = n;
            this.grid= new boolean[n * n];
            this.uf = new WeightedQuickUnionUF(n * n + 2);
        }
    }

    // help make sure if the given index (r, c) is valid
    private boolean isIndexValid(int r, int c) {
        return r > 0 && r <= n && c > 0  && c <= n;
    }

    // help convert (r, c) to index used in grid
    private int convertIndex(int r, int c) {
        return (r - 1) * n + c - 1;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (!isIndexValid(row, col)) {
            throw new IllegalArgumentException("index outside the prescribed range");
        } else {
            // open the site
            int index = convertIndex(row, col);
            grid[index] = true;

            // connect the first row with virtual top
            if (row == 1) {
                uf.union(index, n * n);
            }

            // connect the last row with virtual bottom
            if (row == n) {
                uf.union(index, n * n + 1);
            }

            // connect the new open site to its adjacent open sites
            int[][] possibleAdjSites = {{row - 1, col}, {row + 1, col}, {row, col - 1}, {row, col + 1}};

            for (int[] site : possibleAdjSites) {
                if (isIndexValid(site[0], site[1]) && isOpen(site[0], site[1])) {
                    uf.union(convertIndex(site[0], site[1]), index);
                }
            }
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (!isIndexValid(row, col)) {
            throw new IllegalArgumentException("Index outside the prescribed range");
        } else {
            return grid[convertIndex(row, col)];
        }
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (!isIndexValid(row, col)) {
            throw new IllegalArgumentException("Index outside the prescribed range");
        } else {
            return uf.find(convertIndex(row, col)) == uf.find(n * n);
        }
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        int number = 0;
        for (int i = 0; i < n * n; i++) {
            if (grid[i]) {
                number++;
            }
        }

        return number;
    }

    // does the system percolate?
    public boolean percolates() {
        return uf.find(n * n) == uf.find(n * n + 1);
    }

    public static void main(String[] args) {

    }
}
