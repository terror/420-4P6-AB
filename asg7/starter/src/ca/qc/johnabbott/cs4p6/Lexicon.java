package ca.qc.johnabbott.cs4p6;

import java.util.List;

/**
 * Abstract representation of a lexicon.
 *
 * @author Ian Clement (ian.clement@johnabbott.qc.ca)
 */
public interface Lexicon {

    /**
     * Add a word to the lexicon.
     * @param word the word to add to the lexicon.
     */
    void add(String word);


    /**
     * Test if a word is in the lexicon.
     * @param word the word to check.
     * @return true if the word is in the lexicon, false otherwise.
     */
    boolean contains(String word);

    /**
     * Produce a list of suggested words given a prefix.
     * @param prefix the word prefix to begin with.
     * @param limit the maximum number of words to suggest.
     * @return
     */
    List<String> suggestions(String prefix, int limit);
}
