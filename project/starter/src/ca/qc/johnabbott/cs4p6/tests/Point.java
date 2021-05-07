package ca.qc.johnabbott.cs4p6.tests;

import ca.qc.johnabbott.cs4p6.Utils;
import ca.qc.johnabbott.cs4p6.serialization.SerializationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class Point {
    private File file;

    @BeforeEach
    public void init() throws IOException {
        file = Utils.cleanFile();
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
            ca.qc.johnabbott.cs4p6.Point result = Utils.setup(file, point);
            assertTrue(result instanceof ca.qc.johnabbott.cs4p6.Point);
            assertEquals(result, point);
        }
    }
}
