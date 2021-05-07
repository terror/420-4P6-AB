/*
 * Copyright (c) 2021 Ian Clement. All rights reserved.
 */

package ca.qc.johnabbott.cs4p6.collections.set;

import java.util.Iterator;

/**
 * An implementation of the Set API using a binary search tree.
 *
 * @author Ian Clement (ian.clement@johnabbott.qc.ca)
 */
public class TreeSet<T extends Comparable<T> > implements Set<T> {

    private class Node<T> {
        public T element;
        public Node<T> left;
        public Node<T> right;

        public Node(T element) {
            this.element = element;
        }
    }

    // fields: store the root of ths bst and the size.
    private Node<T> root;
    private int size;


    @Override
    public boolean add(T elem) {
        if(root == null) {
            root = new Node<>(elem);
            size++;
            return true;
        }
        else
            return add(root, elem);
    }

    /**
     * Recursive helper method to add elem as a leaf in the BST.
     * - precondition: current != null
     * @param current
     * @param elem
     * @return true if elem is added, false if it is already in the tree.
     */
    private boolean add(Node<T> current, T elem) {
        int cmp = elem.compareTo(current.element);
        if(cmp == 0)
            return false;
        if(cmp < 0) {  // elem is in left subtree
            if(current.left == null) {
                current.left = new Node<>(elem);
                size++;
                return true;
            }
            else
               return add(current.left, elem);
        }
        else { // elem is in right subtree
            if(current.right == null) {
                current.right = new Node<>(elem);
                size++;
                return true;
            }
            else
                return add(current.right, elem);
        }
    }


    @Override
    public boolean contains(T elem) {
        return contains(root, elem);
    }

    /**
     * Recursively search the tree to check for the element.
     * - uses BST property to optimize the search: check only the subtree that
     *   can possibly have the element.
     * @param current
     * @param elem
     * @return
     */
    private boolean contains(Node<T> current, T elem) {
        if(current == null)
            return false;

        int cmp = elem.compareTo(current.element);
        if(cmp == 0)
            return true;
        if(cmp < 0)
            return contains(current.left, elem);
        else
            return contains(current.right, elem);
    }

    @Override
    public boolean containsAll(Set<T> rhs) {
        return false;
    }

    @Override
    public T floor(T value) {
        return floor(root, value);
    }

    private T floor(Node<T> current, T value) {
        if (current == null)
            return null;
        int c = value.compareTo(current.element);
        if (c == 0)
            return current.element;
        if (c > 0) {
            T elem = floor(current.right, value);
            if(elem == null)
                return current.element;
            else
                return elem;
        }
        else {
            T elem = floor(current.left, value);
            if(elem == null)
                return null;
            else
                return elem;
        }
    }

            @Override
    public boolean remove(T elem) {
        return removeHelper(root, null, elem);
    }

    /**
     * Recurive helper method to remove an element.
     * @param current the current node.
     * @param parent the parent of the current node (null implies current == root)
     * @param elem the element t
     * @return the value removed
     */
    private boolean removeHelper(Node<T> current, Node<T> parent, T elem) {

        // binary search is unsuccessful
        if(current == null)
            return false;

        int cmp = elem.compareTo(current.element);

        // node found:
        if(cmp == 0) {

            // if we need to remove an internal node with two children
            // find the successor in the left subtree and replace the current entry
            // with the successor's entry.
            if(current.left != null && current.right != null) {

                Node<T> tmp = current; // store the current node to replace it's entry

                // Ensure that `current` is the successor and that `parent` is it's parent
                // so that we remove this node below
                current = current.left;
                while(current.right != null) {
                    parent = current;
                    current = current.right;
                }
                tmp.element = current.element;
            }

            removeNode(current, parent);
            size--;
            return true;
        }

        // descend into the subtree that could contain the node
        if(cmp < 0)
            return removeHelper(current.left, current, elem);
        else
            return removeHelper(current.right, current, elem);
    }

    private void removeNode(Node<T> current, Node<T> parent) {

        // case 1: root
        if(current == root) {
            if(current.left == null)
                root = current.right;
            else
                root = current.left;
        }

        // case 2: left subtree of parent
        else if(current == parent.left) {
            if(current.left == null)
                parent.left = current.right;
            else
                parent.left = current.left;
        }

        // case 3: right subtree of parent
        else {
            if(current.right == null)
                parent.right = current.left;
            else
                parent.right = current.right;
        }
    }



    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean isFull() {
        return false;
    }

    @Override
    public T[] toArray() {
        return null;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Iterator<T> iterator() {
        throw new RuntimeException("Not implemented.");
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        toStringHelper(root, builder);
        return builder.toString();
    }

    private void toStringHelper(Node<T> current, StringBuilder builder) {
        if(current == null)
            return;
        toStringHelper(current.left, builder);
        builder.append(current.element);
        toStringHelper(current.right, builder);
    }


}
