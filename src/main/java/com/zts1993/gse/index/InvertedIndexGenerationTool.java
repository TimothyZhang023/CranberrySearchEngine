/*
 * Copyright (c) 2015 By Timothy Zhang
 */

package com.zts1993.gse.index;

import com.zts1993.gse.bean.HtmlDoc;
import com.zts1993.gse.db.redis.RedisDB;
import com.zts1993.gse.index.score.TfIdf;
import com.zts1993.gse.util.Factors;
import org.ansj.domain.Term;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by TianShuo on 2015/3/22.
 */
public class InvertedIndexGenerationTool {

    private static final Logger logger = LogManager.getLogger("InvertedIndex");

    public InvertedIndexGenerationTool() {
    }

    public void addToInvertedIndex(HtmlDoc htmlDoc) {

        Jedis jedis = RedisDB.getJedis();

        List<Term> contentTermList = htmlDoc.getParsedContent();
        List<Term> titleTermList = htmlDoc.getParsedTitle();
//            int totalWordCount = htmlDoc.getWordCount();

        HashMap<String, Integer> wordFreqMap = new HashMap<String, Integer>();
        Term term;
        String word;
        Iterator<Term> termIterator;

        try {


            if (contentTermList.size() < Factors.LowerQuality) {
                //low quality web pages reject
                return;
            }

            /**
             * header
             */
            termIterator = titleTermList.iterator();
            while (termIterator.hasNext()) {
                term = termIterator.next();
                word = term.getRealName().toLowerCase();

                if (wordFreqMap.containsKey(word)) {
                    int newCount = wordFreqMap.get(word) * Factors.titleWeight;
                    wordFreqMap.put(word, newCount);
                } else {
                    wordFreqMap.put(word, Factors.titleWeight);
                }
            }

            /**
             * body
             */
            termIterator = contentTermList.iterator();
            while (termIterator.hasNext()) {
                term = termIterator.next();
                word = term.getRealName().toLowerCase();

                if (wordFreqMap.containsKey(word)) {
                    int newCount = wordFreqMap.get(word) * Factors.contentWeight;
                    wordFreqMap.put(word, newCount);
                } else {
                    wordFreqMap.put(word, Factors.contentWeight);
                }
            }

            jedis.incr("totalPages");
            jedis.set("url:" + htmlDoc.getDocId(), htmlDoc.getUrl());
//            jedis.set("wordCount:" + htmlDoc.getDocId(), htmlDoc.getWordCount() + "");

            for (Map.Entry entry : wordFreqMap.entrySet()) {

                String cWord = entry.getKey().toString();
                Integer termCount = (Integer) entry.getValue();
                double tf = TfIdf.getTfScoreM1(termCount, htmlDoc.getWordCount());

                try {
                    jedis.zadd(cWord, tf, htmlDoc.getDocId());
//                jedis.zremrangeByRank(cWord, 0, -Factors.MaxRecordPerKey);
                } catch (Exception e) {
                    logger.error("Process " + cWord + " with error" + e.getMessage());
                    e.printStackTrace();
                }

            }


        } catch (Exception e) {
            logger.error("Process addToInvertedIndex error");
            e.printStackTrace();
        } finally {
            RedisDB.closeJedis(jedis);
        }


    }

}
