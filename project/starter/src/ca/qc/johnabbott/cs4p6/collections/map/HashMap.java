/*
 * Copyright (c) 2021 Ian Clement. All rights reserved.
 */

package ca.qc.johnabbott.cs4p6.collections.map;


import ca.qc.johnabbott.cs4p6.collections.list.LinkedList;
import ca.qc.johnabbott.cs4p6.collections.list.List;
import ca.qc.johnabbott.cs4p6.serialization.Logger;
import ca.qc.johnabbott.cs4p6.serialization.Serializable;
import ca.qc.johnabbott.cs4p6.serialization.SerializationException;
import ca.qc.johnabbott.cs4p6.serialization.Serializer;
import ca.qc.johnabbott.cs4p6.serialization.util.Double;
import ca.qc.johnabbott.cs4p6.serialization.util.Integer;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Objects;

/**
 * Implements the map using open addressing.
 *
 * @author Ian Clement (ian.clement@johnabbott.qc.ca)
 */
public class HashMap<K extends Serializable, V extends Serializable> implements Map<K, V>, Serializable {
    public static final byte SERIAL_ID = 0x17;

    private static final int DEFAULT_BUCKETS = 7;
    private static final double DEFAULT_REHASHING_THRESHOLD = 0.8;
    /* store as array of link pointers */
    private Link<Entry<K, V>>[] buckets;

    /* Fields */
    private int size;
    private double threshold;  // rehashing threshold

    public HashMap() {
        this(DEFAULT_BUCKETS, DEFAULT_REHASHING_THRESHOLD);
    }

    /* Constructors */

    public HashMap(int totalBuckets) {
        this(totalBuckets, DEFAULT_REHASHING_THRESHOLD);
    }

    public HashMap(int totalBuckets, double threshold) {
        this.threshold = threshold;
        size = 0;
        buckets = (Link<Entry<K, V>>[]) new Link[totalBuckets];
    }

    /**
     * Hash the key to determine its "bucket", i.e.: index in the `buckets` array.
     *
     * @param key
     * @return the index in the buckets array.
     */
    private int hash(K key) {
        int index = key.hashCode() % buckets.length;
        if (index < 0) index += buckets.length; // adjust negative hashCode()
        return index;
    }

    /* Utility Methods */

    /**
     * Get the next number of buckets. Start by doubling, then find the next prime number afterwards.
     *
     * @return the next number of buckets.
     */
    private int nextNumberOfBuckets() {
        int n = buckets.length * 2 + 1; // doubling, ensuring that the next number is odd
        while (!isPrime(n))  // find the next prime number
            n += 2;
        return n;
    }

    /**
     * Check that a number is prime
     * (http://www.mkyong.com/java/how-to-determine-a-prime-number-in-java/)
     *
     * @param n
     * @return true if n is prime, false otherwise.
     */
    private boolean isPrime(int n) {
        //check if n is a multiple of 2
        if (n % 2 == 0) return false;
        //if not, then just check the odds
        for (int i = 3; i * i <= n; i += 2) {
            if (n % i == 0)
                return false;
        }
        return true;
    }

    /**
     * If necessary, resize the buckets array and rehash the entries.
     */
    private void rehash() {
        // only rehash when the load factor exceeds the limit
        if ((double) size / (double) buckets.length < threshold)
            return;

        // allocation the new bucket array
        Link<Entry<K, V>>[] previous = buckets;
        buckets = (Link<Entry<K, V>>[]) new Link[nextNumberOfBuckets()];

        // loop through the previous buckets array and move all links to the new array.
        for (int i = 0; i < previous.length; i++) {

            if (previous[i] != null) { // skip empty lists

                // move each link in the linked list to their new position in the larger bucket array
                // recycle the memory by relinking each link.
                // careful: list of keys that hashed to a bucket in the previous bucket size will not necessarily be hashed to the same bucket in the new size.

                Link<Entry<K, V>> current = previous[i];
                while (current != null) {
                    int index = hash(current.element.getKey());

                    // store next link since we overwrite `current.next` below
                    Link<Entry<K, V>> tmp = current.next;

                    // place at the head of the list
                    current.next = buckets[index];
                    buckets[index] = current;

                    current = tmp;
                }
            }
        }
    }

