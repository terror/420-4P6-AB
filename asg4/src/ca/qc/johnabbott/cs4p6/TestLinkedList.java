package ca.qc.johnabbott.cs4p6;

import org.junit.Test;

public class TestLinkedList {
    @Test
    public void testAdd() {
        int n = 5;
        Main.LinkedList<Character> list = Main.construct(n);
        list.add('I');
        assert (list.size() == 6);
    }

    @Test
    public void testRemove() {
        int n = 5;
        Main.LinkedList<Character> list = Main.construct(n);
        list.remove(list.get(1));
        assert (list.size() == 4);

        var start = list.get(0);

        assert (start.next.element.equals('C'));

        var curr = start.next;
        while (curr.element != start.element) {
            curr = curr.next;
        }
        assert (curr.element == start.element);
    }

    @Test
    public void testCircularClockwise() {
        int n = 5;
        Main.LinkedList<Character> list = Main.construct(n);
        var start = list.get(0);
        var curr = start.next;
        while (curr.element != start.element) {
            curr = curr.next;
        }
        assert (curr.element == start.element);
    }


    @Test
    public void testCircularCounterClockwise() {
        int n = 5;
        Main.LinkedList<Character> list = Main.construct(n);
        var start = list.get(n - 1);
        var curr = start.prev;
        while (curr.element != start.element) {
            curr = curr.prev;
        }
        assert (curr.element == start.element);
    }

    @Test
    public void testGet() {
        int n = 5;
        Main.LinkedList<Character> list = Main.construct(n);
        var got = list.get(1);
        assert (got.prev == list.get(0));
        assert (got.next == list.get(2));
        assert (got.element == (Character) 'B');
    }
}
