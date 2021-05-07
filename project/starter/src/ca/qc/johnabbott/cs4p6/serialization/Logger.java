/*
 * Copyright (c) 2021 Ian Clement. All rights reserved.
 */

package ca.qc.johnabbott.cs4p6.serialization;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * A singleton class for logging information to an output stream.
 *
 * @author Ian Clement (ian.clement@johnabbott.qc.ca)
 */
public class Logger {

    // ======== Inner classes/enums/interfaces ==========

    public enum LogLevel implements Comparable<LogLevel> {
        NONE, ERROR, WARNING, VERBOSE
    }

    // ======== Class members  =========================

    // constants
    private static final SimpleDateFormat LOG_TIMESTAMP_FORMAT;

    // fields
    private static LogLevel logLevel;
    private static Logger instance;

    // initialization of static members (like a constructor)
    static {
        LOG_TIMESTAMP_FORMAT = new SimpleDateFormat("yyyyMMddhhmmssSSS");
        logLevel = LogLevel.ERROR;
    }

    // methods

    public static void setLogLevel(LogLevel logLevel) {
        Logger.logLevel = logLevel;
    }

    public static Logger getInstance() {
        if(instance == null) {
            try {
                instance = new Logger(System.out);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return instance;
    }

    public void create(PrintStream out) throws IOException {
        instance = new Logger(out);
    }

    // ======== Instance members  =======================

    // fields

    private PrintStream out;

    // constructors

    private Logger(PrintStream out) throws IOException {
        this.out = out;
    }

    // methods

    public void error(String message) {
        if(logLevel.compareTo(LogLevel.ERROR) >= 0)
            writeMessage("ERROR", message);
    }

    public void warn(String message) {
        if(logLevel.compareTo(LogLevel.WARNING) >= 0)
            writeMessage("WARNING", message);
    }

    public void log(String message) {
        if(logLevel.compareTo(LogLevel.VERBOSE) >= 0)
            writeMessage("LOG", message);
    }

    private void writeMessage(String prefix, String message) {
        out.printf("[%s/%s] %s\n", LOG_TIMESTAMP_FORMAT.format(new Date()), prefix, message);
        out.flush();
    }

    public void close() {
        out.close();
    }
}
