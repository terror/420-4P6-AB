/*
 * Copyright (c) 2021 Ian Clement. All rights reserved.
 */

package ca.qc.johnabbott.cs4p6.collections;

import ca.qc.johnabbott.cs4p6.terrain.Location;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple implementation of a "sparse" array: an array where the data is not necessarily contiguous.
 *
 * - make indices generic?
 * - improve the implementation, maybe move on from map?
 *
 * @author Ian Clement (ian.clement@johnabbott.qc.ca)
 */
public class SparseArray<T extends AsChar> {

    private final T defaultValue;

    // fields
    private Map<Location, T> elements;

    /**
     * Create a solution of a specific size.
     */
    public SparseArray(T defaultValue) {
        this.defaultValue = defaultValue;
        elements = new HashMap<>();
    }

    // checks to see if a node isn't already at `position`, if not,
    // create one.
    private void makeNodeIfNecessary(Location location) {
        if(!elements.containsKey(location))
            elements.put(location, defaultValue); //.copy());
    }

    /**
     * Retrieve the element at a specific location.
     * @param location
     * @return the element at the position.
     */
    public T get(Location location) {
        makeNodeIfNecessary(location);
        return elements.get(location);
    }


    /**
     * Set the element at a position.
     * @param location
     * @param element
     * @precondition The position is in the terrain.
     */
    public void set(Location location, T element) {
        makeNodeIfNecessary(location);
        elements.put(location, element);
    }


    @Override
    public String toString() {

        int minPosX = Integer.MAX_VALUE;
        int maxPosX = Integer.MIN_VALUE;
        int minPosY = Integer.MAX_VALUE;
        int maxPosY = Integer.MIN_VALUE;

        for(Location location : elements.keySet()) {
            minPosX = Math.min(minPosX, location.getX());
            maxPosX = Math.max(maxPosX, location.getX());
            minPosY = Math.min(minPosY, location.getY());
            maxPosY = Math.max(maxPosY, location.getY());
        }

        int width = maxPosX - minPosX + 1;

        StringBuilder builder = new StringBuilder();
        /*builder.append(Token.BORDER_DOWN_AND_RIGHT);
        for(int j = 0; j < width; j++) builder.append(Token.BORDER_HORIZONTAL);
        builder.append(Token.BORDER_DOWN_AND_LEFT + "\n");
*/
        for(int i = minPosY; i <= maxPosY; i++) {
  //          builder.append(Token.BORDER_VERTICAL);
            for(int j = minPosX; j <= maxPosX; j++) {

                Location pos = new Location(j, i);
                if(elements.containsKey(pos))
                    builder.append(elements.get(pos).toChar());
                else
                    builder.append(' ');//Token.UNKNOWN);

            }
       //     builder.append(Token.BORDER_VERTICAL + "\n");
            builder.append('\n');
        }
/*
        builder.append(Token.BORDER_UP_AND_RIGHT);
        for(int j = 0; j < width; j++) builder.append(Token.BORDER_HORIZONTAL);
        builder.append(Token.BORDER_UP_AND_LEFT + "\n");
        */
        return builder.toString();
    }
}
