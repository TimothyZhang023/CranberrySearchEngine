/*
 * Copyright (c) 2015 By Timothy Zhang
 */

package com.zts1993.gse.index;

import com.zts1993.gse.common.RedisFactory;
import org.ansj.domain.Term;
import redis.clients.jedis.Jedis;

import java.util.*;

/**
 * Created by TianShuo on 2015/3/22.
 */
public class InvertedIndex {

    private HashMap<String, ArrayList<String>> fordwardIndexMap;
    private HashMap<String, ArrayList<String>> invertedIndexMap;

    public InvertedIndex() {
        initInvertedIndex();
    }

    public void initInvertedIndex() {
        invertedIndexMap = new HashMap<String, ArrayList<String>>();

    }

    public HashMap<String, ArrayList<String>> createInvertedIndex() {

        invertedIndexMap = new HashMap<String, ArrayList<String>>();

        //Todo process here

        System.out.println("***************************************************************");
        System.out.println("create invertedIndex finished!!");
        System.out.println("the size of invertedIndex is : " + invertedIndexMap.size());
        return invertedIndexMap;
    }

    public void addToInvertedIndex(List<Term> termList,String url) {

        if(termList.size()==0){ return ; }

        Jedis jedis= RedisFactory.getJedis();
        Term term;
        String word;
        Iterator<Term> termIterator = termList.iterator();
        while (termIterator.hasNext()) {
            term = termIterator.next();

            word=term.getRealName();
            // System.out.println(term.getRealName());

            if(!invertedIndexMap.containsKey(word))
            {
                ArrayList<String> urls = new ArrayList<String>();
                urls.add(url);
                invertedIndexMap.put(word, urls);

                 jedis.sadd(word, url);

            }
            //索引中已经含有这个key，不许要加入这个key，需要找到这个key从而把url链接上
            else
            {
                ArrayList<String> urls = invertedIndexMap.get(word);
                if(!urls.contains(url))
                    urls.add(url);

                jedis.sadd(word, url);

            }

        }

        //RedisFactory.closeJedis(jedis);
        //Todo process here

        System.out.println("***************************************************************");
        System.out.println("add to invertedIndex finished!!");
        System.out.println("the size of invertedIndex is : " + invertedIndexMap.size());
    }

    public HashMap<String, ArrayList<String>> getInvertedIndex() {
        return invertedIndexMap;
    }


    public ArrayList<String> query(String key) {

        Jedis jedis= RedisFactory.getJedis();
        Set<String> querySet=jedis.smembers(key);
        ArrayList<String> stringArrayList=new ArrayList<String>(querySet);
         return stringArrayList;
        //  return invertedIndexMap.get(key);
    }

    public static void main(String[] args) {


    }


}
