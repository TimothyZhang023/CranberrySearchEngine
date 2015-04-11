/*
 * Copyright (c) 2015 By Timothy Zhang
 */

package com.zts1993.gse.bean;

/**
 * Created by TianShuo on 2015/4/6.
 */
public class Factors {


    /**
     * double const
     */
    public static double cpuOverUse = 1.0;
    public static int titleWeight = 10;
    public static int contentWeight = 1;


    /**
     * int const
     */
    public static int LowerQuality = 20;


    /**
     * bm25 const
     */
    public static double BM25K1 = 2;
    public static double BM25b = 0.75;
    public static double BM25avg = 400;


    /**
     * Word Count Punish
     */
    public static int PunishThreshold = 150;
    public static int PunishMultiplier = 1000;


    /**
     * int const
     */
    public static int MaxRecordPerWord =1000;
    public static int MaxRecordPerRequest = 1000;
    public static int MaxRecordPerKey = 1000;


}
