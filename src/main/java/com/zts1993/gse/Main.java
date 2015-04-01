/*
 * Copyright (c) 2015 By Timothy Zhang
 */

package com.zts1993.gse;


import com.zts1993.gse.thread.IndexServiceThread;
import com.zts1993.gse.thread.RestApiThread;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class Main {

    private static final Logger logger = LogManager.getLogger("MainServiceThread");

    public static void main(String[] args) {
        // write your code here
        try {

//            SegmentationServiceThread segmentationServiceThread = new SegmentationServiceThread("Main");
//            segmentationServiceThread.setDaemon(true);
//            segmentationServiceThread.start();
//            Thread.sleep(10 * 1000);
//
            RestApiThread restApiThread = new RestApiThread("Main");
            restApiThread.setDaemon(true);
            restApiThread.start();
            Thread.sleep(3* 1000);

            IndexServiceThread indexServiceThread = new IndexServiceThread("Main");
            indexServiceThread.setDaemon(true);
            indexServiceThread.start();
            Thread.sleep(10 * 1000);


        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        while (true) {
            try {
                Thread.sleep(1000000);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }


}
