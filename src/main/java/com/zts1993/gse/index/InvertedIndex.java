/*
 * Copyright (c) 2015 By Timothy Zhang
 */

package com.zts1993.gse.index;

import com.zts1993.gse.bean.URLInfo;
import com.zts1993.gse.cache.LRUCache;
import com.zts1993.gse.db.redis.RedisDB;
import org.ansj.domain.Term;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Created by TianShuo on 2015/3/22.
 */
public class InvertedIndex {

    private static final int DEFAULT_CAPACITY = 1000;

    private static final Logger logger = LogManager.getLogger("InvertedIndex");

    private static LRUCache<String, ArrayList<URLInfo>> invertedIndexMap;

    static {
        initInvertedIndex();
    }

    public InvertedIndex() {
    }

    private static LRUCache<String, ArrayList<URLInfo>> initInvertedIndex() {
        if (invertedIndexMap == null) {
            synchronized (InvertedIndex.class) {
                if (invertedIndexMap == null) {
                    logger.info("Init InvertedIndex LRUCache");
                    invertedIndexMap = new LRUCache<String, ArrayList<URLInfo>>(DEFAULT_CAPACITY);
                }
            }
        }
        return invertedIndexMap;
    }

    public LRUCache<String, ArrayList<URLInfo>> getInvertedIndex() {
        if (invertedIndexMap == null) {
            initInvertedIndex();
        }
        return invertedIndexMap;
    }


        public static synchronized int count() {
        return invertedIndexMap.size();
    }

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

        Jedis jedis = RedisDB.getJedis();
        Term term;
        String word;
        Iterator<Term> termIterator = termList.iterator();
        while (termIterator.hasNext()) {
            term = termIterator.next();

            word = term.getRealName();
            // System.out.println(term.getRealName());

            if (!invertedIndexMap.containsKey(word)) {
//                logger.info("LRU Write Cache hit~");
                ArrayList<URLInfo> urls = new ArrayList<URLInfo>();
                urls.add(new URLInfo(url));
                invertedIndexMap.put(word, urls);
            } else {
                ArrayList<URLInfo> urls = invertedIndexMap.get(word);
                if (!urls.contains(new URLInfo(url)))
                    urls.add(new URLInfo(url));

            }

            jedis.sadd(word, new URLInfo(url).getHash());
        }
//        logger.info("add into invertedIndex finished!!");
//        logger.info("the size of invertedIndex is : " + invertedIndexMap.size());
//        logger.info("********************************");
        RedisDB.closeJedis(jedis);

    }


    public ArrayList<URLInfo> query(String key) {
        ArrayList<URLInfo> stringArrayList = invertedIndexMap.get(key);

        if (stringArrayList == null) {
//            logger.info("LRU Read Cache missed!");

            stringArrayList = new ArrayList<URLInfo>();

            Jedis jedis = RedisDB.getJedis();
            Set<String> querySet = jedis.smembers(key);
            RedisDB.closeJedis(jedis);

            Iterator<String> it = querySet.iterator();
            while (it.hasNext()) {
                String urlHash = it.next();
                stringArrayList.add(URLInfo.getURLInfoByHash(urlHash));
            }
            invertedIndexMap.put(key, stringArrayList);
        }else{
//            logger.info("LRU Read Cache hit~");

        }

        return stringArrayList;
    }

    public static void main(String[] args) {


    }


}
