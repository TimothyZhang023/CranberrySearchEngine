/*
 * Copyright (c) 2015 By Timothy Zhang
 */

package com.zts1993.gse.bean;

import java.util.ArrayList;

/**
 * Created by TianShuo on 2015/4/9.
 */
public class QueryResult {

    private String keyWord;

    private Pagination paginationInfo;

    private long processTime;

    private Long totalResultCount;

    private ArrayList<HtmlItem> htmlItems;


    public QueryResult(String keyWord, long totalResultCount, long processTime, Pagination paginationInfo, ArrayList<HtmlItem> htmlItems) {
        this.keyWord = keyWord;
        this.totalResultCount = totalResultCount;
        this.processTime = processTime;
        this.paginationInfo = paginationInfo;
        this.htmlItems = htmlItems;
    }

    public QueryResult() {
    }

    public Long getTotalResultCount() {
        return totalResultCount;
    }

    public void setTotalResultCount(Long totalResultCount) {
        this.totalResultCount = totalResultCount;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    public Pagination getPaginationInfo() {
        return paginationInfo;
    }

    public void setPaginationInfo(Pagination paginationInfo) {
        this.paginationInfo = paginationInfo;
    }

    public ArrayList<HtmlItem> getHtmlItems() {
        return htmlItems;
    }

    public void setHtmlItems(ArrayList<HtmlItem> htmlItems) {
        this.htmlItems = htmlItems;
    }

    public long getProcessTime() {
        return processTime;
    }

    public void setProcessTime(long processTime) {
        this.processTime = processTime;
    }

    @Override
    public String toString() {
        return "QueryResult{" +
                "keyWord='" + keyWord + '\'' +
                "totalResultCount='" + totalResultCount + '\'' +
                "processTime='" + processTime + '\'' +
                ", pagerInfo=" + paginationInfo +
                ", queryResultItems=" + htmlItems +
                '}';
    }
}
