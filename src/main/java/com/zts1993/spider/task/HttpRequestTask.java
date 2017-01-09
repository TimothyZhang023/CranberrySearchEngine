/*
 * Copyright (c) 2017 By Timothy Zhang
 */

package com.zts1993.spider.task;

import com.zts1993.spider.http.GseHttpClient;
import com.zts1993.spider.http.GseHttpRequest;
import com.zts1993.spider.http.GseHttpResponsePromise;
import lombok.Getter;
import lombok.ToString;


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
            httpRequest.prepareRequest();
            return gseHttpClient.send(httpRequest);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Getter
    final private GseHttpClient gseHttpClient;

    @Getter
    final private GseHttpRequest httpRequest;

    public HttpRequestTask(GseHttpClient gseHttpClient, GseHttpRequest httpRequest) {
        this.gseHttpClient = gseHttpClient;
        this.httpRequest = httpRequest;
    }
}
