/*
 * Copyright (c) 2015 By Timothy Zhang
 */

package com.zts1993.gse.db.cache;

/**
 * Created by TianShuo on 2015/4/7.
 */
public class KVCache {


    private static LRUCache<String, String> kvLRUCache;
    private static final int DEFAULT_CAPACITY = 10000;

    static {
        init();
    }

    public static void init() {
        if (kvLRUCache == null) {
            synchronized (KVCache.class) {
                if (kvLRUCache == null) {
                    kvLRUCache = new LRUCache<String, String>(DEFAULT_CAPACITY);
                }
            }
        }
    }


    public static LRUCache<String, String> getKvCache() {
        return kvLRUCache;
    }


    public static String get(String key) {
        return getKvCache().get(key);
    }


    public static void set(String key, String value) {
        getKvCache().put(key, value);
    }


    public static int size() {
        return getKvCache().size();
    }


}
