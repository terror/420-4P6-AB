package ca.qc.johnabbott.cs4p6;

import java.io.IOException;
import java.io.Writer;
import java.util.LinkedHashSet;
import java.util.Set;

public class SubstitutionCipherWriter extends BaseWriter {
    private String alphabet = "abcdefghijklmnopqrstuvwxyz";
    private String keyword;

    public SubstitutionCipherWriter(Writer writer, String keyword) {
        super(writer);
        this.keyword = keyword;
    }

    @Override
    public void write(char cbuf[], int off, int len) throws IOException {
        // compute alphabet
        String cipherAlphabet = "";

        Set<Character> s = new LinkedHashSet<Character>();
        for (int i = 0; i < keyword.length(); ++i)
            s.add(keyword.charAt(i));

        for (char c : s)
            cipherAlphabet += c;


        for (int i = 0; i < alphabet.length(); ++i) {
            if (cipherAlphabet.indexOf(alphabet.charAt(i)) == -1)
                cipherAlphabet += alphabet.charAt(i);
        }

        // substitute
        for (int i = 0; i < len; ++i) {
            if (Character.isAlphabetic(cbuf[i])) {
                char ret = cipherAlphabet.charAt(alphabet.indexOf(Character.toLowerCase(cbuf[i])));
                cbuf[i] = Character.isUpperCase(cbuf[i]) ? Character.toUpperCase(ret) : ret;
            }
        }

        super.write(cbuf, off, len);
    }
}
