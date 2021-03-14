package ca.qc.johnabbott.cs4p6;

import java.io.IOException;
import java.io.PrintStream;
import java.io.Reader;

public class StatsReader extends BaseReader {
    private PrintStream output;

    public StatsReader(Reader reader, PrintStream output) {
        super(reader);
        this.output = output;
    }

    @Override
    public int read(char cbuf[], int off, int len) throws IOException {
        int data = super.read(cbuf, off, len);

        int chars = 0, lines = 0;
        for (int i = 0; i < data; ++i) {
            char curr = cbuf[i + off];
            chars += (curr == ' ' || curr == '\t' || curr == '\r' || curr == '\n') ? 1 : 0;
            lines += curr == '\n' ? 1 : 0;
        }

        output.println(String.format("Characters read: %s", data));
        output.println(String.format("Tokens read: %s", chars + 1));
        output.println(String.format("Lines read: %s", lines + 1));

        super.close();
        return data;
    }
}
