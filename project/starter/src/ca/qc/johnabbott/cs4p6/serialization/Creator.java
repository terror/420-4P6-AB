/*
 * Copyright (c) 2021 Ian Clement. All rights reserved.
 */

package ca.qc.johnabbott.cs4p6.serialization;

/**
 * Interface used to structure instance creation used in deserialization.
 * @param <T>
 *
 * @author Ian Clement (ian.clement@johnabbott.qc.ca)
 */
public interface Creator<T> {
    T create();
}
