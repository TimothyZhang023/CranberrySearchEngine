/*
 * Copyright (c) 2017 By Timothy Zhang
 */

package com.zts1993.spider.http;

import io.netty.util.concurrent.Promise;

/**
 * GSE Component
 * Created by TimothyZhang on 2017/1/5.
 */
public class HttpResponsePromise extends ResponsePromise<HttpResponse> {


    @Override
    public HttpResponsePromise attachNettyPromise(Promise<HttpResponse> promise) {
        super.attachNettyPromise(promise);
        return this;
    }
}
