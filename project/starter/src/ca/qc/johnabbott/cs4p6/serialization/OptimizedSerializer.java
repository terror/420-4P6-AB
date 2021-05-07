/*
 * Copyright (c) 2021 Ian Clement. All rights reserved.
 */

package ca.qc.johnabbott.cs4p6.serialization;

import ca.qc.johnabbott.cs4p6.serialization.io.Destination;
import ca.qc.johnabbott.cs4p6.serialization.io.Source;

import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Map;

/**
 * Serializer with optimization for references.
 */
public class OptimizedSerializer extends Serializer {

    private static final byte ALIAS_MARKER = (byte) 0xff;
    private static final byte ORIGINAL_MARKER = 0x00;

    private Map<Object, java.lang.Integer> refs;
    private Map<java.lang.Integer, Object> refsInv;

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

    // TODO: override as necessary

}
