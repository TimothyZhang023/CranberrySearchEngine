/*
 * Copyright (c) 2017 By Timothy Zhang
 */

package com.zts1993.spider;


import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.handler.codec.http.DefaultFullHttpRequest;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpVersion;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.ExecutionException;

/**
 * Created by zts1993 on 2016/11/26.
 */
@Slf4j
public class M {
    public static void main(String[] args) throws IOException, URISyntaxException, InterruptedException, ExecutionException {


        GseHttpClient gseHttpClient = new GseHttpClient();

        gseHttpClient.init();

        URI uri = new URI("Http://docs.oracle.com/javase/specs/jls/se8/html/index.html");



        GseHttpResponsePromise f =  new GseHttpRequest(gseHttpClient, uri).send();
//        GseHttpResponsePromise f2 = gseHttpClient.send(new GseHttpRequest(gseHttpClient, new URI("http://www.hao123.com/")));

        GseHttpResponse gseHttpResponse = f.get();
//        GseHttpResponse gseHttpResponse1 = f2.get();

        System.out.println(gseHttpResponse.getContent());

        gseHttpClient.close();
    }
}
