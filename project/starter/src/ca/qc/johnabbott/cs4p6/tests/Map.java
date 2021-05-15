package ca.qc.johnabbott.cs4p6.tests;

import ca.qc.johnabbott.cs4p6.Utils;
import ca.qc.johnabbott.cs4p6.collections.map.Entry;
import ca.qc.johnabbott.cs4p6.collections.map.HashMap;
import ca.qc.johnabbott.cs4p6.serialization.Serializable;
import ca.qc.johnabbott.cs4p6.serialization.SerializationException;
import ca.qc.johnabbott.cs4p6.serialization.util.Integer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class Map<K extends Serializable, V extends Serializable> {
    private File file;
    private HashMap<K, V> map;

    @BeforeEach
    public void init() throws IOException {
        file = Utils.cleanFile();
        map = new HashMap<>();
    }

    @Test
    public void testMapA() throws SerializationException, IOException {
        ArrayList<Entry<K, V>> entries = new ArrayList<>() {{
            add(new Entry<>((K) new Integer(1), (V) new Integer(2)));
            add(new Entry<>((K) new Integer(2), (V) new Integer(8)));
            add(new Entry<>((K) new Integer(3), (V) new Integer(12)));
        }};

        for (Entry<K, V> entry : entries)
            map.put(entry.getKey(), entry.getValue());

        HashMap<K, V> result = Utils.setup(file, map);

        assertTrue(result instanceof HashMap);
        assertEquals(result, map);
    }

    @Test
    public void testMapB() throws SerializationException, IOException {
        ArrayList<Entry<K, V>> entries = new ArrayList<>() {{
            add(new Entry<>((K) new Integer(1), (V) new Integer(2)));
            add(new Entry<>((K) new Integer(1), (V) new Integer(3)));
            add(new Entry<>((K) new Integer(1), (V) new Integer(4)));
            add(new Entry<>((K) new Integer(1), (V) new Integer(5)));
            add(new Entry<>((K) new Integer(2), (V) new Integer(8)));
            add(new Entry<>((K) new Integer(2), (V) new Integer(9)));
            add(new Entry<>((K) new Integer(2), (V) new Integer(10)));
            add(new Entry<>((K) new Integer(2), (V) new Integer(11)));
            add(new Entry<>((K) new Integer(3), (V) new Integer(12)));
            add(new Entry<>((K) new Integer(3), (V) new Integer(13)));
            add(new Entry<>((K) new Integer(3), (V) new Integer(14)));
        }};

        for (Entry<K, V> entry : entries)
            map.put(entry.getKey(), entry.getValue());

        HashMap<K, V> result = Utils.setup(file, map);

        assertTrue(result instanceof HashMap);
        assertEquals(result, map);
    }
}
