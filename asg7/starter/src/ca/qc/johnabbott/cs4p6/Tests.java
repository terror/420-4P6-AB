package ca.qc.johnabbott.cs4p6;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Tests {
    @Test
    public void testContains() {
        Trie trie = new Trie();
        assert (!trie.contains("hello"));

        trie.add("hello");
        assert (trie.contains("hello"));
    }

    @Test
    public void testAdd() {
        Trie trie = new Trie();
        trie.add("hello");
        trie.add("bonjour");
        trie.add("dijkstra");

        assert (trie.contains("hello"));
        assert (trie.contains("bonjour"));
        assert (trie.contains("dijkstra"));
    }

    @Test
    public void testSuggestions() {
        Trie trie = new Trie();
        String prefix = "hel";

        ArrayList<String> words = new ArrayList<>() {{
            add("helicopter");
            add("heliocentric");
            add("helpline");
            add("hellions");
            add("helper");
            add("helpmates");
            add("helga");
            add("helipad");
            add("helluva");
            add("helpings");
        }};

        for (String word : words)
            trie.add(word);

        List<String> suggestions = trie.suggestions(prefix, 10);

        Collections.sort(words);
        Collections.sort(suggestions);

        assert (suggestions.equals(words));
    }
}
