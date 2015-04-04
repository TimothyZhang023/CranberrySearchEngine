/*
 * Copyright (c) 2015 By Timothy Zhang
 */

package com.zts1993.gse.index;

import com.zts1993.gse.bean.URLInfo;
import com.zts1993.gse.util.ConfigurationUtil;
import com.zts1993.gse.counter.ProceedPageCounter;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.File;
import java.util.ArrayList;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by TianShuo on 2015/3/22.
 */
public class InvertedIndexTestTool {


    private static final Logger logger = LogManager.getLogger("InvertedIndexTool");

    /**
     * @param args
     */
    public static void main(String[] args) {

        genIndex();

        queryIndex("雷锋");
        queryIndex("中国");
        queryIndex("的");
        queryIndex("南京");
        queryIndex("cnbeta");

    }

    public static void queryIndex(String key) {
        ArrayList<URLInfo> urlInfos = query(key);

        if (urlInfos != null) {
            System.out.println(key + "查询结果如下：");
            for (URLInfo urlInfo : urlInfos)
                System.out.println(urlInfo);
        } else {
            System.out.println("真可惜，没找到您要搜索的关键词");
        }
    }

    public static ArrayList<URLInfo> query(String key) {
        InvertedIndex invertedIndex = InvertedIndexSingleton.getInstance();
        return invertedIndex.query(key);
    }

    public static ArrayList<URLInfo> queryAll(Set<String> key) {
        InvertedIndex invertedIndex = InvertedIndexSingleton.getInstance();
        return invertedIndex.queryAll(key);
    }

    public static void genIndexByQueue() {


    }

    public static void genIndex() {
        logger.info("start to genIndex ~ ");


        InvertedIndex invertedIndex = InvertedIndexSingleton.getInstance();
//        List<Term> termList = null;

        //ClassLoader.getSystemResource("html").getPath()
        File root = new File(ConfigurationUtil.getValue("HTMLPATH"));
        File[] fs = root.listFiles();

        logger.info("Html files to be processed : " + fs.length);

        long startMili = System.currentTimeMillis();// 当前时间对应的毫秒数
        long endMili;

        //newCachedThreadPool
        ExecutorService executor = Executors.newFixedThreadPool(4);

        for (int i = 0; i < fs.length; i++) {


            if (!fs[i].isDirectory()) {
                try {

                    String path = fs[i].getAbsolutePath();

                    //  logger.info("path"+path);
                    Runnable runner = new GenIndexFromFileTask(invertedIndex, path);
                    executor.execute(runner);

                    endMili = System.currentTimeMillis();
                    //logger.info("Time elapsed："+getTimeDes(endMili-startMili));
                    if (((endMili - startMili) / 1000 / 60 > 0) && (i % 10 == 1)) {
                        logger.info("Current speed：" + ((double) ProceedPageCounter.sum()) / ((double) (endMili - startMili) / 1000.0 / 60.0) + " pages/min");
                    }

                    Thread.sleep(100);

                } catch (Exception e) {
                    logger.error(e.getMessage());
                    logger.error(e.getStackTrace());
                }

            }


        }

        executor.shutdown(); //关闭后不能加入新线程，队列中的线程则依次执行完


        logger.info("genIndex finished~ ");

    }


    public static String getTimeDes(long ms) {
        int ss = 1000;
        int mi = ss * 60;
        int hh = mi * 60;
        int dd = hh * 24;

        long day = ms / dd;
        long hour = (ms - day * dd) / hh;
        long minute = (ms - day * dd - hour * hh) / mi;
        long second = (ms - day * dd - hour * hh - minute * mi) / ss;
        long milliSecond = ms - day * dd - hour * hh - minute * mi - second * ss;

        StringBuilder str = new StringBuilder();
        if (day > 0) {
            str.append(day).append("天,");
        }
        if (hour > 0) {
            str.append(hour).append("小时,");
        }
        if (minute > 0) {
            str.append(minute).append("分钟,");
        }
        if (second > 0) {
            str.append(second).append("秒,");
        }
        if (milliSecond > 0) {
            str.append(milliSecond).append("毫秒,");
        }
        if (str.length() > 0) {
            str = str.deleteCharAt(str.length() - 1);
        }

        return str.toString();
    }


}
