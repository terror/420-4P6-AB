package ca.qc.johnabbott.cs4p6;

/**
 * Constants storing alphabets and lexicon files
 */
public class Alphabets {

    // hide the constructor --> utility class
    private Alphabets() {};

    public static final char[] FULL_ALPHABET = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    public static final char[] ABCDE_ALPHABET = "abcde".toCharArray();

    public static final String LEXICON_FULL = "starter/lexicon/words-linux-cleaned.txt";
    public static final String LEXICON_ABCDE_ONLY = "lexicon/words-linux-cleaned-abcde.txt";

    // can get from http://wordlist.aspell.net/
    // warning: may contain offensive words
    public static final String LEXICON_CANADIAN_60 = "words-canadian-60.txt";
    public static final String LEXICON_CANADIAN_80 = "words-canadian-80.txt";

}
