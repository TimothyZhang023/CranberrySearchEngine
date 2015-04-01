/*
 * Copyright (c) 2015 By Timothy Zhang
 */

package com.zts1993.gse.bean;

/**
 * Created by TianShuo on 2015/3/31.
 */
public class IndexNotify {

    private String hash_key;
    private String storage_type;

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


    public IndexNotify(String hash_key, String storage_type) {
        this.hash_key = hash_key;
        this.storage_type = storage_type;
    }

    public IndexNotify() {
    }
}
