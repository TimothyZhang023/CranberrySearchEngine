/*
 * Copyright (c) 2017 By Timothy Zhang
 */

package com.zts1993.spider.http;


import java.io.Closeable;

/**
 * GSE Component
 * Created by TimothyZhang on 2017/1/5.
 */
interface GseHttpClientImpl extends Closeable {

    GseHttpResponsePromise send(GseHttpRequest gseHttpRequest) throws InterruptedException;

}
