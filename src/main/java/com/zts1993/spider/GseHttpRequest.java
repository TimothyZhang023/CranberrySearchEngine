/*
 * Copyright (c) 2017 By Timothy Zhang
 */

package com.zts1993.spider;

import com.zts1993.spider.util.Timeout;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
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
    final URI requestUri;


    public GseHttpRequest(GseHttpClientImpl gseHttpClient, URI requestUri) {
        this.gseHttpClient = gseHttpClient;
        this.requestUri = requestUri;

        httpRequest = new DefaultFullHttpRequest(
                HttpVersion.HTTP_1_1, HttpMethod.GET, requestUri.toASCIIString()
                //,Unpooled.wrappedBuffer(msg.getBytes("UTF-8"))
        );

        // 构建http请求
        httpRequest.headers().set(HttpHeaders.Names.HOST, requestUri.getHost());
        httpRequest.headers().set(HttpHeaders.Names.CONNECTION, HttpHeaders.Values.KEEP_ALIVE);
        httpRequest.headers().set(HttpHeaders.Names.CONTENT_LENGTH, httpRequest.content().readableBytes());
    }

    @Getter
    @Setter
    FullHttpRequest httpRequest;


    GseHttpResponsePromise promise;


    public GseHttpResponsePromise send() throws IOException {

//        gseHttpClient.send(httpRequest);

        return promise;
    }


    /*
        optional args
     */
    @Setter
    Timeout time = new Timeout();


}
