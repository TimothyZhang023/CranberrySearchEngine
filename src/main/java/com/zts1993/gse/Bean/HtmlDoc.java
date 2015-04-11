/*
 * Copyright (c) 2015 By Timothy Zhang
 */

package com.zts1993.gse.bean;

import com.zts1993.gse.filter.TermFilter;
import com.zts1993.gse.segmentation.SegmentationFactory;
import org.ansj.domain.Term;

import java.util.List;

/**
 * Created by TianShuo on 2015/4/8.
 */
public class HtmlDoc {

    private String docId;
    private String url;
    private String title;
    private String content;
    private int wordCount;
    private int filteredWordCount;

    private List<Term> parsedTitle;
    private List<Term> parsedContent;

    public HtmlDoc(String docId, String url, String title, String content) {
        this.docId = docId;
        this.url = url;
        this.title = title;
        this.content = content;
    }


    public void parse() {
        parsedTitle = SegmentationFactory.getDefaultSegmentation().parse(title);
        parsedContent = SegmentationFactory.getDefaultSegmentation().parse(content);
        //wordCount punish
        wordCount = wordCountPunish(parsedContent.size());

    }

    //wordCount punish
    private int wordCountPunish(int count) {
        if (count < Factors.PunishThreshold) {
            count = (int) ((1 / Math.log((count + 1) * 1.0)) * Factors.PunishMultiplier);
        }
        return count;
    }

    public void filter() {
        parsedTitle = TermFilter.process(parsedTitle);
        parsedContent = TermFilter.process(parsedContent);
        filteredWordCount = parsedContent.size();

        filteredWordCount = wordCountPunish(filteredWordCount);


    }

    public String getDocId() {
        return docId;
    }

    public int getWordCount() {
        return wordCount;
    }

    public int getFilteredWordCount() {
        return filteredWordCount;
    }

    public List<Term> getParsedContent() {
        return parsedContent;
    }

    public List<Term> getParsedTitle() {
        return parsedTitle;
    }

    public String getUrl() {
        return url;
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
