package ca.qc.johnabbott.cs4p6;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * This is not a real implementation of a lexicon. Delete it very soon.
 *
 * @author needs more sleep...
 */
public class BadLexicon implements Lexicon {

    private Random random;
    private char[] alphabet;

    public BadLexicon(char[] alphabet) {
        random = new Random();
        this.alphabet = alphabet;
    }

    @Override
    public void add(String word) {
        System.out.printf("Bad lexicon is boldly ignoring %s.\n", word);
    }

    @Override
    public boolean contains(String word) {
        System.out.printf("Bad lexicon doesn't know how to %s.\n", word);
        return false;
    }

    @Override
    public List<String> suggestions(String prefix, int limit) {

        System.out.printf("Bad lexicon has suggestions for you.\n");
        System.out.flush();

        // This has absolutely nothing to do with the actual solution...

        List<String> list = new ArrayList<>(limit);
        for (int i = 0; i < limit; i++) {
            int size = random.nextInt(5);

            char[] fakeWord = prefix.toCharArray();
            for (int j = 0; j < size; j++) {
                int x = random.nextInt(fakeWord.length);
                int y = random.nextInt(fakeWord.length);
                char tmp = fakeWord[x];
                fakeWord[x] = fakeWord[y];
                fakeWord[y] = tmp;
            }

            list.add(new String(fakeWord));
        }
        return list;
    }
}
