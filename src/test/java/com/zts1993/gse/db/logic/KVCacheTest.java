/*
 * Copyright (c) 2015 By Timothy Zhang
 */

package com.zts1993.gse.db.logic;

import com.zts1993.gse.db.cache.KVCache;
import com.zts1993.gse.db.cache.LRUCache;
import com.zts1993.gse.db.cache.TTLCache;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.junit.Test;

public class KVCacheTest {

    final private static Logger logger = LogManager.getLogger("KVCacheTest");

    private static LRUCache<String, String> lruCache = new LRUCache<String, String>(10000);
    private static TTLCache<String, String> TTLCache = new TTLCache<String, String>(10000);

    @Test
    public void testSet() throws Exception {
        KVCache.set("KVCacheTest_test", "sss");


    }

    @Test
    public void testGet() throws Exception {
        lruCache.get("KVCacheTest_test");
        TTLCache.get("KVCacheTest_test");
    }


    @Test
    public void testBenchmark() throws Exception {
        lruCache.put("KVCacheTest_test", "sss");

        /**
         *  double timePass = (System.nanoTime() - valueEntry.lastAccess.get()) / 1000000;
         */

        LRUCacheTestThread t1 = new LRUCacheTestThread();
        t1.start();
        LRUCacheTestThread t2 = new LRUCacheTestThread();
        t2.start();
        LRUCacheTestThread t3 = new LRUCacheTestThread();
        t3.start();


        Thread.sleep(60 * 1000);

    }

    @Test
    public void testBenchmark2() throws Exception {
        TTLCache.put("KVCacheTest_test", "sss");

        /**
         *  double timePass = (System.nanoTime() - valueEntry.lastAccess.get()) / 1000000;
         */

        TTLCacheTestThread t1 = new TTLCacheTestThread();
        t1.start();
        TTLCacheTestThread t2 = new TTLCacheTestThread();
        t2.start();
        TTLCacheTestThread t3 = new TTLCacheTestThread();
        t3.start();


        Thread.sleep(60 * 1000);

    }

    private void testLRU() {
        long time1 = System.nanoTime();

        for (int i = 0; i < 50000; i++) {
            lruCache.put("KVCacheTest_test" + i, "sss");

        }
        long time2 = System.nanoTime();

        for (int i = 0; i < 50000; i++) {
            lruCache.get("KVCacheTest_test" + i);
        }

        long time3 = System.nanoTime();

        logger.info(String.format("Put %s ms,get %s ms", (time2 - time1) / 1000000, (time3 - time2) / 1000000));
    }


    public class LRUCacheTestThread extends Thread {

        public void run() {
            testLRU();
        }

    }


    public class TTLCacheTestThread extends Thread {

        public void run() {
            testTTL();
        }

    }

    private void testTTL() {

        long time1 = System.nanoTime();

        for (int i = 0; i < 50000; i++) {
            TTLCache.put("KVCacheTest_test" + i, "sss");

        }
        long time2 = System.nanoTime();

        for (int i = 0; i < 50000; i++) {
            TTLCache.get("KVCacheTest_test" + i);
        }

        long time3 = System.nanoTime();

        logger.info(String.format("Put %s ms,get %s ms", (time2 - time1) / 1000000, (time3 - time2) / 1000000));
    }


}