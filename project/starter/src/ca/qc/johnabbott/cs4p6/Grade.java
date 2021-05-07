package ca.qc.johnabbott.cs4p6;

import ca.qc.johnabbott.cs4p6.serialization.Logger;
import ca.qc.johnabbott.cs4p6.serialization.Serializable;
import ca.qc.johnabbott.cs4p6.serialization.SerializationException;
import ca.qc.johnabbott.cs4p6.serialization.Serializer;
import ca.qc.johnabbott.cs4p6.serialization.util.Date;
import ca.qc.johnabbott.cs4p6.serialization.util.Double;
import ca.qc.johnabbott.cs4p6.serialization.util.String;

import java.io.IOException;
import java.util.Objects;

/**
 * Simple data class.
 *
 * @author Ian Clement (ian.clement@johnabbott.qc.ca)
 */
public class Grade implements Serializable {
    public static final byte SERIAL_ID = 0x12;

    private java.lang.String name;
    private double result;
    private java.util.Date date;

    public Grade(java.lang.String name, double result, java.util.Date date) {
        this.name = name;
        this.result = result;
        this.date = date;
    }

    public Grade() {
    }

    public java.lang.String getName() {
        return name;
    }

    public void setName(java.lang.String name) {
        this.name = name;
    }

    public double getResult() {
        return result;
    }

    public void setResult(double result) {
        this.result = result;
    }

    public java.util.Date getDate() {
        return date;
    }

    public void setDate(java.util.Date date) {
        this.date = date;
    }

    @Override
    public java.lang.String toString() {
        return "Grade{" +
                "name='" + name + '\'' +
                ", result=" + result +
                ", date=" + date +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Grade grade = (Grade) o;
        return result == grade.result &&
                Objects.equals(name, grade.name) &&
                Objects.equals(date, grade.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, result, date);
    }

    @Override
    public byte getSerialId() {
        return SERIAL_ID;
    }

    @Override
    public void serialize(Serializer serializer) throws IOException {
        // fields already implement serializable.
        // simply write each field using `serializer`.

        Logger.getInstance().log("Grade: serializing `name` = " + this.name);
        serializer.write(new String(this.name));

        Logger.getInstance().log("Grade: serializing `result` = " + this.result);
        serializer.write(new Double(this.result));

        Logger.getInstance().log("Grade: serializing `date` = " + this.date);
        serializer.write(new Date(this.date));
    }

    @Override
    public void deserialize(Serializer serializer) throws IOException, SerializationException {
        // grab serialized fields and set them on `this`.

        String name = (String) serializer.readSerializable();
        Double result = (Double) serializer.readSerializable();
        Date date = (Date) serializer.readSerializable();

        this.name = name.get();
        this.result = result.get();
        this.date = date.get();
    }
}
