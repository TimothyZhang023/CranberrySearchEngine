/*
 * Copyright (c) 2015 By Timothy Zhang
 */

package com.zts1993.gse.index;

import com.zts1993.gse.bean.URLInfo;
import com.zts1993.gse.bean.WordFreq;
import com.zts1993.gse.db.redis.RedisDB;
import org.ansj.domain.Term;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import java.util.*;

/**
 * Created by TianShuo on 2015/3/22.
 */
public class InvertedIndex {

    private static final Logger logger = LogManager.getLogger("InvertedIndex");

    public InvertedIndex() {
    }


    public void addToInvertedIndex(List<Term> termList, String url) {

        if (termList.size() == 0) {
            return;
        }

        ArrayList<WordFreq> stringSet = new ArrayList<WordFreq>();
        Jedis jedis = RedisDB.getJedis();
        Term term;
        String word;
        WordFreq wordFreq;

        Iterator<Term> termIterator = termList.iterator();
        while (termIterator.hasNext()) {
            term = termIterator.next();
            word = term.getRealName();


            wordFreq = new WordFreq(word);
            if (stringSet.contains(wordFreq)) {
                int index = stringSet.indexOf(wordFreq);
                wordFreq = stringSet.get(index);
                wordFreq.setIncr();
                stringSet.set(index, wordFreq);

            } else {
                stringSet.add(wordFreq);
            }

         }

        logger.info(String.format("%s words in %s", stringSet.size(), url));

        Iterator<WordFreq> stringIterator = stringSet.iterator();
        while (stringIterator.hasNext()) {
            wordFreq = stringIterator.next();
            //  jedis.sadd(wordFreq.getWord(), new URLInfo(url).getHash());
            URLInfo urlInfo=new URLInfo(url);
            jedis.zadd(wordFreq.getWord(), 1.0 * wordFreq.getCount(), urlInfo.getHash());
        }

        RedisDB.closeJedis(jedis);

    }


    public ArrayList<URLInfo> query(String key) {
        ArrayList<URLInfo> stringArrayList;

        stringArrayList = new ArrayList<URLInfo>();

        Jedis jedis = RedisDB.getJedis();
        //  Set<String> querySet = jedis.smembers(key);
        Set<String> querySet = jedis.zrevrange(key, 0, -1);
        RedisDB.closeJedis(jedis);

        for (String urlHash : querySet) {
            stringArrayList.add(URLInfo.getURLInfoByHash(urlHash));
        }


        return stringArrayList;
    }

    public Set<Tuple> queryKeys(String key) {
        Jedis jedis = RedisDB.getJedis();
        Set<Tuple> queryTuple = jedis.zrevrangeWithScores(key, 0, -1);

        RedisDB.closeJedis(jedis);

        return queryTuple;
    }




}
