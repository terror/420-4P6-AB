package ca.qc.johnabbott.cs4p6;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ThreeMensMorris implements Copyable<ThreeMensMorris>, Game {
    private final int N = 3;
    private Token[][] tokens;
    private Token turn;

    public ThreeMensMorris() {
        tokens = new Token[3][];

        tokens[0] = new Token[3];
        Arrays.fill(tokens[0], Token.NONE);

        tokens[1] = new Token[3];
        Arrays.fill(tokens[1], Token.NONE);

        tokens[2] = new Token[3];
        Arrays.fill(tokens[2], Token.NONE);

        turn = Token.WHITE;
    }

    @Override
    public boolean play(char file, char rank) {
        int filePos = file - 'a';
        int rankPos = rank - '1';

        if (filePos < 0 || filePos > 2 || rankPos < 0 || rankPos > 2)
            throw new IllegalArgumentException();

        if (tokens[rankPos][filePos] != Token.NONE) {
            return false;
        }
        tokens[rankPos][filePos] = turn;
        turn = turn.opposite();

        return true;
    }

    @Override
    public Token winner() {
        // check rows and columns
        for (int i = 0; i < N; ++i) {
            int r = 0, c = 0;
            for (int j = 0; j < N; ++j) {
                // assign white a value of 1 and black a value of -1
                r += tokens[i][j] != Token.NONE ? tokens[i][j] == Token.WHITE ? 1 : -1 : 0;
                c += tokens[j][i] != Token.NONE ? tokens[j][i] == Token.WHITE ? 1 : -1 : 0;
            }
            // return the winning token
            if (Math.abs(c) == 3 || Math.abs(r) == 3)
                return Math.abs(c) == 3 ? c < 0 ? Token.BLACK : Token.WHITE : r < 0 ? Token.BLACK : Token.WHITE;
        }

        // check diagonals
        int main = 0, anti = 0;

        for (int i = 0; i < N; ++i) {
            // assign white a value of 1 and black a value of -1
            main += tokens[i][i] != Token.NONE ? tokens[i][i] == Token.WHITE ? 1 : -1 : 0;
            anti += tokens[i][N - (i + 1)] != Token.NONE ? tokens[i][N - (i + 1)] == Token.WHITE ? 1 : -1 : 0;
        }

        // return the winning token
        if (Math.abs(main) == 3 || Math.abs(anti) == 3)
            return Math.abs(main) == 3 ? main < 0 ? Token.BLACK : Token.WHITE : anti < 0 ? Token.BLACK : Token.WHITE;

        // no one has won the game
        return Token.NONE;
    }

    public boolean isFull() {
        int numTokens = 0;
        for (int i = 0; i < N; ++i) {
            for (int j = 0; j < N; ++j)
                if (tokens[i][j] != Token.NONE)
                    ++numTokens;
        }
        return numTokens == 6;
    }

    public List<Pair> moves() {
        ArrayList<Pair> p = new ArrayList<>();
        for (int i = 0; i < N; ++i) {
            for (int j = 0; j < N; ++j) {
                if (tokens[i][j] == Token.NONE)
                    p.add(new Pair(j, i));
            }
        }
        return p;
    }

    @Override
    public ThreeMensMorris copy() {
        ThreeMensMorris copy = new ThreeMensMorris();
        for (int i = 0; i < N; ++i) {
            for (int j = 0; j < N; ++j)
                copy.tokens[i][j] = tokens[i][j];
        }
        copy.turn = this.turn;
        return copy;
    }

    @Override
    public String toString() {
        String ret = "[";
        Token winner = winner();

        // iterate over cells and append circles
        for (int i = 0; i < N; ++i) {
            for (int j = 0; j < N; ++j)
                ret += tokens[i][j].toString();
        }

        // close the bracket
        ret += "] ";

        // append the turn
        ret += String.format("turn=%s ", turn.toString());

        // append the winner
        ret += String.format("winner=%s", winner != Token.NONE ? winner.toString() : "NONE");

        return ret;
    }

    @Override
    public boolean equals(Object o) {
        ThreeMensMorris other = (ThreeMensMorris) o;
        // n^2 check to see if two boards are the same
        for (int i = 0; i < N; ++i) {
            for (int j = 0; j < N; ++j)
                if (other.tokens[i][j] != tokens[i][j]) return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(tokens);
    }
}
