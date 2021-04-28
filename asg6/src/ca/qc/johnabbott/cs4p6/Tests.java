package ca.qc.johnabbott.cs4p6;

import org.junit.Test;


public class Tests {
    @Test
    public void testGameA() {
        Game game = new ThreeMensMorris();

        game.play('a', '1');
        game.play('b', '2');
        game.play('a', '3');
        game.play('a', '2');
        game.play('c', '1');
        game.play('c', '2');

        assert (game.winner() == Game.Token.BLACK);
    }

    @Test
    public void testGameB() {
        Game game = new ThreeMensMorris();

        game.play('b', '2');
        game.play('c', '1');
        game.play('a', '2');
        game.play('c', '2');
        game.play('c', '1');
        game.play('a', '1');

        assert (game.winner() == Game.Token.NONE);
    }

    @Test
    public void testGameC() {
        Game game = new ThreeMensMorris();

        game.play('b', '1');
        game.play('c', '3');
        game.play('a', '1');
        game.play('c', '2');
        game.play('c', '1');

        assert (game.winner() == Game.Token.WHITE);
    }

    @Test
    public void testGenerate() {
        ThreeMensMorris game = new ThreeMensMorris();

        game.play('b', '2');
        game.play('c', '1');
        game.play('a', '2');
        game.play('c', '2');

        assert (Main.generate(game).size() == 20);
    }
}
