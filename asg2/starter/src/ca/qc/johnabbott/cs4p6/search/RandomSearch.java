/*
 * Copyright (c) 2021 Ian Clement. All rights reserved.
 */

package ca.qc.johnabbott.cs4p6.search;

import ca.qc.johnabbott.cs4p6.collections.SparseArray;
import ca.qc.johnabbott.cs4p6.terrain.*;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * A "search" for the goal, choosing a random direction each step (but favouring the previous direction 3/4 of the time.
 *
 * @author Ian Clement (ian.clement@johnabbott.qc.ca)
 */
public class RandomSearch implements Search {

    // records where we've been and what steps we've taken.
    private SparseArray<Color> colors;
    private SparseArray<Direction> fromDirections;  // not useful in this algorithm...
    private SparseArray<Direction> toDirections;

    // for tracking the "traversable" solution.
    private Location solution;
    private boolean foundSolution;

    // the terrain we're searching in.
    private Terrain terrain;

    // verbose mode
    private boolean verbose;

    /**
     * Create a new Random search.
     */
    public RandomSearch() {
        verbose = false;
        foundSolution = false;
    }

    @Override
    public boolean hasSolution() {
        return foundSolution;
    }

    @Override
    public void solve(Terrain terrain) {

        this.terrain = terrain;

        foundSolution = true;

        // track locations we've been to using our terrain "memory"
        colors = new SparseArray<>(Color.WHITE);
        fromDirections = new SparseArray<>(Direction.NONE);
        toDirections = new SparseArray<>(Direction.NONE);

        // setup random direction generator.
        Random random = new Random();
        RandomGenerator<Direction> generator = Direction.generator();

        // track the current search location, starting at the terrain start location.
        Location current = terrain.getStart();

        // start in a random direction. We will adjust this accordingly.
        Direction previous = generator.generate(random);

        int iteration = 0;

        while(!current.equals(terrain.getGoal())) {

            // find the next direction
            Direction direction = Direction.NONE;
            Location next = current.get(previous);

            // change direction if we can't go in the previous direction, or with a 25% chance of changing direction
            if(random.nextInt(100) < 25 || (!terrain.inTerrain(next) || terrain.isWall(next)) || colors.get(next) != Color.WHITE) {

                // keep track of what we've seen in a set of directions
                Set<Direction> checked = new HashSet<>();

                // check in all directions randomly
                while (checked.size() < 4) {

                    // get a random direction
                    Direction tmp = generator.generate(random);
                    checked.add(tmp);

                    // see if stepping in that direction is possible and do it!
                    next = current.get(tmp);
                    if (terrain.inTerrain(next) && !terrain.isWall(next) && colors.get(next) == Color.WHITE) {
                        previous = direction = tmp;
                        break;
                    }
                }

                // if no direction was found, we are stuck and leave without solution
                // see below on how foundSolution would normally be used.
                if(direction == Direction.NONE) {
                    //foundSolution = false;
                    return;
                }

            }
            else
                direction = previous;

            // record the step we've taken to memory to recreate the solution in the later traversal.
            toDirections.set(current, direction);

            // step
            current = current.get(direction);

            // record that we've been here
            colors.set(current, Color.BLACK);

            // output that could be useful for debugging.
            if(verbose) {
                System.out.println("=============================");
                System.out.println("Iteration: " + iteration++);
                System.out.println("Colors:");
                System.out.println(colors);
                System.out.println("To directions:");
                System.out.println(toDirections);
            }
        }

        // we reached the goal and have a solution.
        // see below on how foundSolution would normally be used.
        foundSolution = true;
    }

    @Override
    public void verbose() {
        verbose = true;
    }

    @Override
    public void reset() {
        // start the traversal of our path at the terrain's start.
        solution = terrain.getStart();
    }

    @Override
    public Direction next() {
        // recall the direction at this location, move to the corresponding location and return it.
        Direction direction = toDirections.get(solution);
        solution = solution.get(direction);
        return direction;
    }

    @Override
    public boolean hasNext() {
        // we're only done when we get to the terrain goal.
        return !solution.equals(terrain.getGoal());
    }

    @Override
    public String toString() {
        return "RandomSearch";
    }
}
