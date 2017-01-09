/*
 * Copyright (c) 2017 By Timothy Zhang
 */

package com.zts1993.spider.task;

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
            return httpRequest.send();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Getter
    final private GseHttpRequest httpRequest;

    public HttpRequestTask(GseHttpRequest httpRequest) {
        this.httpRequest = httpRequest;
    }
}
