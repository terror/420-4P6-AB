package ca.qc.johnabbott.cs4p6;

import ca.qc.johnabbott.cs4p6.generator.Generator;
import ca.qc.johnabbott.cs4p6.generator.SentenceGenerator;
import ca.qc.johnabbott.cs4p6.generator.WordGenerator;
import ca.qc.johnabbott.cs4p6.profiler.Profiler;
import ca.qc.johnabbott.cs4p6.profiler.Region;
import ca.qc.johnabbott.cs4p6.profiler.Report;
import ca.qc.johnabbott.cs4p6.profiler.Section;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ProfileSamples {

    private static final boolean DEBUG = true;

    public static final Generator<String> STRING_GENERATOR;
    public static final Random RANDOM;
    public static final int SAMPLE_SIZE = 10000;

    static {
        RANDOM = new Random();
        STRING_GENERATOR = new SentenceGenerator(new WordGenerator("foo bar baz qux quux quuz corge grault garply waldo fred plugh xyzzy thud".split(" ")), 10);
    }

    public static void main(String[] args) throws InterruptedException {
        example0();
        example1();
        example2();
        example3();
        example4();
        example5();
    }

    // ================================================================================
    public static void example0() {

        Section section = new Section("Sample Section 0: Manual creation of regions and sections to verify the reporting only.");

        Region region1 = new Region(section, 5, 1234, 0.25);
        Region region2 = new Region(section, 1, 10000, 0.45);
        Region region3 = new Region(section, 10, 123, 0.01);

        section.addRegion("Sample Region 1", region1);
        section.addRegion("Sample Region 2", region2);
        section.addRegion("Sample Region 3", region3);

        Region total = new Region(section, 1, 20000, 1.0);
        section.addRegion("TOTAL", total);

        List<Section> list = new ArrayList<>();
        list.add(section);

        Report.printAllSections(list);
    }

    // ================================================================================
    public static void example1() throws InterruptedException {
        Profiler.getInstance().markSectionStart("Sample Section 1: the regions are sequential.");

        Thread.sleep(1);
        Profiler.getInstance().markRegionStart("First region.");
        Thread.sleep(2);
        Profiler.getInstance().markRegionEnd();
        Profiler.getInstance().markRegionStart("Second region.");
        Thread.sleep(5);
        Profiler.getInstance().markRegionEnd();

        Profiler.getInstance().markSectionEnd();

        // TODO: remove when done with this example
        if (DEBUG) printProfiler();

        List<Section> sectionList = Profiler.getInstance().produceProfilingData();
        Report.printAllSections(sectionList);
    }

    // ================================================================================
    public static void example2() throws InterruptedException {
        Profiler.getInstance().markSectionStart("Sample Section 2: entering and exiting a region multiple times.");

        for (int i = 0; i < 10; i++) {
            Profiler.getInstance().markRegionStart("The region.");
            Thread.sleep(2);
            Profiler.getInstance().markRegionEnd();
        }

        Profiler.getInstance().markSectionEnd();

        // TODO: remove when done with this example
        if (DEBUG) printProfiler();

        List<Section> sectionList = Profiler.getInstance().produceProfilingData();
        Report.printAllSections(sectionList);
    }

    // ================================================================================
    public static void example3() throws InterruptedException {


        Profiler.getInstance().markSectionStart("Sample Section 3: inner and outer regions.");

        Thread.sleep(1);

        Profiler.getInstance().markRegionStart("First region.");
        Thread.sleep(1);

        Profiler.getInstance().markRegionStart("Second region.");
        Thread.sleep(1);
        Profiler.getInstance().markRegionEnd();

        Profiler.getInstance().markRegionStart("Third region.");
        Thread.sleep(1);
        Profiler.getInstance().markRegionEnd();

        Profiler.getInstance().markRegionEnd(); // end region 1

        Profiler.getInstance().markSectionEnd();

        // TODO: remove when done with this example
        if (DEBUG) printProfiler();

        List<Section> sectionList = Profiler.getInstance().produceProfilingData();
        Report.printAllSections(sectionList);
    }

    // ================================================================================
    public static void example4() throws InterruptedException {
        Profiler.getInstance().markSectionStart("Sample Section 4: build a string using concatenation.");

        String string = "";
        for (int i = 0; i < 1000; i++) {

            Profiler.getInstance().markRegionStart("Generate random strings.");
            String s = STRING_GENERATOR.generate(RANDOM);
            Profiler.getInstance().markRegionEnd();

            Profiler.getInstance().markRegionStart("String concatenation.");
            string += s;
            Profiler.getInstance().markRegionEnd();
        }

        Profiler.getInstance().markRegionStart("Console IO.");
        System.out.println(string);
        Profiler.getInstance().markRegionEnd();

        Profiler.getInstance().markSectionEnd();

        Profiler.getInstance().markSectionStart("Sample Section 5: build a string using StringBuilder");

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 1000; i++) {

            Profiler.getInstance().markRegionStart("Generate random strings.");
            String s = STRING_GENERATOR.generate(RANDOM);
            Profiler.getInstance().markRegionEnd();

            Profiler.getInstance().markRegionStart("Append to StringBuilder.");
            builder.append(s);
            Profiler.getInstance().markRegionEnd();
        }

        Profiler.getInstance().markRegionStart("Console IO.");
        System.out.println(builder.toString());
        Profiler.getInstance().markRegionEnd();

        Profiler.getInstance().markSectionEnd();

        List<Section> sectionList = Profiler.getInstance().produceProfilingData();
        Report.printAllSections(sectionList);

    }

    // ================================================================================
    public static void example5() throws InterruptedException {
        Profiler.getInstance().markSectionStart("Sample Section 6: InsertionSort");

        Profiler.getInstance().markRegionStart("Generate random string.");
        String[] arr = new String[1000];
        for (int i = 0; i < 1000; i++)
            arr[i] = STRING_GENERATOR.generate(RANDOM);
        Profiler.getInstance().markRegionEnd();

        Profiler.getInstance().markRegionStart("Sort.");
        insertionSort(arr);
        Profiler.getInstance().markRegionEnd();

        Profiler.getInstance().markSectionEnd();

        List<Section> sectionList = Profiler.getInstance().produceProfilingData();
        Report.printAllSections(sectionList);
    }

    private static void printProfiler() {
        System.out.println("Profiler contains marks:");
        System.out.println(Profiler.getInstance());
        System.out.println();
    }


    //https://www.geeksforgeeks.org/insertion-sort/
    private static <T extends Comparable<T>> void insertionSort(T[] arr) {

        int n = arr.length;
        for (int i = 1; i < n; ++i) {
            T key = arr[i];
            int j = i - 1;

            Profiler.getInstance().markRegionStart("Shift.");
            while (j >= 0) {
                Profiler.getInstance().markRegionStart("compare(x,y).");
                int x = arr[j].compareTo(key);
                Profiler.getInstance().markRegionEnd();

                if (x <= 0)
                    break;

                arr[j + 1] = arr[j];
                j = j - 1;
            }
            arr[j + 1] = key;
            Profiler.getInstance().markRegionEnd();
        }
    }

}
