package ca.qc.johnabbott.cs4p6;

import java.io.IOException;
import java.io.Reader;

public class CensorReader extends BaseReader {
    private String word;

    public CensorReader(Reader reader, String word) {
        super(reader);
        this.word = word;
    }

    @Override
    public int read(char cbuf[], int off, int len) throws IOException {
        int data = super.read(cbuf, off, len);

        for (int i = 0; i < data; ++i) {
            // check for match
            int j = i, idx = 0;
            boolean match = false;

            if (Character.toLowerCase(cbuf[j]) == word.charAt(idx)) {
                match = true;
                for (; j < i + word.length(); ++j) {
                    if (Character.toLowerCase(cbuf[j]) != word.charAt(idx)) {
                        match = false;
                        break;
                    }
                    ++idx;
                }
            }

            // censor word if match
            if (match) {
                for (int k = i; k < j; ++k)
                    cbuf[k] = '#';
                i = j;
            }
        }

        super.close();
        return data;
    }
}
