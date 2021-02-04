/*
 * Copyright (c) 2021 Ian Clement. All rights reserved.
 */

package ca.qc.johnabbott.cs4p6.survey;

/**
 * Question "questions" expect a question as a response, so something ending in a '?'.
 */
public class QuestionQuestion implements Questionable {

    // Fields

    private final String title;
    private final String question;
    private final boolean required;

    // Constructors

    public QuestionQuestion(String question) {
        this("", question, false);
    }

    public QuestionQuestion(String title, String question) {
        this(title, question, false);
    }

    public QuestionQuestion(String title, String question, boolean required) {
        this.title = title;
        this.question = question;
        this.required = required;
    }

    // Methods

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getQuestion() {
        return question;
    }

    @Override
    public boolean isRequired() {
        return required;
    }

    @Override
    public boolean isValidResponse(String response) {
        response = response.trim();
        return response.endsWith("?");
    }
}
