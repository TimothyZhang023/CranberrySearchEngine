/*
 * Copyright (c) 2015 By Timothy Zhang
 */

package com.zts1993.gse.index;

import com.zts1993.gse.bean.HtmlDoc;
import com.zts1993.gse.db.redis.RedisDB;
import com.zts1993.gse.db.redis.RedisSafe;
import com.zts1993.gse.index.comparator.WordFreqComparator;
import com.zts1993.gse.index.score.TfIdf;
import com.zts1993.gse.util.Factors;
import com.zts1993.gse.util.SimilarDegreeByCos;


import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;

import java.util.*;

/**
 * Created by TianShuo on 2015/3/22.
 */
@Slf4j
public class InvertedIndexGenerationTool {



    public InvertedIndexGenerationTool() {
    }

    public boolean checkSimilarity(HtmlDoc htmlDoc) {
        long timeQueryStart = System.currentTimeMillis();
        Jedis jedis = null;
        RedisSafe redisSafe = new RedisSafe();

        try {
            jedis = RedisDB.getJedis();


            int totalPages = Integer.parseInt(redisSafe.get("totalPages", "1")) + 1;

            int wordCount = htmlDoc.getRawWordCount();
            HashMap<String, Integer> wordCountMap = htmlDoc.getWordFreqMap();


            HashMap<String, Double> wordFreqMap = new HashMap<String, Double>();

            for (Map.Entry entry : wordCountMap.entrySet()) {
                String key = (String) entry.getKey();
                Integer val = (Integer) entry.getValue();

                Long stSize = jedis.zcount(key, -1000.0, 1000.0);

                double idf = TfIdf.getIdfScoreM1(totalPages, stSize);

                wordFreqMap.put(key, idf * idf * val / wordCount);
            }


            List<Map.Entry<String, Double>> wordMapList = new ArrayList<Map.Entry<String, Double>>(wordFreqMap.entrySet());
            Collections.sort(wordMapList, new WordFreqComparator());

            int wordCountTobeChecked = wordMapList.size() > Factors.checkWordCount ? Factors.checkWordCount : wordMapList.size();
            wordMapList = wordMapList.subList(0, wordCountTobeChecked);

            HashMap<String, Double> urlIdHitsMap = new HashMap<String, Double>();

            for (Map.Entry<String, Double> entry : wordMapList) {

                String keyWord = entry.getKey();
                //      log.info(keyWord + " " + entry.getValue());

                double tf = entry.getValue() * 1.0 / wordCount * 1.0;

                Long stSize = jedis.zcount(keyWord, -1000.0, 1000.0);
                double idf = TfIdf.getIdfScoreM1(totalPages, stSize);

                //Set<String> temp = jedis.zrangeByScore(keyWord, tf - 0.05, tf + 0.05);
                Set<String> temp = jedis.zrange(keyWord, 0, -1);

                for (String t : temp) {
                    if (urlIdHitsMap.containsKey(t)) {
                        urlIdHitsMap.put(t, urlIdHitsMap.get(t) + tf * idf * idf * idf);
                    } else {
                        urlIdHitsMap.put(t, tf * idf * idf * idf);
                    }
                }

            }


            long preparationUrlIdHitsMapTime = System.currentTimeMillis() - timeQueryStart;


            List<Map.Entry<String, Double>> urlIdHitsList = new ArrayList<Map.Entry<String, Double>>(urlIdHitsMap.entrySet());
            Collections.sort(urlIdHitsList, new WordFreqComparator());

            urlIdHitsList = urlIdHitsList.subList(0, urlIdHitsList.size() > Factors.checkUrlCount ? Factors.checkUrlCount : urlIdHitsList.size());


            for (int i = 0, urlIdHitsListSize = urlIdHitsList.size(); i < urlIdHitsListSize; i++) {
                String urlIdToBeChecked = urlIdHitsList.get(i).getKey();
                double rank = SimilarDegreeByCos.getSimilarDegree(htmlDoc.getDocId(), urlIdToBeChecked);
                if (rank > 0.80) {
                    long timeSpend = System.currentTimeMillis() - timeQueryStart;
                    log.info("Found " + htmlDoc.getDocId() + " and " + urlIdToBeChecked +
                                    " with rank " + rank +
                                    " costs " + timeSpend + " ms" +
                                    " preparationurlIdHitsMapTime " + preparationUrlIdHitsMapTime + " ms" +
                                    " urlIdHitsMap " + urlIdHitsMap.size() +
                                    " in NO." + i
                    );

                    return true;
                }
            }
            //long timeSpend = System.currentTimeMillis() - timeQueryStart;
            //log.info("Similarity check costs " + timeSpend + " ms");


        } catch (Exception e) {
            if (jedis != null) {
                RedisDB.closeBrokenJedis(jedis);
            }
        } finally {
            if (jedis != null) {
                RedisDB.closeJedis(jedis);

            }
        }

        return false;


    }


    public void addToInvertedIndex(HtmlDoc htmlDoc) {


        boolean checkRes = checkSimilarity(htmlDoc);

        if (checkRes) {
            return;
        }

        RedisSafe redisSafe = new RedisSafe();

        HashMap<String, Integer> wordFreqMap = htmlDoc.getWordFreqMap();
        HashMap<String, Integer> indexWordFreqMap = htmlDoc.getIndexWordFreqMap();

        if (wordFreqMap.size() < Factors.LowerQuality) {
            //low quality web pages reject
            log.info("Lower Quality page" + htmlDoc.getUrl() + "with " + wordFreqMap.size() + " words");

            return;
        }

        redisSafe.incr("totalPages");
//        redisSafe.set("url:" + htmlDoc.getDocId(), htmlDoc.getUrl());

        for (Map.Entry entry : indexWordFreqMap.entrySet()) {

            String keyWord = entry.getKey().toString();
            Integer termCount = (Integer) entry.getValue();

            double tf = TfIdf.getTfScoreM1(termCount, htmlDoc.getWordCount());

            redisSafe.zadd(keyWord, tf, htmlDoc.getDocId());
        }

    }

}
