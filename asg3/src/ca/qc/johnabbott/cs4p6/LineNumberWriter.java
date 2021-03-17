package ca.qc.johnabbott.cs4p6;

import java.io.IOException;
import java.io.Writer;

public class LineNumberWriter extends BaseWriter {
    private int width;

    public LineNumberWriter(Writer writer, int width) {
        super(writer);
        this.width = width;
    }

    @Override
    public void write(char cbuf[], int off, int len) throws IOException {
        int line = 1;
        StringBuilder sb = new StringBuilder();

        // append width and line number
        for (int i = 0; i < width; ++i) sb.append(" ");
        sb.append(String.format("%d ", line));

        for (int i = 0; i < len; ++i) {
            sb.append(cbuf[i]);
            // check if newline
            if (cbuf[i] == '\n') {
                // append width and line number
                for (int j = 0; j < width; ++j) sb.append(" ");
                sb.append(String.format("%d ", ++line));
            }
        }

        // store output in cbuf
        sb.getChars(0, sb.length(), cbuf, 0);

        // write to output stream
        super.write(cbuf, 0, sb.length());

        super.close();
    }
}
