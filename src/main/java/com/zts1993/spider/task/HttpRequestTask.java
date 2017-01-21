/*
 * Copyright (c) 2017 By Timothy Zhang
 */

package com.zts1993.spider.task;

import com.zts1993.spider.http.GseHttpClientImpl;
import com.zts1993.spider.http.GseHttpRequest;
import com.zts1993.spider.http.GseHttpResponsePromise;
import lombok.Getter;
import lombok.ToString;

import java.io.IOException;


/**
 * GSE Component
 * Created by TimothyZhang on 2017/1/9.
 */
@ToString
public class HttpRequestTask extends AbstractTask<GseHttpResponsePromise> {

    private String name = "HttpRequestTask";

    @Override
    public String Name() {
        return name;
    }

    @Override
    public GseHttpResponsePromise Do() {
        try {
            return client.send(httpRequest);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Getter
    final private GseHttpRequest httpRequest;

    @Getter
    final private GseHttpClientImpl client;

    public HttpRequestTask(GseHttpRequest httpRequest, GseHttpClientImpl client) {
        this.httpRequest = httpRequest;
        this.client = client;
    }
}
