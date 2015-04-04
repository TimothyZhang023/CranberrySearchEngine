/*
 * Copyright (c) 2015 By Timothy Zhang
 */

package com.zts1993.gse.db.redis;

import com.zts1993.gse.db.redis.single.RedisClient;
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
                            ConfigurationUtil.getValue("RedisServerIp"),
                            Integer.parseInt(ConfigurationUtil.getValue("RedisServerPort"))
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
