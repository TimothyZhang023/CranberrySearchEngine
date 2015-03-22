package com.zts1993.gse.common;

import com.zts1993.gse.db.RedisClient;
import com.zts1993.gse.db.RedisShardedClient;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

/**
 * Created by TianShuo on 2015/3/22.
 */
public class RedisFactory {

    private static RedisClient redisClient;
    private static RedisShardedClient redisShardedClient;

    public static RedisClient getRedisClient() {

        if (redisClient == null) {
            redisClient = new RedisClient(
                    ConfigurationFactory.getValue("RedisServerIp"),
                    Integer.parseInt(ConfigurationFactory.getValue("RedisServerPort"))
            );
        }
        return redisClient;
    }

    public static RedisShardedClient getRedisShardedClient() {

        if (redisShardedClient == null) {
            redisShardedClient = new RedisShardedClient(
                    ConfigurationFactory.getValue("RedisServerIp"),
                    Integer.parseInt(ConfigurationFactory.getValue("RedisServerPort"))

            );
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
