package ca.qc.johnabbott.cs4p6;

import ca.qc.johnabbott.cs4p6.serialization.util.Box;
import ca.qc.johnabbott.cs4p6.serialization.util.Date;
import ca.qc.johnabbott.cs4p6.serialization.util.Double;
import ca.qc.johnabbott.cs4p6.serialization.util.Float;
import ca.qc.johnabbott.cs4p6.serialization.util.Integer;
import ca.qc.johnabbott.cs4p6.serialization.util.Long;
import ca.qc.johnabbott.cs4p6.serialization.util.String;
import ca.qc.johnabbott.cs4p6.collections.Tuple;
import ca.qc.johnabbott.cs4p6.collections.list.LinkedList;
import ca.qc.johnabbott.cs4p6.collections.map.HashMap;
import ca.qc.johnabbott.cs4p6.collections.map.Entry;
import ca.qc.johnabbott.cs4p6.collections.set.TreeSet;
import ca.qc.johnabbott.cs4p6.serialization.*;

import java.io.IOException;

public class MainDeserialize {

    public static void main(java.lang.String[] args) throws SerializationException, IOException {
        Logger.setLogLevel(Logger.LogLevel.VERBOSE);

        Serializer serializer = new SerializerBuilder()
                .fromSource("foo.bin")
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
                .registerCreator(HashMap.SERIAL_ID, HashMap::new)
                .registerCreator(Entry.SERIAL_ID, Entry::new)
                .registerCreator(TreeSet.SERIAL_ID, TreeSet::new)
                .build();

        // call the appropriate `read` method based on the data type.
        deserialize(serializer, "Deserializing Box");
        deserialize(serializer, "Deserializing Date");
        deserialize(serializer, "Deserializing Double");
        deserialize(serializer, "Deserializing Float");
        deserialize(serializer, "Deserializing Integer");
        deserialize(serializer, "Deserializing Long");
        deserialize(serializer, "Deserializing String");
        deserialize(serializer, "Deserializing Point");
        deserialize(serializer, "Deserializing Grade");
        deserialize(serializer, "Deserializing Tuple");
        deserialize(serializer, "Deserializing Linked List");
        deserialize(serializer, "Deserializing HashMap");
        deserialize(serializer, "Deserializing TreeSet");

        // close the serializer.
        serializer.close();
    }

    public static <T extends Serializable> void deserialize(Serializer serializer, java.lang.String msg) throws SerializationException, IOException {
        Logger.getInstance().log(msg);
        var object = (T) serializer.readSerializable();
        System.out.println(object);
    }
}
