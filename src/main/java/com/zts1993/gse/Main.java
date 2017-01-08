/*
 * Copyright (c) 2015 By Timothy Zhang
 */

package com.zts1993.gse;


import com.zts1993.gse.index.InvertedIndexThreadSemaphore;
import com.zts1993.gse.thread.IndexServiceThread;
import com.zts1993.gse.thread.RestApiServiceThread;
import com.zts1993.gse.util.ReGenerateIndexNotifyQueue;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.PosixParser;


@Slf4j
public class Main {

    /**
     * add arg " -api " to enable RestApiService
     * add arg " -index " to enable IndexService
     * add arg " -genIndexQueue " to enable BuildIndexNotifyQueue
     */


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
                log.info("RestApiServiceThread  start up ");
                enableApi = true;

            }

            if (cmd.hasOption("index")) {
                log.info("IndexServiceThread start up ");
                enableIndex = true;

            }


            if (cmd.hasOption("genIndexQueue")) {
                log.info("GenIndexQueue start up");
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
            log.error("MainServiceThread catch a unhanded exception:" + e.getMessage());
            e.printStackTrace();
        }


        while (true) {
            try {
                long memoryUsed = (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1000 / 1000;

                log.info(String.format("Memory Used: %s mb", memoryUsed));
                if (enableIndex && indexServiceThread != null) {
                    log.info(String.format("Queue Size: %s and Semaphore: %s ", indexServiceThread.getRedisQueue().size(), InvertedIndexThreadSemaphore.sum()));
                }

                Thread.sleep(DEFAULT_INTERVAL);

            } catch (InterruptedException e) {
                log.error("MainServiceThread catch a unhanded exception:" + e.getMessage());
                e.printStackTrace();
            }

        }
    }


}
