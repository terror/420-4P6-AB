package ca.qc.johnabbott.cs4p6.profiler;

import java.util.*;

/**
 * A simple profiling class.
 */
public class Profiler {

    // store singleton instance
    private static Profiler INSTANCE;

    static {
        INSTANCE = new Profiler();
    }

    // store marks in list
    private List<Mark> marks;
    // use to prevent regions when not wanted/needed.
    private boolean inSection;

    // private constructor for singleton
    private Profiler() {
        // using a linked list because append is a constant time operation.
        marks = new LinkedList<>();
        inSection = false;
    }

    /**
     * Get profiler singleton instance.
     *
     * @return the profiler singleton.
     */
    public static Profiler getInstance() {
        return INSTANCE;
    }

    /**
     * Starts a new profiling section.
     *
     * @param label The section label.
     */
    public void markSectionStart(String label) {
        marks.add(new Mark(Mark.Type.SECTION_START, System.nanoTime(), label));
        inSection = true;
    }

    /**
     * Ends a section. Must be paired with a corresponding call to `startSection(..)`.
     */
    public void markSectionEnd() {
        marks.add(new Mark(Mark.Type.SECTION_END, System.nanoTime()));
        inSection = false;
    }

    /**
     * Starts a new profiling region.
     *
     * @param label The region label.
     */
    public void markRegionStart(String label) {
        if (inSection)
            marks.add(new Mark(Mark.Type.REGION_START, System.nanoTime(), label));
    }

    /**
     * Ends a region. Must be paired with a corresponding call to `startRegion(..)`.
     */
    public void markRegionEnd() {
        if (inSection)
            marks.add(new Mark(Mark.Type.REGION_END, System.nanoTime()));
    }

    /**
     * Using the currently collected data, generate all the section data for reporting.
     * AfterClears the data (marks) for the next call to this method.
     *
     * @return A list of sections.
     */
    public List<Section> produceProfilingData() {
        List<Section> sections = new ArrayList<>();

        // keep track of current sections when dealing with regions
        Section currentSection = null;

        // used for retrieving end marks
        ArrayList<Mark> store = new ArrayList<>();

        // used for storing labels -> regions
        Map<String, Region> regions = new HashMap<>();

        // iterate over marks
        for (int i = 0; i < marks.size(); ++i) {
            Mark currentMark = marks.get(i);
            String label = currentMark.label;

            if (currentMark.type == Mark.Type.REGION_START) {
                // check if we need to increment the run count of the region
                if (regions.containsKey(label)) {
                    regions.get(label).addRun();
                } else {
                    // if we aren't currently storing the region, add it
                    Region regionToAdd = new Region(currentSection, 1, 0, 0);
                    regions.put(label, regionToAdd);
                }

                // record current mark
                store.add(currentMark);
            }

            if (currentMark.type == Mark.Type.REGION_END) {
                Mark last = store.remove(store.size() - 1);
                // add elapsed time to region
                regions.get(last.label).addElapsedTime(currentMark.time - last.time);
            }

            if (currentMark.type == Mark.Type.SECTION_START) {
                // create new section with mark label
                currentSection = new Section(label);

                // create new region with section
                Region region = new Region(currentSection, 0, 0, 1);

                // record current mark
                store.add(currentMark);

                // map the section label to it's corresponding region
                regions.put(label, region);
            }

            if (currentMark.type == Mark.Type.SECTION_END) {
                // retrieve last stored mark
                Mark last = store.remove(store.size() - 1);

                // add relevant data to region
                regions.get(currentSection.getSectionLabel()).addElapsedTime(currentMark.time - last.time);
                regions.get(currentSection.getSectionLabel()).addRun();

                // iterate over regions
                Iterator it = regions.entrySet().iterator();
                while (it.hasNext()) {
                    // get the next { string, region } pair
                    Map.Entry pair = (Map.Entry) it.next();
                    Region region = (Region) pair.getValue();

                    // add relevant region data
                    region.setPercentOfSection((double) region.getElapsedTime() / (double) (currentMark.time - last.time));

                    // add region to current section, checking if we're at the total region
                    if (region != regions.get(currentSection.getSectionLabel()))
                        currentSection.addRegion((String) pair.getKey(), region);
                    else
                        currentSection.addRegion("TOTAL", region);
                }

                // clear data
                regions.clear();

                // add the completed section
                sections.add(currentSection);
            }
        }

        marks.clear();
        return sections;
    }

    @Override
    public String toString() {
        // print all marks using formatting.
        StringBuilder builder = new StringBuilder();
        for (Mark mark : marks)
            builder.append(String.format("%d %13s %-30s\n", mark.time, mark.type.toString(), mark.label != null ? mark.label : ""));
        return builder.toString();
    }

    /*
    Delimits a section or region of the profiling.
    */
    private static class Mark {

        public Type type;
        public long time;
        public String label;

        // Create mark without a label
        public Mark(Type type, long time) {
            this(type, time, null);
        }

        // Create a mark with a label
        public Mark(Type type, long time, String label) {
            this.type = type;
            this.time = time;
            this.label = label;
        }

        @Override
        public String toString() {
            return "Mark{" +
                    "type=" + type +
                    ", time=" + time +
                    ", label='" + label + '\'' +
                    '}';
        }

        // stores the type of mark
        private enum Type {
            REGION_START, REGION_END, SECTION_START, SECTION_END
        }
    }
}