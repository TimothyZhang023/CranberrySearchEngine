/*
 * Copyright (c) 2015 By Timothy Zhang
 */

package com.zts1993.gse.bean;

import com.zts1993.gse.filter.TermFilter;
import com.zts1993.gse.segmentation.SegmentationFactory;
import com.zts1993.gse.util.Factors;
import org.ansj.domain.Term;

import java.util.List;

/**
 * Created by TianShuo on 2015/4/8.
 */
public class HtmlDoc extends HtmlMeta {

    private int wordCount;

    private List<Term> parsedTitle;
    private List<Term> parsedContent;

    public HtmlDoc(String docId, String url, String title, String content) {
        super(docId, url, title, content);
    }


    public void parse() {
        parsedTitle = SegmentationFactory.getDefaultSegmentation().parse(this.title);
        parsedContent = SegmentationFactory.getDefaultSegmentation().parse(this.content);
        //wordCount punish
        wordCount = wordCountPunish(parsedContent.size());
    }

    private int wordCountPunish(int count) {
        if (count < Factors.PunishThreshold) {
            count = (int) ((1 / Math.log((count + 1) * 1.0)) * Factors.PunishMultiplier);
        }
        return count;
    }

    public void filter() {
        parsedTitle = TermFilter.process(parsedTitle);
        parsedContent = TermFilter.process(parsedContent);
    }


    public int getWordCount() {
        return wordCount;
    }

    public List<Term> getParsedContent() {
        return parsedContent;
    }

    public List<Term> getParsedTitle() {
        return parsedTitle;
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
