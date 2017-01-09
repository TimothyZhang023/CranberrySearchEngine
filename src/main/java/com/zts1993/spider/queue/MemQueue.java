/*
 * Copyright (c) 2017 By Timothy Zhang
 */

package com.zts1993.spider.queue;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

/**
 * GSE Component
 * Created by TimothyZhang on 2017/1/9.
 */
public class MemQueue<T> implements QueueImpl<T> {

    private BlockingDeque<T> blockingDeque = new LinkedBlockingDeque<>();

    @Override
    public boolean addLast(T task) {
        return blockingDeque.offer(task);
    }

    @Override
    public boolean addFirst(T task) {
        return blockingDeque.add(task);
    }

    @Override
    public T first() {
        return blockingDeque.peek();
    }

    @Override
    public T poll() {
        return blockingDeque.poll();
    }
    @Override
    public T poll(long timeout, TimeUnit unit) throws InterruptedException {
        return blockingDeque.poll(timeout , unit);
    }

    @Override
    public long size() {
        return blockingDeque.size();
    }

    @Override
    public void clear() {
        blockingDeque.clear();
    }
}
