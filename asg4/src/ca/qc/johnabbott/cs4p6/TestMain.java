package ca.qc.johnabbott.cs4p6;

import org.junit.Test;

public class TestMain {
    @Test
    public void testConstruct() {
        int n = 5;
        Main.LinkedList<Character> list = Main.construct(n);
        assert (list.size() == 5);
    }
}
