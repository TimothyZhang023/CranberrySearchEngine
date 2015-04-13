/*
 * Copyright (c) 2015 By Timothy Zhang
 */

package com.zts1993.gse.db.redis;

/**
 * Created by TianShuo on 2015/3/22.
 */

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;


public class RedisClient {

    private static final Logger logger = LogManager.getLogger("RedisClient");

    private JedisPool jedisPool;//非切片连接池

    public RedisClient(String ip, int port, int poolsize) {
        logger.info("Init RedisClient with " + ip + ":" + port);
        initialPool(ip, port, poolsize);
    }

    /**
     * 初始化非切片池
     */
    private void initialPool(String ip, int port, int poolsize) {
        // 池基本配置
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(poolsize);
        config.setMaxIdle(10);
        // config.setMaxWait(1000l);
        config.setTestOnBorrow(true);

        jedisPool = new JedisPool(config, ip, port);
    }


    public Jedis getJedis() {
        return jedisPool.getResource();
    }


    public JedisPool getJedisPool() {
        return jedisPool;
    }


}