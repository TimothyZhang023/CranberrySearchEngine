/*
 * Copyright (c) 2015 By Timothy Zhang
 */

package com.zts1993.gse.bean;

import com.zts1993.gse.segmentation.SegmentationFactory;
import com.zts1993.gse.segmentation.filter.TermFilterForAnsj;
import com.zts1993.gse.util.Factors;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by TianShuo on 2015/4/8.
 */
public class HtmlDoc extends HtmlMeta {

    private int wordCount;
    HashMap<String, Integer> wordFreqMap = new HashMap<String, Integer>();

    TermFilterForAnsj termTitleFilter;
    TermFilterForAnsj termContentFilter;

    public HtmlDoc(String docId, String url, String title, String content) {
        super(docId, url, title, content);
    }

    public HtmlDoc parse() {

        termTitleFilter = new TermFilterForAnsj(SegmentationFactory.getDefaultSegmentation().parse(this.title), Factors.titleWeight);
        termContentFilter = new TermFilterForAnsj(SegmentationFactory.getDefaultSegmentation().parse(this.content), Factors.contentWeight);

        mergeWordFreqMap(termTitleFilter.process().getWordFreqMap(), termContentFilter.process().getWordFreqMap());
        wordCount = wordCountPunish(termContentFilter.getWordCount());

        return this;
    }

    private void mergeWordFreqMap(HashMap<String, Integer> titleMap, HashMap<String, Integer> contentMap) {

        wordFreqMap.putAll(contentMap);

        for (Map.Entry titleEntry : titleMap.entrySet()) {
            String key = titleEntry.getKey().toString();
            if (wordFreqMap.containsKey(key)) {
                Integer termCount = (Integer) titleEntry.getValue() + wordFreqMap.get(key);
                wordFreqMap.put(key, termCount);
            }
        }

    }

    private int wordCountPunish(int count) {
        if (count < Factors.PunishThreshold) {
            count = (int) ((1 / Math.log((count + 1) * 1.0)) * Factors.PunishMultiplier);
        }
        return count;
    }


    public int getWordCount() {
        return wordCount;
    }

    public HashMap<String, Integer> getWordFreqMap() {
        return wordFreqMap;
    }

    public TermFilterForAnsj getTermContentFilter() {
        return termContentFilter;
    }

    @Override
    public String toString() {
        return "HtmlDoc{" +
                "docId='" + docId + '\'' +
                ", url='" + url + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
