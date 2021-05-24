/*
 * Copyright (c) 2021 Ian Clement. All rights reserved.
 */

package ca.qc.johnabbott.cs4p6;

import ca.qc.johnabbott.cs4p6.serialization.Logger;
import ca.qc.johnabbott.cs4p6.serialization.Serializable;
import ca.qc.johnabbott.cs4p6.serialization.SerializationException;
import ca.qc.johnabbott.cs4p6.serialization.Serializer;

import java.io.IOException;
import java.util.Objects;

/**
 * Simple point class.
 */
public class Point implements Serializable {
    public static final byte SERIAL_ID = 0x11;

    private float x;
    private float y;

    public Point() {
    }

    public Point(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "Point{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        return Float.compare(point.x, x) == 0 && Float.compare(point.y, y) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public byte getSerialId() {
        return SERIAL_ID;
    }

    @Override
    public void serialize(Serializer serializer) throws IOException {
        // serialize both `x` and `y`
        Logger.getInstance().log("Point: serializing `x` = " + this.x);
        serializer.write(this.x);

        Logger.getInstance().log("Point: serializing `y` = " + this.y);
        serializer.write(this.y);
    }

    @Override
    public void deserialize(Serializer serializer) throws IOException, SerializationException {
        // update our current `x` and `y` values
        // to reflect the serialized data
        this.x = serializer.readFloat();
        this.y = serializer.readFloat();
    }
}
