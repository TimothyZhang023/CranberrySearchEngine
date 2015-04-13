/*
 * Copyright (c) 2015 By Timothy Zhang
 */

package com.zts1993.gse.bean;

/**
 * Created by TianShuo on 2015/4/9.
 */
public class HtmlItem extends HtmlMeta {

    private double rank;

    public HtmlItem(String docId, String url, String title, String content, double rank) {
        super(docId, url, title, content);
        this.rank = rank;

    }

    public double getRank() {
        return rank;
    }

    public void setRank(double rank) {
        this.rank = rank;
    }

    @Override
    public String toString() {
        return "QueryResultItem{" +
                "docId='" + docId + '\'' +
                ", url='" + url + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", rank=" + rank +
                '}';
    }
}
