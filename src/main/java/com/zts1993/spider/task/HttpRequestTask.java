/*
 * Copyright (c) 2017 By Timothy Zhang
 */

package com.zts1993.spider.task;

import com.zts1993.spider.http.HttpClientImpl;
import com.zts1993.spider.http.HttpRequest;
import com.zts1993.spider.http.HttpResponsePromise;
import lombok.Getter;
import lombok.ToString;


/**
 * GSE Component
 * Created by TimothyZhang on 2017/1/9.
 */
@ToString
public class HttpRequestTask extends AbstractTask<HttpResponsePromise> {

    private String name = "HttpRequestTask";

    @Override
    public String Name() {
        return name;
    }

    @Override
    public HttpResponsePromise Do() {
        try {
            return client.send(httpRequest);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Getter
    final private HttpRequest httpRequest;

    @Getter
    final private HttpClientImpl client;

    public HttpRequestTask(HttpRequest httpRequest, HttpClientImpl client) {
        this.httpRequest = httpRequest;
        this.client = client;
    }
}
