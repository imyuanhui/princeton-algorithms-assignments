import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] s;
    private int size;

    // construct an empty randomized queue
    public RandomizedQueue() {
        s = (Item[]) new Object[2];
        size = 0;
    }

    // resize when the capacity is not enough to store one more item
    private void resize(int capacity) {
        Item[] copy = (Item[]) new Object[capacity];
        System.arraycopy(s, 0, copy, 0, size);
        s = copy;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return size;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("item should not be null");
        }
        if (size == s.length) {
            resize(s.length * 2);
        }
        s[size++] = item;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException("the queue is empty");
        }
        int randomIndex = StdRandom.uniformInt(size);
        Item itemToRemove = s[randomIndex];
        s[randomIndex] = s[size - 1];
        s[size - 1] = null;
        size--;

        return itemToRemove;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException("the queue is empty");
        }
        return s[StdRandom.uniformInt(size)];
    }

    // an iterator over items in random order
    private class RandomizedQueueIterator implements Iterator<Item> {
        private final Item[] shuffledArray;
        private int index;

        public RandomizedQueueIterator() {
            shuffledArray = (Item[]) new Object[size];
            System.arraycopy(s, 0, shuffledArray, 0, size);
            StdRandom.shuffle(shuffledArray);
            index = 0;
        }

        public boolean hasNext() {
            return index < size;
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException("No more elements");
            }
            return shuffledArray[index++];
        }

        public void remove() {
            throw new UnsupportedOperationException("remove() is not supported");
        }
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    public static void main(String[] args) {
        // test constructor
        RandomizedQueue<Integer> randomQ = new RandomizedQueue<>();
        System.out.println(randomQ.isEmpty());
        System.out.println(randomQ.size());

        // test enqueue
        for (int i = 0; i < 4; i++) {
            randomQ.enqueue(i);
        }
        System.out.println(randomQ.isEmpty());
        System.out.println(randomQ.size());

        // test dequeue
        for (int i = 0; i < 4; i++) {
            System.out.println(randomQ.dequeue());
        }
        System.out.println(randomQ.isEmpty());
        System.out.println(randomQ.size());

        // test resize
        for (int i = 0; i < 10; i++) {
            randomQ.enqueue(i);
        }
        System.out.println(randomQ.size());

        // test sample
        for (int i = 0; i < 10; i++) {
            System.out.println(randomQ.sample());
        }
        System.out.println(randomQ.size());
    }
}
