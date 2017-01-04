/*
 * Copyright (c) 2017 By Timothy Zhang
 */

package com.zts1993.spider;


import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.DefaultFullHttpRequest;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpVersion;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by zts1993 on 2016/11/26.
 */
@Slf4j
public class M {
    public static void main(String[] args) throws IOException, URISyntaxException, InterruptedException {

        val gseHttpClient = new GseHttpClient();

        gseHttpClient.init();

        URI uri = new URI("http://guahao.zjol.com.cn/pb/957108?deptId=7195&fuzzy_deptId=0&docId=&fuzzy_docId=0");


        int port = uri.getPort();
        String host = uri.getHost();
        if (port == -1) {
            port = 80;
        }

        // Start the client.
        ChannelFuture f = gseHttpClient.getBootstrap().connect(host, port).sync();
        String msg = "Are you ok?";
        DefaultFullHttpRequest request = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.GET,
                uri.toASCIIString(), Unpooled.wrappedBuffer(msg.getBytes("UTF-8")));

        // 构建http请求
        request.headers().set(HttpHeaders.Names.HOST, host);
        request.headers().set(HttpHeaders.Names.CONNECTION, HttpHeaders.Values.KEEP_ALIVE);
        request.headers().set(HttpHeaders.Names.CONTENT_LENGTH, request.content().readableBytes());
        // 发送http请求

        f.channel().pipeline().addLast("handler", new HttpResponseHandler());
        f.channel().write(request);
        f.channel().flush();
        f.channel().closeFuture().sync();


        gseHttpClient.close();
    }
}
