package ca.qc.johnabbott.cs4p6.tests;

import ca.qc.johnabbott.cs4p6.Utils;
import ca.qc.johnabbott.cs4p6.collections.list.LinkedList;
import ca.qc.johnabbott.cs4p6.serialization.Serializable;
import ca.qc.johnabbott.cs4p6.serialization.SerializationException;
import ca.qc.johnabbott.cs4p6.serialization.util.Double;
import ca.qc.johnabbott.cs4p6.serialization.util.Integer;
import ca.qc.johnabbott.cs4p6.serialization.util.Long;
import ca.qc.johnabbott.cs4p6.serialization.util.String;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class List<T extends Serializable> {
    private File file;
    private LinkedList<T> list;

    @BeforeEach
    public void init() throws IOException {
        file = Utils.cleanFile();
        list = new LinkedList<>();
    }

    @Test
    public void testLinkedListA() throws SerializationException, IOException {
        ArrayList<T> data = new ArrayList<T>() {{
            add((T) new String("hello"));
            add((T) new String("world"));
            add((T) new String("cool"));
            add((T) new String("awesome"));
        }};

        for (T piece : data)
            list.add(piece);

        LinkedList<T> result = Utils.setup(file, list);

        assertTrue(result instanceof LinkedList);
        assertEquals(result, list);
    }

    @Test
    public void testLinkedListB() throws SerializationException, IOException {
        ArrayList<T> data = new ArrayList<T>() {{
            add((T) new Integer(20));
            add((T) new Integer(2352343));
            add((T) new Integer(234234));
            add((T) new Integer(9032894));
            add((T) new Integer(2929));
            add((T) new String("hello"));
            add((T) new String("world"));
            add((T) new String("cool"));
            add((T) new String("awesome"));
        }};

        for (T piece : data)
            list.add(piece);

        LinkedList<T> result = Utils.setup(file, list);

        assertTrue(result instanceof LinkedList);
        assertEquals(result, list);
    }

    @Test
    public void testLinkedListC() throws SerializationException, IOException {
        ArrayList<T> data = new ArrayList<T>() {{
            add((T) new Integer(200));
            add((T) new Integer(23352343));
            add((T) new Integer(25434234));
            add((T) new Integer(90532894));
            add((T) new Integer(29529));
            add((T) new String("hello"));
            add((T) new String("world"));
            add((T) new String("cool"));
            add((T) new String("awesome"));
            add((T) new Long(1234L));
            add((T) new Long(39048732904L));
            add((T) new Long(29048L));
            add((T) new Long(304980L));
            add((T) new Double(3.14159));
            add((T) new Double(3.14159));
            add((T) new Double(3.14159));
            add((T) new Double(3.14159));
        }};

        for (T piece : data)
            list.add(piece);

        LinkedList<T> result = Utils.setup(file, list);

        assertTrue(result instanceof LinkedList);
        assertEquals(result, list);
    }
}
