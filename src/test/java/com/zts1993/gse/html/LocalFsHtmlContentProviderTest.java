/*
 * Copyright (c) 2015 By Timothy Zhang
 */

package com.zts1993.gse.html;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

public class LocalFsHtmlContentProviderTest {

    private static final Logger logger = LogManager.getLogger("LocalFsHtmlContentProviderTest");

    LocalFsHtmlContentProvider fetchLocalHtmlFile;


    @Before
    public void setUp() throws Exception {
        fetchLocalHtmlFile = new LocalFsHtmlContentProvider("00005ae1c39d731fc344f93eddf84dd10d0dea00eb4e70510e73aa3881bc1028");;

    }

    @Test
    public void testFetchHtml() throws Exception {
        fetchLocalHtmlFile.fetchHtml();
    }

    @Test
    public void testFetchText() throws Exception {
        String content = fetchLocalHtmlFile.fetchText();
        logger.info(content);
    }

    @Test
    public void testFetchMarkedText() throws Exception {

        Set<String> stringSet=new HashSet<String>();
        stringSet.add("美国");
        stringSet.add("澳大利亚");

        String content = fetchLocalHtmlFile.fetchMarkedText(stringSet);
        logger.info(content);
    }
}