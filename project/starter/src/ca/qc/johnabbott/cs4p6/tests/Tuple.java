package ca.qc.johnabbott.cs4p6.tests;

import ca.qc.johnabbott.cs4p6.Utils;
import ca.qc.johnabbott.cs4p6.serialization.SerializationException;
import ca.qc.johnabbott.cs4p6.serialization.util.Float;
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

public class Tuple {
    private File file;

    @BeforeEach
    public void init() throws IOException {
        file = Utils.cleanFile();
    }

    @Test
    public void testSingle() throws SerializationException, IOException {
        ArrayList<ca.qc.johnabbott.cs4p6.collections.Tuple.Single> singles = new ArrayList<>() {{
            add(new ca.qc.johnabbott.cs4p6.collections.Tuple.Single(new String("hello")));
            add(new ca.qc.johnabbott.cs4p6.collections.Tuple.Single(new Integer(20)));
            add(new ca.qc.johnabbott.cs4p6.collections.Tuple.Single(new Long(200L)));
            add(new ca.qc.johnabbott.cs4p6.collections.Tuple.Single(new Float(220.23321F)));
        }};

        for (ca.qc.johnabbott.cs4p6.collections.Tuple.Single single : singles) {
            ca.qc.johnabbott.cs4p6.collections.Tuple.Single result = Utils.setup(file, single);
            assertTrue(result instanceof ca.qc.johnabbott.cs4p6.collections.Tuple.Single);
            assertEquals(single.get(ca.qc.johnabbott.cs4p6.collections.Tuple.Position.FIRST), result.get(ca.qc.johnabbott.cs4p6.collections.Tuple.Position.FIRST));
        }
    }

    @Test
    public void testDouble() throws SerializationException, IOException {
        ArrayList<ca.qc.johnabbott.cs4p6.collections.Tuple.Pair> pairs = new ArrayList<>() {{
            add(new ca.qc.johnabbott.cs4p6.collections.Tuple.Pair(new String("hello"), new Integer(20)));
            add(new ca.qc.johnabbott.cs4p6.collections.Tuple.Pair(new Integer(20), new Long(300000)));
            add(new ca.qc.johnabbott.cs4p6.collections.Tuple.Pair(new Long(200L), new String("wow!")));
            add(new ca.qc.johnabbott.cs4p6.collections.Tuple.Pair(new Float(220.23321F), new Integer(2000)));
        }};

        for (ca.qc.johnabbott.cs4p6.collections.Tuple.Pair pair : pairs) {
            ca.qc.johnabbott.cs4p6.collections.Tuple.Pair result = Utils.setup(file, pair);
            assertTrue(result instanceof ca.qc.johnabbott.cs4p6.collections.Tuple.Pair);
            assertEquals(pair.get(ca.qc.johnabbott.cs4p6.collections.Tuple.Position.FIRST), result.get(ca.qc.johnabbott.cs4p6.collections.Tuple.Position.FIRST));
            assertEquals(pair.get(ca.qc.johnabbott.cs4p6.collections.Tuple.Position.SECOND), result.get(ca.qc.johnabbott.cs4p6.collections.Tuple.Position.SECOND));
        }
    }

    @Test
    public void testTriple() throws SerializationException, IOException {
        ArrayList<ca.qc.johnabbott.cs4p6.collections.Tuple.Triple> triples = new ArrayList<>() {{
            add(new ca.qc.johnabbott.cs4p6.collections.Tuple.Triple(new String("hello"), new Integer(20), new Integer(40)));
            add(new ca.qc.johnabbott.cs4p6.collections.Tuple.Triple(new Integer(20), new Long(300000), new String("wow!")));
            add(new ca.qc.johnabbott.cs4p6.collections.Tuple.Triple(new Long(200L), new String("wow!"), new Float(3.14150F)));
            add(new ca.qc.johnabbott.cs4p6.collections.Tuple.Triple(new Float(220.23321F), new Integer(2000), new Long(200)));
        }};

        for (ca.qc.johnabbott.cs4p6.collections.Tuple.Triple triple : triples) {
            ca.qc.johnabbott.cs4p6.collections.Tuple.Triple result = Utils.setup(file, triple);
            assertTrue(result instanceof ca.qc.johnabbott.cs4p6.collections.Tuple.Triple);
            assertEquals(triple.get(ca.qc.johnabbott.cs4p6.collections.Tuple.Position.FIRST), result.get(ca.qc.johnabbott.cs4p6.collections.Tuple.Position.FIRST));
            assertEquals(triple.get(ca.qc.johnabbott.cs4p6.collections.Tuple.Position.SECOND), result.get(ca.qc.johnabbott.cs4p6.collections.Tuple.Position.SECOND));
            assertEquals(triple.get(ca.qc.johnabbott.cs4p6.collections.Tuple.Position.THIRD), result.get(ca.qc.johnabbott.cs4p6.collections.Tuple.Position.THIRD));
        }
    }
}
