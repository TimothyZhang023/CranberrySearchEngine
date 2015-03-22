/*
 * Copyright (c) 2015 By Timothy Zhang
 */

package com.zts1993.gse.integration;

import com.zts1993.gse.util.HtmlParser;
import com.zts1993.wc.common.SegmentationFactory;
import org.ansj.domain.Term;

import java.io.*;
import java.util.List;

/**
 * Created by TianShuo on 2015/3/22.
 */
public class WordSegIntegrationTest{



    public static void main(String[] args) {
        // write your code here

        viewHtml();


    }

    private static void viewHtml() {
        File root = new File(ClassLoader.getSystemResource("html").getPath());

        try {
            showAllFiles(root);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void readFile(String fileName) {
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
        System.out.println("Segmentation : " + parse);

    }


    final static void showAllFiles(File dir) throws Exception {
        File[] fs = dir.listFiles();
        for (int i = 0; i < fs.length; i++) {
            System.out.println(fs[i].getAbsolutePath());
            if (fs[i].isDirectory()) {
                try {
                    showAllFiles(fs[i]);
                } catch (Exception e) {
                }
            } else {
                readFile(fs[i].getAbsolutePath());
            }
        }
    }




}
