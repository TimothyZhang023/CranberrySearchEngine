/*
 * Copyright (c) 2015 By Timothy Zhang
 */

package com.zts1993.gse.counter;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by TianShuo on 2015/3/29.
 */
public class GenIndexThreadSemaphore {


    public static final int Threads = Runtime.getRuntime().availableProcessors();

    private static AtomicInteger counter_integer = new AtomicInteger(Threads);


    public static int sum() {
        return counter_integer.get();
    }

    public static int incr() {
        return counter_integer.getAndIncrement();
    }

    public static int decr() {
        return counter_integer.getAndDecrement();
    }


}
