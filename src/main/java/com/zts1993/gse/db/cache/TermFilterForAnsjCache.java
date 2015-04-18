/*
 * Copyright (c) 2015 By Timothy Zhang
 */

package com.zts1993.gse.db.cache;

import com.zts1993.gse.segmentation.filter.TermFilterForAnsj;

/**
 * Created by TianShuo on 2015/4/7.
 */
public class TermFilterForAnsjCache {


    private static LRUCache<String, TermFilterForAnsj> termFilterForAnsjCache;
    private static final int DEFAULT_CAPACITY = 100;

    static {
        init();
    }

    public static void init() {
        if (termFilterForAnsjCache == null) {
            synchronized (TermFilterForAnsjCache.class) {
                if (termFilterForAnsjCache == null) {
                    termFilterForAnsjCache = new LRUCache<String, TermFilterForAnsj>(DEFAULT_CAPACITY);
                }
            }
        }
    }

    public static LRUCache<String, TermFilterForAnsj> getCache() {
        return termFilterForAnsjCache;
    }


    public static TermFilterForAnsj get(String key) {
        return getCache().get(key);
    }


    public static void set(String key, TermFilterForAnsj value) {
        getCache().put(key, value);
    }


    public static int size() {
        return getCache().size();
    }


}
