/*
 * Copyright (c) 2021 Ian Clement. All rights reserved.
 */

package ca.qc.johnabbott.cs4p6;

/**
 * Get a copy of the object.
 * - A simpler implementation of Java's Cloneable interface.
 *
 * @param <T>
 */
public interface Copyable<T extends Copyable<T>> {
    /**
     * Get a copy (clone) of the object.
     *
     * @return The copy.
     */
    T copy();
}
