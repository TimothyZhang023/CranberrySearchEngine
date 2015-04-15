/*
 * Copyright (c) 2015 By Timothy Zhang
 */

package com.zts1993.gse.bean;

/**
 * Created by TianShuo on 2015/4/8.
 */
public class HtmlMeta {

    protected String docId;
    protected String url;
    protected String title;
    protected String content;

    public HtmlMeta(String docId, String url, String title, String content) {
        this.docId = docId;
        this.url = url;
        this.title = title;
        this.content = content;
    }


    public String getDocId() {
        return docId;
    }

    public String getUrl() {
        return url;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    @Override
    public String toString() {
        return "HtmlInfo{" +
                "docId='" + docId + '\'' +
                ", url='" + url + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
