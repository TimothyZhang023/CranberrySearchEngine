/*
 * Copyright (c) 2015 By Timothy Zhang
 */

package com.zts1993.gse.db.logic;

import com.zts1993.gse.db.cache.KVCache;
import org.junit.Test;

public class KVCacheTest {



    @Test
    public void testSet() throws Exception {
        KVCache.set("KVCacheTest_test", "sss");

    }

    @Test
    public void testGet() throws Exception {
        KVCache.get("KVCacheTest_test");
    }



}