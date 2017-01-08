/*
 * Copyright (c) 2015 By Timothy Zhang
 */

package com.zts1993.gse.index;

import com.zts1993.gse.bean.HtmlItem;
import com.zts1993.gse.db.redis.RedisDB;
import com.zts1993.gse.db.redis.RedisSafe;
import com.zts1993.gse.html.HtmlContentProvider;
import com.zts1993.gse.html.IHtmlContentProvider;
import com.zts1993.gse.index.comparator.UrlScoreComparator;
import com.zts1993.gse.index.score.IScore;
import com.zts1993.gse.index.score.TfIdf;
import com.zts1993.gse.segmentation.SegmentationFactory;
import com.zts1993.gse.segmentation.filter.TermFilter;
import com.zts1993.gse.segmentation.filter.TermFilterForAnsj;
import com.zts1993.gse.util.Factors;


import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import java.util.*;

/**
 * Created by TianShuo on 2015/3/28.
 */
@Slf4j
public class InvertedIndexQueryTool {



    private String queryKey;


    private long totalResultCount = 0;

    private Set<String> queryWordsSet = new HashSet<String>();

    List<Map.Entry<String, Double>> infoIds;

    private HashMap<String, Double> urlScores = new HashMap<String, Double>();
    private HashMap<String, Integer> urlHits = new HashMap<String, Integer>();


    public ArrayList<HtmlItem> getHtmlItems() {
        return htmlItems;
    }

    private ArrayList<HtmlItem> htmlItems = new ArrayList<HtmlItem>();

    public InvertedIndexQueryTool(String queryKey) {
        this.queryKey = queryKey;
    }


    public void divide() {

        TermFilter termFilterForAnsj = new TermFilterForAnsj(queryKey, 1, SegmentationFactory.getIndexSegmentation());

        HashMap<String, Integer> wordFreqMap = termFilterForAnsj.getWordFreqMap();

        queryWordsSet = new HashSet<String>();
        for (Map.Entry wordFreq : wordFreqMap.entrySet()) {
            queryWordsSet.add(wordFreq.getKey().toString());
        }

    }


    public void preQueryProcess() {

        RedisSafe redisSafe = new RedisSafe();
        Jedis jedis = RedisDB.getJedis();
        IScore scoreCalculator = new TfIdf();

        int totalPages = Integer.parseInt(redisSafe.get("totalPages", "1")) + 1;
        int queryWordsCount = queryWordsSet.size();

        try {


            for (String eachKeywords : queryWordsSet) {

                Set<Tuple> st = jedis.zrevrangeWithScores(eachKeywords, 0, Factors.MaxRecordPerWord);
                Long stSize = jedis.zcount(eachKeywords, -1000.0, 1000.0);

                double idf = TfIdf.getIdfScoreM1(totalPages, stSize);

                for (Tuple tuple : st) {
                    // int wordCount = Integer.valueOf(KVCache.get("wordCount:" + tuple.getElement(), jedis));
                    double rank = scoreCalculator.getScore(tuple.getScore(), idf, 0);
                    String key = tuple.getElement();

                    if (urlScores.containsKey(key)) {
                        rank = urlScores.get(key) + rank;
                        urlScores.put(key, rank);

                        int hits = urlHits.get(key);
                        urlHits.put(key, hits + 1);
                    } else {
                        urlScores.put(key, rank);
                        urlHits.put(key, 1);
                    }
                }
            }

            totalResultCount = urlScores.size();

            //update coord rank
            for (String key : urlScores.keySet()) {
                double val = urlScores.get(key) * (urlHits.get(key) * 1.0 / queryWordsCount * 1.0);
                urlScores.put(key, val);
            }

        } catch (Exception e) {
            log.error("Process preQueryProcess error");
            RedisDB.closeBrokenJedis(jedis);
            e.printStackTrace();
        } finally {
            RedisDB.closeJedis(jedis);
        }
    }


    public void processQuery(int start, int end) {

        //Sort
        infoIds = new ArrayList<Map.Entry<String, Double>>(urlScores.entrySet());
        Collections.sort(infoIds, new UrlScoreComparator());

        //Pagination
        List<Map.Entry<String, Double>> resultDocIds = infoIds.subList(start, end);

        //Fetch Html info
        for (Map.Entry<String, Double> resultDocIdEntry : resultDocIds) {

            String docId = resultDocIdEntry.getKey();
            double value = resultDocIdEntry.getValue();


            IHtmlContentProvider iHtmlContentProvider = HtmlContentProvider.getHtmlContentProvider(docId);
            String content = iHtmlContentProvider.fetchMarkedText(queryWordsSet);
            String title = iHtmlContentProvider.fetchTitle();
            String url = iHtmlContentProvider.fetchUrl();

            HtmlItem htmlItem = new HtmlItem(docId, url, title, content, value);
            htmlItems.add(htmlItem);
        }

    }


    public Set<String> getQueryWordsSet() {
        return queryWordsSet;
    }


    public long getTotalResultCount() {
        return totalResultCount;
    }


}
