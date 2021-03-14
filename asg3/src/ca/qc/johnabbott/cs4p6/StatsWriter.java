package ca.qc.johnabbott.cs4p6;

import java.io.IOException;
import java.io.PrintStream;
import java.io.Writer;

public class StatsWriter extends BaseWriter {
    private PrintStream output;

    public StatsWriter(Writer writer, PrintStream output) {
        super(writer);
        this.output = output;
    }

    @Override
    public void write(char cbuf[], int off, int len) throws IOException {
        int chars = 0, lines = 0;
        for (int i = 0; i < len; ++i) {
            char curr = cbuf[i + off];
            chars += (curr == ' ' || curr == '\t' || curr == '\r' || curr == '\n') ? 1 : 0;
            lines += curr == '\n' ? 1 : 0;
        }

        output.println(String.format("Characters read: %s", len));
        output.println(String.format("Tokens read: %s", chars + 1));
        output.println(String.format("Lines read: %s", lines + 1));

        super.write(cbuf, off, len);
        super.close();
    }
}
