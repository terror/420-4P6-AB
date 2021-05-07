/*
 * Copyright (c) 2021 Ian Clement. All rights reserved.
 */

package ca.qc.johnabbott.cs4p6.serialization;

import java.io.IOException;

/**
 * Serializable interface
 *
 * - Implemented by classes that can be serialized.
 *
 * @author Ian Clement (ian.clement@johnabbott.qc.ca)
 */
public interface Serializable {

    /**
     * Get this classes unique serialization ID, used to identify the object when deserializing.
     * @return serial ID.
     */
    byte getSerialId();

    /**
     * Write the binary representation of the object to the provided serializer.
     * @param serializer serializer
     * @preconditions A serialization header has been written to the output.
     * @postconditions A binary representation of the object has been written to the output.
     *     This representation is sufficient to create an identical copy when deserializing.
     */
    void serialize(Serializer serializer) throws IOException;

    /**
     * Read the binary representation of the object from the provided serializer.
     * @param serializer serializer
     * @preconditions The serialization header has been read from the input, and validated.
     * @postconditions The binary representation has been read from the input and the object state
     *     has initialized as an identical copy of the serialized version.
     */
    void deserialize(Serializer serializer) throws IOException, SerializationException;

}
