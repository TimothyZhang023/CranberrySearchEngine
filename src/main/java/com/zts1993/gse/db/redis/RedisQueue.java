/*
 * Copyright (c) 2015 By Timothy Zhang
 */

package com.zts1993.gse.db.redis;

import com.zts1993.gse.util.ConfigurationUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * Created by TianShuo on 2015/3/31.
 */
public class RedisQueue {

    private static RedisClient redisQueueClient;

    public static RedisClient getRedisQueueClient() {
        if (redisQueueClient == null) {
            redisQueueClient = new RedisClient(
                    ConfigurationUtil.getValue("RedisIndexNotifyServerIp", "127.0.0.1"),
                    Integer.parseInt(ConfigurationUtil.getValue("RedisIndexNotifyServerPort", "6379")),
                    50
            );

        }
        return redisQueueClient;
    }


    public static Jedis getJedis() {
        return getRedisQueueClient().getJedis();
    }


    public static JedisPool getJedisPool() {
        return getRedisQueueClient().getJedisPool();
    }


    public static void closeJedis(Jedis jedis) {
        getJedisPool().returnResource(jedis);
    }





    private String key;

    public RedisQueue(String key) {
        this.key = key;
    }

    public void push(String string) {
        Jedis jedis = RedisQueue.getJedis();
        jedis.lpush(this.key, string);
        RedisQueue.closeJedis(jedis);

    }

    public void push(String[] string) {
        Jedis jedis = RedisQueue.getJedis();
        jedis.lpush(this.key, string);
        RedisQueue.closeJedis(jedis);

    }


    public String pop() {
        Jedis jedis = RedisQueue.getJedis();
        String value = jedis.lpop(this.key);
        RedisQueue.closeJedis(jedis);


        return value;
    }

    public long size() {
        Jedis jedis = RedisQueue.getJedis();
        long size = jedis.llen(this.key);
        RedisQueue.closeJedis(jedis);
        return size;
    }


}
