/*
 * Copyright (c) 2017 By Timothy Zhang
 */

package com.zts1993.spider.http.channel;

import com.zts1993.spider.http.HttpClient;
import com.zts1993.spider.http.HttpRequest;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * GSE Component
 * Created by TimothyZhang on 2017/1/23.
 */
public class HttpMessageHandler extends ChannelInboundHandlerAdapter {

    private final HttpClient client;

    private final HttpRequest request;

    public HttpMessageHandler(HttpClient client, HttpRequest request) {
        this.client = client;
        this.request = request;
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        //todo
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        //
    }





}
