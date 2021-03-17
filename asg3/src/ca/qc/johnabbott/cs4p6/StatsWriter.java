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
            // current char to evaluate
            char curr = cbuf[i + off];

            // check if token
            chars += (curr == ' ' || curr == '\t' || curr == '\r' || curr == '\n') ? 1 : 0;

            // check if newline
            lines += curr == '\n' ? 1 : 0;
        }

        // print stats to given print stream
        output.println(String.format("Characters read: %s", len));
        output.println(String.format("Tokens read: %s", chars + 1));
        output.println(String.format("Lines read: %s", lines + 1));

        super.write(cbuf, off, len);
        super.close();
    }
}
