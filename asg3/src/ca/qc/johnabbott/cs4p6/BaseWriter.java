package ca.qc.johnabbott.cs4p6;

import java.io.IOException;
import java.io.Writer;

/**
 * A writer wrapper class meant to be extended by other writers.
 */
public class BaseWriter extends Writer {

    protected Writer destination;

    /**
     * Create a writer from another writer.
     * @param destination
     */
    public BaseWriter(Writer destination) {
        this.destination = destination;
    }

    @Override
    public void write(char[] cbuf, int off, int len) throws IOException {
        destination.write(cbuf, off, len);
    }

    @Override
    public void flush() throws IOException {
        destination.flush();
    }

    @Override
    public void close() throws IOException {
        destination.close();
    }
}