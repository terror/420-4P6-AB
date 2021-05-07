/*
 * Copyright (c) 2021 Ian Clement. All rights reserved.
 */

package ca.qc.johnabbott.cs4p6.serialization.util;

import ca.qc.johnabbott.cs4p6.serialization.Serializable;
import ca.qc.johnabbott.cs4p6.serialization.Serializer;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Objects;

/**
 * A serializable wrapper class for floats.
 */
public class Float implements Serializable, Comparable<Float> {

    public static final byte SERIAL_ID = 0x04;

    // optimization, use a static ByteBuffer to avoid extra allocations on each (de)serialize operation.
    private static final ByteBuffer buffer;

    // initialize a static field in a static initialization block.
    static {
        byte[] bytes = new byte[java.lang.Float.BYTES];
        buffer = ByteBuffer.wrap(bytes);
    }

    /**
     * Write a float primitive to the serializer.
     * @param value The float primitive to write.
     * @param serializer The serializer to write to.
     * @throws IOException
     */
    public static void write(float value, Serializer serializer) throws IOException {
        // clear the buffer in case of previous use.
        buffer.clear();

        // place the float
        buffer.putFloat(value);
        serializer.write(buffer.array());
    }

    /**
     * Read a float primitive from the serializer.
     * @param serializer The serializer to read from.
     * @return The float read from the serializer.
     * @throws IOException
     */
    public static float read(Serializer serializer) throws IOException {
        // clear the buffer in case of previous use.
        buffer.clear();

        // extract the backing byte[] and read into it.
        byte[] bytes = buffer.array();
        serializer.read(bytes);

        // read the float value from the ByteBuffer,
        // since the backing byte[] now has the data.
        return buffer.getFloat();
    }

    // store the wrapped float value.
    private float value;

    /**
     * Serializable 0 value.
     */
    public Float() {
        value = 0.0f;
    }

    /**
     * Custom serializable value.
     * @param value
     */
    public Float(float value) {
        this.value = value;
    }

    /**
     * Get the value of the serializable float.
     * @return
     */
    public float get() {
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
    public int compareTo(Float rhs) {
        float tmp = this.value - rhs.value;
        if(tmp < 0)
            return -1;
        else if(tmp > 0)
            return 1;
        else
            return 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Float rhs = (Float) o;
        return value == rhs.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
