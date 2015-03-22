/*
 * Copyright (c) 2015 By Timothy Zhang
 */

package com.zts1993.gse;


import com.zts1993.gse.index.InvertedIndexTool;
import com.zts1993.gse.service.MainServiceThread;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class Main {

    private static final Logger logger = LogManager.getLogger("MainServiceThread");

    public static void main(String[] args) {
        // write your code here

        MainServiceThread mainServiceThread=new MainServiceThread("Main");
        mainServiceThread.setDaemon(true);
        mainServiceThread.start();



        while(true){

            try {
                Thread.sleep(10000);

                InvertedIndexTool.queryIndex("雷锋");
                //InvertedIndexTool.queryIndex("中国");
                InvertedIndexTool.queryIndex("的");
                InvertedIndexTool.queryIndex("南京");
                //InvertedIndexTool.queryIndex("cnbeta");


            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }


}
