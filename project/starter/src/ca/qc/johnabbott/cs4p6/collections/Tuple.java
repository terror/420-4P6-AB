/*
 * Copyright (c) 2021 Ian Clement. All rights reserved.
 */

package ca.qc.johnabbott.cs4p6.collections;

import ca.qc.johnabbott.cs4p6.serialization.Serializable;
import ca.qc.johnabbott.cs4p6.serialization.SerializationException;
import ca.qc.johnabbott.cs4p6.serialization.Serializer;

import java.io.IOException;

/**
 * Store homogeneous values together, i.e.: a tuple. Currently only tuples of size 1, 2, and 3 are supported.
 * <p>
 * The naming convention used is https://en.wikipedia.org/wiki/Tuple#Names_for_tuples_of_specific_lengths
 *
 * @param <T>
 * @author Ian Clement (ian.clement@johnabbott.qc.ca)
 */
public abstract class Tuple<T extends Serializable> implements Serializable {
    /**
     * Create a tuple.
     *
     * @param size The size of the tuple.
     * @param <T>
     * @return The created tuple.
     */
    public static <T extends Serializable> Tuple<T> makeTuple(int size) {
        switch (size) {
            case 1:
                return new Single<>();
            case 2:
                return new Pair<>();
            case 3:
                return new Triple<>();
            default:
                if (size <= 0)
                    throw new IllegalArgumentException("Invalid tuple size: " + size);
                if (size > 4)
                    throw new IllegalArgumentException("Maximum supported tuple size is currently 3.");
        }
        return null;
    }

    /**
     * Retrieve value from a position in a tuple.
     *
     * @param pos The position in the tuple.
     * @return The value at the position in the tuple.
     */
    public abstract T get(Position pos);

    /**
     * Set a value at a position in the tuple.
     *
     * @param pos   The position in the tuple.
     * @param value The new value at the position in the tuple.
     */
    public abstract void set(Position pos, T value);


    /**
     * Position enumeration used for tuple get and set operations.
     */
    public enum Position {
        FIRST, SECOND, THIRD, FORTH, FIFTH, SIXTH, SEVENTH, EIGHT;
    }

    /**
     * A tuple of size 1.
     *
     * @param <T>
     */
    public static class Single<T extends Serializable> extends Tuple<T> {
        public static final byte SERIAL_ID = 0x13;

        private T first;

        public Single() {
        }

        public Single(T first) {
            this.first = first;
        }

        @Override
        public T get(Position pos) {
            if (pos != Position.FIRST)
                throw new IllegalArgumentException();
            return first;
        }

        @Override
        public void set(Position pos, T value) {
            if (pos != Position.FIRST)
                throw new IllegalArgumentException();
            first = value;
        }

        @Override
        public String toString() {
            return String.format("(%s)", first);
        }

        @Override
        public byte getSerialId() {
            return SERIAL_ID;
        }

        @Override
        public void serialize(Serializer serializer) throws IOException {
            serializer.write(first);
        }

        @Override
        public void deserialize(Serializer serializer) throws IOException, SerializationException {
            T first = (T) serializer.readSerializable();
            this.first = first;
        }
    }

    /**
     * A tuple of size 2.
     *
     * @param <T>
     */
    public static class Pair<T extends Serializable> extends Tuple<T> {
        public static final byte SERIAL_ID = 0x14;

        private T first;
        private T second;

        public Pair() {
        }

        public Pair(T first, T second) {
            this.first = first;
            this.second = second;
        }

        @Override
        public T get(Position pos) {
            switch (pos) {
                case FIRST:
                    return first;
                case SECOND:
                    return second;
                default:
                    throw new IllegalArgumentException();
            }
        }

        @Override
        public void set(Position pos, T value) {
            switch (pos) {
                case FIRST:
                    first = value;
                    break;
                case SECOND:
                    second = value;
                    break;
                default:
                    throw new IllegalArgumentException();
            }
        }

        @Override
        public String toString() {
            return String.format("(%s, %s)", first, second);
        }

        @Override
        public byte getSerialId() {
            return SERIAL_ID;
        }

        @Override
        public void serialize(Serializer serializer) throws IOException {
            serializer.write(first);
            serializer.write(second);
        }

        @Override
        public void deserialize(Serializer serializer) throws IOException, SerializationException {
            T first = (T) serializer.readSerializable();
            T second = (T) serializer.readSerializable();

            this.first = first;
            this.second = second;
        }
    }

    /**
     * A tuple of size 3.
     *
     * @param <T>
     */
    public static class Triple<T extends Serializable> extends Tuple<T> {
        public static final byte SERIAL_ID = 0x15;

        private T first;
        private T second;
        private T third;

        public Triple() {
        }

        public Triple(T first, T second, T third) {
            this.first = first;
            this.second = second;
            this.third = third;
        }

        @Override
        public T get(Position pos) {
            switch (pos) {
                case FIRST:
                    return first;
                case SECOND:
                    return second;
                case THIRD:
                    return third;
                default:
                    throw new IllegalArgumentException();
            }
        }

        @Override
        public void set(Position pos, T value) {
            switch (pos) {
                case FIRST:
                    first = value;
                    break;
                case SECOND:
                    second = value;
                    break;
                case THIRD:
                    third = value;
                    break;
                default:
                    throw new IllegalArgumentException();
            }
        }

        @Override
        public String toString() {
            return String.format("(%s, %s, %s)", first, second, third);
        }

        @Override
        public byte getSerialId() {
            return SERIAL_ID;
        }

        @Override
        public void serialize(Serializer serializer) throws IOException {
            serializer.write(first);
            serializer.write(second);
            serializer.write(third);
        }

        @Override
        public void deserialize(Serializer serializer) throws IOException, SerializationException {
            T first = (T) serializer.readSerializable();
            T second = (T) serializer.readSerializable();
            T third = (T) serializer.readSerializable();

            this.first = first;
            this.second = second;
            this.third = third;
        }
    }
}
