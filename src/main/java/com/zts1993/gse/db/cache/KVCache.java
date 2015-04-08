/*
 * Copyright (c) 2015 By Timothy Zhang
 */

package com.zts1993.gse.db.cache;

import com.zts1993.gse.db.redis.RedisDB;
import redis.clients.jedis.Jedis;

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

    public static LRUCache<String, String> getURLInfoCache() {
        init();
        return urlInfoLRUCache;
    }


    public static String get(String key) {

        String value = getURLInfoCache().get(key);
        if (value == null) {
            Jedis jedis = RedisDB.getJedis();
            value = jedis.get(key);
            RedisDB.closeJedis(jedis);
            getURLInfoCache().put(key, value);
        }
        return value;
    }

    public static String get(String key, Jedis jedis) {

        String value = getURLInfoCache().get(key);
        if (value == null) {
            value = jedis.get(key);
            getURLInfoCache().put(key, value);
        }
        return value;
    }


    public static void set(String key, String value) {

        getURLInfoCache().put(key, value);

        Jedis jedis = RedisDB.getJedis();
        jedis.set(key, value);
        RedisDB.closeJedis(jedis);
    }

    public static void set(String key, String value, Jedis jedis) {

        getURLInfoCache().put(key, value);

        jedis.set(key, value);
    }


}
