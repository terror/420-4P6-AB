/*
 * Copyright (c) 2021 Ian Clement. All rights reserved.
 */

package ca.qc.johnabbott.cs4p6.collections.map;

import ca.qc.johnabbott.cs4p6.serialization.Serializable;
import ca.qc.johnabbott.cs4p6.serialization.SerializationException;
import ca.qc.johnabbott.cs4p6.serialization.Serializer;

import java.io.IOException;
import java.util.Objects;

/**
 * Entry class used to store individual mappings in a map structure.
 *
 * @author Ian Clement (ian.clement@johnabbott.qc.ca)
 */
public class Entry<K extends Serializable, V extends Serializable> implements Serializable {
    public static final byte SERIAL_ID = 0x18;

    private K key;
    private V value;

    public Entry() {
    }

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Entry<?, ?> entry = (Entry<?, ?>) o;
        return Objects.equals(key, entry.key) && Objects.equals(value, entry.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, value);
    }

    @Override
    public String toString() {
        return "Entry{" +
                "key=" + key +
                ", value=" + value +
                '}';
    }

    @Override
    public byte getSerialId() {
        return SERIAL_ID;
    }

    @Override
    public void serialize(Serializer serializer) throws IOException {
        // serialize both key and value
        serializer.write(this.getKey());
        serializer.write(this.getValue());
    }

    @Override
    public void deserialize(Serializer serializer) throws IOException, SerializationException {
        // read both key and value and set them on `this`
        this.key = (K) serializer.readSerializable();
        this.value = (V) serializer.readSerializable();
    }
}
