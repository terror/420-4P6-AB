package ca.qc.johnabbott.cs4p6.profiler;

/**
 * Store the information for a profiling region.
 */
public class Region {

    private Section section;
    private int runCount;
    private long elapsedTime;
    private double percentOfSection;

    /**
     * Create a region.
     */
    public Region() {
    }

    /**
     * Create a region.
     * @param section The section name of the region.
     * @param runCount The number of times the region has been run.
     * @param elapsedTime The total elapsed time of the region.
     * @param percentOfSection The percent of the total section elapsed time.
     */
    public Region(Section section, int runCount, long elapsedTime, double percentOfSection) {
        this.section = section;
        this.runCount = runCount;
        this.elapsedTime = elapsedTime;
        this.percentOfSection = percentOfSection;
    }

    public Section getSection() {
        return section;
    }

    public void setSection(Section section) {
        this.section = section;
    }

    public int getRunCount() {
        return runCount;
    }

    public long getElapsedTime() {
        return elapsedTime;
    }

    public double getPercentOfSection() {
        return percentOfSection;
    }

    public void setPercentOfSection(double percentOfSection) {
        this.percentOfSection = percentOfSection;
    }

    /**
     * Add elapsed time to the region.
     * @param elapsed
     */
    public void addElapsedTime(long elapsed) {
        elapsedTime += elapsed;
    }

    /**
     * Add a run for the region.
     */
    public void addRun() {
        runCount++;
    }

    @Override
    public String toString() {
        return "Region{" +
                "section='" + section + '\'' +
                ", runCount=" + runCount +
                ", elapsedTime=" + elapsedTime +
                ", percentOfSection=" + percentOfSection +
                '}';
    }
}
