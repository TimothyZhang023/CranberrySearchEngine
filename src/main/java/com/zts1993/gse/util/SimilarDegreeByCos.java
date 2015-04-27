/*
 * Copyright (c) 2015 By Timothy Zhang
 */

package com.zts1993.gse.util;

/**
 * Created by TianShuo on 2015/4/14.
 */

import com.zts1993.gse.db.cache.KVCache;
import com.zts1993.gse.html.HtmlContentProvider;
import com.zts1993.gse.html.IHtmlContentProvider;
import com.zts1993.gse.index.comparator.WordCountComparator;
import com.zts1993.gse.segmentation.SegmentationFactory;
import com.zts1993.gse.segmentation.filter.TermFilter;
import com.zts1993.gse.segmentation.filter.TermFilterForAnsj;
import org.ansj.domain.Term;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.*;

public class SimilarDegreeByCos {

    private static final Logger logger = LogManager.getLogger("SimilarDegreeByCos");


    public static TermFilter getTermFilterForAnsj(String docId) {

        String textContent = KVCache.get("textContent:" + docId);

        if (textContent == null) {
            IHtmlContentProvider iHtmlContentProvider = HtmlContentProvider.getHtmlContentProvider(docId);
            textContent = iHtmlContentProvider.fetchText();
            KVCache.set("textContent:" + docId, textContent);
        }

        List<Term> termList = SegmentationFactory.getNlpSegmentation().parse(textContent);

        return new TermFilterForAnsj(termList, 1).process();
    }


    public static double getSimilarDegree(TermFilter termFilter1, TermFilter termFilter2) {


        HashMap<String, Integer> map1 = termFilter1.getWordFreqMap();
        HashMap<String, Integer> map2 = termFilter2.getWordFreqMap();

        int count1 = termFilter1.getWordCount();
        int count2 = termFilter2.getWordCount();


        List<Map.Entry<String, Integer>> map1List;
        map1List = new ArrayList<Map.Entry<String, Integer>>(map1.entrySet());
        Collections.sort(map1List, new WordCountComparator());

        List<Map.Entry<String, Integer>> map2List;
        map2List = new ArrayList<Map.Entry<String, Integer>>(map2.entrySet());
        Collections.sort(map2List, new WordCountComparator());


        int minSize = map1List.size() > map2List.size() ? map2List.size() : map1List.size();
        int finalSize = minSize > Factors.checkSimWordCount ? Factors.checkSimWordCount : minSize;


        map1List = map1List.subList(0, finalSize);
        map2List = map2List.subList(0, finalSize);


        //创建向量空间模型，使用map实现，主键为词项，值为长度为2的数组，存放着对应词项在字符串中的出现次数
        Map<String, double[]> vectorSpace = new HashMap<String, double[]>();
        double[] itemCountArray;

        for (Map.Entry<String, Integer> map1Item : map1List) {
            String keyWord = map1Item.getKey();
            int value = map1Item.getValue();
            itemCountArray = new double[2];
            itemCountArray[0] = value * 1.0 / count1;
            itemCountArray[1] = 0.0;
            vectorSpace.put(keyWord, itemCountArray);
        }


        for (Map.Entry<String, Integer> map2Item : map2List) {
            String keyWord = map2Item.getKey();
            int value = map2Item.getValue();
            if (vectorSpace.containsKey(keyWord))
                vectorSpace.get(keyWord)[1] = value * 1.0 / count2;
            else {
                itemCountArray = new double[2];
                itemCountArray[0] = 0.0;
                itemCountArray[1] = value * 1.0 / count2;
                vectorSpace.put(keyWord, itemCountArray);

            }
        }

        //计算相似度
        double vector1Modulo = 0.00;//向量1的模
        double vector2Modulo = 0.00;//向量2的模
        double vectorProduct = 0.00; //向量积

        for (Object o : vectorSpace.entrySet()) {
            Map.Entry entry = (Map.Entry) o;
            itemCountArray = (double[]) entry.getValue();

            vector1Modulo += itemCountArray[0] * itemCountArray[0];
            vector2Modulo += itemCountArray[1] * itemCountArray[1];

            vectorProduct += itemCountArray[0] * itemCountArray[1];
        }

        vector1Modulo = Math.sqrt(vector1Modulo);
        vector2Modulo = Math.sqrt(vector2Modulo);


        return (vectorProduct / (vector1Modulo * vector2Modulo));
    }

    public static double getSimilarDegree(String docId1, String docId2) {


        TermFilter termFilter1 = getTermFilterForAnsj(docId1);
        TermFilter termFilter2 = getTermFilterForAnsj(docId2);

        return getSimilarDegree(termFilter1, termFilter2);
    }


}