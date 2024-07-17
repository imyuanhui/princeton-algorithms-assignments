import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Board {
    private final int[][] tiles;
    private final int n;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        if (tiles == null || tiles.length < 2 || tiles.length >= 128) {
            throw new IllegalArgumentException("Number of tiles is not valid.");
        }

        int n = tiles.length;
        int spaceCount = 0;
        boolean[] seen = new boolean[n * n];
        for (int i = 0; i < n; i++) {
            if (tiles[i].length != n) {
                throw new IllegalArgumentException("Tiles must form a square matrix.");
            }
            for (int j = 0; j < n; j++) {
                int tile = tiles[i][j];
                if (tile < 0 || tile >= n * n || seen[tile]) {
                    throw new IllegalArgumentException("Tile value is not valid or repeated.");
                }
                seen[tile] = true;
                if (tile == 0) {
                    spaceCount++;
                }
            }
        }

        if (spaceCount != 1) {
            throw new IllegalArgumentException("There must be exactly one blank space.");
        }

        this.tiles = tiles;
        this.n = n;
    }

    // string representation of this board
    public String toString() {
        StringBuilder board = new StringBuilder();
        board.append(n).append("\n");

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                board.append(String.format("%2d ", tiles[i][j]));
            }
            board.append("\n");
        }
        return board.toString();
    }

    // board dimension n
    public int dimension() {
        return n;
    }

    // number of tiles out of place
    public int hamming() {
        int hamming = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (tiles[i][j] != 0 && tiles[i][j] != n * i + j + 1) {
                    hamming++;
                }
            }
        }
        return hamming;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int manhattan = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int tile = tiles[i][j];
                if (tile != 0 && tile != n * i + j + 1) {
                    int expectedRow = tile / n;
                    int expectedCol = tile % n - 1;
                    manhattan += Math.abs(expectedRow - i) + Math.abs(expectedCol - j);
                }
            }
        }
        return manhattan;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return this.hamming() == 0;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (this == y) return true;

        if (y == null || this.getClass() != y.getClass()) return false;

        Board board = (Board) y;
        return this.n == board.n && Arrays.deepEquals(this.tiles, board.tiles);
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        List<Board> neighbors = new ArrayList<>();

        // find the position of space tile
        int spaceRow = 0;
        int spaceCol = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (tiles[i][j] == 0) {
                    spaceRow = i;
                    spaceCol = j;
                    break;
                }
            }
        }

        int[][] directions =  { {0, 1}, {1, 0}, {0, -1}, {-1, 0} };
        for (int[] direction : directions) {
            int newSpaceRow = spaceRow + direction[0];
            int newSpaceCol = spaceCol + direction[1];
            if (newSpaceRow >= 0 && newSpaceRow < n && newSpaceCol >= 0 && newSpaceCol < n) {
                int[][] newTiles = this.copyTiles();
                newTiles[spaceRow][spaceCol] = newTiles[newSpaceRow][newSpaceCol];
                newTiles[newSpaceRow][newSpaceCol] = 0;
                neighbors.add(new Board(newTiles));
            }
        }

        return neighbors;
    }

    // cope the tiles array
    private int[][] copyTiles() {
        int[][] copy = new int[n][n];
        for (int i = 0; i < n; i++) {
            copy[i] = Arrays.copyOf(tiles[i], n);
        }
        return copy;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        int[][] newTiles = this.copyTiles();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n - 1; j++) {
                if (newTiles[i][j] != 0 && newTiles[i][j + 1] != 0) {
                    int temp = newTiles[i][j];
                    newTiles[i][j] = newTiles[i][j + 1];
                    newTiles[i][j + 1] = temp;
                    return new Board(newTiles);
                }
            }
        }
        return null;
    }

    // unit testing (not graded)
    public static void main(String[] args) {

        // initialize goal board and current board
        int[][] goalTiles = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 0}
        };

        int[][] tiles = {
                {6, 5, 3},
                {4, 2, 8},
                {7, 1, 0}
        };

        Board goalBoard = new Board(goalTiles);
        Board board = new Board(tiles);


        System.out.println(goalBoard);
        System.out.println("Goal board hamming: " + goalBoard.hamming());
        System.out.println("Goal board manhattan: " + goalBoard.manhattan());
        System.out.println("Is goal: " + goalBoard.isGoal());
        System.out.println("Neighbors:");
        for (Board neighbor : goalBoard.neighbors()) {
            System.out.println(neighbor);
        }
        System.out.println("Twin:\n" + goalBoard.twin());

        System.out.println(board);
        System.out.println("Goal board hamming: " + board.hamming());
        System.out.println("Goal board manhattan: " + board.manhattan());
        System.out.println("Is goal: " + board.isGoal());
        System.out.println("Neighbors:");
        for (Board neighbor : board.neighbors()) {
            System.out.println(neighbor);
        }
        System.out.println("Twin:\n" + board.twin());
        System.out.println("Is equal to goal board: " + board.equals(goalBoard));
    }
}
