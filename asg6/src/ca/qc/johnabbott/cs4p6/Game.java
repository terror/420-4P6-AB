/*
 * Copyright (c) 2021 Ian Clement. All rights reserved.
 */

package ca.qc.johnabbott.cs4p6;

/**
 * The structure of a basic 2-player board game.
 */
public interface Game {

    boolean DARK_MODE = true;

    /**
     * Represents a Token
     */
    enum Token {
        NONE, WHITE, BLACK;

        public Token opposite() {
            switch (this) {
                case WHITE:
                    return BLACK;
                case BLACK:
                    return WHITE;
                default:
                    return NONE;
            }
        }

        @Override
        public String toString() {
            switch (this) {
                case NONE:
                    return " ";
                case BLACK:
                    return DARK_MODE ? "\u25CB" : "\u25CF";
                case WHITE:
                    return DARK_MODE ? "\u25CF" : "\u25CB";
            }
            return null;
        }
    }


    /**
     * Play the current piece at the specified position
     *
     * @param file The file of
     * @param rank
     * @return True if the move was performed, false otherwise.
     */
    boolean play(char file, char rank);

    /**
     * Determine the winner of the game.
     *
     * @return The winner of the game, or NONE if there is no winner yet.
     */
    Token winner();
}
