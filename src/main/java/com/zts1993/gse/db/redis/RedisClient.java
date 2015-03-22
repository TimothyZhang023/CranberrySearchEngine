/*
 * Copyright (c) 2015 By Timothy Zhang
 */

package com.zts1993.gse.db.redis;

/**
 * Created by TianShuo on 2015/3/22.
 */

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;


public class RedisClient implements IRedisClient {

    private Jedis jedis;//非切片额客户端连接
    private JedisPool jedisPool;//非切片连接池

    public RedisClient() {
        initialPool("127.0.0.1", 6379);
        jedis = jedisPool.getResource();
    }

    public RedisClient(String ip, int port) {
        initialPool(ip, port);
        jedis = jedisPool.getResource();
    }

    public RedisClient(String ip) {
        initialPool(ip, 6379);
        jedis = jedisPool.getResource();
    }

    /**
     * 初始化非切片池
     */
    private void initialPool(String ip, int port) {
        // 池基本配置
        JedisPoolConfig config = new JedisPoolConfig();
        //  config.setMaxActive(20);
        config.setMaxIdle(5);
        // config.setMaxWait(1000l);
        config.setTestOnBorrow(false);

        jedisPool = new JedisPool(config, ip, 6379);
    }


    public Jedis getJedis() {
        return jedis;
    }

    public void setJedis(Jedis jedis) {
        this.jedis = jedis;
    }

    public JedisPool getJedisPool() {
        return jedisPool;
    }

    public void setJedisPool(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }


}