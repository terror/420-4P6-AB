package ca.qc.johnabbott.cs4p6;

import java.io.IOException;
import java.io.Reader;
import java.util.LinkedHashSet;
import java.util.Set;

public class SubstitutionCipherReader extends BaseReader {
    private String keyword;
    private String alphabet = "abcdefghijklmnopqrstuvwxyz";

    public SubstitutionCipherReader(Reader reader, String keyword) {
        super(reader);
        this.keyword = keyword;
    }

    @Override
    public int read(char cbuf[], int off, int len) throws IOException {
        int data = super.read(cbuf, off, len);

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
        for (int i = 0; i < data; ++i) {
            if (Character.isAlphabetic(cbuf[i])) {
                char ret = alphabet.charAt(cipherAlphabet.indexOf(Character.toLowerCase(cbuf[i])));
                cbuf[i] = Character.isUpperCase(cbuf[i]) ? Character.toUpperCase(ret) : ret;
            }
        }

        return data;
    }
}
