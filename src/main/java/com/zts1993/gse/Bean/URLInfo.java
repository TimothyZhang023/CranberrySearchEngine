/*
 * Copyright (c) 2015 By Timothy Zhang
 */

package com.zts1993.gse.bean;

import com.zts1993.gse.db.redis.RedisDB;
import com.zts1993.gse.encrypt.StringEncrypt;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by TianShuo on 2015/3/22.
 */
public class URLInfo implements Comparable<Object> {

    private String hash;
    private String url;
    private String date;
    private double rank;
    private int wordCount;


    public URLInfo(String url, int wordCount) {
        this.url = url;
        this.hash = new StringEncrypt(StringEncrypt.SHA_256).encrypt(url);
        this.date = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date());
        this.rank = 10.0;
        this.wordCount = wordCount;

        Jedis jedis = RedisDB.getJedis();
        jedis.set("url:" + hash, url);
        jedis.set("date:" + hash, date);
        jedis.set("rank:" + hash, rank + "");
        jedis.set("wordCount:" + hash, wordCount + "");
        RedisDB.closeJedis(jedis);

    }


    public URLInfo(String hash, String url, String date, double rank, int wordCount) {
        this.hash = hash;
        this.url = url;
        this.date = date;
        this.rank = rank;
        this.wordCount = wordCount;
    }

    public static URLInfo getURLInfoByHash(String hash) {
        Jedis jedis = RedisDB.getJedis();

        String url = jedis.get("url:" + hash);
        String date = jedis.get("date:" + hash);
        double rank = Double.valueOf(jedis.get("rank:" + hash));
        int wordCount = Integer.valueOf(jedis.get("wordCount:" + hash));

        RedisDB.closeJedis(jedis);

        return new URLInfo(hash, url, date, rank, wordCount);
    }

    public static URLInfo getURLInfoByHash(Tuple tuple) {
        String hash = tuple.getElement();
        double rank = tuple.getScore();

        Jedis jedis = RedisDB.getJedis();

        String url = jedis.get("url:" + hash);
        String date = jedis.get("date:" + hash);
        int wordCount = Integer.valueOf(jedis.get("wordCount:" + hash));

        RedisDB.closeJedis(jedis);

        return new URLInfo(hash, url, date, rank, wordCount);
    }

    public String getHash() {
        return hash;
    }

    @Override
    public String toString() {
        return "URLInfo{" +
                "hash='" + hash + '\'' +
                ", url='" + url + '\'' +
                ", date='" + date + '\'' +
                ", rank='" + rank + '\'' +
                ", wordCount='" + wordCount + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof URLInfo)) return false;

        URLInfo urlInfo = (URLInfo) o;

        return hash.equals(urlInfo.hash);

    }

    @Override
    public int hashCode() {
        int result = hash.hashCode();
        result = 31 * result;
        return result;
    }


    @Override
    public int compareTo(Object o) {
        if (this == o) {
            return 0;
        } else if (o != null && o instanceof URLInfo) {
            URLInfo u = (URLInfo) o;
            if (rank >= u.rank) {
                return -1;
            } else {
                return 1;
            }
        } else {
            return -1;
        }
    }
}
