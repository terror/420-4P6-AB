package ca.qc.johnabbott.cs4p6.tests;

import ca.qc.johnabbott.cs4p6.Utils;
import ca.qc.johnabbott.cs4p6.collections.set.TreeSet;
import ca.qc.johnabbott.cs4p6.serialization.SerializationException;
import ca.qc.johnabbott.cs4p6.serialization.util.Integer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class Set {
    private File file;
    private TreeSet set;

    @BeforeEach
    public void init() throws IOException {
        file = Utils.cleanFile();
        set = new TreeSet();
    }

    @Test
    public void testSetA() throws SerializationException, IOException {
        ArrayList<Integer> nodes = new ArrayList<>() {{
            add(new Integer(1));
            add(new Integer(2));
            add(new Integer(3));
            add(new Integer(4));
            add(new Integer(5));
        }};

        for (Integer node : nodes)
            set.add(node);

        TreeSet result = Utils.setup(file, set);

        assertTrue(result instanceof TreeSet);
        assertEquals(result, set);
    }

    @Test
    public void testSetB() throws SerializationException, IOException {
        ArrayList<Integer> nodes = new ArrayList<>() {{
            add(new Integer(1));
            add(new Integer(2));
            add(new Integer(3));
            add(new Integer(4));
            add(new Integer(5));
            add(new Integer(1));
            add(new Integer(2));
            add(new Integer(3));
            add(new Integer(4));
            add(new Integer(5));
        }};

        for (Integer node : nodes)
            set.add(node);

        TreeSet result = Utils.setup(file, set);

        assertTrue(result instanceof TreeSet);
        assertEquals(result, set);
    }
}
