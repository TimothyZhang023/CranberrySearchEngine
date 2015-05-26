/*
 * Copyright (c) 2015 By Timothy Zhang
 */

package com.zts1993.gse.segmentation.filter;

import org.junit.Test;

public class StopWordsFilterTest {

    StopWordsFilter stopWordsFilter = new StopWordsFilter();


    @Test
    public void testIsPuncOrStopWords() throws Exception {
        System.out.println(stopWordsFilter.isPuncOrStopWords("？"));
        System.out.println(stopWordsFilter.isPuncOrStopWords("："));
        System.out.println(stopWordsFilter.isPuncOrStopWords("，"));
        System.out.println(stopWordsFilter.isPuncOrStopWords("、"));
        System.out.println(stopWordsFilter.isPuncOrStopWords("。"));
    }

    @Test
    public void testShowAllStopWords() throws Exception {
        stopWordsFilter.showAllStopWords();
    }

}