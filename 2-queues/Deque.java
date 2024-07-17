import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private Item[] array;
    private int size;
    private int head;
    private int tail;

    // construct an empty deque
    public Deque() {
        array = (Item[]) new Object[2];
        size = 0;
        head = 0;
        tail = 1;
    }

    // resize when the capacity is not enough to store one more item
    private void resize(int capacity, int flag, boolean isFlagHead) {
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < size; i++) {
            copy[i + 1] = array[flag];
            flag = isFlagHead ? nextTail(flag) : nextHead(flag);
        }
        array = copy;
        head = 0;
        tail = size + 1;
    }

    // help determine the index of next head
    private int nextHead(int h) {
        return (h == 0) ? array.length - 1 : h - 1;
    }

    // help determine the index of next tail
    private int nextTail(int t) {
        return (t == array.length - 1) ? 0 : t + 1;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("item should not be null");
        }

        if (size == array.length) {
            resize(array.length * 2, nextTail(head), true);
        } else if (array[head] != null) {
            head = nextHead(head);
        }

        array[head] = item;
        head = nextHead(head);
        size++;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("item should not be null");
        }

        if (size == array.length) {
            resize(array.length * 2, nextHead(tail), false);
        } else if (array[tail] != null) {
            tail = nextTail(tail);
        }

        array[tail] = item;
        tail = nextTail(tail);
        size++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException("deque is empty");
        }

        head = nextTail(head);
        Item first = array[head];
        array[head] = null;
        size--;
        return first;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException("deque is empty");
        }

        tail = nextHead(tail);
        Item last = array[tail];
        array[tail] = null;
        size--;
        return last;
    }

    // an iterator over items in order from front to back
    private class DequeIterator implements Iterator<Item> {
        private int current = head;
        private int remaining = size;

        public boolean hasNext() {
            return remaining > 0;
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException("deque has no next item");
            }
            Item item = array[current];
            current = nextTail(current);
            remaining--;
            return item;
        }

        public void remove() {
            throw new UnsupportedOperationException("not support remove() operation");
        }
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    // unit testing (required)
    public static void main(String[] args) {


        // test constructor
        System.out.println("===test constructor===");
        Deque<String> stringDeque = new Deque<>();
        System.out.println(stringDeque.size());
        System.out.println(stringDeque.isEmpty());

        // test addFirst and addLast
        System.out.println("===test add item===");
        stringDeque.addFirst("a");
        stringDeque.addLast("b");
        System.out.println(stringDeque.size());
        System.out.println(stringDeque.isEmpty());

        // test removeFirst and RemoveLat
        System.out.println("===test remove item===");
        System.out.println(stringDeque.removeFirst());
        System.out.println(stringDeque.removeLast());
        System.out.println(stringDeque.size());
        System.out.println(stringDeque.isEmpty());

/*
        System.out.println("===test on addFirst, removeFirst, isEmpty===");
        Deque<Integer> intDeque = new Deque<>();
        for (int i = 0; i < 50; i++) {
            System.out.println("test" + (i+1) + "th");
            if (StdRandom.bernoulli(0.8)) {
                System.out.println("add " + i);
                intDeque.addFirst(i);
            } else if (StdRandom.bernoulli(0.5)) {
                System.out.println("remove " + intDeque.removeFirst());

            } else {
                System.out.println("is deque empty? " + intDeque.isEmpty());
            }
        }


        System.out.println("===test on addFirst, removeLast, isEmpty===");
        Deque<Integer> intDeque1 = new Deque<>();
        for (int i = 0; i < 50; i++) {
            System.out.println("test" + i + "th");
            if (StdRandom.bernoulli(0.8)) {
                System.out.println("*add " + i);
                intDeque1.addFirst(i);
                System.out.println("  head: " + intDeque1.head);
                System.out.println("  tail: " + intDeque1.tail);

            } else if (StdRandom.bernoulli(0.5)) {
                System.out.println("*remove " + intDeque1.removeLast());
                System.out.println("  head: " + intDeque1.head);
                System.out.println("  tail: " + intDeque1.tail);
            } else {
                System.out.println("is deque empty? " + intDeque1.isEmpty());
            }
        }
*/
    }
}
