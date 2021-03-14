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

        for (int i = 0; i < width; ++i) System.out.print(" ");
        System.out.print(String.format("%d ", line));

        for (int i = 0; i < len; ++i) {
            System.out.print(cbuf[i]);
            if (cbuf[i] == '\n') {
                for (int j = 0; j < width; ++j) System.out.print(" ");
                System.out.print(String.format("%d ", ++line));
            }
        }

        super.close();
        super.flush();
    }
}
