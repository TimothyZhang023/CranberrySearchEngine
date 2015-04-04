/*
 * Copyright (c) 2015 By Timothy Zhang
 */

package com.zts1993.gse.thread;

import com.alibaba.fastjson.JSON;
import com.zts1993.gse.bean.IndexNotify;
import com.zts1993.gse.counter.GenIndexThreadSemaphore;
import com.zts1993.gse.index.GenIndexFromFileTask;
import com.zts1993.gse.index.InvertedIndex;
import com.zts1993.gse.index.InvertedIndexSingleton;
import com.zts1993.gse.util.ConfigurationUtil;
import com.zts1993.gse.util.RedisQueue;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * Created by TianShuo on 2015/3/22.
 */
public class IndexServiceThread extends Thread {

    private static final Logger logger = LogManager.getLogger("MainServiceThread");

    private static final int DEFAULT_INTERVAL = 1000000;

    public IndexServiceThread(String name) {

        super(name);//调用父类带参数的构造方法

    }

    public void run() {


//        try {
        logger.info("IndexServiceThread working~ ");

        //InvertedIndexTestTool.genIndex();


        InvertedIndex invertedIndex = InvertedIndexSingleton.getInstance();
        ExecutorService executor = Executors.newFixedThreadPool(GenIndexThreadSemaphore.Threads);
        RedisQueue redisQueue = new RedisQueue("IndexNotifyQueue");

        while (true) {
            try {

                String jsonText = redisQueue.pop();
                if (jsonText == null) {
                    try {

                        logger.info("No task in Queue : IndexNotifyQueue");
                        Thread.sleep(1000);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                } else {

                    IndexNotify indexNotify = JSON.parseObject(jsonText, IndexNotify.class);

                    try {

                        String filePath = ConfigurationUtil.getValue("HTMLPATH") + indexNotify.getHash_key() + ".html";

                        Runnable runner = new GenIndexFromFileTask(invertedIndex, filePath,indexNotify);
                        executor.execute(runner);


                        logger.info(String.format("Queue Size: %s and Semaphore: %s ", redisQueue.size(),GenIndexThreadSemaphore.sum()));

                        while (GenIndexThreadSemaphore.sum() < 1) {
                            Thread.sleep(50);
                        }

                    } catch (Exception e) {

                        logger.info(e.getMessage());
                        logger.info(e.getStackTrace());

                    }

                }


            } catch (Exception e) {

                //Check if redis got problem
                logger.info(e.getMessage());
                logger.info(e.getStackTrace());

            }


        }
    }

    public static void main(String[] args) {

        IndexServiceThread indexServiceThread = new IndexServiceThread("Main");
        indexServiceThread.setDaemon(true);
        indexServiceThread.start();


        while (true) {
            try {
                Thread.sleep(1000000);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }


    }


}
