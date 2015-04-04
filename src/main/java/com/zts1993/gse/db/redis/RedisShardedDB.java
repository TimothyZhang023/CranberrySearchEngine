/*
 * Copyright (c) 2015 By Timothy Zhang
 */

package com.zts1993.gse.db.redis;

import com.zts1993.gse.db.redis.sharded.RedisShardedClient;
import com.zts1993.gse.util.ConfigurationUtil;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

/**
 * Created by TianShuo on 2015/3/22.
 */
public class RedisShardedDB {

    private static RedisShardedClient redisShardedClient;

    private RedisShardedDB() {
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


    public static ShardedJedis getShardedJedis() {
        return getRedisShardedClient().getJedis();
    }


    public static ShardedJedisPool getShardedJedisPool() {
        return getRedisShardedClient().getJedisPool();
    }


    public static void closeShardedJedis(ShardedJedis jedis) {
        getShardedJedisPool().returnResource(jedis);
    }


}
