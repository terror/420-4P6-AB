package ca.qc.johnabbott.cs4p6.profiler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * A simple console report for the profiling data.
 *
 * @author Ian Clement (ian.clement@johnabbott.qc.ca)
 */
public class Report {

    public static final String ROW_FORMAT = "| %-30s | %9d | %10.2f\u00B5s | %6.2f%% %-30s |\n";

    private Report() {
    }

    /**
     * Print all section data.
     * @param sections A list of sections to print.
     */
    public static void printAllSections(List<Section> sections) {
        for(Section section : sections)
            printSection(section);
    }

    /**
     * Print a section data in a table.
     * @param section The section data.
     */
    private static void printSection(Section section) {

        printHeader(section);

        Map<String, Region> regions = section.getRegions();

        // get all region labels from the map, but make sure TOTAL appears last.
        List<String> labels = new ArrayList<>();
        for (String label : regions.keySet()) {
            if (!label.equals(Section.TOTAL))
                labels.add(label);
        }

        // for each region, print it's statistics to console
        for (String label : labels)
            printRow(label, regions.get(label));

        printFooter();
        printRow("TOTAL", regions.get("TOTAL"));
        printFooter();
        System.out.println();
    }

    /**
     * Print the table header.
     * @param section The section to get the section label.
     */
    private static void printHeader(Section section) {
        System.out.println("* " + section.getSectionLabel());
        System.out.println();
        System.out.println(
                "|--------------------------------+-----------+--------------+----------------------------------------|\n" +
                "| Region                         | Run Count | Total Time   | Percent of Section                     |\n" +
                "|--------------------------------+-----------+--------------+----------------------------------------|"
        );
    }

    /**
     * Print the table footer
     */
    private static void printFooter() {
        System.out.println(
                "|--------------------------------+-----------+--------------+----------------------------------------|"
        );
    }

    /**
     * Print a row of the table corresponding to a single region.
     * @param label The region label.
     * @param region The region data.
     */
    private static void printRow(String label, Region region) {
        double elapsedTimeInMicroSeconds = (double) region.getElapsedTime() / 1000.0;

        // construct a nice ASCII progress bar!
        StringBuilder bar = new StringBuilder();
        int pct = (int) (30.0 * region.getPercentOfSection());
        for (int i = 0; i < pct; i++)
            bar.append('\u25A0');

        System.out.format(ROW_FORMAT, label, region.getRunCount(), elapsedTimeInMicroSeconds, region.getPercentOfSection() * 100, bar);
    }

}
