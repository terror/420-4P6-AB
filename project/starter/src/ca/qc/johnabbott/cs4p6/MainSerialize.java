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
import ca.qc.johnabbott.cs4p6.collections.set.TreeSet;
import ca.qc.johnabbott.cs4p6.serialization.*;

import java.io.IOException;
import java.time.Instant;

public class MainSerialize {
    public static void main(java.lang.String[] args) throws SerializationException, IOException {
        // set the log level to `verbose` when serializing data.
        Logger.setLogLevel(Logger.LogLevel.VERBOSE);

        // initialize our serializer with the binary output file `foo.bin`
        Serializer serializer = new SerializerBuilder().toDestination("foo.bin").build();

        // each call to `write` invokes an overload based on the type of serializable data
        // if they are primitives.

        // our own types must implement the `Serializable` interface
        // if we plan on making calls to `write`.

        // initialize and serialize data
        serialize(serializer, new Box(new String("hello, world!")), "Serializing Box");
        serialize(serializer, new Date(java.util.Date.from(Instant.now())), "Serializing Date");
        serialize(serializer, new Double(3.14), "Serializing Double");
        serialize(serializer, new Float(1.5F), "Serializing Float");
        serialize(serializer, new Integer(10), "Serializing Integer");
        serialize(serializer, new Long(934823094324L), "Serializing Long");
        serialize(serializer, new Box(new String("hello, world!")), "Serializing String");
        serialize(serializer, new Point(1.2F, 1.5F), "Serializing Point");
        serialize(serializer, new Grade("name", 4.0, java.util.Date.from(Instant.now())), "Serializing Grade");
        serialize(serializer, new Tuple.Pair<>(new Float(1.4F), new Float(1.5F)), "Serializing Tuple");

        LinkedList list = new LinkedList<>();
        list.add(new String("a"));
        list.add(new String("b"));
        list.add(new String("c"));
        serialize(serializer, list, "Serializing Linked List");

        HashMap map = new HashMap();
        map.put(new Integer(1), new Integer(2));
        map.put(new Integer(2), new Integer(3));
        map.put(new Integer(3), new Integer(4));
        serialize(serializer, map, "Serializing HashMap");

        TreeSet set = new TreeSet();
        set.add(new Integer(1));
        set.add(new Integer(2));
        set.add(new Integer(3));
        serialize(serializer, set, "Serializing TreeSet");

        // close the serializer.
        serializer.close();
    }

    public static void serialize(Serializer serializer, Serializable o, java.lang.String msg) throws IOException {
        Logger.getInstance().log(msg);
        serializer.write(o);
    }
}
