/*
 * Copyright (c) 2021 Ian Clement. All rights reserved.
 */

package ca.qc.johnabbott.cs4p6.survey;


import java.util.ArrayList;
import java.util.StringBuilder;

public class MultipleChoiceQuestion implements Questionable {
    private String question;
    private String title;
    private ArrayList<String> choices = new ArrayList<>();
    private boolean req;

    public MultipleChoiceQuestion(String question) {
        this.question = question;
        this.title = "";
        this.req = false;
    }

    public MultipleChoiceQuestion(String question, boolean req) {
        this.question = question;
        this.title = "";
        this.req = req;
    }

    public MultipleChoiceQuestion(String question, String title, boolean req) {
        this.question = question;
        this.title = title;
        this.req = req;
    }

    public void addChoice(String choice) {
        this.choices.add(choice);
    }

    public String getChoices() {
       StringBuilder sb = new StringBuilder();
       for(int i = 0; i < choices.size(); ++i)
           sb.append(choices.get(i) + '\n');
       return sb.ToString();
    }

    @Override
    public String getTitle() {
        StringBuilder sb = new StringBuilder();
        return this.title;
    }

    @Override
    public String getQuestion() {
        StringBuilder sb = new StringBuilder();
        sb.append(question + "\n");
        for(int i = 0; i < choices.size(); i++)
            sb.append(String.format("%s. %s \n", (char)(i+97), choices.get(i)));
        return sb.toString();
    }

    @Override
    public boolean isRequired() {
        return this.req;
    }

    @Override
    public boolean isValidResponse(String response) {
        return (int)response.charAt(0) >= 97 && (int)response.charAt(0) <= 97 + choices.size() && response.length() == 1;
    }
}
