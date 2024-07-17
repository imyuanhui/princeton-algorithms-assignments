import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Solver {
    private final List<Board> solutions = new ArrayList<>();
    private final boolean solvable;
    private final int moves;

    // search node class for the A* algorithm
    private static class SearchNode {
        private final Board board;
        private final int moves;
        private final SearchNode previous;

        public SearchNode(Board board, int moves, SearchNode previous) {
            this.board = board;
            this.moves = moves;
            this.previous = previous;
        }
    }

    // comparator for the priority queue
    private static class SearchNodeComparator implements Comparator<SearchNode> {
        public int compare(SearchNode o1, SearchNode o2) {
            return Integer.compare(o1.board.manhattan() + o1.moves, o2.board.manhattan() + o2.moves);
        }
    }

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) {
            throw new IllegalArgumentException("Argument should not be null.");
        }

        MinPQ<SearchNode> pq = new MinPQ<>(new SearchNodeComparator());
        pq.insert(new SearchNode(initial, 0, null));
        MinPQ<SearchNode> twinPQ = new MinPQ<>(new SearchNodeComparator());
        twinPQ.insert(new SearchNode(initial.twin(), 0, null));

        SearchNode goalNode = null;

        while (!pq.isEmpty() && !twinPQ.isEmpty()) {
            goalNode = solve(pq);
            if (goalNode != null || solve(twinPQ) != null) break;
        }

        if (goalNode != null) {
            this.solvable = true;
            this.moves = goalNode.moves;
            for (SearchNode node = goalNode; node != null; node = node.previous) {
                solutions.add(0, node.board);
            }
        } else {
            this.solvable = false;
            this.moves = -1;
        }
    }

    // helper method to solve the puzzle using priority queue
    private SearchNode solve(MinPQ<SearchNode> pq) {
        if (pq.isEmpty()) return null;

        SearchNode currentNode = pq.delMin();

        if (currentNode.board.isGoal()) return currentNode;

        for (Board neighbor : currentNode.board.neighbors()) {
            if (currentNode.previous == null || !neighbor.equals(currentNode.previous.board)) {
                pq.insert(new SearchNode(neighbor, currentNode.moves + 1, currentNode));
            }
        }

        return null;
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return solvable;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        return moves;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        return solvable ? solutions : null;
    }

    // test client (see below)
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}
