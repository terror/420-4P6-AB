package ca.qc.johnabbott.cs4p6;

import java.util.ArrayList;
import java.util.List;


public class Main {
    private static final int N = 3;
    static char[] file = {'a', 'b', 'c'};
    static char[] rank = {'1', '2', '3'};

    public static void main(String[] args) {
        ThreeMensMorris game = new ThreeMensMorris();

        game.play('a', '2');
        game.play('c', '1');
        game.play('b', '2');
        game.play('c', '2');

        System.out.println("Initial board: " + game);

        List<ThreeMensMorris> configurations = generate(game);
        for (ThreeMensMorris config : configurations)
            System.out.println(config);

        System.out.println("Number of possible board configurations: " + configurations.size());
    }

    public static List<ThreeMensMorris> generate(ThreeMensMorris initial) {
        List<ThreeMensMorris> ret = new ArrayList<>();
        generateHelper(initial, ret, new Color(), new Color());
        return ret;
    }

    public static void generateHelper(ThreeMensMorris current, List<ThreeMensMorris> acc, Color white, Color black) {
        if (black.rank == N)
            return;

        ThreeMensMorris copy = current.copy();

        // play our current color positions
        // add the configuration if it's full and has not already been seen

        if (copy.play(file[white.file], rank[white.rank]) && copy.isFull() && !acc.contains(copy))
            acc.add(copy);

        if (copy.play(file[black.file], rank[black.rank]) && copy.isFull() && !acc.contains(copy))
            acc.add(copy);

        /*
        go through all possibilities of color file and rank positions

         white  |  black
          0 0   |   0 0
          1 0   |   0 0
          2 0   |   0 0
          0 1   |   0 0
          1 1   |   0 0
          2 1   |   0 0
          0 2   |   0 0
          1 2   |   0 0
          2 2   |   0 0
            ... etc
         */

        if (white.file < N - 1)
            white.set(white.file + 1, white.rank);
        else
            white.set(0, white.rank + 1);


        if (white.rank == N) {
            white.set(white.file, 0);
            black.set(black.file + 1, black.rank);
        }

        if (black.file == N)
            black.set(0, black.rank + 1);

        // recursive step
        generateHelper(current, acc, white, black);
    }

    // representing a color with file and rank values
    public static class Color {
        public int file;
        public int rank;

        public Color() {
            this.file = 0;
            this.rank = 0;
        }

        public void set(int file, int rank) {
            this.file = file;
            this.rank = rank;
        }
    }
}
