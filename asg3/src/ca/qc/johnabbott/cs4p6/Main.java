package ca.qc.johnabbott.cs4p6;

import java.io.*;
import java.util.Scanner;

public class Main {
    public static void main(String args[]) throws FileNotFoundException, IOException {
        /*───────────────────────────────────────────────────────────────────────────│─╗
        │ Setup                                                                    ─╬─│┼
        ╚────────────────────────────────────────────────────────────────────────────│*/

        FileReader reader = new FileReader(new File("input.txt").getAbsolutePath());
        FileWriter writer = new FileWriter(new File("output.txt").getAbsolutePath());
        PrintStream output = new PrintStream(System.out);

        /*───────────────────────────────────────────────────────────────────────────│─╗
        │ Testing                                                                  ─╬─│┼
        ╚────────────────────────────────────────────────────────────────────────────│*/

        statsReader(reader, output);
        statsWriter(writer, output);

        lineNumberWriter(writer, 3);

        censorReader(reader, "each");

        substitutionReader(reader, "programmingiv");
        substitutionWriter(writer, "programmingiv");

        streamBuilderReader("input.txt");
        streamBuilderWriter("output.txt");
    }


    /*───────────────────────────────────────────────────────────────────────────│─╗
    │ Stats Reader & Writer                                                    ─╬─│┼
    ╚────────────────────────────────────────────────────────────────────────────│*/

    public static void statsReader(Reader reader, PrintStream output) {
        StatsReader statsReader = new StatsReader(reader, output);
        Scanner scanner = new Scanner(statsReader);
        while (scanner.hasNextLine())
            scanner.nextLine();
        scanner.close();
    }

    public static void statsWriter(Writer writer, PrintStream output) {
        StatsWriter statsWriter = new StatsWriter(writer, output);
        PrintWriter printWriter = new PrintWriter(statsWriter);
        printWriter.println(
                "Live each season as it passes;" +
                        "\nbreath the air," +
                        "\ndrink the drink," +
                        "\ntaste the fruit," +
                        "\nand resign yourself to the influences of each." +
                        "\n- Henry David Thoreau");
        printWriter.close();
    }

    /*───────────────────────────────────────────────────────────────────────────│─╗
    │ LineNumber Writer                                                        ─╬─│┼
    ╚────────────────────────────────────────────────────────────────────────────│*/

    public static void lineNumberWriter(Writer writer, int width) {
        LineNumberWriter lineNumberWriter = new LineNumberWriter(writer, width);
        PrintWriter printWriter = new PrintWriter(lineNumberWriter);
        printWriter.println(
                "Live each season" +
                        "\nas it passes;" +
                        "\nbreath the air," +
                        "\ndrink the drink," +
                        "\ntaste the fruit," +
                        "\nand resign yourself " +
                        "\nto the influences " +
                        "\nof each." +
                        "\n" +
                        "\n- Henry David Thoreau");
        printWriter.close();
    }

    /*───────────────────────────────────────────────────────────────────────────│─╗
    │ Censor Reader                                                            ─╬─│┼
    ╚────────────────────────────────────────────────────────────────────────────│*/

    public static void censorReader(Reader reader, String word) {
        CensorReader censorReader = new CensorReader(reader, word);
        Scanner scanner = new Scanner(censorReader);
        while (scanner.hasNextLine())
            System.out.println(scanner.nextLine());
        scanner.close();
    }

    /*───────────────────────────────────────────────────────────────────────────│─╗
    │ Substitution Reader & Writer                                             ─╬─│┼
    ╚────────────────────────────────────────────────────────────────────────────│*/

    public static void substitutionReader(Reader reader, String keyword) {
        SubstitutionCipherReader cipherReader = new SubstitutionCipherReader(reader, keyword);
        Scanner scanner = new Scanner(cipherReader);
        while (scanner.hasNextLine())
            System.out.println(scanner.nextLine());
        scanner.close();
    }

    public static void substitutionWriter(Writer writer, String keyword) {
        SubstitutionCipherWriter cipherWriter = new SubstitutionCipherWriter(writer, keyword);
        PrintWriter printWriter = new PrintWriter(cipherWriter);
        printWriter.println("Live each season as it passes;" +
                "\nbreath the air," +
                "\ndrink the drink," +
                "\ntaste the fruit," +
                "\nand resign yourself to the influences of each." +
                "\n- Henry David Thoreau");
        printWriter.close();
    }

    /*───────────────────────────────────────────────────────────────────────────│─╗
    │ Stream Builder                                                           ─╬─│┼
    ╚────────────────────────────────────────────────────────────────────────────│*/

    public static void streamBuilderReader(String filename) throws FileNotFoundException {
        Reader reader = new StreamBuilder(filename)
                .stats(System.out)
                .censor("foo")
                .encrypted("garply")
                .createReader();

        Scanner scanner = new Scanner(reader);

        while (scanner.hasNextLine())
            System.out.println(scanner.nextLine());

        scanner.close();
    }

    public static void streamBuilderWriter(String filename) throws IOException {
        Writer writer = new StreamBuilder(filename)
                .stats(System.out)
                .encrypted("garply")
                .includeLineNumbers(4)
                .createWriter();

        PrintWriter printWriter = new PrintWriter(writer);

        printWriter.println("Live each season as it passes;" +
                "\nbreath the air," +
                "\ndrink the drink," +
                "\ntaste the fruit," +
                "\nand resign yourself to the influences of each." +
                "\n- Henry David Thoreau");

        printWriter.close();
    }
}