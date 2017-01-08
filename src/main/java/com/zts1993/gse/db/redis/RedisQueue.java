/*
 * Copyright (c) 2015 By Timothy Zhang
 */

package com.zts1993.gse.db.redis;

import com.zts1993.gse.util.ConfigurationUtil;


import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Created by TianShuo on 2015/3/31.
 */
@Slf4j
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
                    100
            );
        }
        return redisQueueClient;
    }


    public static JedisPool getJedisPool() {
        return getRedisQueueClient().getJedisPool();
    }

    public void push(String string) {
        JedisPool pool = RedisQueue.getJedisPool();
        try (Jedis jedis = pool.getResource()) {
            jedis.lpush(this.key, string);
        } catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
        }
    }

    public String pop() {
        JedisPool pool = RedisQueue.getJedisPool();
        try (Jedis jedis = pool.getResource()) {
            return jedis.lpop(key);
        } catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
        }
        return null;
    }

    public long size() {
        JedisPool pool = RedisQueue.getJedisPool();
        long size = 0;
        try (Jedis jedis = pool.getResource()) {
            size = jedis.llen(key);
        } catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
        }
        return size;
    }

    public void flushAll() {
        JedisPool pool = RedisQueue.getJedisPool();
        try (Jedis jedis = pool.getResource()) {
            jedis.del(key);
        } catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
        }
    }


}
