/*
 * Copyright (c) 2015 By Timothy Zhang
 */

package com.zts1993.gse.db.logic;

import com.zts1993.gse.bean.Factors;
import com.zts1993.gse.bean.HtmlDoc;
import com.zts1993.gse.bean.URLInfo;
import com.zts1993.gse.db.cache.KVCache;
import com.zts1993.gse.db.redis.RedisDB;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

/**
 * Created by TianShuo on 2015/4/6.
 */
public class URLInfoLogic {


    public static void storeURLInfo(HtmlDoc htmlDoc) {
        String hash = htmlDoc.getDocId();
        Jedis jedis = RedisDB.getJedis();

        if (!jedis.exists("url:" + hash)) {
//            int wordCount = htmlDoc.getWordCount();
//            String date = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date());
            KVCache.set("url:" + hash, htmlDoc.getUrl(), jedis);
//            KVCache.set("date:" + hash, date, jedis);
//            KVCache.set("wordCount:" + hash, wordCount + "", jedis);
        }
        RedisDB.closeJedis(jedis);
    }


    public static URLInfo getURLInfo(Tuple tuple, double idf) {
        String hash = tuple.getElement();

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
