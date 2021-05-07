/*
 * Copyright (c) 2021 Ian Clement. All rights reserved.
 */

package ca.qc.johnabbott.cs4p6.collections;

/**
 * Store homogeneous values together, i.e.: a tuple. Currently only tuples of size 1, 2, and 3 are supported.
 *
 * The naming convention used is https://en.wikipedia.org/wiki/Tuple#Names_for_tuples_of_specific_lengths
 *
 * @author Ian Clement (ian.clement@johnabbott.qc.ca)
 *
 * @param <T>
 */
public abstract class Tuple<T> {

    /**
     * Position enumeration used for tuple get and set operations.
     */
    public enum Position {
        FIRST, SECOND, THIRD, FORTH, FIFTH, SIXTH, SEVENTH, EIGHT;
    }

    /**
     * Retrieve value from a position in a tuple.
     * @param pos The position in the tuple.
     * @return The value at the position in the tuple.
     */
    public abstract T get(Position pos);

    /**
     * Set a value at a position in the tuple.
     * @param pos The position in the tuple.
     * @param value The new value at the position in the tuple.
     */
    public abstract void set(Position pos, T value);


    /**
     * Create a tuple.
     * @param size The size of the tuple.
     * @param <T>
     * @return The created tuple.
     */
    public static <T> Tuple<T> makeTuple(int size) {
        switch(size) {
            case 1:
                return new Single<>();
            case 2:
                return new Pair<>();
            case 3:
                return new Triple<>();
            default:
                if(size <= 0)
                    throw new IllegalArgumentException("Invalid tuple size: " + size);
                if(size > 4)
                    throw new IllegalArgumentException("Maximum supported tuple size is currently 3.");
        }
        return null;
    }

    /**
     * A tuple of size 1.
     * @param <T>
     */
    public static class Single<T> extends Tuple<T> {

        private T first;

        @Override
        public T get(Position pos) {
            if(pos != Position.FIRST)
                throw new IllegalArgumentException();
            return first;
        }

        @Override
        public void set(Position pos, T value) {
            if(pos != Position.FIRST)
                throw new IllegalArgumentException();
            first = value;
        }

        @Override
        public String toString() {
            return String.format("(%s)", first);
        }
    }

    /**
     * A tuple of size 2.
     * @param <T>
     */
    private static class Pair<T> extends Tuple<T> {

        private T first;
        private T second;

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
    }

    /**
     * A tuple of size 3.
     * @param <T>
     */
    private static class Triple<T> extends Tuple<T> {

        private T first;
        private T second;
        private T third;

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
    }

}
