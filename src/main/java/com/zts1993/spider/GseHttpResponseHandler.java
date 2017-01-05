/*
 * Copyright (c) 2017 By Timothy Zhang
 */

package com.zts1993.spider;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.util.concurrent.Promise;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.Charset;

/**
 * GSE Component
 * Created by TimothyZhang on 2017/1/5.
 */
@Slf4j
@RequiredArgsConstructor
public class GseHttpResponseHandler extends SimpleChannelInboundHandler<FullHttpResponse> {

    protected final GseHttpClient gseHttpClient;

    protected final GseHttpRequest request;

//    protected final Promise<String> promise;


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpResponse msg) throws Exception {

        String res = msg.content().toString(Charset.defaultCharset());

        log.info(res);
        log.info(msg.toString());


    }
}
