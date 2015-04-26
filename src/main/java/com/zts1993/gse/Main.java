/*
 * Copyright (c) 2015 By Timothy Zhang
 */

package com.zts1993.gse;


import com.zts1993.gse.thread.IndexServiceThread;
import com.zts1993.gse.thread.RestApiServiceThread;
import com.zts1993.gse.util.Args;
import com.zts1993.gse.util.ReGenerateIndexNotifyQueue;
import org.apache.commons.cli.*;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class Main {

    /**
     * add arg " -api disable " to disable RestApiService
     * add arg " -index disable " to disable IndexService
     * add arg " -genIndexQueue enable " to enable BuildIndexNotifyQueue
     */

    private static final Logger logger = LogManager.getLogger("MainServiceThread");

    private static final int DEFAULT_INTERVAL = 30 * 1000;


    private static boolean enableBuildIndexNotifyQueue = false;
    private static boolean enableApi = true;
    private static boolean enableIndex = true;


    public static String[] globalArgs = new String[0];


    public static void main(String[] args) {
        try {

            globalArgs = args;

            Options options = new Options();
            options.addOption("api", true, "if RestApiService is enable");//参数不可用
            options.addOption("index", true, "if IndexService  is enable");//参数可用
            options.addOption("genIndexQueue", true, "if BuildIndexNotifyQueue is enable");//参数可用


            CommandLineParser parser = new PosixParser();
            CommandLine cmd = parser.parse(options, args);

            if (cmd.hasOption("api")) {
                String apiCheck = cmd.getOptionValue("api");

                if (apiCheck.equals("disable")) {
                    logger.info("RestApiServiceThread will not start up due to args");
                    enableApi = false;
                }

            }

            if (cmd.hasOption("index")) {
                String indexCheck = cmd.getOptionValue("index");
                System.out.println(indexCheck);

                if (indexCheck.equals("disable")) {
                    logger.info("IndexServiceThread will not start up due to args");
                    enableIndex = false;
                }

            }


            if (cmd.hasOption("genIndexQueue")) {
                String indexCheck = cmd.getOptionValue("genIndexQueue");
                System.out.println(indexCheck);

                if (indexCheck.equals("enable")) {
                    logger.info("Gen IndexQueue will start up due to args");
                    enableBuildIndexNotifyQueue = true;
                }

            }


            if (enableBuildIndexNotifyQueue) {
                ReGenerateIndexNotifyQueue.main(args);
            }

            if (enableApi) {
                RestApiServiceThread restApiServiceThread = new RestApiServiceThread("Main");
                restApiServiceThread.setDaemon(true);
                restApiServiceThread.start();
            }

            if (enableIndex) {
                IndexServiceThread.main(args);
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
