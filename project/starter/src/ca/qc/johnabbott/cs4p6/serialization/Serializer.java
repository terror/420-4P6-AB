/*
 * Copyright (c) 2021 Ian Clement. All rights reserved.
 */

package ca.qc.johnabbott.cs4p6.serialization;

import ca.qc.johnabbott.cs4p6.serialization.io.Destination;
import ca.qc.johnabbott.cs4p6.serialization.io.Source;
import ca.qc.johnabbott.cs4p6.serialization.util.Integer;
import ca.qc.johnabbott.cs4p6.serialization.util.Long;
import ca.qc.johnabbott.cs4p6.serialization.util.Double;
import ca.qc.johnabbott.cs4p6.serialization.util.Float;

import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Serializer class.
 *
 * - Serialize and deserialize primitives.
 * - Serialize and deserialize Serializable objects.
 * - Manage the interaction with the input source and output destination.
 *
 * @author Ian Clement (ian.clement@johnabbott.qc.ca)
 */
public class Serializer {

    public static byte SERIAL_NULL_ID = 0x00;

    /**
     * Manifest for storing creators by serial ID.
     */
    public static class CreatorManifest {

        private Map<Byte, Creator<Serializable>> creators;

        CreatorManifest() {
            creators = new HashMap<>();
        }

        public void register(byte id, Creator<Serializable> creator) throws SerializationException {
            if(id == SERIAL_NULL_ID)
                return;
            if(creators.containsKey(id))
                throw new SerializationException("Duplicate SERIAL_ID: " + id);
            creators.put(id, creator);
        }

        public Serializable create(byte id) throws SerializationException {
            if(id == SERIAL_NULL_ID)
                return null;
            if(creators.containsKey(id))
                return creators.get(id).create();
            else
                throw new SerializationException("Unknown serial ID: " + id + ".");
        }

    }

    // instance fields

    private Source source;
    private Destination destination;
    private CreatorManifest manifest;

    /**
     * Create a Serializer.
     * @param source The source to deserialize from.
     * @param destination The destination to serialize to.
     */
    public Serializer(Source source, Destination destination, CreatorManifest manifest) {
        this.source = source;
        this.destination = destination;
        this.manifest = manifest;
    }

    /**
     * Create a Serializer
     * @param source
     * @param manifest
     */
    public Serializer(Source source, CreatorManifest manifest) {
        this(source, null, manifest);
        Logger.getInstance().log("Serializer: created deserializer.");
    }

    /**
     * Create a Serializer
     * @param destination
     * @param manifest
     */
    public Serializer(Destination destination, CreatorManifest manifest) {
        this(null, destination, manifest);
        Logger.getInstance().log("Serializer: created serializer.");
    }


    /**
     * Write a NULL record to the output destination.
     * @throws IOException
     */
    public void writeNull() throws IOException {
        Logger.getInstance().log("Serializer: writing null.");
        destination.write(SERIAL_NULL_ID);
    }

    /**
     * Write the serializable object to the destination. Includes a serialization header.
     * @param value The object to serialize.
     * @throws IOException
     */
    public void write(Serializable value) throws IOException {
        Logger.getInstance().log("Serializer: writing serializable with SERIAL_ID = " + value.getSerialId());
        destination.write(value.getSerialId());
        value.serialize(this);
    }

    /**
     * Write a byte array to the destination.
     * @param bytes The bytes to write.
     * @throws IOException
     */
    public void write(byte[] bytes) throws IOException {
        Logger.getInstance().log("Serializer: writing byte[] = " + Arrays.toString(bytes));
        destination.write(bytes);
    }

    /**
     * Write a byte to the destination.
     * @param value The byte to write.
     * @throws IOException
     */
    public void write(byte value) throws IOException {
        Logger.getInstance().log("Serializer: writing byte = " + value);
        destination.write(value);
    }

    /**
     * Write an integer to the destination.
     * @param value The integer to write.
     * @throws IOException
     */
    public void write(int value) throws IOException {
        Logger.getInstance().log("Serializer: writing int = " + value);
        Integer.write(value, this);
    }

    /**
     * Write a long to the destination.
     * @param value The long to write.
     * @throws IOException
     */
    public void write(long value) throws IOException {
        Logger.getInstance().log("Serializer: writing long = " + value);
        Long.write(value, this);
    }

    /**
     * Write a double to the destination.
     * @param value The double to write.
     * @throws IOException
     */
    public void write(double value) throws IOException {
        Logger.getInstance().log("Serializer: writing double = " + value);
        Double.write(value, this);
    }

