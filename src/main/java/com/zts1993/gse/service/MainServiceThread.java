/*
 * Copyright (c) 2015 By Timothy Zhang
 */

package com.zts1993.gse.service;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;


/**
 * Created by TianShuo on 2015/3/22.
 */
public class MainServiceThread extends Thread {

    private static final Logger logger = LogManager.getLogger("MainServiceThread");


    public MainServiceThread(String name) {

        super(name);//调用父类带参数的构造方法

    }

    public void run() {

        while(true){

            try {
                Thread.sleep(1000);

                logger.info("MainServiceThread ~ ");

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

    }

}
