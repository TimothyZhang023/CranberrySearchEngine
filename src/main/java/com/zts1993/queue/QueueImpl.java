/*
 * Copyright (c) 2017 By Timothy Zhang
 */

package com.zts1993.queue;

import java.util.concurrent.TimeUnit;

/**
 * GSE Component
 * Created by TimothyZhang on 2017/1/9.
 */
public interface QueueImpl<T> {

    boolean addLast(T task);

    boolean addFirst(T task);

    T first();

    T poll();

    T poll(long timeout, TimeUnit unit) throws InterruptedException;

    long size();

    void clear();

}
