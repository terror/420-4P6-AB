package ca.qc.johnabbott.cs4p6;

import ca.qc.johnabbott.cs4p6.collections.Tuple;
import ca.qc.johnabbott.cs4p6.collections.list.LinkedList;
import ca.qc.johnabbott.cs4p6.serialization.Serializable;
import ca.qc.johnabbott.cs4p6.serialization.SerializationException;
import ca.qc.johnabbott.cs4p6.serialization.Serializer;
import ca.qc.johnabbott.cs4p6.serialization.SerializerBuilder;
import ca.qc.johnabbott.cs4p6.serialization.util.Double;
import ca.qc.johnabbott.cs4p6.serialization.util.Float;
import ca.qc.johnabbott.cs4p6.serialization.util.Integer;
import ca.qc.johnabbott.cs4p6.serialization.util.Long;
import ca.qc.johnabbott.cs4p6.serialization.util.String;
import ca.qc.johnabbott.cs4p6.serialization.util.*;

import java.io.File;
import java.io.IOException;

public class Utils {
    // serialize and deserialize the passed in `object`
    // and return the deserialized result
    public static <T extends Serializable> T setup(File file, T object) throws SerializationException, IOException {
        Serializer serializer = new SerializerBuilder().toDestination(file.getAbsolutePath()).build();

        serializer.write(object);

        serializer.close();

        Serializer deserializer = new SerializerBuilder()
                .fromSource(file.getAbsolutePath())
                .registerCreator(Box.SERIAL_ID, Box::new)
                .registerCreator(Date.SERIAL_ID, Date::new)
                .registerCreator(Double.SERIAL_ID, Double::new)
                .registerCreator(Float.SERIAL_ID, Float::new)
                .registerCreator(Integer.SERIAL_ID, Integer::new)
                .registerCreator(Long.SERIAL_ID, Long::new)
                .registerCreator(String.SERIAL_ID, String::new)
                .registerCreator(Point.SERIAL_ID, Point::new)
                .registerCreator(Grade.SERIAL_ID, Grade::new)
                .registerCreator(Tuple.Single.SERIAL_ID, Tuple.Single::new)
                .registerCreator(Tuple.Pair.SERIAL_ID, Tuple.Pair::new)
                .registerCreator(Tuple.Triple.SERIAL_ID, Tuple.Triple::new)
                .registerCreator(LinkedList.SERIAL_ID, LinkedList::new)
                .build();

        T result = (T) deserializer.readSerializable();

        deserializer.close();

        return result;
    }

    // ensure that `file` is deleted and recreated before returning
    public static File cleanFile() throws IOException {
        File file = new File("foo.bin");
        file.delete();
        file.createNewFile();
        return file;
    }
}
