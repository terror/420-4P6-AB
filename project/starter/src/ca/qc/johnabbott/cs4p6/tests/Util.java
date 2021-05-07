package ca.qc.johnabbott.cs4p6.tests;

import ca.qc.johnabbott.cs4p6.Utils;
import ca.qc.johnabbott.cs4p6.serialization.SerializationException;
import ca.qc.johnabbott.cs4p6.serialization.util.Double;
import ca.qc.johnabbott.cs4p6.serialization.util.Float;
import ca.qc.johnabbott.cs4p6.serialization.util.Integer;
import ca.qc.johnabbott.cs4p6.serialization.util.Long;
import ca.qc.johnabbott.cs4p6.serialization.util.String;
import ca.qc.johnabbott.cs4p6.serialization.util.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class Util {
    private File file;

    @BeforeEach
    public void init() throws IOException {
        file = Utils.cleanFile();
    }

    @Test
    public void testBox() throws IOException, SerializationException {
        Box<String> object = new Box(new String("hello"));

        Box result = Utils.setup(file, object);

        assertTrue(result instanceof Box);
        assertEquals(result.getValue(), new String("hello"));
    }

    @Test
    public void testDate() throws IOException, SerializationException {
        Date object = new Date(new java.util.Date());

        Date result = Utils.setup(file, object);

        assertTrue(result instanceof Date);
    }

    @Test
    public void testDouble() throws SerializationException, IOException {
        Double object = new Double(20.25);

        Double result = Utils.setup(file, object);

        assertTrue(result instanceof Double);
        Assertions.assertEquals(result.get(), 20.25);
    }

    @Test
    public void testFloat() throws IOException, SerializationException {
        Float object = new Float(12434.1234F);

        Float result = Utils.setup(file, object);

        assertTrue(result instanceof Float);
        Assertions.assertEquals(result.get(), 12434.1234F);
    }

    @Test
    public void testInteger() throws SerializationException, IOException {
        Integer object = new Integer(20);

        Integer result = Utils.setup(file, object);

        assertTrue(result instanceof Integer);
        assertEquals(result, new Integer(20));
    }

    @Test
    public void testLong() throws IOException, SerializationException {
        Long object = new Long(3000L);

        Long result = Utils.setup(file, object);

        assertTrue(result instanceof Long);
        assertEquals(result, new Long(3000L));
    }

    @Test
    public void testString() throws IOException, SerializationException {
        ArrayList<java.lang.String> values = new ArrayList<>() {{
            add(new java.lang.String("hello, world!"));
            add(new java.lang.String("this project is awesome!"));
            add(new java.lang.String("the quick brown fox jumped over the lazy dog."));
        }};

        for (java.lang.String value : values) {
            String object = new String(value);

            String result = Utils.setup(file, object);

            assertTrue(result instanceof String);
            assertEquals(result, new String(value));
        }

    }
}