    public double getThreshold() {
        return threshold;
    }

    /* Methods */

    @Override
    public void put(K key, V value) {
        // find index by hashing key
        int index = hash(key);

        // search for the key in the list
        Link<Entry<K, V>> current;
        for (current = buckets[index]; current != null && !current.element.getKey().equals(key); current = current.next)
            ;

        // key not found: add an entry to the "head" of the list
        if (current == null) {
            Link<Entry<K, V>> tmp = new Link<>(new Entry<K, V>(key, value));
            tmp.next = buckets[index];
            buckets[index] = tmp;
            size++;

            // added an entry -> rehash
            rehash();
        } else // key found: replace value
            current.element.setValue(value);
    }

    @Override
    public V get(K key) {
        // find index by hashing key
        int index = hash(key);

        // search list for the link containing key
        Link<Entry<K, V>> current;
        for (current = buckets[index]; current != null && !current.element.getKey().equals(key); current = current.next)
            ;

        // throw if key not found: precondition violated.
        if (current == null)
            throw new KeyNotFoundException();

        return current.element.getValue();
    }

    @Override
    public V remove(K key) {
        // find index by hashing key
        int index = hash(key);

        // search list for link containing key, keep track of previous link for deletion.
        Link<Entry<K, V>> current;
        Link<Entry<K, V>> previous = null;
        for (current = buckets[index]; current != null && !current.element.getKey().equals(key); current = current.next)
            previous = current;

        // key not found: precondition violated.
        if (current == null)
            throw new KeyNotFoundException();

        V tmp = current.element.getValue();
        if (previous == null) // remove buckets[i]
            buckets[index] = current.next;
        else             // remove within linked list
            previous.next = current.next;

        size--;

        return tmp;
    }

    @Override
    public void clear() {
        for (int i = 0; i < buckets.length; i++)
            buckets[i] = null;
        // alternative: buckets = (Link[]) new Object[buckets.length];
        size = 0;
    }

    @Override
    public boolean containsKey(K key) {
        // find index by hashing key
        int index = hash(key);

        // search list for link containing key
        Link<Entry<K, V>> current;
        for (current = buckets[index]; current != null && !current.element.getKey().equals(key); current = current.next)
            ;
        return current != null;
    }

    @Override
    public List<K> keys() {
        List<K> keys = new LinkedList<>();
        for (int i = 0; i < buckets.length; i++)
            for (Link<Entry<K, V>> current = buckets[i]; current != null; current = current.next)
                keys.add(current.element.getKey());
        return keys;
    }

