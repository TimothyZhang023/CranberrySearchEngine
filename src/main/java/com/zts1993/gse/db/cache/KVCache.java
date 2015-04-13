/*
 * Copyright (c) 2015 By Timothy Zhang
 */

package com.zts1993.gse.db.cache;

import com.zts1993.gse.db.redis.RedisSafe;

/**
 * Created by TianShuo on 2015/4/7.
 */
public class KVCache {


    private static LRUCache<String, String> urlInfoLRUCache;
    private static final int DEFAULT_CAPACITY = 50000;

    static {
        init();
    }

    public static void init() {
        if (urlInfoLRUCache == null) {
            synchronized (KVCache.class) {
                if (urlInfoLRUCache == null) {
                    urlInfoLRUCache = new LRUCache<String, String>(DEFAULT_CAPACITY);
                }
            }
        }
    }

    public static LRUCache<String, String> getKVCache() {
        return urlInfoLRUCache;
    }


    public static String get(String key) {

        String value = getKVCache().get(key);
        if (value == null) {
            RedisSafe redisSafe=new RedisSafe();
            value = redisSafe.get(key);
            getKVCache().put(key, value);
        }
        return value;
    }


    public static void set(String key, String value) {
        getKVCache().put(key, value);

        RedisSafe redisSafe=new RedisSafe();
        redisSafe.set(key, value);
    }


    public static int size() {
        return getKVCache().size();
    }


}
