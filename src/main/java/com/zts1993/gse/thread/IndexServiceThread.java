/*
 * Copyright (c) 2015 By Timothy Zhang
 */

package com.zts1993.gse.thread;

import com.alibaba.fastjson.JSON;
import com.zts1993.gse.bean.Factors;
import com.zts1993.gse.bean.IndexNotify;
import com.zts1993.gse.db.redis.RedisQueue;
import com.zts1993.gse.index.InvertedIndexGenerationTask;
import com.zts1993.gse.index.InvertedIndexThreadSemaphore;
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

        ExecutorService executor = Executors.newFixedThreadPool(InvertedIndexThreadSemaphore.Threads);
        RedisQueue redisQueue = new RedisQueue("IndexNotifyQueue");

        while (true) {
            try {

                String jsonText = redisQueue.pop();
                if (jsonText == null) {
                    try {

                        logger.debug("No task in Queue : IndexNotifyQueue");
                        Thread.sleep(1000);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        IndexNotify indexNotify = JSON.parseObject(jsonText, IndexNotify.class);

                        Runnable runner = new InvertedIndexGenerationTask(indexNotify);
                        executor.execute(runner);

                        logger.info(String.format("Queue Size: %s and Semaphore: %s ", redisQueue.size(), InvertedIndexThreadSemaphore.sum()));

                        while (InvertedIndexThreadSemaphore.sum() < Factors.InvertedIndexThreadSemaphoreThreshold) {
                            Thread.sleep(50);
                        }
                    } catch (Exception e) {
                        logger.info(e.getMessage());
                        e.printStackTrace();
                    }

                }


            } catch (Exception e) {
                //Check if redis got problem
                logger.info(e.getMessage());
                e.printStackTrace();
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
