/*
 * Copyright (c) 2017 By Timothy Zhang
 */

package com.zts1993.spider;


import com.zts1993.spider.http.*;
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

        GseHttpResponsePromise f = gseHttpClient.send(new GseHttpRequest(gseHttpClient, new GseURL("http://cqt.njtech.edu.cn/")));
//        GseHttpResponsePromise f2 = gseHttpClient.send(new GseHttpRequest(gseHttpClient, new URI("http://www.hao123.com/")));

        GseHttpResponse gseHttpResponse = f.get();
//        GseHttpResponse gseHttpResponse1 = f2.get();

        System.out.println(gseHttpResponse.getContent());

        gseHttpClient.close();
    }
}