    /**
     * Write a float to the destination.
     * @param value The float to write.
     * @throws IOException
     */
    public void write(float value) throws IOException {
        Logger.getInstance().log("Serializer: writing float = " + value);
        Float.write(value, this);
    }

    /**
     * Read a serializable object from the source.
     * @return The deserialized object.
     * @throws IOException
     * @throws SerializationException
     */
    public Serializable readSerializable() throws IOException, SerializationException {
        byte serialId = source.read();
        Serializable s = manifest.create(serialId);
        if(s != null)
            s.deserialize(this);
        Logger.getInstance().log("Serializer: read serializable with SERIAL_ID = " + serialId);
        return s;
    }

    /**
     * Read a length `n` from the source (as an int),
     * and use this length as the number of bytes to read and return
     * @return the bytes read.
     * @throws IOException
     */
    public byte[] readNextByteArray() throws IOException {
        byte[] bytes = new byte[java.lang.Integer.BYTES];
        source.read(bytes, java.lang.Integer.BYTES);
        int n = ByteBuffer.wrap(bytes).getInt();
        bytes = new byte[n];
        source.read(bytes, n);
        Logger.getInstance().log("Serializer: read byte[] = " + Arrays.toString(bytes));
        return bytes;
    }

    /**
     * Read a byte from the source.
     * @return The byte read.
     * @throws IOException
     */
    public byte read() throws IOException {
        byte tmp = source.read();
        Logger.getInstance().log("Serializer: read byte = " + tmp);
        return tmp;
    }

    /**
     * Read a byte array from the source.
     * @param bytes The byte array to store the read bytes.
     * @return The number of bytes read.
     * @throws IOException
     */
    public int read(byte[] bytes) throws IOException {
        int count = read(bytes, bytes.length);
        Logger.getInstance().log("Serializer: read byte[] = " + Arrays.toString(bytes) + " with count = " +  count);
        return count;
    }

    // TODO
    /**
     * Read a byte array from the source with a specified length.
     * @param bytes The byte array to store the read bytes.
     * @param length The maximum number of bytes to read.
     * @return The number of bytes read.
     * @throws IOException
     */
    protected int read(byte[] bytes, int length) throws IOException {
        int count = source.read(bytes, length);
        Logger.getInstance().log("Serializer: read byte[] = " + Arrays.toString(bytes) + " with count = " +  count);
        return count;
    }

    /**
     * Read a null from the source.
     * @throws IOException
     * @throws SerializationException
     */
    public void readNull() throws IOException, SerializationException {
        byte serialId = source.read();
        if(serialId != SERIAL_NULL_ID)
            throw new SerializationException("Trying to read a null, but found a non-null representation in the source.");
        Logger.getInstance().log("Serializer: read null");
    }

    /**
     * Read an integer from the source.
     * @return The integer value read.
     * @throws IOException
     * @throws SerializationException
     */
    public int readInt() throws IOException {
        int tmp =  Integer.read(this);
        Logger.getInstance().log("Serializer: read int = " + tmp);
        return tmp;
    }

    /**
     * Read a long from the source.
     * @return The long value read.
     * @throws IOException
     * @throws SerializationException
     */
    public long readLong() throws IOException {
        long tmp = Long.read(this);
        Logger.getInstance().log("Serializer: read long = " + tmp);
        return tmp;
    }

    /**
     * Read a double from the source.
     * @return The double value read.
     * @throws IOException
     */
    public double readDouble() throws IOException {
        double tmp = Double.read(this);
        Logger.getInstance().log("Serializer: read double = " + tmp);
        return tmp;
    }

    /**
     * Read a float from the source.
     * @return The float value read.
     * @throws IOException
     */
    public float readFloat() throws IOException {
        float tmp = Float.read(this);
        Logger.getInstance().log("Serializer: read float = " + tmp);
        return tmp;
    }

    /**
     * Get source.
     * @return
     */
    public Source getSource() {
        return source;
    }

    /**
     * Get destination.
     * @return
     */
    public Destination getDestination() {
        return destination;
    }

    /**
     * get creator manifest.
     * @return
     */
    public CreatorManifest getManifest() {
        return manifest;
    }

    /**
     * Close serializer.
     * @throws IOException
     */
    public void close() throws IOException {
        Logger.getInstance().log("Serializer: closing serializer.");
        if(source != null)
            source.close();
        if(destination != null)
            destination.close();
    }

}
