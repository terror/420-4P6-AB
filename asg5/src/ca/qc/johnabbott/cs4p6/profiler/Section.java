package ca.qc.johnabbott.cs4p6.profiler;


import java.util.HashMap;
import java.util.Map;

/**
 * Store the information for a profiling section.
 */
public class Section {

    // A special region representing the entire section.
    public static final String TOTAL = "TOTAL";

    //
    private String sectionLabel;
    private Map<String, Region> regions;

    /**
     * Create a section.
     * @param sectionLabel The section label.
     */
    public Section(String sectionLabel) {
        this.sectionLabel = sectionLabel;
        regions = new HashMap<>();
        regions.put(TOTAL, new Region());
    }

    public String getSectionLabel() {
        return sectionLabel;
    }

    public void setSectionLabel(String sectionLabel) {
        this.sectionLabel = sectionLabel;
    }

    public void addRegion(String regionLabel, Region region) {
        regions.put(regionLabel, region);
    }

    public Map<String, Region> getRegions() {
        return regions;
    }

}
