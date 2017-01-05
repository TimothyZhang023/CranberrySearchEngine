/*
 * Copyright (c) 2017 By Timothy Zhang
 */

package com.zts1993.spider;

import com.zts1993.spider.util.Timeout;
import io.netty.handler.codec.http.*;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.net.URI;

/**
 * GSE Component
 * Created by TimothyZhang on 2017/1/5.
 */
public class GseHttpRequest {

    final GseHttpClientImpl gseHttpClient;

    @Getter
    @Setter
    URI uri;

    @Getter
    HttpMethod method= HttpMethod.GET;


    public GseHttpRequest(GseHttpClientImpl gseHttpClient, URI uri) {
        this.gseHttpClient = gseHttpClient;
        this.uri = uri;

        httpRequest = new DefaultFullHttpRequest(
                HttpVersion.HTTP_1_1, HttpMethod.GET, uri.toASCIIString()
                //,Unpooled.wrappedBuffer(msg.getBytes("UTF-8"))
        );

        // 构建http请求
        httpRequest.headers().set(HttpHeaderNames.HOST, uri.getHost());
        httpRequest.headers().set(HttpHeaderNames.CONNECTION, HttpHeaders.Values.KEEP_ALIVE);
        httpRequest.headers().set(HttpHeaderNames.CONTENT_LENGTH, httpRequest.content().readableBytes());
    }

    @Getter
    @Setter
    FullHttpRequest httpRequest;


    @Getter
    @Setter
    GseHttpResponsePromise promise;


    public GseHttpResponsePromise send() throws IOException, InterruptedException {

        return promise = gseHttpClient.send(this);

    }


    /*
        optional args
     */
    @Setter
    Timeout time = new Timeout();


}
