/*
 * Copyright (c) 2015 By Timothy Zhang
 */

package com.zts1993.gse.index.comparator;

import java.util.Comparator;
import java.util.Map;

/**
 * Created by TianShuo on 2015/4/11.
 */
public class UrlScoreComparator implements Comparator<Map.Entry<String, Double>> {

    @Override
    public int compare(Map.Entry<String, Double> o1, Map.Entry<String, Double> o2) {
        return o2.getValue().compareTo(o1.getValue());
    }
}
