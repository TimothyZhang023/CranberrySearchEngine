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


    public static double getTfScoreM1(int termCount, int wordCount) {
        return (1.0 * termCount) / (1.0 * wordCount);
    }

    public static double getTfScoreM2(int termCount, int wordCount) {
        return Math.log(
                (1.0 * termCount)
                        /
                        (1.0 * wordCount)
        ) + 1;
    }

    public static double getIdfScoreM1(int totolPages, long stSize) {
        return java.lang.Math.log(totolPages * 1.0 / stSize + 1);
    }

    public static double getIdfScoreM2(int totolPages, long stSize) {
        return java.lang.Math.log((totolPages * 1.0 - stSize + 0.5) / (stSize + 0.5));
    }

}
