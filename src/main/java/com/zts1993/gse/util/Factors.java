/*
 * Copyright (c) 2015 By Timothy Zhang
 */

package com.zts1993.gse.util;

/**
 * Created by TianShuo on 2015/4/6.
 */
public class Factors {


    /**
     * double const
     */
    public static int titleWeight = 9;
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
    public static int MaxRecordPerWord =10000;
    public static int MaxRecordPerRequest = 10000;
    public static int MaxRecordPerKey = 10000;

    public static int PageSize = 10;


    public static double CpuOverUse = 1.5;
    public static int InvertedIndexThreadSemaphoreThreshold = 1;


    /**
     * SimilarDegree
     */
    public static int checkWordCount = 10;
    public static int checkSimWordCount = 50;
    public static int checkUrlCount = 12;



}
