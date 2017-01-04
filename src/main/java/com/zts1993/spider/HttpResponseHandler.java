/*
 * Copyright (c) 2017 By Timothy Zhang
 */

package com.zts1993.spider;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.HttpObject;
import lombok.extern.slf4j.Slf4j;

/**
 * GSE Component
 * Created by TimothyZhang on 2017/1/5.
 */
@Slf4j
public class HttpResponseHandler extends SimpleChannelInboundHandler<HttpObject> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {


        log.info(msg.toString());


    }
}
