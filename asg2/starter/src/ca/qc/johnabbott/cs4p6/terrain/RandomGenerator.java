/*
 * Copyright (c) 2021 Ian Clement. All rights reserved.
 */

package ca.qc.johnabbott.cs4p6.terrain;

import java.util.Random;

/**
 * Interface for random value generator.
 *
 * @author Ian Clement (ian.clement@johnabbott.qc.ca)
 */
public interface RandomGenerator<T> {

    /**
     * Generate a random value.
     * @param random the random number generator.
     * @return a random value.
     */
    T generate(Random random);
}
