/*
 * Copyright (c) 2015 By Timothy Zhang
 */

package com.zts1993.gse.db.redis;

import redis.clients.jedis.Jedis;

/**
 * Created by TianShuo on 2015/3/31.
 */
public class RedisQueue {

    private String key;

    public RedisQueue(String key) {
        this.key = key;
    }

    public void push(String string) {
        Jedis jedis = RedisDB.getJedis();
        jedis.lpush(this.key, string);
        RedisDB.closeJedis(jedis);

    }

    public void push(String[] string) {
        Jedis jedis = RedisDB.getJedis();
        jedis.lpush(this.key, string);
        RedisDB.closeJedis(jedis);

    }


    public String pop() {
        Jedis jedis = RedisDB.getJedis();
        String value = jedis.lpop(this.key);
        RedisDB.closeJedis(jedis);


        return value;
    }

    public long size() {
        Jedis jedis = RedisDB.getJedis();
        long size = jedis.llen(this.key);
        RedisDB.closeJedis(jedis);
        return size;
    }


}
