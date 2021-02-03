/*
 * Copyright (c) 2021 Ian Clement. All rights reserved.
 */

package ca.qc.johnabbott.cs4p6.survey;

/**
 * Short-answer style questions expect a response containing a single sentence of text.
 */
public class ShortAnswerQuestion implements Questionable {

    // Fields

    private String title;
    private String question;
    private boolean required;

    // Constructors

    public ShortAnswerQuestion(String question) {
        this("", question, false);
    }

    public ShortAnswerQuestion(String title, String question) {
        this(title, question, false);
    }

    public ShortAnswerQuestion(String title, String question, boolean required) {
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
        return true;
    }
}
