/*
 * Copyright (c) 2021 Ian Clement. All rights reserved.
 */

package ca.qc.johnabbott.cs4p6.terrain;

import ca.qc.johnabbott.cs4p6.collections.AsChar;

import java.util.Random;

/**
 * <p>The directions used to move position.</p>

 * @author Ian Clement
 */
public enum Direction implements AsChar {

    NONE, UP, DOWN, LEFT, RIGHT;

    private static Direction[] clockwise;
    private static Direction[] counterClockwise;

    static {
        clockwise = new Direction[]{UP, RIGHT, DOWN, LEFT};
        counterClockwise = new Direction[] { LEFT, DOWN, RIGHT, UP };
    }

    @Override
    public char toChar() {
        switch (this) {
            case RIGHT:
                return '>';
            case LEFT:
                return '<';
            case UP:
                return '^';
            case DOWN:
                return 'v';
            case NONE:
                return ' ';
        }
        return ' ';
    }

    /**
     * Generates random directions.
     */
    private static class DirectionRandomGenerator implements RandomGenerator<Direction> {

        @Override
        public Direction generate(Random random) {
            return clockwise[random.nextInt(clockwise.length)];
        }
    }

    /**
     * Get a random direction generator.
     * @return
     */
    public static RandomGenerator<Direction> generator() {
        return new DirectionRandomGenerator();
    }

    /**
     * Get directions clockwise from UP.
     * @return
     */
    public static Direction[] getClockwise() {
        return clockwise;
    }

    /**
     * Get directions counterclockwise from LEFT.
     * @return
     */
    public static Direction[] getCounterClockwise() {
        return counterClockwise;
    }

    /**
     * Get the opposite direction of the current direction.
     * @return
     */
    public Direction opposite() {
        switch(this) {
            case UP:
                return DOWN;
            case DOWN:
                return UP;
            case LEFT:
                return RIGHT;
            case RIGHT:
                return LEFT;
        }
        return NONE;
    }
}
