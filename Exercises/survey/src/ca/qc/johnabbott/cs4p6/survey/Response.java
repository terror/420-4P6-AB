/*
 * Copyright (c) 2021 Ian Clement. All rights reserved.
 */

package ca.qc.johnabbott.cs4p6.survey;

import java.util.Date;

/**
 * A data class that holds a timestamped response.
 */
public class Response {

    // Fields

    private String response;
    private Date timestamp;

    // Constructors

    public Response(String response, Date timestamp) {
        this.response = response;
        this.timestamp = timestamp;
    }

    public Response(String response) {
        this(response, new Date());
    }

    public Response() {
    }

    // Methods

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "survey.Response{" +
                "response='" + response + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
