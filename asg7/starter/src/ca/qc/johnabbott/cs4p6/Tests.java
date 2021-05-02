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
    public void testSuggestionsA() {
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

        for (String word : words)
            assert (trie.contains(word));

        List<String> suggestions = trie.suggestions(prefix, 10);

        Collections.sort(words);
        Collections.sort(suggestions);

        assert (suggestions.equals(words));
    }

    @Test
    public void testSuggestionsB() {
        Trie trie = new Trie();
        String prefix = "a";

        ArrayList<String> words = new ArrayList<>() {{
            add("animal");
            add("at");
            add("axe");
            add("art");
            add("all");
            add("ale");
            add("argument");
            add("aqua");
            add("awkward");
            add("aw");
        }};

        for (String word : words)
            trie.add(word);

        for (String word : words)
            assert (trie.contains(word));

        List<String> suggestions = trie.suggestions(prefix, 5);

        assert (suggestions.size() == 5);

        // they all should match a word in `words`
        for (String word : suggestions)
            assert (words.contains(word));
    }

    @Test
    public void testSuggestionsC() {
        Trie trie = new Trie();
        String prefix = "at";

        ArrayList<String> words = new ArrayList<>() {{
            add("at");
            add("ate");
            add("atee");
            add("ateee");
        }};

        for (String word : words)
            trie.add(word);

        for (String word : words)
            assert (trie.contains(word));

        List<String> suggestions = trie.suggestions(prefix, 10);

        assert (suggestions.size() == 4);
        assert (suggestions.equals(words));

    }

    @Test
    public void testSuggestionsD() {
        Trie trie = new Trie();
        String prefix = "w";

        ArrayList<String> words = new ArrayList<>() {{
            add("q");
            add("duck");
            add("link");
            add("hey");
        }};

        for (String word : words)
            trie.add(word);

        List<String> suggestions = trie.suggestions(prefix, 10);

        // none should be found
        assert (suggestions.isEmpty());
    }
}
