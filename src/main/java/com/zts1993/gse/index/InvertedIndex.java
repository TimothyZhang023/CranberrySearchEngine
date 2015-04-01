/*
 * Copyright (c) 2015 By Timothy Zhang
 */

package com.zts1993.gse.index;

import com.zts1993.gse.bean.URLInfo;
import com.zts1993.gse.db.redis.RedisDB;
import org.ansj.domain.Term;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import redis.clients.jedis.Jedis;

import java.util.*;

/**
 * Created by TianShuo on 2015/3/22.
 */
public class InvertedIndex {

    private static final int DEFAULT_CAPACITY = 1000;

    private static final Logger logger = LogManager.getLogger("InvertedIndex");

//    private static LRUCache<String, ArrayList<URLInfo>> invertedIndexMap;

    static {
//        initInvertedIndex();
    }

    public InvertedIndex() {
    }

//    private static LRUCache<String, ArrayList<URLInfo>> initInvertedIndex() {
//        if (invertedIndexMap == null) {
//            synchronized (InvertedIndex.class) {
//                if (invertedIndexMap == null) {
//                    logger.info("Init InvertedIndex LRUCache");
//                    invertedIndexMap = new LRUCache<String, ArrayList<URLInfo>>(DEFAULT_CAPACITY);
//                }
//            }
//        }
//        return invertedIndexMap;
//    }
//
//    public LRUCache<String, ArrayList<URLInfo>> getInvertedIndex() {
//        if (invertedIndexMap == null) {
//            initInvertedIndex();
//        }
//        return invertedIndexMap;
//    }


//    public static synchronized int count() {
//        return invertedIndexMap.size();
//    }

//    public LRUCache<String, ArrayList<URLInfo>> createInvertedIndex() {
//
//        invertedIndexMap = new LRUCache<String, ArrayList<URLInfo>>();
//
//        //Todo process here
//
//        logger.info("create invertedIndex finished!!");
//        logger.info("the size of invertedIndex is : " + invertedIndexMap.size());
//        logger.info("********************************");
//
//        return invertedIndexMap;
//    }

    public void addToInvertedIndex(List<Term> termList, String url) {

        if (termList.size() == 0) {
            return;
        }

        Set<String> stringSet=new HashSet<String>() ;
        Jedis jedis = RedisDB.getJedis();
        Term term;
        String word;
        Iterator<Term> termIterator = termList.iterator();
        while (termIterator.hasNext()) {
            term = termIterator.next();
            word = term.getRealName();
            stringSet.add(word);

        }

        Iterator<String> stringIterator= stringSet.iterator();
        while(stringIterator.hasNext()){
            word = stringIterator.next();
            jedis.sadd(word, new URLInfo(url).getHash());
        }

        RedisDB.closeJedis(jedis);

    }


    public ArrayList<URLInfo> query(String key) {
        ArrayList<URLInfo> stringArrayList;//= invertedIndexMap.get(key);

//        if (stringArrayList == null) {
//            logger.info("LRU Read Cache missed!");

        stringArrayList = new ArrayList<URLInfo>();

        Jedis jedis = RedisDB.getJedis();
        Set<String> querySet = jedis.smembers(key);
        RedisDB.closeJedis(jedis);

        for (String urlHash : querySet) {
            stringArrayList.add(URLInfo.getURLInfoByHash(urlHash));
        }
        //          invertedIndexMap.put(key, stringArrayList);
//        } else {
////            logger.info("LRU Read Cache hit~");
//
//        }

        return stringArrayList;
    }

    public ArrayList<URLInfo> queryAll(Set<String> key) {
        ArrayList<URLInfo> stringArrayList;//= invertedIndexMap.get(key);

        stringArrayList = new ArrayList<URLInfo>();

        Jedis jedis = RedisDB.getJedis();


        Set<String> querySet = jedis.sinter(key.toArray(new String[key.size()]));
        RedisDB.closeJedis(jedis);

        for (String urlHash : querySet) {
            stringArrayList.add(URLInfo.getURLInfoByHash(urlHash));
        }

        return stringArrayList;
    }

    public static void main(String[] args) {


    }


}
