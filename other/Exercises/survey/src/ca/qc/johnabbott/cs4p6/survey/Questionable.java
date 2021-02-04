/*
 * Copyright (c) 2021 Ian Clement. All rights reserved.
 */

package ca.qc.johnabbott.cs4p6.survey;

/**
 * The structure of a survey question.
 */
public interface Questionable {

    /**
     * Get the title of the question.
     * @return The title.
     */
    String getTitle();

    /**
     * Get the question.
     * @return The question.
     */
    String getQuestion();

    /**
     * Determine if the question is required.
     * @return
     */
    boolean isRequired();

    /**
     * Determine if the response provided is valid. Ex: "true" or "false" for true-false questions.
     * @param response The user's response
     * @return True of the response is valid, false otherwise.
     */
    boolean isValidResponse(String response);
}
