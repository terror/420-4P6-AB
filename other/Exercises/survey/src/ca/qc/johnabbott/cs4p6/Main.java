/*
 * Copyright (c) 2021 Ian Clement. All rights reserved.
 */

package ca.qc.johnabbott.cs4p6;

import ca.qc.johnabbott.cs4p6.survey.ShortAnswerQuestion;
import ca.qc.johnabbott.cs4p6.survey.QuestionQuestion;
import ca.qc.johnabbott.cs4p6.survey.Response;
import ca.qc.johnabbott.cs4p6.survey.Survey;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.BinaryOperator;
import java.util.function.Function;

/**
 * Programming IV Intro Survey
 */
public class Main {

    public static final String OUTPUT_FILE_NAME = "output.txt";

    public static void main(String[] args) {
        Survey survey = createSurvey();
        survey.administer(new Scanner(System.in));
        try {
            outputResponses(survey.getResponses());
            System.out.println("Your responses have been written to a file \"" + OUTPUT_FILE_NAME + "\"");
            System.out.println("Please upload this file to the Moodle dropbox called \"Survey\"");

        } catch (FileNotFoundException e) {
            System.err.println("Something went wrong writing the survey questions to file...");
            System.err.println("The error message is: " + e.getMessage());
            System.err.println("You should probably copy your answers just in case!");
        }
    }

    // Create a survey
    private static Survey createSurvey() {
        Survey survey = new Survey("Welcome to Programming IV!");
        survey.addQuestion(new ShortAnswerQuestion("Q1.", "What name would you like me to use when addressing you?", true));
        survey.addQuestion(new ShortAnswerQuestion("Q2.", "What is your github username?",true));
        survey.addQuestion(new ShortAnswerQuestion("Q3.", "What are your pronouns? (ex: she/her)"));
        survey.addQuestion(new ShortAnswerQuestion("Q4.", "What is your favourite programming language?"));
        survey.addQuestion(new ShortAnswerQuestion("Q5.", "What motivates you to code?"));
        survey.addQuestion(new ShortAnswerQuestion("Q6.", "What is your favourite text editor?"));
        survey.addQuestion(new ShortAnswerQuestion("Q7.", "If you could choose to do anything for a day, what would it be?"));
        survey.addQuestion(new QuestionQuestion("Q8.", "Ask me a question!"));
        return survey;
    }

    // output the user's responses to a CSV file
    private static void outputResponses(List<Response> responses) throws FileNotFoundException {
        FileOutputStream fout = new FileOutputStream(OUTPUT_FILE_NAME);
        PrintWriter writer = new PrintWriter(fout);

        // get the timestamp of the first and last answered questions

        Date last = maxBy(responses, response -> response.getTimestamp())
                .get()
                .getTimestamp();

        // format the
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        writer.print(format.format(last));

        for(Response response : responses) {
            writer.print(',');
            String value = response.getResponse();
            if(value.contains(","))
                value = "\"" + value + "\"";
            writer.print(value);
        }

        // close the print-writer and therefore the file.
        writer.close();
    }

    // find the maximum value in a list using a projection function.
    private static <T, R extends Comparable<R>> Optional<T> maxBy(List<T> data, Function<T, R> f) {

        Iterator<T> iter = data.iterator();

        // empty list -> max is undefined.
        if(!iter.hasNext())
            return Optional.empty();

        // start: the max is the first element in the list
        T max = iter.next();
        R maxValue = f.apply(max);

        while(iter.hasNext()) {
            // get the next value
            T next = iter.next();
            R nextValue = f.apply(next);

            // check to see if 'next' is the new 'max'
            if(maxValue.compareTo(nextValue) < 0) {
                max = next;
                maxValue = nextValue;
            }
        }

        // wrap in an optional
        return Optional.of(max);
    }


}
