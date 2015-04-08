/*
 * Copyright (c) 2015 By Timothy Zhang
 */

package com.zts1993.gse.db.redis;

import com.zts1993.gse.util.ConfigurationUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * Created by TianShuo on 2015/3/22.
 */
public class RedisDB {

    private static RedisClient redisClient;

    private RedisDB() {
    }

    public static RedisClient getRedisClient() {

        if (redisClient == null) {
            synchronized (RedisDB.class) {
                if (redisClient == null) {
                    redisClient = new RedisClient(
                            ConfigurationUtil.getValue("RedisServerIp", "127.0.0.1"),
                            Integer.parseInt(ConfigurationUtil.getValue("RedisServerPort", "6379")),
                            Integer.parseInt(ConfigurationUtil.getValue("RedisServerPoolSize", "5000"))
                    );
                }
            }
        }
        return redisClient;
    }


    public static Jedis getJedis() {
        return getRedisClient().getJedis();
    }


    public static JedisPool getJedisPool() {
        return getRedisClient().getJedisPool();
    }


    public static void closeJedis(Jedis jedis) {
        getJedisPool().returnResource(jedis);
    }


}
