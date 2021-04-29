package ca.qc.johnabbott.cs4p6;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;


public class Main {
    private static char[] file = {'a', 'b', 'c'};
    private static char[] rank = {'1', '2', '3'};

    public static void main(String[] args) {
        ThreeMensMorris game = new ThreeMensMorris();

        game.play('a', '2');
        game.play('c', '1');
        game.play('b', '2');
        game.play('c', '2');

        System.out.println("Initial board: " + game);

        List<ThreeMensMorris> all = generate(game);

        for (ThreeMensMorris g : all)
            System.out.println(g);

        System.out.println("Number of possible board configurations: " + all.size());
    }

    public static List<ThreeMensMorris> generate(ThreeMensMorris initial) {
        HashSet<ThreeMensMorris> ret = new HashSet<>();
        generateHelper(initial, ret);
        return new ArrayList<>(ret);
    }

    public static void generateHelper(ThreeMensMorris current, HashSet<ThreeMensMorris> acc) {
        // go through all possible moves
        for (Pair move : current.moves()) {
            ThreeMensMorris board = current.copy();

            // play the current position
            board.play(file[move.x], rank[move.y]);

            // add board is it's full
            if (board.isFull())
                acc.add(board);

            // recursive step
            generateHelper(board, acc);
        }
    }
}
