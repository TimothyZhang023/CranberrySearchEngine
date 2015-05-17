/*
 * Copyright (c) 2015 By Timothy Zhang
 */

package com.zts1993.gse.thread;

import com.zts1993.gse.Main;
import com.zts1993.gse.util.ExceptionHandler;
import com.zts1993.gse.webservice.RestService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.IOException;

/**
 * Created by TianShuo on 2015/3/23.
 */
public class RestApiServiceThread extends Thread {

    private static final Logger logger = LogManager.getLogger("RestApiThread");

    private static final int DEFAULT_INTERVAL = 30*1000;

    public RestApiServiceThread(String name) {

        super(name);//调用父类带参数的构造方法

    }

    public void run() {


        try {
            logger.info("RestApiThread working~ ");

            RestService.main(Main.globalArgs);

        } catch (IOException e) {

            new ExceptionHandler(e).process();
        }


    }


    public static void main(String[] args) {

    }
}
