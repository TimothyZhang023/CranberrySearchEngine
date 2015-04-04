/*
 * Copyright (c) 2015 By Timothy Zhang
 */

package com.zts1993.gse.thread;

import com.zts1993.gse.segmentation.common.SegmentationFactory;
import com.zts1993.gse.segmentation.util.ISegmentation;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * Created by TianShuo on 2015/3/29.
 */
public class SegmentationServiceThread extends Thread {

    private static final Logger logger = LogManager.getLogger("SegmentationServiceThread");

    private static final int DEFAULT_INTERVAL = 60 * 1000;


    public SegmentationServiceThread(String name) {

        super(name);//调用父类带参数的构造方法

    }

    public void run() {


        try {
            logger.info("SegmentationServiceThread working~ ");

            ISegmentation iSegmentation = SegmentationFactory.getDefaultSegmentation();
            iSegmentation.parse("Let us warm up~");

            Thread.sleep(DEFAULT_INTERVAL);


        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }
}
