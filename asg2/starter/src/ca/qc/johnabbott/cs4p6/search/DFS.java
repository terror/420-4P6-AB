/*
 * Copyright (c) 2021 Ian Clement. All rights reserved.
 */

package ca.qc.johnabbott.cs4p6.search;

import ca.qc.johnabbott.cs4p6.collections.SparseArray;
import ca.qc.johnabbott.cs4p6.collections.Stack;
import ca.qc.johnabbott.cs4p6.terrain.Direction;
import ca.qc.johnabbott.cs4p6.terrain.Location;
import ca.qc.johnabbott.cs4p6.terrain.Terrain;

public class DFS implements Search {
    private Location solution;
    private SparseArray<Color> colors;
    private SparseArray<Direction> fromDirections;
    private SparseArray<Direction> toDirections;
    private Terrain terrain;
    private boolean hasSolution;
    private boolean verbose;

    public DFS() {
        this.verbose     = false;
        this.hasSolution = false;
    }

    @Override
    public void solve(Terrain terrain) {
        this.terrain   = terrain;
        Location start = terrain.getStart();
        Location goal  = terrain.getGoal();

        // initialize our colors, from directions and to directions arrays
        this.colors         = new SparseArray(Color.WHITE);
        this.toDirections   = new SparseArray(Direction.NONE);
        this.fromDirections = new SparseArray(Direction.NONE);

        // initialize stack and push starting location
        Stack<Location> st = new Stack(1000);
        st.push(start);

        int iteration = 0;
        while (!st.isEmpty()) {
            Location curr = st.pop();

            // mark current position as visited
            colors.set(curr, Color.BLACK);

            // check if at goal
            if (curr.equals(goal)) {
                this.hasSolution = true;

                // recreate path
                while(!curr.equals(start)) {
                    Direction tail = this.fromDirections.get(curr);
                    curr = curr.get(tail);
                    this.toDirections.set(curr, tail.opposite());
                }
                return;
            }

            // iterate through neighboring cells
            Direction[] neighbors = Direction.getClockwise();
            for (int i = neighbors.length - 1; i >= 0; --i) {
                Location pos = curr.get(neighbors[i]);

                // continue if visited
                if (colors.get(pos) == Color.BLACK) continue;

                if (terrain.inTerrain(pos) && !terrain.isWall(pos)) {
                    // push neighboring cell to stack
                    st.push(pos);

                    // set from direction
                    this.fromDirections.set(pos, neighbors[i].opposite());
                }
            }

            // print useful information
            if (this.verbose) {
                System.out.println("=============================");
                System.out.println("Iteration: " + iteration++);
                System.out.println("Colors:");
                System.out.println(colors);
                System.out.println("From directions:");
                System.out.println(fromDirections);
            }
        }

        // we have not found a solution at this point
        this.hasSolution = false;
    }

    @Override
    public boolean hasSolution() {
        return this.hasSolution;
    }

    @Override
    public void verbose() {
        this.verbose = true;
    }

    @Override
    public void reset() {
        this.solution = this.terrain.getStart();
    }

    @Override
    public Direction next() {
        Direction direction = toDirections.get(solution);
        solution = solution.get(direction);
        return direction;
    }

    @Override
    public boolean hasNext() {
        return !this.solution.equals(this.terrain.getGoal());
    }

    @Override
    public String toString() {
        return "DFS";
    }
}
