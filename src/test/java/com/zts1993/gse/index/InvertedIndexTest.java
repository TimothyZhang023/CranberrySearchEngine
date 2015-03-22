/*
 * Copyright (c) 2015 By Timothy Zhang
 */

package com.zts1993.gse.index;

import com.zts1993.gse.bean.URLInfo;
import com.zts1993.gse.util.HtmlParser;
import com.zts1993.wc.common.SegmentationFactory;
import org.ansj.domain.Term;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by TianShuo on 2015/3/22.
 */
public class InvertedIndexTest {


    /**
     * @param args
     */
    public static void main(String[] args) {
        InvertedIndex invertedIndex = new InvertedIndex();
        List<Term> termList=null;

        File root = new File(ClassLoader.getSystemResource("html").getPath());
        File[] fs = root.listFiles();
        for (int i = 0; i < fs.length; i++) {
            System.out.println(fs[i].getAbsolutePath());
            if (fs[i].isDirectory()) {
            } else {
                termList=readFile(fs[i].getAbsolutePath());
                invertedIndex.addToInvertedIndex(termList,fs[i].getAbsolutePath());

            }
        }



        String key = "雷锋";
        ArrayList<URLInfo> urlInfos = invertedIndex.query(key);

        if(urlInfos != null)
        {
            System.out.println("得到了结果如下：");
            for(URLInfo urlInfo : urlInfos)
                System.out.println(urlInfo);
        }
        else
        {
            System.out.println("真可惜，没找到您要搜索的关键词");
        }


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
        System.out.println("Segmentation : " + parse);

        return parse;

    }

}
