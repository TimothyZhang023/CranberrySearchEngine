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

/**
 * Created by zts1993 on 2016/11/26.
 */
@Slf4j
public class M {
    public static void main(String[] args) throws IOException, URISyntaxException, InterruptedException {


        GseHttpClientConfig config = new GseHttpClientConfig();

        GseHttpClient gseHttpClient = new GseHttpClient(config);

        gseHttpClient.init();

        URI uri = new URI("http://guahao.zjol.com.cn/pb/957108?deptId=7195&fuzzy_deptId=0&docId=&fuzzy_docId=0");

        GseHttpRequest request = new GseHttpRequest(gseHttpClient, uri);


        ChannelFuture f = gseHttpClient.send(request);
        f.channel().closeFuture().sync();

        gseHttpClient.close();
    }
}
