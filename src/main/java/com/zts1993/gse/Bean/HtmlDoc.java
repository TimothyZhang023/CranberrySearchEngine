/*
 * Copyright (c) 2015 By Timothy Zhang
 */

package com.zts1993.gse.bean;

import com.zts1993.gse.segmentation.filter.TermFilter;
import com.zts1993.gse.segmentation.filter.TermFilterForAnsj;
import com.zts1993.gse.util.Factors;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by TianShuo on 2015/4/8.
 */
public class HtmlDoc extends HtmlMeta {

    private int wordCount=-1;
    private int rawWordCount=-1;
    HashMap<String, Integer> wordFreqMap = new HashMap<String, Integer>();
    HashMap<String, Integer> titleWordFreqMap = new HashMap<String, Integer>();
    HashMap<String, Integer> contentWordFreqMap = new HashMap<String, Integer>();


    public HtmlDoc(String docId, String url, String title, String content) {
        super(docId, url, title, content);
    }

    public HtmlDoc parse() {

        TermFilter termTitleFilter = new TermFilterForAnsj(this.title, Factors.titleWeight);
        TermFilter termContentFilter = new TermFilterForAnsj(this.content, Factors.contentWeight);

        titleWordFreqMap = termTitleFilter.getWordFreqMap();
        contentWordFreqMap = termContentFilter.getWordFreqMap();

        mergeWordFreqMap();

        rawWordCount = termContentFilter.getWordCount() + termTitleFilter.getWordCount();
        wordCount = wordCountPunish(termContentFilter.getWordCount());

        return this;
    }

    private void mergeWordFreqMap() {

        wordFreqMap.putAll(contentWordFreqMap);

        for (Map.Entry titleEntry : titleWordFreqMap.entrySet()) {
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
        if(wordCount==-1){
            parse();
        }
        return wordCount+1;
    }

    public HashMap<String, Integer> getWordFreqMap() {
        if(wordCount==-1){
            parse();
        }
        return wordFreqMap;
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

    public int getRawWordCount() {
        if(wordCount==-1){
            parse();
        }
        return rawWordCount+1;
    }
}
