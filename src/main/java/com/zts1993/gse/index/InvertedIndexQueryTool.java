/*
 * Copyright (c) 2015 By Timothy Zhang
 */

package com.zts1993.gse.index;

import com.zts1993.gse.bean.Factors;
import com.zts1993.gse.bean.URLInfo;
import com.zts1993.gse.db.logic.URLInfoLogic;
import com.zts1993.gse.db.redis.RedisDB;
import com.zts1993.gse.filter.TermFilter;
import com.zts1993.gse.segmentation.ISegmentation;
import com.zts1993.gse.segmentation.SegmentationFactory;
import org.ansj.domain.Term;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import java.util.*;

/**
 * Created by TianShuo on 2015/3/28.
 */
public class InvertedIndexQueryTool {

    private static final Logger logger = LogManager.getLogger("QueryResult");

    private String queryKey;

    private Set<String> queryWordsSet = new HashSet<String>();


    public InvertedIndexQueryTool(String queryKey) {
        this.queryKey = queryKey;
    }


    public void divide() {

        ISegmentation iSegmentation = SegmentationFactory.getDefaultSegmentation();
        List<Term> termList = iSegmentation.parse(queryKey);
        termList = TermFilter.process(termList);


        queryWordsSet = new HashSet<String>();
        for (Term term : termList) {
            queryWordsSet.add(term.getRealName());
        }

    }

    public Set<String> getQueryWordsSet() {
        return queryWordsSet;
    }

    public ArrayList<URLInfo> queryResult() {

        Jedis jedis = RedisDB.getJedis();
        ArrayList<URLInfo> urlInfoArrayList = new ArrayList<URLInfo>();


        int totolPages = Integer.parseInt(jedis.get("totolPages"));
        int queryWordsCount = queryWordsSet.size();


        for (String eachKeywords : queryWordsSet) {

            Set<Tuple> st = jedis.zrevrangeWithScores(eachKeywords, 0, Factors.MaxFetchPerWord);

            Long stSize = jedis.zcount(eachKeywords, -1000.0, 1000.0);


//            double idf = java.lang.Math.log(
//                    (
//                            totolPages * 1.0 - stSize + 0.5
//                    ) / (
//                            stSize + 0.5
//
//                    ));

            //inverse frequency smooth
            double idf = java.lang.Math.log(totolPages * 1.0 / stSize + 1);

            for (Tuple tuple : st) {
                URLInfo newUrlInfo = URLInfoLogic.getSimpleURLInfo(tuple, idf);

                if (urlInfoArrayList.contains(newUrlInfo)) {
                    newUrlInfo = URLInfoLogic.getURLInfo(tuple, idf);

                    int index = urlInfoArrayList.indexOf(newUrlInfo);
                    URLInfo preUrlInfo = urlInfoArrayList.get(index);
                    preUrlInfo.addHit();

                    preUrlInfo.addRank(newUrlInfo.getRank());
                    urlInfoArrayList.set(index, preUrlInfo);

                } else {
                    newUrlInfo = URLInfoLogic.getURLInfo(tuple, idf);
                    urlInfoArrayList.add(newUrlInfo);
                }

            }

        }

        //update rank
        for (int position = 0; position < urlInfoArrayList.size()&&position < Factors.MaxFetchPerRequest ; position++) {
            double coord = urlInfoArrayList.get(position).getHits() * 1.0 / queryWordsCount * 1.0;
            URLInfo preUrlInfo = urlInfoArrayList.get(position);
            preUrlInfo.setRank(preUrlInfo.getRank() * coord);
            urlInfoArrayList.set(position, preUrlInfo);

        }

        RedisDB.closeJedis(jedis);

        Collections.sort(urlInfoArrayList);

        return urlInfoArrayList;

    }

//    public ArrayList<Tuple> queryResultKeys() {
//
//        ArrayList<Tuple> urlHashKeys = new ArrayList<Tuple>();
//        Iterator<String> iterator = queryWordsSet.iterator();
//        String aQyeryKeySet = iterator.next();
//        urlHashKeys.addAll(InvertedIndexTestTool.queryKeys(aQyeryKeySet));
//
//        while (iterator.hasNext()) {
//            aQyeryKeySet = iterator.next();
//            urlHashKeys.addAll(InvertedIndexTestTool.queryKeys(aQyeryKeySet));
//        }
//
//        return urlHashKeys;
//
//    }


}
