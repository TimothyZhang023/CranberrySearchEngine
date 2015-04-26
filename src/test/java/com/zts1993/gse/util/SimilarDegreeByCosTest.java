/*
 * Copyright (c) 2015 By Timothy Zhang
 */

package com.zts1993.gse.util;

import org.junit.Test;

public class SimilarDegreeByCosTest {

    @Test
    public void testGetSimilarDegree() throws Exception {

        String docId1 = "c327895dc2cbbf82a6371eadfb3a431f1a92f38a9baa438ba60d5e9cc184f1d8".toLowerCase();
        String docId2 = "c327895dc2cbbf82a6371eadfb3a431f1a92f38a9baa438ba60d5e9cc184f1d8".toLowerCase();
        String docId3 = "266ac0c4d3b0865187569a231943022f95a56f1163a34cf35a695d6dd03e1d96".toLowerCase();

//        System.out.println(SimilarDegreeByCos.getSimilarDegree(docId1, docId2));
//        System.out.println(SimilarDegreeByCos.getSimilarDegree(docId1, docId3));
//
//
//
//         docId1 = "da055ccf096535caba5425d457eee7188b815f2947230e8e2ac5502342cae5bb".toLowerCase();
//         docId2 = "72011b683f76615a943f0b8226088728fef752c013e80640ea020bfa11dc733f".toLowerCase();
//         docId3 = "111e4e190e257219ad7006748a27ea856e06d548d1a6a04f3158d5c054d7f014".toLowerCase();
//
//        System.out.println(SimilarDegreeByCos.getSimilarDegree(docId1, docId2));
//        System.out.println(SimilarDegreeByCos.getSimilarDegree(docId1, docId3));
//
//
//        System.out.println(SimilarDegreeByCos.getSimilarDegree(docId1, docId2));
//        System.out.println(SimilarDegreeByCos.getSimilarDegree(docId1, docId3));
//
//



        docId1 = "277KjjfdlB3x-U6oQGsVKpuuIivertQATjnSEdRM1yr";
        docId2 = "9adkdFuzSe94OxRb6B7d78Y08qhBpNHulg-kp76chg0";

        System.out.println(SimilarDegreeByCos.getSimilarDegree(docId1, docId2));


    }

}