/*
 * Copyright (c) 2015 By Timothy Zhang
 */

package com.zts1993.gse.html;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.junit.Test;

public class FetchLocalHtmlFileTest {

    private static final Logger logger = LogManager.getLogger("FetchLocalHtmlFileTest");

    @Test
    public void testFetch() throws Exception {

        LocalFsHtmlContentProvider fetchLocalHtmlFile = new LocalFsHtmlContentProvider("00005ae1c39d731fc344f93eddf84dd10d0dea00eb4e70510e73aa3881bc1028");
        String content = fetchLocalHtmlFile.Html();
        logger.info(content);
    }
}