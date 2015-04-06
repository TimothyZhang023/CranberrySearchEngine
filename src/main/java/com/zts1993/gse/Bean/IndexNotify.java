/*
 * Copyright (c) 2015 By Timothy Zhang
 */

package com.zts1993.gse.bean;

/**
 * Created by TianShuo on 2015/3/31.
 */
public class IndexNotify {

    private String url;
    private String title;
    private String hash_key;
    private String storage_type;
    private String page_encoding;
    private String queue_time;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getHash_key() {
        return hash_key;
    }

    public void setHash_key(String hash_key) {
        this.hash_key = hash_key;
    }

    public String getStorage_type() {
        return storage_type;
    }

    public void setStorage_type(String storage_type) {
        this.storage_type = storage_type;
    }

    public String getPage_encoding() {
        return page_encoding;
    }

    public void setPage_encoding(String page_encoding) {
        this.page_encoding = page_encoding;
    }

    public String getQueue_time() {
        return queue_time;
    }

    public void setQueue_time(String queue_time) {
        this.queue_time = queue_time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public IndexNotify(String url, String title, String hash_key, String storage_type, String page_encoding, String queue_time) {
        this.url = url;
        this.title = title;
        this.hash_key = hash_key;
        this.storage_type = storage_type;
        this.page_encoding = page_encoding;
        this.queue_time = queue_time;
    }

    public IndexNotify() {
    }

    @Override
    public String toString() {
        return "IndexNotify{" +
                "url='" + url + '\'' +
                ", title='" + title + '\'' +
                ", hash_key='" + hash_key + '\'' +
                ", storage_type='" + storage_type + '\'' +
                ", page_encoding='" + page_encoding + '\'' +
                ", queue_time='" + queue_time + '\'' +
                '}';
    }
}
