package ca.qc.johnabbott.cs4p6;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        Game game = new ThreeMensMorris();
        game.play('a', '1');
        game.play('b', '2');
        game.play('a', '3');
        game.play('a', '2');
        game.play('c', '1');
        game.play('c', '2');
        System.out.println(game);
        System.out.println(game.winner());
    }

    public static List<ThreeMensMorris> generate(ThreeMensMorris initial) {
        return null;
    }

    public static void generateHelper(ThreeMensMorris current, List<ThreeMensMorris> acc) {
        return;
    }
}
