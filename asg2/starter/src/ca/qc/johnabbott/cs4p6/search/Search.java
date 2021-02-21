/*
 * Copyright (c) 2021 Ian Clement. All rights reserved.
 */

package ca.qc.johnabbott.cs4p6.search;

import ca.qc.johnabbott.cs4p6.collections.Traversable;
import ca.qc.johnabbott.cs4p6.terrain.Direction;
import ca.qc.johnabbott.cs4p6.terrain.Terrain;

/**
 * <p>Defines the structure of a search algorithm. Includes `Traversable<Direction>` as way of navigating
 * the solution path.</p>
 *
 * @author Ian Clement (ian.clement@johnabbott.qc.ca)
 */
public interface Search extends Traversable<Direction> {

    /**
     * Search the provided terrain to find a solution path.
     * @param terrain the terrain to search for a solution path.
     */
    void solve(Terrain terrain);


    /**
     * Determine if the search algorithm has obtained a solution in it's last attempt to solve a terrain.
     * @return true of last terrain has a solution, false otherwise.
     */
    boolean hasSolution();

    /**
     * Print information while performing the search algorithm.
     */
    void verbose();
}
