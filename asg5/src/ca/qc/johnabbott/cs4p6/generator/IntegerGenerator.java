package ca.qc.johnabbott.cs4p6.generator;

import java.util.Random;

/**
 * Generate random integers.
 */
public class IntegerGenerator implements Generator<Integer> {

    @Override
    public Integer generate(Random random) {
        return random.nextInt();
    }
}
