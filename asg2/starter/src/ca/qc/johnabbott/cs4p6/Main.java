/*
 * Copyright (c) 2021 Ian Clement. All rights reserved.
 */

package ca.qc.johnabbott.cs4p6;

import ca.qc.johnabbott.cs4p6.terrain.RandomGenerator;
import ca.qc.johnabbott.cs4p6.graphics.GraphicalTerrain;
import ca.qc.johnabbott.cs4p6.search.Search;
import ca.qc.johnabbott.cs4p6.search.UnknownAlgorithm;
import ca.qc.johnabbott.cs4p6.terrain.Terrain;
import ca.qc.johnabbott.cs4p6.utility.Config;
import ca.qc.johnabbott.cs4p6.utility.Menu;
import ca.qc.johnabbott.cs4p6.utility.PropertiesException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

/**
 * Main
 *
 * @author Ian Clement (ian.clement@johnabbott.qc.ca)
 */
public class Main {

    public static final String RESOURCES_LOCATION = "res";
    public static final int WINDOW_WIDTH = 1200;
    public static final int WINDOW_HEIGHT = 800;

    public static void main(String[] args) throws IOException, UnknownAlgorithm {

        // create the menu from the files in the resourses folder.
        Menu<File> menu = new Menu<>();
        File[] fileList = new File(RESOURCES_LOCATION).listFiles();
        Arrays.sort(fileList, new Comparator<File>() {
            @Override
            public int compare(File o1, File o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });
        for(File f : fileList) {
            if(f.isDirectory() || f.getName().startsWith("global"))
                continue;
            menu.addMenuItem(f.getName().replace(".properties", ""), f);
        }

        menu.printMenu();
        System.out.println();

        while(menu.nextChoice()) {

            // get the user's choice
            File choice = menu.getChoice();

            // print header
            System.out.println();
            System.out.println("=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
            System.out.println("Choose a configuration:");
            System.out.flush();

            // load configuration, reporting errors as the happen
            Config config = null;
            try {
                config = new Config(choice);
            } catch (PropertiesException e) {
                System.err.println(e.getMessage());
                e.printStackTrace();
                System.exit(0);
            }

            System.out.println(config);

            if(config.getTerrain().equals("random"))
                runRandom(config);
            else
                runFromFile(config);


            // print footer
            System.out.println("=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
            System.out.println();

            // ask the user again for input
            menu.printMenu();
            System.out.println();
        }

    }

    /**
     * Run a random
     */
    private static void runRandom(Config config) {

        Scanner consoleInput = new Scanner(System.in);

        RandomGenerator<Terrain> generator = Terrain.generator(config.getWidth(), config.getHeight(), config.getDensity(), config.getClusters());
        Random random = new Random();

        Search search = config.getSearch();

        // load terrain
        Terrain terrain = generator.generate(random);
        System.out.println(terrain);

        // solve terrain
        search.solve(terrain);

        if(search.hasSolution()) {
            // output solution
            terrain.applySolution(search);
            System.out.println(terrain.toString(false));
            if (!terrain.isSolved())
                System.out.println("Applying search algorithm does not yield a solution.");
        }
        else {
            System.out.println("Search algorithm could not find a solution.");
        }

        // animate is setup
        if(config.animate())
            GraphicalTerrain.run(terrain, search, WINDOW_WIDTH, WINDOW_HEIGHT, false);

    }

    /**
     * Run the terrain search from a file.
     * @param config
     * @throws FileNotFoundException
     */
    private static void runFromFile(Config config) throws FileNotFoundException {

        // load terrain
        Terrain terrain = new Terrain(config.getTerrain());
        System.out.println(terrain);

        // solve terrain
        Search search = config.getSearch();
        search.solve(terrain);

        if(search.hasSolution()) {
            // output solution
            terrain.applySolution(search);
            System.out.println(terrain.toString(false));
            if (!terrain.isSolved())
                System.out.println("Applying search algorithm does not yield a solution.");
        }
        else {
            System.out.println("Search algorithm could not find a solution.");
        }

        // animate if setup
        if(config.animate())
            GraphicalTerrain.run(terrain, search, WINDOW_WIDTH, WINDOW_HEIGHT, false);

    }


}
