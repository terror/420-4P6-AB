package ca.qc.johnabbott.cs4p6.serialization.util;

import ca.qc.johnabbott.cs4p6.serialization.Serializable;
import ca.qc.johnabbott.cs4p6.serialization.SerializationException;
import ca.qc.johnabbott.cs4p6.serialization.Serializer;

import java.io.IOException;


public class Box<T extends Serializable> implements Serializable {
    // A simple generic box class.
    // `T` must itself be serializable

    public static final byte SERIAL_ID = 0x08;

    private T value;

    public Box() {
    }

    public Box(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    @Override
    public java.lang.String toString() {
        return "Box{" +
                "value=" + value +
                '}';
    }

    @Override
    public byte getSerialId() {
        return SERIAL_ID;
    }

    @Override
    public void serialize(Serializer serializer) throws IOException {
        serializer.write(value);
    }

    @Override
    public void deserialize(Serializer serializer) throws IOException, SerializationException {
        value = (T) serializer.readSerializable();
    }
}
