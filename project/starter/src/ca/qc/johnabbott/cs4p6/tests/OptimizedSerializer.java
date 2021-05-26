package ca.qc.johnabbott.cs4p6.tests;

import ca.qc.johnabbott.cs4p6.Point;
import ca.qc.johnabbott.cs4p6.Utils;
import ca.qc.johnabbott.cs4p6.collections.list.LinkedList;
import ca.qc.johnabbott.cs4p6.collections.map.Entry;
import ca.qc.johnabbott.cs4p6.collections.map.HashMap;
import ca.qc.johnabbott.cs4p6.collections.set.TreeSet;
import ca.qc.johnabbott.cs4p6.serialization.Serializable;
import ca.qc.johnabbott.cs4p6.serialization.SerializationException;
import ca.qc.johnabbott.cs4p6.serialization.util.Integer;
import ca.qc.johnabbott.cs4p6.serialization.util.String;
import com.sun.source.tree.Tree;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class OptimizedSerializer<K extends Serializable, V extends Serializable, T extends Serializable> {
    private File file;
    private LinkedList list;
    private HashMap map;
    private TreeSet set;

    @BeforeEach
    public void init() throws IOException {
        file = Utils.cleanFile();
        list = new LinkedList();
        map = new HashMap();
        set = new TreeSet();
    }

    @Test
    public void testPoint() throws SerializationException, IOException {
        ArrayList<ca.qc.johnabbott.cs4p6.Point> points = new ArrayList<>() {{
            add(new ca.qc.johnabbott.cs4p6.Point(1F, 1F));
            add(new ca.qc.johnabbott.cs4p6.Point(5.34234F, 2.234234F));
            add(new ca.qc.johnabbott.cs4p6.Point(3.14159F, 3.14159F));
            add(new ca.qc.johnabbott.cs4p6.Point(1.23423F, 299.22F));
            add(new ca.qc.johnabbott.cs4p6.Point(6.222F, 10.000F));
            add(new ca.qc.johnabbott.cs4p6.Point(8.111F, 80.12F));
        }};

        for (ca.qc.johnabbott.cs4p6.Point point : points) {
            ArrayList<Point> objects = Utils.setupOptimal(file, point);
            Point a = objects.get(0), b = objects.get(1), c = objects.get(2);

            assertTrue(a instanceof ca.qc.johnabbott.cs4p6.Point);
            assertEquals(a, point);

            assertTrue(b instanceof ca.qc.johnabbott.cs4p6.Point);
            assertEquals(b, a);

            assertTrue(c instanceof ca.qc.johnabbott.cs4p6.Point);
            assertEquals(c, b);
        }
    }

    @Test
    public void testLinkedList() throws SerializationException, IOException {
        ArrayList<T> data = new ArrayList<T>() {{
            add((T) new String("hello"));
            add((T) new String("world"));
            add((T) new String("cool"));
            add((T) new String("awesome"));
        }};

        for (T piece : data)
            list.add(piece);

        ArrayList<LinkedList<T>> result = Utils.setupOptimal(file, list);

        LinkedList a = result.get(0), b = result.get(1), c = result.get(2);

        assertTrue(a instanceof LinkedList);
        assertEquals(a, list);

        assertTrue(b instanceof LinkedList);
        assertEquals(b, a);

        assertTrue(c instanceof LinkedList);
        assertEquals(c, b);
    }

    @Test
    public void testMap() throws SerializationException, IOException {
        ArrayList<Entry<K, V>> entries = new ArrayList<>() {{
            add(new Entry<>((K) new Integer(1), (V) new Integer(2)));
            add(new Entry<>((K) new Integer(2), (V) new Integer(8)));
            add(new Entry<>((K) new Integer(3), (V) new Integer(12)));
        }};

        for (Entry<K, V> entry : entries)
            map.put(entry.getKey(), entry.getValue());

        ArrayList<HashMap<K, V>> result = Utils.setupOptimal(file, map);

        HashMap a = result.get(0), b = result.get(1), c = result.get(2);

        assertTrue(a instanceof HashMap);
        assertEquals(a, map);

        assertTrue(b instanceof HashMap);
        assertEquals(b, a);

        assertTrue(c instanceof HashMap);
        assertEquals(c, b);
    }

    @Test
    public void testSet() throws SerializationException, IOException {
        ArrayList<Integer> nodes = new ArrayList<>() {{
            add(new Integer(1));
            add(new Integer(2));
            add(new Integer(3));
            add(new Integer(4));
            add(new Integer(5));
        }};

        for (Integer node : nodes)
            set.add(node);

        ArrayList<TreeSet> result = Utils.setupOptimal(file, set);

        TreeSet a = result.get(0), b = result.get(1), c = result.get(2);

        assertTrue(a instanceof TreeSet);
        assertEquals(a, set);

        assertTrue(b instanceof TreeSet);
        assertEquals(b, a);

        assertTrue(c instanceof TreeSet);
        assertEquals(c, b);
    }
}
