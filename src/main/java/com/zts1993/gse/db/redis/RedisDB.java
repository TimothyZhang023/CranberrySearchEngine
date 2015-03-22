/*
 * Copyright (c) 2015 By Timothy Zhang
 */

package com.zts1993.gse.db.redis;

import com.zts1993.gse.common.ConfigurationUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

/**
 * Created by TianShuo on 2015/3/22.
 */
public class RedisDB {

    private static RedisClient redisClient;
    private static RedisShardedClient redisShardedClient;

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

    public static RedisShardedClient getRedisShardedClient() {

        if (redisShardedClient == null) {
            synchronized (RedisDB.class) {
                if (redisShardedClient == null) {
                    redisShardedClient = new RedisShardedClient(
                            ConfigurationUtil.getValue("RedisServerIp"),
                            Integer.parseInt(ConfigurationUtil.getValue("RedisServerPort"))

                    );
                }
            }
        }
        return redisShardedClient;
    }


    public static Jedis getJedis() {
        return getRedisClient().getJedis();
    }

    public static ShardedJedis getShardedJedis() {
        return getRedisShardedClient().getJedis();
    }

    public static JedisPool getJedisPool() {
        return getRedisClient().getJedisPool();
    }

    public static ShardedJedisPool getShardedJedisPool() {
        return getRedisShardedClient().getJedisPool();
    }


    public static void closeJedis(Jedis jedis) {
        getJedisPool().returnResource(jedis);
    }

    public static void closeShardedJedis(ShardedJedis jedis) {
        getShardedJedisPool().returnResource(jedis);
    }


}
