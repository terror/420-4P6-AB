package ca.qc.johnabbott.cs4p6.collections;

import com.sun.jdi.ObjectReference;

import java.util.Arrays;
import java.util.Objects;

/**
 * TODO
 */
public class SortedSet<T extends Comparable<T>> implements Set<T> {

    private static final int DEFAULT_CAPACITY = 100;

    private T[] elements;
    private int size;
    private int ptr;

    public SortedSet() {
        this(DEFAULT_CAPACITY);
    }

    public SortedSet(int capacity) {
        this.size = 0;
        this.elements = (T[]) new Comparable[capacity];
    }

    // Standard insertion sort
    private void sort() {
        if (isEmpty()) return;
        for (int i = 1; i < size; ++i) {
            T key = elements[i];
            int j = i - 1;
            while (j >= 0 && (key.compareTo(elements[j]) < 0)) {
                // swap;
                elements[j + 1] = elements[j--];
                elements[j + 1] = key;
            }
        }
    }

    public void swap(int a, int b) {
        T t = elements[a];
        elements[a] = elements[b];
        elements[b] = t;
    }

    private void shiftNull(int idx) {
        for(int i = idx; i < elements.length-1; ++i) {
            if (elements[i+1] == null) break;
            swap(i, i+1);
        }
    }

    @Override
    public boolean contains(T elem) {
        // elements is sorted, so we can binary search for the element.
        return Arrays.binarySearch(elements, 0, size, elem) >= 0;
    }

    @Override
    public boolean containsAll(Set<T> rhs) {
        while (rhs.hasNext()) {
            T el = rhs.next();
            // binary search for desired element
            if (Arrays.binarySearch(elements, 0, size, el) < 0) return false;
        }
        rhs.reset();
        return true;
    }

    @Override
    public boolean add(T elem) {
        // check if full
        if (size == elements.length) throw new FullSetException();

        // are we currently traversing?
        if (ptr != 0) throw new TraversalException();

        // check for a duplicate
        for (int i = 0; i < elements.length; ++i)
            if (!Objects.isNull(elements[i]) && elements[i].equals(elem)) return false;

        // we can add
        for (int i = 0; i < elements.length; ++i) {
            if (Objects.isNull(elements[i])) {
                elements[i] = elem;
                ++size;
                break;
            }
        }

        sort();
        return true;
    }

    @Override
    public boolean remove(T elem) {
        if (isEmpty()) return false;

        for (int i = 0; i < size; ++i) {
            // remove if found
            if (elements[i].equals(elem)) {
                elements[i] = null;
                --size;

                // shift the null created
                shiftNull(i);
                return true;
            }
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * TODO
     *
     * @return
     */
    public T min() {
        if (isEmpty()) throw new EmptySetException();
        return elements[0];
    }

    /**
     * TODO
     *
     * @return
     */
    public T max() {
        if (isEmpty()) throw new EmptySetException();
        return elements[size - 1];
    }

    /**
     * TODO
     *
     * @param first
     * @param last
     * @return
     */
    public SortedSet<T> subset(T first, T last) {
        // edge cases
        if (first.equals(last) || isEmpty()) return new SortedSet<T>();
        if (first.compareTo(last) > 0) throw new IllegalArgumentException();

        SortedSet<T> ret = new SortedSet<>();
        for (int i = 0; i < size; ++i) {
            if (elements[i].compareTo(first) >= 0 && elements[i].compareTo(last) < 0)
                ret.add(elements[i]);
        }

        ret.sort();
        return ret;
    }

    @Override
    public boolean isFull() {
        return size == elements.length;
    }

    @Override
    public String toString() {
        String ret = "{";
        for (int i = 0; i < size; ++i) {
            if (i != size - 1)
                ret += elements[i].toString() + ", ";
            else
                ret += elements[i].toString();
        }
        return ret + "}";
    }

    @Override
    public void reset() {
        ptr = 0;
    }

    @Override
    public T next() {
        if (!hasNext()) throw new TraversalException();
        return elements[ptr++];
    }

    @Override
    public boolean hasNext() {
        if (isEmpty()) return false;
        return !Objects.isNull(elements[ptr]);
    }
}
