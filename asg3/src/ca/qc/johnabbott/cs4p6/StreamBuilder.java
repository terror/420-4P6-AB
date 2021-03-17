package ca.qc.johnabbott.cs4p6;

import java.io.*;

public class StreamBuilder {
    private PrintStream out;
    private String filename;
    private String keyword;
    private String word;
    private int precision;

    public StreamBuilder(String filename) {
        this.filename = filename;
        this.out = null;
        this.keyword = "";
        this.word = "";
        this.precision = -1;
    }

    public StreamBuilder stats(PrintStream out) {
        this.out = out;
        return this;
    }

    public StreamBuilder censor(String word) {
        this.word = word;
        return this;
    }

    public StreamBuilder includeLineNumbers(int precision) {
        this.precision = precision;
        return this;
    }

    public StreamBuilder encrypted(String keyword) {
        this.keyword = keyword;
        return this;
    }

    public Reader createReader() throws FileNotFoundException {
        // check for illegal state
        if (this.precision != -1)
            throw new IllegalStateException();

        FileReader reader = new FileReader(this.filename);
        SubstitutionCipherReader cipherReader = null;
        CensorReader censorReader = null;
        StatsReader statsReader = null;

        // substitute input
        if (this.keyword != "") {
            cipherReader = new SubstitutionCipherReader(reader, keyword);
        }

        // censor input
        if (this.word != "") {
            censorReader = new CensorReader(cipherReader != null ? cipherReader : reader, keyword);
        }

        // output stats to this.out
        if (this.out != null) {
            statsReader = new StatsReader(censorReader != null ? censorReader : cipherReader != null ? cipherReader : reader, this.out);
        }

        // return the correct chained reader
        return statsReader != null ? statsReader : censorReader != null ? censorReader : cipherReader != null ? cipherReader : reader;
    }

    public Writer createWriter() throws IOException {
        // check for illegal state
        if (this.word != "" || (this.precision != -1 && this.precision < 0))
            throw new IllegalStateException();

        FileWriter writer = new FileWriter(this.filename);
        SubstitutionCipherWriter cipherWriter = null;
        LineNumberWriter lineNumberWriter = null;
        StatsWriter statsWriter = null;

        // decrypt data with keyword
        if (this.keyword != "") {
            cipherWriter = new SubstitutionCipherWriter(writer, this.keyword);
        }

        // add line numbers to output
        if (this.precision != -1) {
            lineNumberWriter = new LineNumberWriter(cipherWriter != null ? cipherWriter : writer, this.precision);
        }

        // output stats to this.out
        if (this.out != null) {
            statsWriter = new StatsWriter(lineNumberWriter != null ? lineNumberWriter : cipherWriter != null ? cipherWriter : writer, this.out);
        }

        // return the correct chained writer
        return statsWriter != null ? statsWriter : lineNumberWriter != null ? lineNumberWriter : cipherWriter != null ? cipherWriter : writer;
    }
}
