/*
 * Copyright (c) 2017 By Timothy Zhang
 */

package com.zts1993.spider.http.channel;

import com.zts1993.spider.http.HttpRequest;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.ssl.SslHandler;
import lombok.extern.slf4j.Slf4j;

import javax.net.ssl.SSLEngine;

/**
 * GSE Component
 * Created by TimothyZhang on 2017/1/23.
 */
@Slf4j
public class ExceptionForwardingSslHandler extends SslHandler {

    private HttpRequest request;

    public ExceptionForwardingSslHandler(SSLEngine engine, HttpRequest request) {
        super(engine);
        this.request = request;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("", cause);
    }
}
