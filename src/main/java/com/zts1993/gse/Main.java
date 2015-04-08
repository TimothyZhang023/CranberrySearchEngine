/*
 * Copyright (c) 2015 By Timothy Zhang
 */

package com.zts1993.gse;


import com.zts1993.gse.thread.IndexServiceThread;
import com.zts1993.gse.thread.RestApiServiceThread;
import com.zts1993.gse.util.Args;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class Main {

    /**
     * add arg "-disableApi true" to disable RestApiService
     * add arg "-disableIndex true" to disable IndexService
     */

    private static final Logger logger = LogManager.getLogger("MainServiceThread");

    private static final int DEFAULT_INTERVAL = 60 * 1000;

    public static void main(String[] args) {
        // write your code here
        try {
            Args.setArgs(args);


            if (Args.getArgs("disableApi").equals("TRUE")) {
                logger.info("RestApiServiceThread will not start up due to args");

            } else {
                RestApiServiceThread restApiServiceThread = new RestApiServiceThread("Main");
                restApiServiceThread.setDaemon(true);
                restApiServiceThread.start();
            }

            if (Args.getArgs("disableIndex").equals("TRUE")) {
                logger.info("IndexServiceThread will not start up due to args");

            } else {
                IndexServiceThread indexServiceThread = new IndexServiceThread("Main");
                indexServiceThread.setDaemon(true);
                indexServiceThread.start();

            }

        } catch (Exception e) {
            logger.error("MainServiceThread catch a unhanded exception:" + e.getMessage());
            e.printStackTrace();
        }


        while (true) {
            try {
                long memoryUsed = (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1000 / 1000;

                logger.info(String.format("Memory Used: %s mb", memoryUsed));

                Thread.sleep(DEFAULT_INTERVAL);

            } catch (InterruptedException e) {
                logger.error("MainServiceThread catch a unhanded exception:" + e.getMessage());
                e.printStackTrace();
            }

        }
    }


}
