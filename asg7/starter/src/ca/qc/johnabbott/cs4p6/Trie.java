package ca.qc.johnabbott.cs4p6;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Trie implements Lexicon {
    // store the root node
    private Node root = new Node(false);

    @Override
    public void add(String word) {
        add(root, word, 0);
    }

    public void add(Node node, String word, int iter) {
        // if we're still traversing the string,
        // add the node to the end of the trie
        if (iter < word.length()) {
            if (!node.children.containsKey(word.charAt(iter)))
                node.children.put(word.charAt(iter), new Node(false));
            add(node.children.get(word.charAt(iter)), word, iter + 1);
        } else
            // we're done traversing, the current node is a leaf node
            node.leaf = true;
    }


    @Override
    public boolean contains(String word) {
        return contains(root, word, 0, word.length() + 1);
    }

    public boolean contains(Node node, String word, int iter, int size) {
        // if we've overstepped, the trie does not contain `word`
        if (node == null || (iter == size - 1 && !node.leaf))
            return false;

        // are we at a leaf node? if so, the trie contains `word`
        if (node.leaf && iter == size - 1)
            return true;

        // recursive step
        return contains(node.children.get(word.charAt(iter)), word, iter + 1, size);
    }

    public boolean hasPrefix(Node node, String prefix, int iter, int size) {
        // if we've overstepped, the trie does not contain `word`
        if (node == null)
            return false;

        // we're at the prefix
        if (iter == size - 1)
            return true;

        // recursive step
        return hasPrefix(node.children.get(prefix.charAt(iter)), prefix, iter + 1, size);
    }

    @Override
    public List<String> suggestions(String prefix, int limit) {
        ArrayList<String> acc = new ArrayList<>();

        // return an empty list if the trie does not contain `prefix`
        if (!hasPrefix(root, prefix, 0, prefix.length() + 1))
            return new ArrayList<>();

        // traverse until last prefix node
        Node node = root;
        for (int i = 0; i < prefix.length(); ++i)
            node = node.children.get(prefix.charAt(i));

        // call our recursive helper method
        suggestionsHelper(node, prefix, limit, acc);

        // return the suggestions list
        return acc;
    }

    public void suggestionsHelper(Node node, String prefix, int limit, List<String> acc) {
        // check if our list has reached the limit capacity
        if (acc.size() == limit)
            return;

        // add the prefix if we're at a leaf node
        if (node.leaf)
            acc.add(prefix);

        // traverse all children
        for (Map.Entry item : node.children.entrySet())
            suggestionsHelper((Node) item.getValue(), prefix + item.getKey(), limit, acc);
    }

    private static class Node {
        // is this a leaf node? (end of word)
        public boolean leaf;
        // the children collection
        public Map<Character, Node> children = new HashMap<>();

        public Node(boolean leaf) {
            this.leaf = leaf;
        }
    }
}
