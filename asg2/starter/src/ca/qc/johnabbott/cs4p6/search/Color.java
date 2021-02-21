/*
 * Copyright (c) 2021 Ian Clement. All rights reserved.
 */

package ca.qc.johnabbott.cs4p6.search;

import ca.qc.johnabbott.cs4p6.collections.AsChar;
import ca.qc.johnabbott.cs4p6.terrain.Token;

/**
 * <p>The used by the search algorithms.</p>

 * @author Ian Clement
 */
public enum Color implements AsChar {
    NONE, WHITE, BLACK, GREY;

    @Override
    public char toChar() {
        switch (this) {
            case GREY:
                return Token.GREY_TOKEN.toChar();
            case BLACK:
                return Token.BLACK_TOKEN.toChar();
            default:
                return Token.EMPTY.toChar();
        }
    }
}
