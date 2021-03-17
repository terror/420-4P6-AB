package ca.qc.johnabbott.cs4p6;

import java.io.IOException;
import java.io.Writer;
import java.util.LinkedHashSet;
import java.util.Set;

public class SubstitutionCipherWriter extends BaseWriter {
    private String keyword;
    private String alphabet = "abcdefghijklmnopqrstuvwxyz";

    public SubstitutionCipherWriter(Writer writer, String keyword) {
        super(writer);
        this.keyword = keyword;
    }

    @Override
    public void write(char cbuf[], int off, int len) throws IOException {
        // compute alphabet
        String cipherAlphabet = "";

        // store all unique chars
        Set<Character> unique = new LinkedHashSet<Character>();
        for (int i = 0; i < keyword.length(); ++i)
            unique.add(keyword.charAt(i));

        // add unique chars to beginning of cipher alphabet
        for (char c : unique)
            cipherAlphabet += c;

        // add unseen chars in alphabet to cipher alphabet, in order
        for (int i = 0; i < alphabet.length(); ++i) {
            if (cipherAlphabet.indexOf(alphabet.charAt(i)) == -1)
                cipherAlphabet += alphabet.charAt(i);
        }

        // substitute
        for (int i = 0; i < len; ++i) {
            if (Character.isAlphabetic(cbuf[i])) {
                char current = cipherAlphabet.charAt(alphabet.indexOf(Character.toLowerCase(cbuf[i])));
                cbuf[i] = Character.isUpperCase(cbuf[i]) ? Character.toUpperCase(current) : current;
            }
        }

        super.write(cbuf, off, len);
        super.close();
    }
}
