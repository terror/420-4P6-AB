/*
 * Copyright (c) 2021 Ian Clement. All rights reserved.
 */

package ca.qc.johnabbott.cs4p6.collections.list;

import ca.qc.johnabbott.cs4p6.serialization.Serializable;
import ca.qc.johnabbott.cs4p6.serialization.SerializationException;
import ca.qc.johnabbott.cs4p6.serialization.Serializer;

import java.io.IOException;
import java.util.Iterator;
import java.util.Objects;

/**
 * An implementation of the List interface using unidirectional links, forming a "chain".
 *
 * @author Ian Clement (ian.clement@johnabbott.qc.ca)
 */
public class LinkedList<T extends Serializable> implements List<T>, Serializable {
    public static final byte SERIAL_ID = 0x16;

    /* a list is represented with a head "dummy" node to simplify the
     * add/remove operation implementation. */
    private Link<T> head;

    /* a last reference is used to make list append operations
     *     add(x),
     *     add(size(), x)
     * more efficient */
    private Link<T> last;

    private int size;

    public LinkedList() {
        last = head = null;
        size = 0;
    }

    @Override
    public byte getSerialId() {
        return SERIAL_ID;
    }

    @Override
    public void serialize(Serializer serializer) throws IOException {
        // write the `size` of the list, useful for deserializing
        serializer.write(size);

        // traverse the list and serialize each link `element`
        Link curr = head;
        while (curr.next != null) {
            serializer.write(curr.element);
            curr = curr.next;
        }

        // write `last`'s element
        serializer.write(last.element);
    }

    @Override
    public void deserialize(Serializer serializer) throws IOException, SerializationException {
        // read the `size` and set it on `this`
        this.size = serializer.readInt();

        // set the `head` element
        Link curr = new Link((T) serializer.readSerializable());
        this.head = curr;

        for (int i = 0; i < this.size - 1; ++i) {
            curr.next = new Link((T) serializer.readSerializable());
            curr = curr.next;
        }

        // set the `last` element
        this.last = curr;
    }

    /**
     * Move a reference `i` links along the chain. Returns null if `i` exceeds chain length.
     *
     * @param i number of links to move.
     * @return the reference to the link after `i` moves.
     */
    private Link<T> move(int i) {
        // move traversal forward i times.
        Link<T> current = head;
        for (int j = 0; j < i && current != null; j++)
            current = current.next;
        return current;
    }

    @Override
    public void add(T element) {
        if (head == null)
            head = last = new Link<>(element);
        else {
            // add a new link at the end of the list, put last accordingly
            last.next = new Link<>(element);
            last = last.next;
        }
        size++;
    }

    @Override
    public void add(int position, T element) {
        if (position < 0 || position > size)
            throw new ListBoundsException();

        // when "appending" call the add(x) method
        if (position == size) {
            add(element);
            return;
        }

        if (position == 0) {
            Link<T> tmp = head;
            head = new Link<>(element);
            head.next = tmp;
        } else {
            // move a link reference to the desired position (point to link "position")
            Link<T> current = move(position - 1);

            // place new link between "position" and "position + 1"
            Link<T> tmp = new Link<T>(element);
            tmp.next = current.next;
            current.next = tmp;
        }
        size++;
    }

    @Override
    public T remove(int position) {
        if (position < 0 || position >= size)
            throw new ListBoundsException();

        T element;

        if (position == 0) {
            element = head.element;
            head = head.next;
            if (head.next == null)
                last = head;
        } else {
            // move a link pointer to the desired position (point to link "position")
            Link<T> current = move(position - 1);

            element = current.next.element;
            current.next = current.next.next;

            // reset the last if we're removing the last link
            if (current.next == null)
                last = current;
        }
        size--;

        return element;
    }

    @Override
    public void clear() {
        head = last = null; // remove all the links
        size = 0;
    }

    @Override
    public T get(int position) {
        if (position < 0 || position >= size)
            throw new ListBoundsException();

        // move a link pointer to the desired position (point to link "position")
        Link<T> link = move(position);
        return link.element;
    }

    @Override
    public T set(int position, T element) {
        if (position < 0 || position >= size)
            throw new ListBoundsException();

        // move a link pointer to the desired position (point to link "position")
        Link<T> current = move(position);
        T ret = current.element;
        current.element = element;
        return ret;
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean contains(T element) {
        // simple linear search
        Link<T> current = head;
        while (current != null) {
            if (current.element.equals(element))
                return true;
            current = current.next;
        }
        return false;
    }

    @Override
    public Iterator<T> iterator() {
        throw new RuntimeException("Not implemented.");
    }

    @Override
    public List<T> subList(int from, int to) {
        throw new RuntimeException("Not implemented.");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LinkedList<?> that = (LinkedList<?>) o;

        // check for the same `size`
        if (that.size != this.size)
            return false;

        Link a = that.head;
        Link b = this.head;

        // check for each link `element` equality
        while (a.next != null && b.next != null) {
            if (!a.element.equals(b.element))
                return false;
            a = a.next;
            b = b.next;
        }

        return this.last.element.equals(that.last.element) ? true : false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(head, last, size);
    }

    @Override
    public boolean remove(T element) {
        throw new RuntimeException("not implemented");
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append('[');
        for (Link<T> current = head; current != null; current = current.next) {
            sb.append(current.element);
            if (current.next != null)
                sb.append(", ");
        }
        sb.append(']');
        return sb.toString();
    }

    /* private inner class for link "chains" */
    private static class Link<T extends Serializable> {
        T element;
        Link<T> next;

        public Link(T element) {
            this.element = element;
        }

        public Link() {
        }
    }
}


     
      
