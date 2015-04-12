/*
 * Copyright (c) 2015 By Timothy Zhang
 */

package com.zts1993.gse.index;

import com.zts1993.gse.bean.Factors;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by TianShuo on 2015/3/29.
 */
public class InvertedIndexThreadSemaphore {


    public static final int Threads = (int) (Runtime.getRuntime().availableProcessors() * Factors.CpuOverUse);

    private static AtomicInteger counter_integer = new AtomicInteger(Threads);


    public synchronized static int sum() {
        return counter_integer.get();
    }

    public synchronized static int incr() {
        return counter_integer.getAndIncrement();
    }

    public synchronized static int decr() {
        return counter_integer.getAndDecrement();
    }


}
