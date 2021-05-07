/*
 * Copyright (c) 2021 Ian Clement. All rights reserved.
 */

package ca.qc.johnabbott.cs4p6.serialization.util;

import ca.qc.johnabbott.cs4p6.serialization.*;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Objects;

/**
 * A serializable wrapper class for doubles.
 */
public class Double implements Serializable, Comparable<Double> {

    public static final byte SERIAL_ID = 0x05;

    // optimization, use a static ByteBuffer to avoid extra allocations on each (de)serialize operation.
    private static final ByteBuffer buffer;

    // initialize a static field in a static initialization block.
    static {
        byte[] bytes = new byte[java.lang.Double.BYTES];
        buffer = ByteBuffer.wrap(bytes);
    }

    /**
     * Write a double primitive to the serializer.
     * @param value The double primitive to write.
     * @param serializer The serializer to write to.
     * @throws IOException
     */
    public static void write(double value, Serializer serializer) throws IOException {
        // clear the buffer in case of previous use.
        buffer.clear();

        // place the double
        buffer.putDouble(value);
        serializer.write(buffer.array());
    }

    /**
     * Read a double primitive from the serializer.
     * @param serializer The serializer to read from.
     * @return The double read from the serializer.
     * @throws IOException
     */
    public static double read(Serializer serializer) throws IOException {
        // clear the buffer in case of previous use.
        buffer.clear();

        // extract the backing byte[] and read into it.
        byte[] bytes = buffer.array();
        serializer.read(bytes);

        // read the double value from the ByteBuffer,
        // since the backing byte[] now has the data.
        return buffer.getDouble();
    }

    // store the wrapped double value.
    private double value;

    /**
     * Serializable 0 value.
     */
    public Double() {
        value = 0.0;
    }

    /**
     * Custom serializable value.
     * @param value
     */
    public Double(double value) {
        this.value = value;
    }

    /**
     * Get the value of the serializable double.
     * @return
     */
    public double get() {
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
    public int compareTo(Double rhs) {
         double tmp = this.value - rhs.value;
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
        Double rhs = (Double) o;
        return value == rhs.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

}
