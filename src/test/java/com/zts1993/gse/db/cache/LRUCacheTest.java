/*
 * Copyright (c) 2015 By Timothy Zhang
 */

package com.zts1993.gse.db.cache;

import org.junit.Before;
import org.junit.Test;

public class LRUCacheTest {

    private LRUCache<String, String> urlInfoLRUCache;

    private static final int DEFAULT_CAPACITY = 10000;

    @Before
    public void setUp() throws Exception {
        urlInfoLRUCache= new LRUCache<String, String>(DEFAULT_CAPACITY);
        urlInfoLRUCache.put("test","ok");
        urlInfoLRUCache.put("test2","ok2");
        urlInfoLRUCache.put("test3","ok3");
        urlInfoLRUCache.put("test4","ok4");
        urlInfoLRUCache.put("test5","ok5");
    }

    @Test
    public void testContainsKey() throws Exception {
        urlInfoLRUCache.containsKey("test");
    }

    @Test
    public void testPut() throws Exception {
        urlInfoLRUCache.put("test2","ok");
        urlInfoLRUCache.put("test","ok");
    }

    @Test
    public void testGet() throws Exception {
        urlInfoLRUCache.get("test");
    }

    @Test
    public void testClear() throws Exception {
        urlInfoLRUCache.clear();

    }

    @Test
    public void testSize() throws Exception {
        urlInfoLRUCache.size();

    }

    @Test
    public void testGetAll() throws Exception {
        urlInfoLRUCache.getAll();

    }
}