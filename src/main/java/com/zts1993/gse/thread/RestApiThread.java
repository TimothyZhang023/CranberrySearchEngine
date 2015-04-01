/*
 * Copyright (c) 2015 By Timothy Zhang
 */

package com.zts1993.gse.thread;

import com.zts1993.gse.webservice.RestService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.IOException;

/**
 * Created by TianShuo on 2015/3/23.
 */
public class RestApiThread extends Thread {

    private static final Logger logger = LogManager.getLogger("RestApiThread");


    public RestApiThread(String name) {

        super(name);//调用父类带参数的构造方法

    }

    public void run() {


        try {
            logger.info("RestApiThread working~ ");

            String[] args = new String[0];

            RestService.main(args);

        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
