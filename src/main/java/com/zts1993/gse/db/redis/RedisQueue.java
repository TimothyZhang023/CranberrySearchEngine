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

    private String key;

    public RedisQueue(String key) {
        this.key = key;
                
    }

    private static RedisPoolClient redisQueueClient;

    public static RedisPoolClient getRedisQueueClient() {
        if (redisQueueClient == null) {
            redisQueueClient = new RedisPoolClient(
                    ConfigurationUtil.getValue("RedisIndexNotifyServerIp", "127.0.0.1"),
                    Integer.parseInt(ConfigurationUtil.getValue("RedisIndexNotifyServerPort", "6379")),
                    50
            );

        }
        return redisQueueClient;
    }



    public static JedisPool getJedisPool() {
        return getRedisQueueClient().getJedisPool();
    }



    public void push(String string) {
        JedisPool pool = RedisQueue.getJedisPool();
        Jedis jedis = null;
        String res = null;
        try {
            jedis = pool.getResource();
            jedis.lpush(this.key, string);
        } catch (Exception e) {
            pool.returnBrokenResource(jedis);
            e.printStackTrace();
        } finally {
            returnResource(pool, jedis);
        }
    }

    public String pop() {
        JedisPool pool = RedisQueue.getJedisPool();
        Jedis jedis = null;
        String res = null;
        try {
            jedis = pool.getResource();
            res = jedis.lpop(key);
        } catch (Exception e) {
            pool.returnBrokenResource(jedis);
            e.printStackTrace();
        } finally {
            returnResource(pool, jedis);
        }
        return res;
    }

    public long size() {

        JedisPool pool = RedisQueue.getJedisPool();
        Jedis jedis = null;
        long size = 0;
        try {
            jedis = pool.getResource();
            size = jedis.llen(key);
        } catch (Exception e) {
            pool.returnBrokenResource(jedis);
            e.printStackTrace();
        } finally {
            returnResource(pool, jedis);
        }
        return size;

    }


    /**
     * 返还到连接池
     *
     * @param pool
     * @param jedis
     */
    public static void returnResource(JedisPool pool, Jedis jedis) {
        if (jedis != null) {
            pool.returnResource(jedis);
        }
    }

}
