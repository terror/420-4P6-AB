package ca.qc.johnabbott.cs4p6;

import ca.qc.johnabbott.cs4p6.serialization.SerializationException;
import ca.qc.johnabbott.cs4p6.serialization.Serializer;
import ca.qc.johnabbott.cs4p6.serialization.SerializerBuilder;
import ca.qc.johnabbott.cs4p6.serialization.util.Box;
import ca.qc.johnabbott.cs4p6.serialization.util.String;

import java.io.IOException;

public class MainDeserialize {

    public static void main(java.lang.String[] args) throws SerializationException, IOException {
        Serializer serializer = new SerializerBuilder()
                .fromSource("foo.bin")
                .registerCreator(String.SERIAL_ID, String::new)
                .registerCreator(Box.SERIAL_ID, Box::new)
                .build();

        // call the appropriate `read` method based on the data type.
        Box box = (Box) serializer.readSerializable();

        // close the serializer.
        serializer.close();

        // output deserialized data to stdout.
        System.out.println(box);
    }
}
