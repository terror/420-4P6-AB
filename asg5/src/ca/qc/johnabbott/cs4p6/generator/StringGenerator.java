/*
 * Copyright (c) 2019 Ian Clement.  All rights reserved.
 */

package ca.qc.johnabbott.cs4p6.generator;

import java.util.Random;

/**
 * Generates random string from a given alphabet.
 * The length of the string is also random.
 *
 * @author Ian Clement (ian.clement@johnabbott.qc.ca)
 */
public class StringGenerator implements Generator<String> {

    private char[] alphabet;
    private int maxLength;

    public StringGenerator(char[] alphabet, int maxLength) {
        this.alphabet = alphabet;
        this.maxLength = maxLength;
    }

    public StringGenerator(String alphabet, int maxLength) {
        this(alphabet.toCharArray(), maxLength);
    }

    @Override
    public String generate(Random random) {
        int len = random.nextInt(maxLength - 1) + 1;
        StringBuilder sb = new StringBuilder();
        for(int i=0; i<len; i++)
            sb.append(alphabet[random.nextInt(alphabet.length)]);
        return sb.toString();
    }
}
