/*
 * Copyright (c) 2021 Ian Clement. All rights reserved.
 */

package ca.qc.johnabbott.cs4p6.collections;

/**
 * Exception thrown when a queue underflows.
 *
 * @author Ian Clement
 */
public class QueueUnderflowException extends RuntimeException {

    public QueueUnderflowException() {
        super();
    }

    public QueueUnderflowException(String message) {
        super(message);
    }
}
