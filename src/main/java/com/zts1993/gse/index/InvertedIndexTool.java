/*
 * Copyright (c) 2015 By Timothy Zhang
 */

package com.zts1993.gse.index;

import com.zts1993.gse.bean.Factors;
import com.zts1993.gse.bean.HtmlDoc;
import com.zts1993.gse.bean.URLInfo;
import com.zts1993.gse.db.logic.URLInfoLogic;
import com.zts1993.gse.db.redis.RedisDB;
import com.zts1993.gse.util.QueryResult;
import org.ansj.domain.Term;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import redis.clients.jedis.Jedis;

import java.util.*;

/**
 * Created by TianShuo on 2015/3/22.
 */
public class InvertedIndexTool {

    private static final Logger logger = LogManager.getLogger("InvertedIndex");

    public InvertedIndexTool() {
    }

    public void addToInvertedIndex(HtmlDoc htmlDoc) {

        List<Term> contentTermList = htmlDoc.getParsedContent();
        List<Term> titleTermList = htmlDoc.getParsedTitle();
        int totalWordCount = htmlDoc.getWordCount();

        long startMili = System.currentTimeMillis();


        if (contentTermList.size() < Factors.LowerQuality) {
            //low quality web pages reject
            return;
        }


        Jedis jedis = RedisDB.getJedis();


        HashMap<String, Integer> wordFreqMap = new HashMap<String, Integer>();
        Term term;
        String word;
        Iterator<Term> termIterator;

        /**
         * header
         */
        termIterator = titleTermList.iterator();
        while (termIterator.hasNext()) {
            term = termIterator.next();
            word = term.getRealName();

            if (wordFreqMap.containsKey(word)) {
                int newCount = wordFreqMap.get(word) + Factors.titleWeight;
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
            word = term.getRealName();

            if (wordFreqMap.containsKey(word)) {
                int newCount = wordFreqMap.get(word) + Factors.contentWeight;
                wordFreqMap.put(word, newCount);
            } else {
                wordFreqMap.put(word, Factors.contentWeight);
            }
        }

        long endMili = System.currentTimeMillis();
        long timespend1 = endMili - startMili;
        startMili = System.currentTimeMillis();



         URLInfoLogic.storeURLInfo(htmlDoc);

        for (Map.Entry entry : wordFreqMap.entrySet()) {

            String cWord = entry.getKey().toString();
            Integer termCount = (Integer) entry.getValue();
            double tf = (1.0 * termCount) / (1.0 * htmlDoc.getWordCount());

            try {
                jedis.zadd(cWord, tf, htmlDoc.getDocId());
            } catch (Exception e) {
                logger.info("cWord" + e.getMessage());

            }

        }


        endMili = System.currentTimeMillis();
        long timespend2 = endMili - startMili;

        logger.debug(String.format("%s contains %s words process time %s ms and redis-action %s ms",
                htmlDoc.getUrl(), totalWordCount, timespend1, timespend2));

        RedisDB.closeJedis(jedis);

    }


    public ArrayList<URLInfo> query(String queryWords) {
        ArrayList<URLInfo> stringArrayList = new ArrayList<URLInfo>();



        QueryResult queryResult = new QueryResult(queryWords);
        queryResult.divide();
        stringArrayList = queryResult.queryResult();


        return stringArrayList;

    }


}
