/*
 * Copyright (c) 2015 By Timothy Zhang
 */

package com.zts1993.gse.util;

import com.zts1993.gse.bean.URLInfo;
import com.zts1993.gse.db.logic.URLInfoLogic;
import com.zts1993.gse.index.InvertedIndex;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import redis.clients.jedis.Tuple;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by TianShuo on 2015/3/28.
 */
public class QueryResult {

    private static final Logger logger = LogManager.getLogger("QueryResult");

    private Set<String> queryWordsSet = new HashSet<String>();
    private int totolPages = 1;

    public QueryResult(Set<String> queryWordsSet) {
        this.queryWordsSet = queryWordsSet;
    }


    public QueryResult(Set<String> queryWordsSet, int totolPages) {
        this.queryWordsSet = queryWordsSet;
        this.totolPages = totolPages;


    }

    public static Set<Tuple> queryKeys(String key) {
        InvertedIndex invertedIndex = new InvertedIndex();
        return invertedIndex.queryKeys(key);
    }


    public ArrayList<URLInfo> queryResult() {

        ArrayList<URLInfo> urlInfoArrayList = new ArrayList<URLInfo>();

        int queryWordsCount = queryWordsSet.size();

        for (String eachKeywords : queryWordsSet) {
            Set<Tuple> st = queryKeys(eachKeywords);

            double idf = java.lang.Math.log(totolPages * 1.0 - st.size() + 0.5 / st.size() + 0.5);
//            double idf = java.lang.Math.log(totolPages * 1.0 / st.size() + 1);

            for (Tuple tuple : st) {
                URLInfo newUrlInfo = URLInfoLogic.getSimpleURLInfo(tuple, idf);

                if (urlInfoArrayList.contains(newUrlInfo)) {
                    newUrlInfo = URLInfoLogic.getURLInfo(tuple, idf);

                    int index = urlInfoArrayList.indexOf(newUrlInfo);
                    URLInfo preUrlInfo = urlInfoArrayList.get(index);
                    preUrlInfo.addHit();
                    //  logger.info(String.format("newUrlInfo Rank %s IDF: ,newUrlInfo Rank%s", newUrlInfo.getRank(), preUrlInfo.getRank()));

                    preUrlInfo.addRank(newUrlInfo.getRank());
                    urlInfoArrayList.set(index, preUrlInfo);

                } else {
                    newUrlInfo = URLInfoLogic.getURLInfo(tuple, idf);
                    urlInfoArrayList.add(newUrlInfo);
                }

            }

        }

        //update rank
        for (int position = 0; position < urlInfoArrayList.size(); position++) {
            double coord = urlInfoArrayList.get(position).getHits() * 1.0 / queryWordsCount * 1.0;
            URLInfo preUrlInfo = urlInfoArrayList.get(position);
            preUrlInfo.setRank(preUrlInfo.getRank() * coord);
            urlInfoArrayList.set(position, preUrlInfo);

        }


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
