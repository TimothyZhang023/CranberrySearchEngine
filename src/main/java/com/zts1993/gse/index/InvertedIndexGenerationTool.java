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
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import redis.clients.jedis.Jedis;

import java.util.*;

/**
 * Created by TianShuo on 2015/3/22.
 */
public class InvertedIndexGenerationTool {

    private static final Logger logger = LogManager.getLogger("InvertedIndex");

    public InvertedIndexGenerationTool() {
    }

    public boolean checkSimilarity(HtmlDoc htmlDoc) {
        long timeQueryStart = System.currentTimeMillis();

        RedisSafe redisSafe = new RedisSafe();

        String totalPageString=redisSafe.get("totalPages");
        if(totalPageString==null){totalPageString="1";}
        int totalPages = Integer.parseInt(totalPageString) + 1;

        int wordCount = htmlDoc.parse().getWordCount();

        HashMap<String, Integer> wordCountMap = htmlDoc.parse().getWordFreqMap();

        HashMap<String, Double> wordFreqMap = new HashMap<String, Double>();


        for (Object o : wordCountMap.entrySet()) {
            Map.Entry entry = (Map.Entry) o;
            String key = (String) entry.getKey();
            Integer val = (Integer) entry.getValue();

            Long stSize = redisSafe.zcount(key, -1000.0, 1000.0);
            double idf = TfIdf.getIdfScoreM1(totalPages, stSize);

            wordFreqMap.put(key, idf * idf * val / wordCount);
        }


        List<Map.Entry<String, Double>> wordMapList = new ArrayList<Map.Entry<String, Double>>(wordFreqMap.entrySet());
        Collections.sort(wordMapList, new WordFreqComparator());

        int c2 = wordMapList.size() > Factors.checkWordCount ? Factors.checkWordCount : wordMapList.size();
        wordMapList = wordMapList.subList(0, c2);


        Jedis jedis = null;
        HashMap<String, Double> urlIdHitsMap = new HashMap<String, Double>();


        for (Map.Entry<String, Double> entry : wordMapList) {


            String keyWord = entry.getKey();

      //      logger.info(keyWord + " " + entry.getValue());

            double tf = entry.getValue() * 1.0 / wordCount * 1.0;
//            Set<String> temp = redisSafe.zrange(keyWord, 0, -1);

            try {
                jedis = RedisDB.getJedis();

                Long stSize = jedis.zcount(keyWord, -1000.0, 1000.0);
                double idf = TfIdf.getIdfScoreM1(totalPages, stSize);


                //      Set<String> temp = jedis.zrangeByScore(keyWord, tf - 0.05, tf + 0.05);

                Set<String> temp = jedis.zrange(keyWord, 0, -1);

                for (String t : temp) {
                    if (urlIdHitsMap.containsKey(t)) {
                        urlIdHitsMap.put(t, urlIdHitsMap.get(t) + tf * idf * idf * idf);
                    } else {
                        urlIdHitsMap.put(t, tf * idf * idf * idf);
                    }
                }

            } catch (Exception e) {
                if (jedis != null) {
                    RedisDB.closeBrokenJedis(jedis);
                }
            } finally {
                if (jedis != null) {
                    RedisDB.closeJedis(jedis);

                }
            }


        }

        long preparationurlIdHitsMapTime = System.currentTimeMillis() - timeQueryStart;


        List<Map.Entry<String, Double>> urlIdHitsList = new ArrayList<Map.Entry<String, Double>>(urlIdHitsMap.entrySet());
        Collections.sort(urlIdHitsList, new WordFreqComparator());

        urlIdHitsList = urlIdHitsList.subList(0, urlIdHitsList.size() > Factors.checkUrlCount ? Factors.checkUrlCount : urlIdHitsList.size());


        for (int i = 0, urlIdHitsListSize = urlIdHitsList.size(); i < urlIdHitsListSize; i++) {
            Map.Entry<String, Double> entry = urlIdHitsList.get(i);
            String urlIdToBeChecked = entry.getKey();
            double rank = SimilarDegreeByCos.getSimilarDegree(htmlDoc.getDocId(), urlIdToBeChecked);
            if (rank > 0.80) {
                long timeSpend = System.currentTimeMillis() - timeQueryStart;
                logger.info("Similarity check " + htmlDoc.getDocId() + " and " + urlIdToBeChecked +
                                " with rank " + rank +
                                " costs " + timeSpend + " ms" +
                                " preparationurlIdHitsMapTime " + preparationurlIdHitsMapTime + " ms" +
                                " urlIdHitsMap " + urlIdHitsMap.size()+
                                " in NO." + i + " , in all " + urlIdHitsList.size()
                );

                return true;
            }
        }
//        long timeSpend = System.currentTimeMillis() - timeQueryStart;
//        logger.info("Similarity check costs " + timeSpend + " ms");

        return false;
    }


    public void addToInvertedIndex(HtmlDoc htmlDoc) {


        boolean checkRes = checkSimilarity(htmlDoc);

        if (checkRes) {
            return;
        }


        RedisSafe redisSafe = new RedisSafe();
        HashMap<String, Integer> wordFreqMap = htmlDoc.parse().getWordFreqMap();

        if (wordFreqMap.size() < Factors.LowerQuality) {
            //low quality web pages reject
            return;
        }

        redisSafe.incr("totalPages");
        redisSafe.set("url:" + htmlDoc.getDocId(), htmlDoc.getUrl());
//            jedis.set("wordCount:" + htmlDoc.getDocId(), htmlDoc.getWordCount() + "");

        for (Map.Entry entry : wordFreqMap.entrySet()) {

            String keyWord = entry.getKey().toString();
            Integer termCount = (Integer) entry.getValue();

            double tf = TfIdf.getTfScoreM1(termCount, htmlDoc.getWordCount());

            redisSafe.zadd(keyWord, tf, htmlDoc.getDocId());
//                jedis.zremrangeByRank(cWord, 0, -Factors.MaxRecordPerKey);

        }


    }

}