    @Override
    public Iterator<Entry<K, V>> iterator() {
        throw new RuntimeException("Not implemented.");
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public int size() {
        return size;
    }

    /**
     * Determine the size of the bucket array.
     *
     * @return the size of the bucket array.
     * @precondition None.
     * @postcondition Returns the size of the bucket array.
     */
    public int getTotalBuckets() {
        return buckets.length;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        boolean first = true;
        for (int i = 0; i < buckets.length; i++)
            for (Link<Entry<K, V>> head = buckets[i]; head != null; head = head.next) {
                Entry<K, V> entry = head.element;
                sb.append(first ? "" : ", ").append(entry.getKey()).append(" => ").append(entry.getValue());
                first = false;
            }
        sb.append("}");
        return sb.toString();
    }

    public String toStringBuckets() {
        StringBuilder sb = new StringBuilder();
        sb.append("{\n");
        for (int i = 0; i < buckets.length; i++) {
            int count = 0;
            for (Link<Entry<K, V>> head = buckets[i]; head != null; head = head.next) count++;
            sb.append("\t" + count + ": [");

            boolean first = true;
            for (Link<Entry<K, V>> head = buckets[i]; head != null; head = head.next) {
                Entry<K, V> entry = head.element;
                sb.append(first ? "" : ", ").append(entry.getKey()).append(" => ").append(entry.getValue());
                first = false;
            }
            sb.append("]\n");
        }
        sb.append("}");

        return sb.toString();
    }

    @Override
    public byte getSerialId() {
        return SERIAL_ID;
    }

    @Override
    public void serialize(Serializer serializer) throws IOException {
        // first serialize `threshold`, `size` and buckets `length`
        Logger.getInstance().log("HashMap: serializing `threshold` = " + this.threshold);
        serializer.write(new Double(this.threshold));

        Logger.getInstance().log("HashMap: serializing `size` = " + this.size);
        serializer.write(new Integer(this.size));

        Logger.getInstance().log("HashMap: serializing `buckets.length` = " + this.buckets.length);
        serializer.write(new Integer(this.buckets.length));

        // serialize enough information so as to be able to rebuild buckets
        // 1. we need to know the size of each chain
        // 2. we need to serialize all (K->V) pairs
        // size, (Key->Value), (Key->Value) ...

        ArrayList<Integer> sz = new ArrayList<>();
        for (int i = 0; i < this.buckets.length; ++i) {
            int currSize = 0;
            Link<Entry<K, V>> curr = this.buckets[i];
            while (curr != null) {
                curr = curr.next;
                ++currSize;
            }
            if (currSize != 0)
                sz.add(new Integer(currSize));
        }

        int start = 0;
        for (; start < this.buckets.length; ++start) {
            if (this.buckets[start] != null)
                break;
        }

        for (int i = 0; i < sz.size(); ++i) {
            // write this lists `size`
            Logger.getInstance().log("HashMap: serializing `buckets[i] size` = " + sz.get(i));
            serializer.write(sz.get(i));

            // traverse and serialize each (K, V) pair
            Link<Entry<K, V>> curr = this.buckets[start++];
            Logger.getInstance().log("HashMap: serializing `buckets[i] linked list`");
            while (curr != null) {
                Logger.getInstance().log("HashMap: serializing `buckets[i] linked list: current element: `" + curr.element);
                serializer.write(curr.element);
                curr = curr.next;
            }
        }
    }

    @Override
    public void deserialize(Serializer serializer) throws IOException, SerializationException {
        // read `threshold`, `size` and buckets `length` and set them on `this`
        this.threshold = ((Double) serializer.readSerializable()).get();
        this.size = ((Integer) serializer.readSerializable()).get();
        this.buckets = (Link<Entry<K, V>>[]) new Link[((Integer) serializer.readSerializable()).get()];

        // recreate each new linked list of entries
        for (int i = 0; i < this.size; ++i) {
            // read this lists `size`
            int size = ((Integer) serializer.readSerializable()).get();

            // traverse and recreate the current list of entries
            Link<Entry<K, V>> curr = new Link<>(((Entry<K, V>) serializer.readSerializable()));
            this.buckets[i] = curr;
            for (int j = 0; j < size - 1; ++j) {
                curr.next = new Link<>((Entry<K, V>) serializer.readSerializable());
                curr = curr.next;
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        HashMap<?, ?> hashMap = (HashMap<?, ?>) o;

        if (this.size != hashMap.size)
            return false;

        if (java.lang.Double.compare(hashMap.threshold, threshold) != 0)
            return false;

        // compare buckets
        for (int i = 0; i < hashMap.buckets.length; ++i) {
            // continue if both links are null
            if (hashMap.buckets[i] == null && this.buckets[i] == null)
                continue;

            // compare this chain
            Link a = this.buckets[i], b = hashMap.buckets[i];
            while (a != null && b != null) {
                if (!a.element.equals(b.element))
                    return false;
                a = a.next;
                b = b.next;
            }
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(size, threshold);
        result = 31 * result + Arrays.hashCode(buckets);
        return result;
    }

    private static class Link<T> {
        public Link<T> next;
        public T element;

        public Link() {
        }

        public Link(T element) {
            this.element = element;
        }
    }
}
