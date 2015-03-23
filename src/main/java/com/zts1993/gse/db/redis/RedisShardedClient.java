/*
 * Copyright (c) 2015 By Timothy Zhang
 */

package com.zts1993.gse.db.redis;

/**
 * Created by TianShuo on 2015/3/22.
 */

import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

import java.util.ArrayList;
import java.util.List;


public class RedisShardedClient implements IRedisClient {

    private ShardedJedisPool jedisPool;//切片连接池
    private List<JedisShardInfo> shards;
    JedisPoolConfig config;


    public RedisShardedClient() {
        initialPool("127.0.0.1", 6379);
    }

    public RedisShardedClient(String ip, int port) {
        initialPool(ip, port);
    }

    public RedisShardedClient(String ip) {
        initialPool(ip, 6379);
    }

    /**
     * 初始化切片池
     */
    private void initialPool(String ip, int port) {
        // 池基本配置
        config = new JedisPoolConfig();
        //config.setMaxActive(20);
        config.setMaxIdle(5);
        //config.setMaxWait(1000l);
        config.setTestOnBorrow(false);
        // slave链接
        shards = new ArrayList<JedisShardInfo>();
        shards.add(new JedisShardInfo(ip, port, "master"));

        // 构造池
        jedisPool = new ShardedJedisPool(config, shards);
    }

    public void addServer(String ip, int port, String name) {
        shards.add(new JedisShardInfo(ip, port, name));
        jedisPool = new ShardedJedisPool(config, shards);
    }


    public ShardedJedis getJedis() {
        return jedisPool.getResource();
    }


    public ShardedJedisPool getJedisPool() {
        return jedisPool;
    }

}