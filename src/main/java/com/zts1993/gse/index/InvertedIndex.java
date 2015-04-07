/*
 * Copyright (c) 2015 By Timothy Zhang
 */

package com.zts1993.gse.index;

import com.zts1993.gse.bean.URLInfo;
import com.zts1993.gse.bean.WordFreq;
import com.zts1993.gse.db.logic.URLInfoLogic;
import com.zts1993.gse.db.redis.RedisDB;
import com.zts1993.gse.util.DivideQuery;
import com.zts1993.gse.util.QueryResult;
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
        long startMili;
        long endMili;
        startMili = System.currentTimeMillis();

        if (termList.size() == 0) {
            return;
        }

        ArrayList<WordFreq> stringSet = new ArrayList<WordFreq>();

        HashMap<String, Integer> wordFreqMap = new HashMap<String, Integer>();

        Jedis jedis = RedisDB.getJedis();
        Term term;
        String word;
        WordFreq wordFreq;


        Iterator<Term> termIterator = termList.iterator();
        while (termIterator.hasNext()) {
            term = termIterator.next();
            word = term.getRealName();

            if (wordFreqMap.containsKey(word)) {
                int newCount = wordFreqMap.get(word) + 1;
                wordFreqMap.put(word, newCount);
            } else {
                wordFreqMap.put(word, 1);
            }
//
//
//            wordFreq = new WordFreq(word);
//            if (stringSet.contains(wordFreq)) {
//                int index = stringSet.indexOf(wordFreq);
//                wordFreq = stringSet.get(index);
//                wordFreq.setIncr();
//                stringSet.set(index, wordFreq);
//
//
//            } else {
//                stringSet.add(wordFreq);
//            }
//
        }

        endMili = System.currentTimeMillis();
        long timespend1 = endMili - startMili;
        startMili = System.currentTimeMillis();

        int wordCount = termList.size();
        if (wordCount < 200) {
            wordCount = (int) ((1 / Math.sqrt(wordCount * 1.0)) * 4000);
        }

        URLInfo urlInfo = URLInfoLogic.genURLInfo(url, wordCount);

        for (Map.Entry entry : wordFreqMap.entrySet()) {
            String cWord = entry.getKey().toString();
            Integer cCount = (Integer) entry.getValue();
            double tf = (1.0 * cCount) / (1.0 * wordCount);
            try {
                jedis.zadd(cWord, tf, urlInfo.getHash());

            } catch (Exception e) {
                logger.info("cWord" + e.getMessage());

            }

        }

//        Iterator<WordFreq> stringIterator = stringSet.iterator();
//        while (stringIterator.hasNext()) {
//            wordFreq = stringIterator.next();
//            URLInfo urlInfo = URLInfoLogic.genURLInfo(url, termList.size());
//            jedis.zadd(wordFreq.getWord(), (1.0 * wordFreq.getCount()) / (1.0 * termList.size()), urlInfo.getHash());
//        }

        endMili = System.currentTimeMillis();
        long timespend2 = endMili - startMili;

        logger.info(String.format(
                "%s words in %s process time %s ms " +
                        "and redis-action %s ms",
                stringSet.size(), url,
                timespend1, timespend2));

        RedisDB.closeJedis(jedis);

    }


    public ArrayList<URLInfo> query(String queryWords) {
        ArrayList<URLInfo> stringArrayList = new ArrayList<URLInfo>();

        Jedis jedis = RedisDB.getJedis();
        int totolPages = Integer.parseInt(jedis.get("totolPages"));
        RedisDB.closeJedis(jedis);

        DivideQuery divideQuery = new DivideQuery(queryWords);
        Set<String> queryWordsSet = divideQuery.divide();

        QueryResult queryResult = new QueryResult(queryWordsSet, totolPages);
        stringArrayList = queryResult.queryResult();


        return stringArrayList;

    }

//    }
//    public ArrayList<URLInfo> query(String key) {
//        ArrayList<URLInfo> stringArrayList;
//
//        stringArrayList = new ArrayList<URLInfo>();
//
//        Jedis jedis = RedisDB.getJedis();
//        //  Set<String> querySet = jedis.smembers(key);
//        Set<String> querySet = jedis.zrevrange(key, 0, -1);
//        RedisDB.closeJedis(jedis);
//
//        for (String urlHash : querySet) {
//            stringArrayList.add(URLInfoLogic.getURLInfo(urlHash));
//        }
//
//
//        return stringArrayList;
//    }

    public Set<Tuple> queryKeys(String key) {
        Jedis jedis = RedisDB.getJedis();
        Set<Tuple> queryTuple = jedis.zrevrangeWithScores(key, 0, 5000);
        RedisDB.closeJedis(jedis);

        return queryTuple;
    }


}
