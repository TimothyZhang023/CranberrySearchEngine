/*
 * Copyright (c) 2015 By Timothy Zhang
 */

package com.zts1993.gse.index;

import com.zts1993.gse.bean.IndexNotify;
import com.zts1993.gse.counter.GenIndexThreadSemaphore;
import com.zts1993.gse.counter.ProceedPageCounter;
import com.zts1993.gse.segmentation.common.SegmentationFactory;
import com.zts1993.gse.util.FileCharsetDetector;
import com.zts1993.gse.util.HtmlParser;
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


    private String filePath;
    private InvertedIndex invertedIndex;
    private IndexNotify indexNotify;


    public GenIndexFromFileTask(InvertedIndex invertedIndex, String filePath, IndexNotify indexNotify) {
        this.filePath = filePath;
        this.invertedIndex = invertedIndex;
        this.indexNotify = indexNotify;
    }

    public void run() {

        try {
            GenIndexThreadSemaphore.decr();

            genIndexFromFile();

            GenIndexThreadSemaphore.incr();
        } catch (Exception e) {

            logger.info(e.getMessage());
            e.printStackTrace();
        }

    }


    private void genIndexFromFile() {
        long startMili;
        long endMili;

        startMili = System.currentTimeMillis();
        List<Term> termList = getSegmentation();
        endMili = System.currentTimeMillis();

        long timespend1 = endMili - startMili;

        startMili = System.currentTimeMillis();

        invertedIndex.addToInvertedIndex(termList, indexNotify.getUrl());

        endMili = System.currentTimeMillis();

        long timespend2 = endMili - startMili;

        ProceedPageCounter.incr();

        logger.debug(String.format("No.%s Segmentation: %s ms; InvertedIndex: %s ms",
                ProceedPageCounter.sum(), timespend1, timespend2));

    }

    /**
     * Segmentation
     *
     * @return List of all words
     */
    private List<Term> getSegmentation() {
        BufferedReader br;
        StringBuffer buffer = new StringBuffer();
        String line;

        try {

            String fileEncode = FileCharsetDetector.getFileEncode(filePath);

            br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), fileEncode));
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
        parse = SegmentationFactory.getDefaultSegmentation().parse(text);


        return parse;

    }

}
