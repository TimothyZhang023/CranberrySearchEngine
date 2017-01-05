/*
 * Copyright (c) 2017 By Timothy Zhang
 */

package com.zts1993.spider;

import io.netty.channel.ChannelFuture;
import io.netty.util.concurrent.Promise;
import lombok.Getter;
import lombok.Setter;

import java.util.concurrent.ExecutionException;

/**
 * GSE Component
 * Created by TimothyZhang on 2017/1/5.
 */
public class GseHttpResponsePromise {

    ChannelFuture f;

    @Setter
    @Getter
    Promise<GseHttpResponse> nettyPromise;


    public void attachNettyPromise(Promise<GseHttpResponse> promise) {
        this.nettyPromise = promise;
    }

    public GseHttpResponse get() throws ExecutionException, InterruptedException {
        return nettyPromise.get();
    }

}
