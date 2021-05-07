package ca.qc.johnabbott.cs4p6.tests;

import ca.qc.johnabbott.cs4p6.Utils;
import ca.qc.johnabbott.cs4p6.serialization.SerializationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class Grade {
    private File file;

    @BeforeEach
    public void init() throws IOException {
        file = Utils.cleanFile();
    }

    @Test
    public void testGrade() throws SerializationException, IOException {
        ArrayList<ca.qc.johnabbott.cs4p6.Grade> grades = new ArrayList<>() {{
            add(new ca.qc.johnabbott.cs4p6.Grade("Tom", 4.0, new Date()));
            add(new ca.qc.johnabbott.cs4p6.Grade("Jack", 3.9, new Date()));
            add(new ca.qc.johnabbott.cs4p6.Grade("Mary", 2.0, new Date()));
            add(new ca.qc.johnabbott.cs4p6.Grade("Bob", 1.1, new Date()));
            add(new ca.qc.johnabbott.cs4p6.Grade("Tim", 3.8, new Date()));
            add(new ca.qc.johnabbott.cs4p6.Grade("Roy", 3.4, new Date()));
            add(new ca.qc.johnabbott.cs4p6.Grade("Cliff", 2.5, new Date()));
            add(new ca.qc.johnabbott.cs4p6.Grade("Rick", 2.3, new Date()));
            add(new ca.qc.johnabbott.cs4p6.Grade("Bill", 3.4, new Date()));
            add(new ca.qc.johnabbott.cs4p6.Grade("Dan", 1.2, new Date()));
            add(new ca.qc.johnabbott.cs4p6.Grade("Troy", 4.0, new Date()));
        }};

        for (ca.qc.johnabbott.cs4p6.Grade grade : grades) {
            ca.qc.johnabbott.cs4p6.Grade result = Utils.setup(file, grade);
            assertTrue(result instanceof ca.qc.johnabbott.cs4p6.Grade);
            assertEquals(result, grade);
        }
    }
}
