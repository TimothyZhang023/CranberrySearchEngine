/*
 * Copyright (c) 2015 By Timothy Zhang
 */

package com.zts1993.gse.bean;

import java.util.ArrayList;

/**
 * Created by TianShuo on 2015/4/9.
 */
public class QueryResult {

    String keyWord;

    Pager pager;

    long timeSpeed;

    Long totalResultCount;

    ArrayList<QueryResultItem> queryResultItems;


    public QueryResult(String keyWord,long totalResultCount,long timeSpeed, Pager pager, ArrayList<QueryResultItem> queryResultItems) {
        this.keyWord = keyWord;
        this.totalResultCount = totalResultCount;
        this.timeSpeed = timeSpeed;
        this.pager = pager;
        this.queryResultItems = queryResultItems;
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

    public Pager getPager() {
        return pager;
    }

    public void setPager(Pager pager) {
        this.pager = pager;
    }

    public ArrayList<QueryResultItem> getQueryResultItems() {
        return queryResultItems;
    }

    public void setQueryResultItems(ArrayList<QueryResultItem> queryResultItems) {
        this.queryResultItems = queryResultItems;
    }

    public long getTimeSpeed() {
        return timeSpeed;
    }

    public void setTimeSpeed(long timeSpeed) {
        this.timeSpeed = timeSpeed;
    }

    @Override
    public String toString() {
        return "QueryResult{" +
                "keyWord='" + keyWord + '\'' +
                "totalResultCount='" + totalResultCount + '\'' +
                "timeSpeed='" + timeSpeed + '\'' +
                ", pager=" + pager +
                ", queryResultItems=" + queryResultItems +
                '}';
    }
}
