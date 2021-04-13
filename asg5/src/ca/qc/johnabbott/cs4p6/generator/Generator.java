/*
 * Copyright (c) 2019 Ian Clement.  All rights reserved.
 */

package ca.qc.johnabbott.cs4p6.generator;

import java.util.Random;

/**
 * Random Generator interface
 *
 * @author Ian Clement (ian.clement@johnabbott.qc.ca)
 */
public interface Generator<T> {

    /**
     * Generate a random value.
     * @param random random number generator to base the value on.
     * @return
     */
    T generate(Random random);
}
