/*
 * Copyright (c) 2021 Ian Clement. All rights reserved.
 */

package ca.qc.johnabbott.cs4p6.serialization;

import ca.qc.johnabbott.cs4p6.serialization.io.Destination;
import ca.qc.johnabbott.cs4p6.serialization.io.Source;
import com.sun.jdi.event.VMStartEvent;

import java.io.IOException;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Map;

/**
 * Serializer with optimization for references.
 */
public class OptimizedSerializer<T extends Serializable> extends Serializer {

    private static final byte ALIAS_MARKER = (byte) 0xff;
    private static final byte ORIGINAL_MARKER = 0x00;

    private IdentityHashMap<Serializable, java.lang.Integer> refs;
    private Map<java.lang.Integer, Serializable> refsInv;

    public OptimizedSerializer(Source source, Destination destination, CreatorManifest manifest) {
        super(source, destination, manifest);
        refs = new IdentityHashMap<>();
        refsInv = new HashMap<>();
    }

    public OptimizedSerializer(Source source, CreatorManifest manifest) {
        this(source, null, manifest);
    }

    public OptimizedSerializer(Destination destination, CreatorManifest manifest) {
        this(null, destination, manifest);
    }

    @Override
    public Serializable readSerializable() throws IOException, SerializationException {
        // two cases:
        // - when we are reading a serializable, it can either be an alias or the original
        // - if we know it's an alias, we can check the `refsInv` map with the given index
        // - if we know it's the original, we should just deserialize it normally

        // grab the serial id and flag
        byte serialId = super.read();
        byte flag = super.read();

        // check if it's an alias
        if (flag == ALIAS_MARKER)
            return (T) refsInv.get(super.readInt());

        Serializable s = this.getManifest().create(serialId);
        if (s != null)
            s.deserialize(this);

        return s;
    }

    @Override
    public void write(Serializable value) throws IOException {
        // two cases:
        // - when we are writing a serializable, we are either writing an alias marker or an original
        // - if it's an alias marker, just write the index (destination position) of the aliased object
        // - if it's the original, just serialize the object as normal

        // grab the current destinations position
        Integer idx = this.getDestination().getPosition();

        // write the serial id
        super.write(value.getSerialId());

        // check if it's an alias
        if (refs.containsKey(value)) {
            super.write(ALIAS_MARKER);
            super.write(refs.get(value));
            return;
        }

        // otherwise, write the original and save it
        super.write(ORIGINAL_MARKER);
        super.writeOriginal(value);
        refsInv.put(idx, value);
        refs.put(value, idx);
    }
}
