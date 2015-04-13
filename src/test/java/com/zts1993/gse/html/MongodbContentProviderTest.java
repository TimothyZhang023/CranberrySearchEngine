/*
 * Copyright (c) 2015 By Timothy Zhang
 */

package com.zts1993.gse.html;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

public class MongodbContentProviderTest {

    private static final Logger logger = LogManager.getLogger("MongodbContentProviderTest");

    MongodbContentProvider mongodbContentProvider = new MongodbContentProvider("0001dd7ca15a5d2260c69d9fa160f168cbc2aff438c1ee1c3d98f1cae85bc3fb");

    @Test
    public void testFetchHtml() throws Exception {
        logger.info(mongodbContentProvider.fetchHtml());
    }

    @Test
    public void testFetchText() throws Exception {
        System.out.println(mongodbContentProvider.fetchText());

    }

    @Test
    public void testFetchTitle() throws Exception {
        System.out.println(mongodbContentProvider.fetchTitle());

    }

    @Test
    public void testFetchMarkedText() throws Exception {
        Set<String> stringSet = new HashSet<String>();
        stringSet.add("美国");
        stringSet.add("专题");

        String content = mongodbContentProvider.fetchMarkedText(stringSet).toLowerCase();
        logger.info(content);
    }
}