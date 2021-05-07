/*
 * Copyright (c) 2021 Ian Clement. All rights reserved.
 */

package ca.qc.johnabbott.cs4p6.serialization;

import ca.qc.johnabbott.cs4p6.serialization.io.BufferedChannel;
import ca.qc.johnabbott.cs4p6.serialization.io.Destination;
import ca.qc.johnabbott.cs4p6.serialization.io.Source;

import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * Build a Serializer instance using custom settings.
 *
 * @author Ian Clement (ian.clement@johnabbott.qc.ca)
 */
public class SerializerBuilder {

    // fields

    private boolean optimizeReferences;
    private String destinationFile;
    private String sourceFile;
    private final Serializer.CreatorManifest manifest;

    /**
     * Create the builder.
     */
    public SerializerBuilder() {
        manifest = new Serializer.CreatorManifest();
    }

    /**
     * Enable reference optimization.
     * @return
     */
    public SerializerBuilder optimizeReferences() {
        optimizeReferences = true;
        return this;
    }

    /**
     * Set the destination of the serializer.
     * @param binaryFile
     * @return
     */
    public SerializerBuilder toDestination(String binaryFile) {
        destinationFile = binaryFile;
        return this;
    }

    /**
     * Set the source of the serializer.
     * @param binaryFile
     * @return
     */
    public SerializerBuilder fromSource(String binaryFile) {
        sourceFile = binaryFile;
        return this;
    }

    /**
     * Register a creator for a specific serial ID (deserialization only).
     * @param serialId
     * @param creator
     * @return
     */
    public SerializerBuilder registerCreator(byte serialId, Creator<Serializable> creator) {
        try {
            manifest.register(serialId, creator);
        } catch (SerializationException e) {
            e.printStackTrace();
        }
        return this;
    }

    /**
     * Create the serializer.
     * @return
     * @throws SerializationException
     */
    public Serializer build() throws SerializationException {

        Logger.getInstance().log("SerializerBuilder: building Serializer.");

        if(sourceFile != null && destinationFile != null) {
            Logger.getInstance().error("SerializerBuilder: cannot build serializer that the serializes and deserializes at the same time.");
            throw new SerializationException("Cannot build serializer that the serializes and deserializes at the same time.");
        }

        Serializer serializer = null;
        try {
            if (sourceFile != null) {
                Source channel = new BufferedChannel(new RandomAccessFile(sourceFile, "r").getChannel(), BufferedChannel.Mode.READ);
                if(optimizeReferences)
                    serializer = new OptimizedSerializer(channel, manifest);
                else
                    serializer = new Serializer(channel, manifest);
            }
            else {
                Destination channel = new BufferedChannel(new RandomAccessFile(destinationFile, "rw").getChannel(), BufferedChannel.Mode.WRITE);
                if(optimizeReferences)
                    serializer = new OptimizedSerializer(channel, manifest);
                else
                    serializer = new Serializer(channel, manifest);
            }
        }
        catch (IOException e) {
            throw new SerializationException(e);
        }

        return serializer;
    }

}
