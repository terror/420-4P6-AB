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
    public void testGameD() {
        Game game = new ThreeMensMorris();

        game.play('a', '1');
        game.play('c', '2');
        game.play('b', '2');
        game.play('c', '1');
        game.play('c', '3');

        assert (game.winner() == Game.Token.WHITE);
    }

    @Test
    public void testGameE() {
        Game game = new ThreeMensMorris();

        game.play('a', '3');
        game.play('c', '2');
        game.play('b', '2');
        game.play('b', '1');
        game.play('c', '1');

        assert (game.winner() == Game.Token.WHITE);
    }

    @Test
    public void testEquals() {
        Game a = new ThreeMensMorris();

        a.play('a', '3');
        a.play('c', '2');
        a.play('b', '2');
        a.play('b', '1');
        a.play('c', '1');

        Game b = new ThreeMensMorris();

        b.play('a', '3');
        b.play('c', '2');
        b.play('b', '2');
        b.play('b', '1');
        b.play('c', '1');

        assert (b.equals(a));
        assert (a.equals(b));
    }

    @Test
    public void testFull() {
        ThreeMensMorris a = new ThreeMensMorris();

        a.play('a', '3');
        a.play('c', '2');
        a.play('b', '2');
        a.play('b', '1');
        a.play('c', '1');

        assert (!a.isFull());

        ThreeMensMorris b = new ThreeMensMorris();

        b.play('a', '3');
        b.play('c', '2');
        b.play('b', '2');
        b.play('b', '1');
        b.play('c', '1');
        b.play('b', '3');

        assert (b.isFull());
    }

    @Test
    public void testCopy() {
        ThreeMensMorris a = new ThreeMensMorris();

        a.play('a', '3');
        a.play('c', '2');
        a.play('b', '2');
        a.play('b', '1');
        a.play('c', '1');

        ThreeMensMorris b = a.copy();

        assert (a != b);
    }

    @Test
    public void testToString() {
        ThreeMensMorris game = new ThreeMensMorris();

        game.play('a', '2');
        game.play('a', '1');
        game.play('b', '2');
        game.play('c', '1');
        game.play('c', '3');
        game.play('c', '2');

        assert (game.winner() == Game.Token.NONE);
        assert (game.isFull());
        System.out.println(game);
        assert (game.toString().equals("[○ ○●●○  ●] turn=● winner=NONE"));
    }

    @Test
    public void testGenerateA() {
        ThreeMensMorris game = new ThreeMensMorris();

        game.play('b', '2');
        game.play('c', '1');
        game.play('a', '2');
        game.play('c', '2');

        assert (Main.generate(game)).size() == 20;
    }

    @Test
    public void testGenerateB() {
        ThreeMensMorris game = new ThreeMensMorris();
        assert (Main.generate(game)).size() == 1680;
    }
}
