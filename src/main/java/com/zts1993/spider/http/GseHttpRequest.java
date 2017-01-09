/*
 * Copyright (c) 2017 By Timothy Zhang
 */

package com.zts1993.spider.http;

import com.zts1993.spider.http.channel.GseChannelCallback;
import com.zts1993.spider.util.IpUtil;
import com.zts1993.spider.util.Timeout;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.IOException;
import java.net.URI;

/**
 * GSE Component
 * Created by TimothyZhang on 2017/1/5.
 */
@ToString
public class GseHttpRequest {

    public static final String HTTP = "http";
    public static final String HTTPS = "https";

    @Getter
    @Setter
    GseChannelCallback channelCallback;

    @Getter
    private final GseHttpClientImpl gseHttpClient;

    @Getter
    private URI uri;

    @Getter
    private boolean ssl;

    public void updateUri(URI uri) {
        this.uri = uri;
        this.host = uri.getHost();
        if (host == null) host = "127.0.0.1";
        if (uri.getScheme().toLowerCase().equals(HTTPS)) ssl = true;
        this.port = (uri.getPort() == -1) ? (ssl ? 443 : 80) : uri.getPort();
        this.httpRequest = null;
    }

    @Getter
    private String host;

    @Getter
    private int port;


    @Getter
    @Setter
    private HttpMethod method = HttpMethod.GET;

    @Getter
    @Setter
    private HttpVersion httpVersion = HttpVersion.HTTP_1_1;

    public GseHttpRequest(GseHttpClientImpl gseHttpClient, URI uri) {
        this.gseHttpClient = gseHttpClient;
        updateUri(uri);
    }

    @Getter
    @Setter
    private FullHttpRequest httpRequest;


    @Getter
    @Setter
    private GseHttpResponsePromise promise;


    private ByteBuf content = Unpooled.buffer(0);

    public synchronized void prepareRequest() {
        if (httpRequest == null) {
            httpRequest = new DefaultFullHttpRequest(httpVersion, method, uri.toASCIIString(), content);
            httpRequest.headers().set(HttpHeaderNames.HOST, host);
            httpRequest.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
            httpRequest.headers().set(HttpHeaderNames.CONTENT_LENGTH, httpRequest.content().readableBytes());

            String randomIp = IpUtil.getRandomIp();

            httpRequest.headers().set("X-Forwarded-For", randomIp);
            httpRequest.headers().set("X-Real-IP", randomIp);
        }
    }

    public GseHttpResponsePromise send() throws IOException, InterruptedException {
        prepareRequest();
        return promise = gseHttpClient.send(this);

    }


    /*
        optional args
     */
    @Setter
    Timeout time = new Timeout();


}
