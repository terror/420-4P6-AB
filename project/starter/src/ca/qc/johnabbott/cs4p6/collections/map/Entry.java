/*
 * Copyright (c) 2021 Ian Clement. All rights reserved.
 */

package ca.qc.johnabbott.cs4p6.collections.map;

/**
 * Entry class used to store individual mappings in a map structure.
 *
 * @author Ian Clement (ian.clement@johnabbott.qc.ca)
 */
public class Entry<K, V> {
    private K key;
    private V value;

    public Entry(K key) {
        this.key = key;
    }

    public Entry(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public K getKey() {
        return key;
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Entry{" +
                "key=" + key +
                ", value=" + value +
                '}';
    }
}
