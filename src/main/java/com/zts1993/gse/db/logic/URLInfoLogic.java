/*
 * Copyright (c) 2015 By Timothy Zhang
 */

package com.zts1993.gse.db.logic;

import com.zts1993.gse.bean.Factors;
import com.zts1993.gse.bean.URLInfo;
import com.zts1993.gse.db.redis.RedisDB;
import com.zts1993.gse.encrypt.StringEncrypt;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by TianShuo on 2015/4/6.
 */
public class URLInfoLogic {


    public static URLInfo genURLInfo(String url, int wordCount) {
        String hash = new StringEncrypt(StringEncrypt.SHA_256).encrypt(url);
        String date = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date());
        double rank = 10.0;

        Jedis jedis = RedisDB.getJedis();

        if (!jedis.exists("wordCount:" + hash)) {
            KVCache.set("url:" + hash, url, jedis);
            KVCache.set("date:" + hash, date, jedis);
            KVCache.set("wordCount:" + hash, wordCount + "", jedis);
            jedis.set("rank:" + hash, rank + "");
        }

        RedisDB.closeJedis(jedis);

        return new URLInfo(hash, url, date, rank, wordCount);

    }


    public static URLInfo getURLInfo(Tuple tuple, double idf) {
        String hash = tuple.getElement();
        //double rank = tuple.getScore()*idf;


        Jedis jedis = RedisDB.getJedis();

        String url = KVCache.get("url:" + hash, jedis);
        String date = KVCache.get("date:" + hash, jedis);
        int wordCount = Integer.valueOf(KVCache.get("wordCount:" + hash, jedis));

        RedisDB.closeJedis(jedis);

        double rank =
                idf * (
                        (
                                (Factors.BM25K1 + 2) * tuple.getScore()
                        )
                                /
                                (
                                        tuple.getScore() + Factors.BM25K1 *
                                                (
                                                        1 - Factors.BM25b
                                                                +
                                                                Factors.BM25b * (wordCount / Factors.BM25avg)
                                                )
                                )
                );

        return new URLInfo(hash, url, date, rank, wordCount);
    }


    public static URLInfo getSimpleURLInfo(Tuple tuple, double idf) {
        String hash = tuple.getElement();
        double rank = tuple.getScore() * idf;

        return new URLInfo(hash, "", "", rank, 0);
    }


}
