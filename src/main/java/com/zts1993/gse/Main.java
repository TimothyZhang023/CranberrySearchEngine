/*
 * Copyright (c) 2015 By Timothy Zhang
 */

package com.zts1993.gse;


import com.zts1993.gse.index.InvertedIndexThreadSemaphore;
import com.zts1993.gse.thread.IndexServiceThread;
import com.zts1993.gse.thread.RestApiServiceThread;
import com.zts1993.gse.util.ReGenerateIndexNotifyQueue;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.PosixParser;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class Main {

    /**
     * add arg " -api " to enable RestApiService
     * add arg " -index " to enable IndexService
     * add arg " -genIndexQueue " to enable BuildIndexNotifyQueue
     */

    private static final Logger logger = LogManager.getLogger("MainServiceThread");

    private static final int DEFAULT_INTERVAL = 30 * 1000;


    private static boolean enableApi = false;
    private static boolean enableIndex = false;
    private static boolean enableBuildIndexNotifyQueue = false;


    public static String[] globalArgs = new String[0];


    public static void main(String[] args) {

        RestApiServiceThread restApiServiceThread = null;
        IndexServiceThread indexServiceThread = null;


        try {

            globalArgs = args;

            Options options = new Options();
            options.addOption("api", false, "if RestApiService is enable");//参数可用
            options.addOption("index", false, "if IndexService  is enable");//参数可用
            options.addOption("genIndexQueue", false, "if BuildIndexNotifyQueue is enable");//参数可用


            CommandLineParser parser = new PosixParser();
            CommandLine cmd = parser.parse(options, args);

            if (cmd.hasOption("api")) {
                logger.info("RestApiServiceThread  start up ");
                enableApi = true;

            }

            if (cmd.hasOption("index")) {
                logger.info("IndexServiceThread start up ");
                enableIndex = true;

            }


            if (cmd.hasOption("genIndexQueue")) {
                logger.info("GenIndexQueue start up");
                enableBuildIndexNotifyQueue = true;

            }


            if (enableBuildIndexNotifyQueue) {
                ReGenerateIndexNotifyQueue.main(args);
            }

            if (enableApi) {
                restApiServiceThread = new RestApiServiceThread("Main");
                restApiServiceThread.setDaemon(true);
                restApiServiceThread.start();


            }

            if (enableIndex) {
                indexServiceThread = new IndexServiceThread("Main");
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
                if (enableIndex && indexServiceThread != null) {
                    logger.info(String.format("Queue Size: %s and Semaphore: %s ", indexServiceThread.getRedisQueue().size(), InvertedIndexThreadSemaphore.sum()));
                }

                Thread.sleep(DEFAULT_INTERVAL);

            } catch (InterruptedException e) {
                logger.error("MainServiceThread catch a unhanded exception:" + e.getMessage());
                e.printStackTrace();
            }

        }
    }


}
