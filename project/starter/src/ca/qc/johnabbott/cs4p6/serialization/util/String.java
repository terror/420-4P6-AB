package ca.qc.johnabbott.cs4p6.serialization.util;

import ca.qc.johnabbott.cs4p6.serialization.Serializable;
import ca.qc.johnabbott.cs4p6.serialization.SerializationException;
import ca.qc.johnabbott.cs4p6.serialization.Serializer;

import java.io.IOException;

public class String implements Serializable, Comparable<String> {
    public static final byte SERIAL_ID = 0x06;

    private java.lang.String value;

    public String() {

    }

    public String(java.lang.String value) {
        this.value = value;
    }

    public java.lang.String get() {
        return this.value;
    }

    public void set(java.lang.String value) {
        this.value = value;
    }

    @Override
    public byte getSerialId() {
        return SERIAL_ID;
    }

    @Override
    public void serialize(Serializer serializer) throws IOException {
        char[] chars = value.toCharArray();
        byte[] bytes = new byte[chars.length];

        // write the number of characters in the string
        serializer.write(chars.length);

        // assume strings are `ascii` encoded
        for (int i = 0; i < chars.length; ++i)
            bytes[i] = (byte) chars[i];

        serializer.write(bytes);
    }

    @Override
    public void deserialize(Serializer serializer) throws IOException, SerializationException {
        byte[] bytes = serializer.readNextByteArray();
        value = new java.lang.String(bytes);
    }

    @Override
    public java.lang.String toString() {
        return "String{" +
                "value='" + value + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        String string = (String) o;

        return value != null ? value.equals(string.value) : string.value == null;
    }

    @Override
    public int hashCode() {
        return value != null ? value.hashCode() : 0;
    }

    @Override
    public int compareTo(String rhs) {
        return value.compareTo(rhs.value);
    }
}
