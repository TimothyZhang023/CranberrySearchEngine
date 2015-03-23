/*
 * Copyright (c) 2015 By Timothy Zhang
 */

package com.zts1993.gse.bean;

import com.zts1993.gse.db.redis.RedisDB;
import com.zts1993.gse.encrypt.StringEncrypt;
import redis.clients.jedis.Jedis;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by TianShuo on 2015/3/22.
 */
public class URLInfo {

    private String hash;
    private String url;
    private String date;
    private double rank;

    public URLInfo(String url) {
        this.url = url;
        this.hash = new StringEncrypt().encrypt(url);
        this.date = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date());
        this.rank = 1.0;

        Jedis jedis = RedisDB.getJedis();
        jedis.set(hash + "_url", url);
        jedis.set(hash + "_date", date);
        jedis.set(hash + "_rank", String.valueOf(rank));
        RedisDB.closeJedis(jedis);

    }

    public URLInfo(String hash, String url, String date, double rank) {
        this.hash = hash;
        this.url = url;
        this.date = date;
        this.rank = rank;
    }

    public static URLInfo getURLInfoByHash(String hash) {
        Jedis jedis = RedisDB.getJedis();
        String url = jedis.get(hash + "_url");
        String date = jedis.get(hash + "_date");
//        double rank=Double.valueOf(jedis.get(hash + "_rank"));
        double rank = 1.0;

        RedisDB.closeJedis(jedis);

        return new URLInfo(hash, url, date, rank);
    }

    public String getHash() {
        return hash;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getRank() {
        return rank;
    }

    public void setRank(double rank) {
        this.rank = rank;
    }

    @Override
    public String toString() {
        return "URLInfo{" +
                "hash='" + hash + '\'' +
                ", url='" + url + '\'' +
                ", date='" + date + '\'' +
                ", rank='" + rank + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof URLInfo)) return false;

        URLInfo urlInfo = (URLInfo) o;

        if (!hash.equals(urlInfo.hash)) return false;
        if (!url.equals(urlInfo.url)) return false;
        if (!url.equals(urlInfo.date)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = hash.hashCode();
        result = 31 * result + url.hashCode();
        result = 31 * result + date.hashCode();
        result = 31 * result;
        return result;
    }
}
