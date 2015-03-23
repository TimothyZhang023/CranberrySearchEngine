/*
 * Copyright (c) 2015 By Timothy Zhang
 */

package com.zts1993.gse.index;

import com.zts1993.gse.bean.URLInfo;
import com.zts1993.gse.util.HtmlParser;
import com.zts1993.wc.common.SegmentationFactory;
import org.ansj.domain.Term;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

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
        InvertedIndex invertedIndex = InvertedIndexSingleton.getInstance();
        ArrayList<URLInfo> urlInfos = invertedIndex.query(key);

        if (urlInfos != null) {
            System.out.println(key + "查询结果如下：");
            for (URLInfo urlInfo : urlInfos)
                System.out.println(urlInfo);
        } else {
            System.out.println("真可惜，没找到您要搜索的关键词");
        }
    }

    public static void genIndex() {
        logger.info("start to genIndex ~ ");


        InvertedIndex invertedIndex = InvertedIndexSingleton.getInstance();
        List<Term> termList = null;

        File root = new File(ClassLoader.getSystemResource("html").getPath());
        File[] fs = root.listFiles();
        for (int i = 0; i < fs.length; i++) {
            System.out.println(fs[i].getAbsolutePath());
            if (fs[i].isDirectory()) {
            } else {
                termList = readFile(fs[i].getAbsolutePath());
                invertedIndex.addToInvertedIndex(termList, fs[i].getAbsolutePath());

            }
        }

        logger.info("genIndex finished~ ");

    }


    private static List<Term> readFile(String fileName) {
        BufferedReader br;
        StringBuffer buffer = new StringBuffer();
        String line;

        try {
            br = new BufferedReader(new FileReader(fileName));
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
        String text = htmlParser.html2Text(fileContent);
//        System.out.println(text);

        List<Term> parse;
        parse = SegmentationFactory.getIndexSegmentation().parse(text);
//        System.out.println("Segmentation : " + parse);

        return parse;

    }

}
