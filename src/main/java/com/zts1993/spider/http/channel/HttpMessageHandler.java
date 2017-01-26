/*
 * Copyright (c) 2017 By Timothy Zhang
 */

package com.zts1993.spider.http.channel;

import com.zts1993.spider.http.HttpClient;
import com.zts1993.spider.http.HttpRequest;
import com.zts1993.spider.http.HttpResponsePromise;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.CompositeByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.HttpResponse;

/**
 * GSE Component
 * Created by TimothyZhang on 2017/1/23.
 */
public class HttpMessageHandler extends ChannelInboundHandlerAdapter {

    private final HttpClient client;

    private final HttpRequest request;

    protected HttpResponsePromise promise;


    public HttpMessageHandler(HttpClient client, HttpRequest request) {
        this.client = client;
        this.request = request;
        this.promise = request.getPromise();
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        //todo
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        //
    }

    public void channelRead(final ChannelHandlerContext ctx, Object msg) throws Exception {

        if (msg instanceof io.netty.handler.codec.http.HttpResponse) {


        } else if (msg instanceof HttpContent) {
            HttpContent c = (HttpContent) msg;


        }
    }





}
