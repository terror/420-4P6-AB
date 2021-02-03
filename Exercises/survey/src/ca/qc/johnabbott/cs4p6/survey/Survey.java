/*
 * Copyright (c) 2021 Ian Clement. All rights reserved.
 */

package ca.qc.johnabbott.cs4p6.survey;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * A simple survey consisting of a series of questions.
 */
public class Survey {

    // Fields

    private String title;
    private List<Questionable> questions;
    private List<Response> responses;

    // Constructors

    /**
     * Create an empty survey.
     * @param title The title of the survey
     */
    public Survey(String title) {
        this.title = title;
        questions = new ArrayList<>();
        responses = new ArrayList<>();
    }

    // Methods

    /**
     * Add a question to the end of the survey.
     * @param question The question to add to the survey.
     */
    public void addQuestion(Questionable question) {
        questions.add(question);
    }

    /**
     * Perform the survey from the user input.
     * @param input The input source to draw the survey responses.
     */
    public void administer(Scanner input) {
        System.out.println(title);
        System.out.println();
        for(Questionable question : questions)
            responses.add(askQuestion(input, question));
    }

    /**
     * Get the responses from a performed survey.
     * @return The responses to the survey questions in the order they were performed.
     */
    public List<Response> getResponses() {
        return responses;
    }

    // perform a single question from the survey.
    private Response askQuestion(Scanner in, Questionable question) {

        String response;
        boolean first = true;  // tracks whether the question is new or is being re-asked.
        boolean requiredCheck;  // tracks whether an required question is not-blank

        do {

            // warn the user that the previous response was invalid.
            if(!first)
                System.out.println("Invalid response, please try again.");
            else
                first = false;

            // prompt the user.
            System.out.println();
            System.out.printf("# %s %s", question.getTitle(), question.isRequired() ? "" : "(optional)");
            System.out.println();
            System.out.println(question.getQuestion());
            System.out.println();
            System.out.print("> ");

            // read the response
            response = in.nextLine();

            // required questions cannot be left blank, so setting this to 'false' will re-prompt.
            requiredCheck = !question.isRequired() || !response.isBlank();

        } while (!requiredCheck || !question.isValidResponse(response));

        System.out.println();

        return new Response(response);
    }

}
