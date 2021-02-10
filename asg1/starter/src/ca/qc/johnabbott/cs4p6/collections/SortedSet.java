package ca.qc.johnabbott.cs4p6.collections;

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
    private boolean modified;

    public SortedSet() {
        this(DEFAULT_CAPACITY);
    }

    public SortedSet(int capacity) {
        this.size = 0;
        this.elements = (T[]) new Comparable[capacity];
    }

    // Insertion sort
    private void sort() {
        // check if empty
        if (isEmpty()) return;

        // sort
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

    // Swap elements who reside at index a, b
    public void swap(int a, int b) {
        T t = elements[a];
        elements[a] = elements[b];
        elements[b] = t;
    }

    // Shift a created null to sit before an existing one
    private void shiftNull(int idx) {
        for (int i = idx; i < elements.length - 1; ++i) {
            if (elements[i + 1] == null) break;
            swap(i, i + 1);
        }
    }

    @Override
    public boolean contains(T elem) {
        // elements is sorted, so we can binary search for the element.
        return Arrays.binarySearch(elements, 0, size, elem) >= 0;
    }

    @Override
    public boolean containsAll(Set<T> rhs) {
        rhs.reset();
        while (rhs.hasNext()) {
            T el = rhs.next(); // current element

            // binary search for desired element
            if (Arrays.binarySearch(elements, 0, size, el) < 0) return false;
        }
        rhs.reset();
        return true;
    }

    @Override
    public boolean add(T elem) {
        // check if full
        if (isFull()) throw new FullSetException();

        // check for a duplicate
        if (Arrays.binarySearch(elements, 0, size, elem) >= 0) return false;

        // we can add
        elements[size++] = elem;
        modified = true;

        sort();
        return true;
    }

    @Override
    public boolean remove(T elem) {
        if (isEmpty()) return false;

        // first check if element exists within set
        if (Arrays.binarySearch(elements, 0, size, elem) < 0) return false;

        // scan for the elements index
        for (int i = 0; i < size; ++i) {
            // "remove" the element when found
            if (elements[i].equals(elem)) {
                elements[i] = null;
                --size;
                modified = true;

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
     * Gets the minimum element from the set.
     *
     * @return T
     */
    public T min() {
        if (isEmpty()) throw new EmptySetException();
        return elements[0];
    }

    /**
     * Gets the maximum element from the set.
     *
     * @return T
     */
    public T max() {
        if (isEmpty()) throw new EmptySetException();
        return elements[size - 1];
    }

    /**
     * Returns a subset in which whose elements fall between the range [first, last[
     *
     * @param first
     * @param last
     * @return SortedSet<T>
     */
    public SortedSet<T> subset(T first, T last) {
        // edge cases
        if (first.equals(last) || isEmpty()) return new SortedSet<T>();
        if (first.compareTo(last) > 0) throw new IllegalArgumentException();

        SortedSet<T> ret = new SortedSet<>();
        for (int i = 0; i < size; ++i) {
            // check first <= val < last
            if (elements[i].compareTo(first) >= 0 && elements[i].compareTo(last) < 0)
                ret.add(elements[i]);
        }

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
        modified = false;
    }

    @Override
    public T next() {
        if (!hasNext() || modified) throw new TraversalException();
        return elements[ptr++];
    }

    @Override
    public boolean hasNext() {
        if (isEmpty()) return false;
        return !Objects.isNull(elements[ptr]);
    }
}
