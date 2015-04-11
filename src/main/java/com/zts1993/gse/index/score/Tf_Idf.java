/*
 * Copyright (c) 2015 By Timothy Zhang
 */

package com.zts1993.gse.index.score;

/**
 * Created by TianShuo on 2015/4/11.
 */
public class Tf_Idf implements IScore {
    @Override
    public double getScore(double tf, double idf, int wordCount) {
        return tf * idf;
    }
}
