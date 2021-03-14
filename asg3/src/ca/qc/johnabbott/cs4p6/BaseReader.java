package ca.qc.johnabbott.cs4p6;

import java.io.IOException;
import java.io.Reader;

/**
 * A reader wrapper class meant to be extended by other readers.
 */
public class BaseReader extends Reader {

    protected Reader source;

    /**
     * Create a reader from another reader.
     * @param source
     */
    public BaseReader(Reader source) {
        this.source = source;
    }

    @Override
    public int read(char[] cbuf, int off, int len) throws IOException {
        return source.read(cbuf, off, len);
    }

    @Override
    public void close() throws IOException {
        source.close();
    }
}