package ca.qc.johnabbott.cs4p6;

import java.util.Scanner;

public class Main {
    public static void main(String args[]) {
        Scanner scanner = new Scanner(System.in);

        // scan input and validate for given constraints
        // 0 < n <= 26
        // 0 <= m < n
        // 0 < o

        System.out.print("n> ");
        int n = scanner.nextInt();
        while (n < 0 || n > 26) {
            System.out.println("n must be between 0 and 26 (0 < n <= 26).");
            System.out.print("n> ");
            n = scanner.nextInt();
        }

        System.out.print("m> ");
        int m = scanner.nextInt();
        while (m < 0 || m > n) {
            System.out.println("m must be between 0 and n (0 < m <= n).");
            System.out.print("m> ");
            m = scanner.nextInt();
        }

        System.out.print("o> ");
        int o = scanner.nextInt();
        while (o < 0) {
            System.out.println("o must be greater than 0 (o > 0).");
            System.out.print("o> ");
            o = scanner.nextInt();
        }

        // create circular linked list of given size n
        LinkedList<Character> list = construct(n);

        // get link at position m (our exit position)
        LinkedList.DoubleLink start = list.get(m);
        LinkedList.DoubleLink it = start;
        Character itElement = (Character) it.element;

        // create the exit
        start.element = null;

        // advance `it`
        it = it.next;

        // simulate the process until the list size is one (only containing the exit)
        while (list.size() != 1) {

            // traverse until we reach tagged
            int count = 1;
            while (count < o) {
                // advance but don't increment count if we encounter exit
                if (it.next.element == null) {
                    it = it.next;
                    continue;
                }
                it = it.next;
                ++count;
            }

            // get and remove tagged link from the list
            LinkedList.DoubleLink tagged = list.remove(it);
            Character taggedElement = (Character) it.element;

            // place `it` at their starting position
            it = tagged.next;

            // start the race
            // tagged goes left (counter-clockwise), it goes right (clockwise)
            while (true) {
                // check for tie
                if (it.element == null && tagged.element == null) {
                    System.out.println(String.format("%c escaped!", taggedElement));
                    it = it.next;
                    break;
                }

                // check if `it` has reached the exit
                if (it.element == null) {
                    System.out.println(String.format("%c escaped!", itElement));
                    it = tagged;
                    itElement = taggedElement;
                    break;
                }

                // check if tagged has reached the exit
                if (tagged.element == null) {
                    System.out.println(String.format("%c escaped!", taggedElement));
                    break;
                }

                // advance `it`
                it = it.next;

                // advance tagged
                tagged = tagged.prev;
            }
        }
        System.out.println(String.format("%c is the loser.", itElement));
    }

    // construct a linked list of size n
    public static LinkedList construct(int n) {
        int ascii_start = 65;
        LinkedList<Character> list = new LinkedList<>();
        for (int i = 0; i < n; ++i)
            list.add((char) ascii_start++);
        return list;
    }

    public static class ListBoundsException extends RuntimeException {
    }

    public static class LinkedList<T> {
        private DoubleLink head;
        private DoubleLink last;
        private int size;

        public LinkedList() {
            head = last = null;
            size = 0;
        }

        public void add(T element) {
            DoubleLink newNode = new DoubleLink<>(element);
            // check if list is empty
            if (head == null) {
                newNode.next = newNode.prev = newNode;
                head = newNode;
                last = head;
            } else {
                // add new link to the end of the list
                newNode.prev = last;
                last.next = head.prev = newNode;
                newNode.next = head;
                last = newNode;
            }
            // increment size
            ++size;
        }

        public DoubleLink remove(DoubleLink link) {
            // check if list is empty
            if (head == null)
                return null;

            // find link to remove
            DoubleLink curr = head, prev = null;
            while (curr.element != link.element) {
                prev = curr;
                curr = curr.next;
            }

            // check if link is the only link in the list
            if (curr == head && curr.next == head) {
                head = null;
                return null;
            }

            // remove link from list
            if (curr == head) {
                prev = head;
                while (prev.next != head)
                    prev = prev.next;
                head = curr.next;
                prev.next = head;
            } else if (curr.next == head)
                prev.next = head;
            else
                prev.next = curr.next;

            --size;
            return prev;
        }

        public DoubleLink get(int position) {
            if (position < 0 || position >= size)
                throw new ListBoundsException();

            // get link at given position
            DoubleLink<T> curr = head;
            for (int i = 0; i < position; ++i)
                curr = curr.next;
            return curr;
        }

        public int size() {
            return size;
        }

        public static class DoubleLink<T> {
            public T element;
            public DoubleLink<T> next;
            public DoubleLink<T> prev;

            public DoubleLink(T element) {
                this.element = element;
            }
        }
    }
}