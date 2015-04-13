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


    private static RedisPoolClient redisPoolClient;

    private RedisDB() {
    }

    public static RedisPoolClient getRedisPoolClient() {

        if (redisPoolClient == null) {
            synchronized (RedisDB.class) {
                if (redisPoolClient == null) {
                    redisPoolClient = new RedisPoolClient(
                            ConfigurationUtil.getValue("RedisServerIp", "127.0.0.1"),
                            Integer.parseInt(ConfigurationUtil.getValue("RedisServerPort", "6379")),
                            Integer.parseInt(ConfigurationUtil.getValue("RedisServerPoolSize", "5000"))
                    );
                }
            }
        }
        return redisPoolClient;
    }


    public static Jedis getJedis() {
        return getRedisPoolClient().getJedis();
    }


    public static JedisPool getJedisPool() {
        return getRedisPoolClient().getJedisPool();
    }


    public static void closeJedis(Jedis jedis) {
        getJedisPool().returnResource(jedis);
    }

    public static void closeBrokenJedis(Jedis jedis) {
        getJedisPool().returnBrokenResource(jedis);
    }


}
