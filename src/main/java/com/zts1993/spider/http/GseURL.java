/*
 * Copyright (c) 2017 By Timothy Zhang
 */

package com.zts1993.spider.http;

import io.netty.handler.codec.http.HttpScheme;
import lombok.Getter;

import java.net.URI;
import java.net.URISyntaxException;

import static io.netty.handler.codec.http.HttpScheme.HTTP;
import static io.netty.handler.codec.http.HttpScheme.HTTPS;

/**
 * GSE Component
 * Created by TimothyZhang on 2017/1/21.
 */
public class GseURL {

    @Getter
    private URI uri;

    @Getter
    private String host;

    @Getter
    private int port;

    @Getter
    private boolean ssl;

    @Getter
    private HttpScheme httpScheme;

    public GseURL(String url) throws URISyntaxException {
        this(new GseURLBuilder(url).build());
    }

    public GseURL(GseURLBuilder builder) throws URISyntaxException {
        this(builder.build());
    }

    public GseURL(URI uri) throws URISyntaxException {
        updateUri(uri);
    }

    public void updateUri(URI uri) throws URISyntaxException {
        this.uri = uri;
        checkUri();
        this.ssl = uri.getScheme().toLowerCase().equals(HTTPS.toString());
        this.port = (uri.getPort() == -1) ? (ssl ? 443 : 80) : uri.getPort();
        this.host = (uri.getHost() == null) ? "127.0.0.1" : uri.getHost();
    }


    public void checkUri() throws URISyntaxException {
        if (!uri.getScheme().toLowerCase().startsWith(HTTP.toString())) {
            throw new URISyntaxException(uri.toASCIIString(), "scheme not supported");
        }
    }

    String getHostName() {
        return host + ":" + port;
    }


}
