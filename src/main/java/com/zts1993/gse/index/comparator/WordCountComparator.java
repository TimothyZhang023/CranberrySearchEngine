/*
 * Copyright (c) 2015 By Timothy Zhang
 */

package com.zts1993.gse.index.comparator;

import java.util.Comparator;
import java.util.Map;

/**
 * Created by TianShuo on 2015/4/14.
 */
public class WordCountComparator implements Comparator<Map.Entry<String, Integer>> {
    @Override
    public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
        return o2.getValue().compareTo(o1.getValue());
    }
}
