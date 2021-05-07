/*
 * Copyright (c) 2021 Ian Clement. All rights reserved.
 */

package ca.qc.johnabbott.cs4p6.serialization;

/**
 * Represents a serialization error.
 *
 * @author Ian Clement (ian.clement@johnabbott.qc.ca)
 */
public class SerializationException extends Exception {

    public SerializationException() {
        super();
    }

    public SerializationException(String message) {
        super(message);
    }

    public SerializationException(Throwable cause) {
        super(cause);
    }
}
