import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {

    private final RandomizedQueue<String> randomizedQueue;
    private final int k;

    private Permutation(int k) {
        this.k = k;
        randomizedQueue = new RandomizedQueue<>();

        // Read all strings into the randomizedQueue
        while (!StdIn.isEmpty()) {
            randomizedQueue.enqueue(StdIn.readString());
        }
    }

    private void printRandomStrings() {
        // Print k strings from the randomizedQueue
        for (int i = 0; i < k; i++) {
            StdOut.println(randomizedQueue.dequeue());
        }
    }

    public static void main(String[] args) {
        if (args.length != 1) {
            throw new IllegalArgumentException("Usage: java Permutation k");
        }

        int k = Integer.parseInt(args[0]);
        Permutation permutation = new Permutation(k);
        permutation.printRandomStrings();
    }
}
