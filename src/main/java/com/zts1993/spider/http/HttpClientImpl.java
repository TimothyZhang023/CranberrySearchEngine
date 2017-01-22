/*
 * Copyright (c) 2017 By Timothy Zhang
 */

package com.zts1993.spider.http;


import java.io.Closeable;

/**
 * GSE Component
 * Created by TimothyZhang on 2017/1/5.
 */
public interface HttpClientImpl extends Closeable {

    HttpResponsePromise send(HttpRequest httpRequest) throws InterruptedException;

    void init();
}
