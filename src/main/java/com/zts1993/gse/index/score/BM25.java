/*
 * Copyright (c) 2015 By Timothy Zhang
 */

package com.zts1993.gse.index.score;

import com.zts1993.gse.util.Factors;

/**
 * Created by TianShuo on 2015/4/11.
 */
public class BM25 implements IScore {
    @Override
    public double getScore(double tf ,double idf ,int wordCount) {
        double rank = idf * (((Factors.BM25K1 + 2) * tf) / (tf + Factors.BM25K1 * (1 - Factors.BM25b + Factors.BM25b * (wordCount / Factors.BM25avg))));

        return 0;
    }
}
