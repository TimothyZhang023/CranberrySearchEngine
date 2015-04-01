/*
 * Copyright (c) 2015 By Timothy Zhang
 */

package com.zts1993.gse.thread;

import com.zts1993.gse.index.InvertedIndex;
import com.zts1993.gse.util.FileCharsetDetector;
import com.zts1993.gse.util.HtmlParser;
import com.zts1993.gse.util.MutliThreadCounter;
import com.zts1993.wc.common.SegmentationFactory;
import org.ansj.domain.Term;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.*;
import java.util.List;

/**
 * Created by TianShuo on 2015/3/29.
 */
public class GenIndexFromFileTask implements Runnable {

    private static final Logger logger = LogManager.getLogger("GenInfexFromFileThread");

    private static final int DEFAULT_INTERVAL = 1000000;

    private String filePath;
    private InvertedIndex invertedIndex;


    public GenIndexFromFileTask(InvertedIndex invertedIndex, String filePath) {

        this.filePath = filePath;
        this.invertedIndex = invertedIndex;
    }

    public void run() {

            try {

                logger.info(String.format("processed no.%s: %s ", MutliThreadCounter.incr()+1, filePath));
                genIndexFromFile(invertedIndex, filePath);
            } catch (Exception e) {

                logger.info(e.getMessage());
                logger.info(e.getStackTrace());

             }

    }


    private static void genIndexFromFile(InvertedIndex invertedIndex, String path) {
        long startMili;// 当前时间对应的毫秒数
        long endMili;

        startMili = System.currentTimeMillis();
        List<Term> termList = readFile(path);
        endMili = System.currentTimeMillis();

        long timespend1 = endMili - startMili;

        startMili = System.currentTimeMillis();
        invertedIndex.addToInvertedIndex(termList, path);
        endMili = System.currentTimeMillis();

        long timespend2 = endMili - startMili;

        logger.info(String.format("Segmentation: %s ms;InvertedIndex: %s ms", timespend1, timespend2));

    }

    private static List<Term> readFile(String fileName) {
        BufferedReader br;
        StringBuffer buffer = new StringBuffer();
        String line;

        try {

            String fileEncode= FileCharsetDetector.getFileEncode(fileName);

            br = new BufferedReader(new InputStreamReader(new FileInputStream(fileName),fileEncode));
            while ((line = br.readLine()) != null) {
                buffer.append(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String fileContent = buffer.toString();

        HtmlParser htmlParser = new HtmlParser();
       // fileContent = htmlParser.gb2utf8(fileContent);
        String text = htmlParser.html2Text(fileContent);


        List<Term> parse;
        parse = SegmentationFactory.getIndexSegmentation().parse(text);


        return parse;

    }

}
