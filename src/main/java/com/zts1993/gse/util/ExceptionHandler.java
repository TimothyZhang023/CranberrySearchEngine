/*
 * Copyright (c) 2015 By Timothy Zhang
 */

package com.zts1993.gse.util;




import lombok.extern.slf4j.Slf4j;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Created by zts1993 on 15-4-25.
 */
@Slf4j
public class ExceptionHandler {



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
        log.info(exception.getMessage());
        StringWriter sw = new StringWriter();
        exception.printStackTrace(new PrintWriter(sw, true));
        String str = sw.toString();
        log.error("Exception : " + str);
    }

    private void sendNotification() {

    }

    private void print() {
        exception.printStackTrace();
    }


}
