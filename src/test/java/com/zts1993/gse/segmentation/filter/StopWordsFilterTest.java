/*
 * Copyright (c) 2015 By Timothy Zhang
 */

package com.zts1993.gse.segmentation.filter;

import org.junit.Test;

public class StopWordsFilterTest {

    @Test
    public void testIsPuncOrStopWords() throws Exception {
        StopWordsFilter stopWordsFilter = new StopWordsFilter();
        System.out.println(stopWordsFilter.isPuncOrStopWords("？"));
    }
}