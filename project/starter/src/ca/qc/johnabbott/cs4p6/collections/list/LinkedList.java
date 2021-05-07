/*
 * Copyright (c) 2021 Ian Clement. All rights reserved.
 */

package ca.qc.johnabbott.cs4p6.collections.list;

import java.util.Iterator;

/**
 * An implementation of the List interface using unidirectional links, forming a "chain".
 *
 * @author Ian Clement (ian.clement@johnabbott.qc.ca)
 */
public class LinkedList<T> implements List<T>{

    /* private inner class for link "chains" */
    private static class Link<T> {
        T element;
        Link<T> next;
        public Link(T element) {
            this.element = element;
        }
        public Link() {}
    }

    /**
     * Move a reference `i` links along the chain. Returns null if `i` exceeds chain length.
     * @param i number of links to move.
     * @return the reference to the link after `i` moves.
     */
    private Link<T> move(int i) {
        // move traversal forward i times.
        Link<T> current = head;
        for(int j=0; j<i && current != null; j++)
            current = current.next;
        return current;
    }

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
    public void add(T element) {
        if(head == null)
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
        if(position < 0 || position > size)
            throw new ListBoundsException();
        
        // when "appending" call the add(x) method
        if(position == size) {
            add(element);
            return;
        }

        if(position == 0) {
            Link<T> tmp = head;
            head = new Link<>(element);
            head.next = tmp;
        }
        else {
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
        if(position < 0 || position >= size)
            throw new ListBoundsException();

        T element;

        if(position == 0) {
            element = head.element;
            head = head.next;
            if(head.next == null)
                last = head;
        }
        else {
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
    public T get(int position){
        if(position < 0 || position >= size)
            throw new ListBoundsException();

        // move a link pointer to the desired position (point to link "position")
        Link<T> link = move(position);
        return link.element;
    }

    @Override
    public T set(int position, T element){
        if(position < 0 || position >= size)
            throw new ListBoundsException();

        // move a link pointer to the desired position (point to link "position")
        Link<T> current = move(position);
        T ret = current.element;
        current.element = element;
        return ret;
    }

    @Override
    public boolean isEmpty(){
        return size() == 0;
    }

    @Override
    public int size(){
        return size;
    }

    @Override
    public boolean contains(T element){
        // simple linear search
        Link<T> current = head;
        while(current != null) {
            if(current.element.equals(element))
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
    public boolean remove(T element) {
        throw new RuntimeException("not implemented");
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append('[');
        for(Link<T> current = head; current != null; current = current.next) {
            sb.append(current.element);
            if(current.next != null)
                sb.append(", ");
        }
        sb.append(']');
        return sb.toString();
    }


}


     
      
