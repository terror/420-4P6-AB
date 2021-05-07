package ca.qc.johnabbott.cs4p6;

import ca.qc.johnabbott.cs4p6.serialization.Logger;
import ca.qc.johnabbott.cs4p6.serialization.SerializationException;
import ca.qc.johnabbott.cs4p6.serialization.Serializer;
import ca.qc.johnabbott.cs4p6.serialization.SerializerBuilder;
import ca.qc.johnabbott.cs4p6.serialization.util.Box;
import ca.qc.johnabbott.cs4p6.serialization.util.String;

import java.io.IOException;

public class MainSerialize {
    public static void main(java.lang.String[] args) throws SerializationException, IOException {
        // set the log level to `verbose` when serializing data.
        Logger.setLogLevel(Logger.LogLevel.VERBOSE);

        // initialize data to serialize.
        Box<String> box = new Box(new String("hello"));

        // initialize our serializer with the binary output file `foo.bin`
        Serializer serializer = new SerializerBuilder().toDestination("foo.bin").build();

        // each call to `write` invokes an overload based on the type of serializable data
        // if they are primitives.

        // our own types must implement the `Serializable` interface
        // if we plan on making calls to `write`.

        serializer.write(box);

        // close the serializer.
        serializer.close();
    }
}
