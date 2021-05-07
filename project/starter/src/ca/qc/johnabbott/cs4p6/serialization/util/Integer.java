/*
 * Copyright (c) 2021 Ian Clement. All rights reserved.
 */

package ca.qc.johnabbott.cs4p6.serialization.util;

import ca.qc.johnabbott.cs4p6.serialization.*;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Objects;

/**
 * A serializable wrapper class for integers.
 */
public class Integer implements Serializable, Comparable<Integer> {

    public static final byte SERIAL_ID = 0x01;

    // optimization, use a static ByteBuffer to avoid extra allocations on each (de)serialize operation.
    private static final ByteBuffer buffer;

    // initialize a static field in a static initialization block.
    static {
        byte[] bytes = new byte[java.lang.Integer.BYTES];
        buffer = ByteBuffer.wrap(bytes);
    }

    /**
     * Write an integer primitive to the serializer.
     * @param value The integer primitive to write.
     * @param serializer The serializer to write to.
     * @throws IOException
     */
    public static void write(int value, Serializer serializer) throws IOException {
        // clear the buffer in case of previous use.
        buffer.clear();

        // place the integer
        buffer.putInt(value);
        serializer.write(buffer.array());
    }

    /**
     * Read an integer primitive from the serializer.
     * @param serializer The serializer to read from.
     * @return The integer read from the serializer.
     * @throws IOException
     */
    public static int read(Serializer serializer) throws IOException {
        // clear the buffer in case of previous use.
        buffer.clear();

        // extract the backing byte[] and read into it.
        byte[] bytes = buffer.array();
        serializer.read(bytes);

        // read the integer value from the ByteBuffer,
        // since the backing byte[] now has the data.
        return buffer.getInt();
    }

    // store the wrapped integer value.
    private int value;

    /**
     * Serializable 0 value.
     */
    public Integer() {
        value = 0;
    }

    /**
     * Custom serializable value.
     * @param value
     */
    public Integer(int value) {
        this.value = value;
    }

    /**
     * Get the value of the serializable integer.
     * @return
     */
    public int get() {
        return value;
    }

    @Override
    public byte getSerialId() {
        return SERIAL_ID;
    }

    @Override
    public void serialize(Serializer serializer) throws IOException {
        write(value, serializer);
    }

    @Override
    public void deserialize(Serializer serializer) throws IOException {
        value = read(serializer);
    }

    @Override
    public java.lang.String toString() {
        return java.lang.String.valueOf(value);
    }

    @Override
    public int compareTo(Integer rhs) {
        return this.value - rhs.value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Integer rhs = (Integer) o;
        return value == rhs.value;
    }

    @Override
    public int hashCode() {

        return Objects.hash(value);
    }


}
