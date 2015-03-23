/*
 * Copyright (c) 2015 By Timothy Zhang
 */

package com.zts1993.gse.service;

import com.zts1993.gse.index.InvertedIndexTestTool;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;


/**
 * Created by TianShuo on 2015/3/22.
 */
public class IndexServiceThread extends Thread {

    private static final Logger logger = LogManager.getLogger("MainServiceThread");

    private static final int DEFAULT_INTERVAL = 10000;

    public IndexServiceThread(String name) {

        super(name);//调用父类带参数的构造方法

    }

    public void run() {

        while (true) {

            try {
                logger.info("IndexServiceThread working~ ");

                InvertedIndexTestTool.genIndex();

                logger.info("IndexServiceThread sleep~ ");
                Thread.sleep(DEFAULT_INTERVAL);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

    }

}
