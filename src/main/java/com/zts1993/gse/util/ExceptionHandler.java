/*
 * Copyright (c) 2015 By Timothy Zhang
 */

package com.zts1993.gse.util;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Created by zts1993 on 15-4-25.
 */
public class ExceptionHandler {

    private static final Logger logger = LogManager.getLogger("ExceptionHandler");

    private Exception exception;

    public ExceptionHandler(Exception exception) {
        this.exception = exception;
    }

    public void process() {
        log();
        print();
        sendNotification();

    }

    private void log() {
        logger.info(exception.getMessage());
        StringWriter sw = new StringWriter();
        exception.printStackTrace(new PrintWriter(sw, true));
        String str = sw.toString();
        logger.error("Exception : " + str);
    }

    private void sendNotification() {

    }

    private void print() {
        exception.printStackTrace();
    }


}
