/*
 * Copyright (c) 2021 Ian Clement. All rights reserved.
 */

package ca.qc.johnabbott.cs4p6.utility;

import ca.qc.johnabbott.cs4p6.search.*;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

/**
 * Represents the app configuration, loaded from properties files in the 'res' folder.
 */
public class Config {

    /**
     * Create a Search object for the algorithm name provided.
     * @param algorithmName
     * @return
     * @throws UnknownAlgorithm
     */
    public static Search getAlgorithmByName(String algorithmName) throws UnknownAlgorithm {
        switch (algorithmName.toUpperCase()) {
            case "DFS":
                return new DFS();
            case "BFS":
                return new BFS();
            case "RANDOM":
                return new RandomSearch();
            default:
                throw new UnknownAlgorithm("Unknown search algorithm: " + algorithmName +
                        ". You will need add a case to \"getAlgorithmByName(..)\". Follow this stacktrace to see where to add it!");
        }
    }

    // fields

    private Search search;
    private String terrain;
    private boolean animate;
    private int width;
    private int height;
    private double density;
    private int clusters;
    private boolean verbose;

    /**
     * Load the config from the properties file.
     * @param propertiesFile
     * @throws PropertiesException
     */
    public Config(File propertiesFile) throws PropertiesException {

        // extract app config from the .properties file
        Properties properties = new Properties();
        try {
            // load global properties first
            properties.load(new FileReader("res/global.properties"));
            // load specific properties second.
            properties.load(new FileReader(propertiesFile));
        } catch (IOException e) {
            throw new PropertiesException(e);
        }

        String algorithmName = properties.getProperty("search");
        if(algorithmName == null)
            throw new PropertiesException("Missing search property");

        try {
            search = getAlgorithmByName(algorithmName);
        } catch (UnknownAlgorithm e) {
            throw new PropertiesException(e);
        }

        terrain = properties.getProperty("terrain");

        try {
            animate = Boolean.valueOf(properties.getProperty("animate"));
            width = Integer.parseInt(properties.getProperty("width"));
            height = Integer.parseInt(properties.getProperty("height"));
            density = Double.parseDouble(properties.getProperty("density"));
            clusters = Integer.parseInt(properties.getProperty("clusters"));
            verbose = Boolean.valueOf(properties.getProperty("verbose"));
            if(verbose)
                search.verbose();
        }
        catch (NumberFormatException e) {
            throw new PropertiesException(e);
        }

    }

    public Search getSearch() {
        return search;
    }

    public String getTerrain() {
        return terrain;
    }

    public boolean animate() {
        return animate;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public double getDensity() {
        return density;
    }

    public int getClusters() {
        return clusters;
    }

    public boolean debug() {
        return verbose;
    }

    @Override
    public String toString() {
        return "Config{" +
                "search=" + search +
                ", terrain='" + terrain + '\'' +
                ", animate=" + animate +
                ", width=" + width +
                ", height=" + height +
                ", density=" + density +
                ", clusters=" + clusters +
                ", verbose=" + verbose +
                '}';
    }
}
