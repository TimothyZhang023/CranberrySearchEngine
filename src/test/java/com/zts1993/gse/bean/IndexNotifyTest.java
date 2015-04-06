/*
 * Copyright (c) 2015 By Timothy Zhang
 */

package com.zts1993.gse.bean;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class IndexNotifyTest {

    private static final Logger logger = LogManager.getLogger("IndexNotifyTest");


    IndexNotify indexNotify = new IndexNotify();

    @Before
    public void setUp() throws Exception {
        String url = "http://www.zts1993.com";
        String title = "Timothy Blog";
        String hash_key = "hash_keyTest";
        String storage_type = "local_fs";
        String page_encoding = "utf-8";
        String queue_time = "2014-4-6 14:30:00";
        indexNotify = new  IndexNotify(url, title, hash_key, storage_type, page_encoding, queue_time);
        logger.info("Set Up IndexNotify :"+ indexNotify.toString());
    }

    @Test
    public void testGetUrl() throws Exception {
        indexNotify.getUrl();
    }

    @Test
    public void testSetUrl() throws Exception {
        indexNotify.setUrl("http://blog.zts1993.com");
    }

    @Test
    public void testGetHash_key() throws Exception {
        indexNotify.getHash_key();
    }

    @Test
    public void testSetHash_key() throws Exception {
        indexNotify.setHash_key("hash_keyTest2");

    }

    @Test
    public void testGetStorage_type() throws Exception {
        indexNotify.getStorage_type();
    }

    @Test
    public void testSetStorage_type() throws Exception {
        indexNotify.setStorage_type("mongodb");
    }

    @Test
    public void testGetPage_encoding() throws Exception {
        indexNotify.getPage_encoding();
    }

    @Test
    public void testSetPage_encoding() throws Exception {
        indexNotify.setPage_encoding("gbk");
    }

    @Test
    public void testGetQueue_time() throws Exception {
        indexNotify.getQueue_time();

    }

    @Test
    public void testSetQueue_time() throws Exception {
        indexNotify.setQueue_time("2015-5-6 15:33:00");
    }

    @Test
    public void testGetTitle() throws Exception {
        indexNotify.getTitle();
    }

    @Test
    public void testSetTitle() throws Exception {
        indexNotify.setTitle("Timothy");
    }

    @After
    public void tearDown() throws Exception {
        logger.info("Tear Down IndexNotify :"+ indexNotify.toString());
    }

}