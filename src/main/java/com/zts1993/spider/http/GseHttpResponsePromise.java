/*
 * Copyright (c) 2017 By Timothy Zhang
 */

package com.zts1993.spider.http;

import io.netty.util.concurrent.Promise;

/**
 * GSE Component
 * Created by TimothyZhang on 2017/1/5.
 */
public class GseHttpResponsePromise extends ResponsePromise<GseHttpResponse> {


    @Override
    public GseHttpResponsePromise attachNettyPromise(Promise<GseHttpResponse> promise) {
        super.attachNettyPromise(promise);
        return this;
    }
}
