/*
 * Copyright (c) 2015 By Timothy Zhang
 */

package com.zts1993.gse.segmentation.filter;

import java.util.HashMap;

/**
 * Created by TianShuo on 2015/4/18.
 */
public interface TermFilter {

    HashMap<String, Integer> getWordFreqMap();

    int getWordCount();
}
