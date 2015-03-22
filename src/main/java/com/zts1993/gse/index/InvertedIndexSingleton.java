/*
 * Copyright (c) 2015 By Timothy Zhang
 */

package com.zts1993.gse.index;

/**
 * Created by TianShuo on 2015/3/22.
 */
public class InvertedIndexSingleton {

    private static InvertedIndex invertedIndex;

    private InvertedIndexSingleton() {
    }

    public InvertedIndex getInstance() {
        if (invertedIndex == null) {
            synchronized (InvertedIndexSingleton.class) {
                if (invertedIndex == null) {
                    invertedIndex = new InvertedIndex();
                }
            }
        }

        return invertedIndex;
    }


}
