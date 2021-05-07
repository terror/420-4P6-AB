package ca.qc.johnabbott.cs4p6.serialization.util;

import ca.qc.johnabbott.cs4p6.serialization.Logger;
import ca.qc.johnabbott.cs4p6.serialization.Serializable;
import ca.qc.johnabbott.cs4p6.serialization.SerializationException;
import ca.qc.johnabbott.cs4p6.serialization.Serializer;

import java.io.IOException;

public class Date implements Serializable {
    public static final byte SERIAL_ID = 0x07;

    private java.util.Date value;

    public Date() {
    }

    public Date(java.util.Date value) {
        this.value = value;
    }

    public java.util.Date get() {
        return this.value;
    }

    public void set(java.util.Date value) {
        this.value = value;
    }

    @Override
    public java.lang.String toString() {
        return "Date{" +
                "value=" + value +
                "}";
    }

    @Override
    public byte getSerialId() {
        return SERIAL_ID;
    }

    @Override
    public void serialize(Serializer serializer) throws IOException {
        long time = value.getTime();
        Logger.getInstance().log("Date: serializing time = " + time);
        serializer.write(time);
    }

    @Override
    public void deserialize(Serializer serializer) throws IOException, SerializationException {
        long time = serializer.readLong();
        Logger.getInstance().log("Date: deserializing time = " + time);
        value = new java.util.Date(time);
    }
}
