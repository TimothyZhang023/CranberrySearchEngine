/*
 * Copyright (c) 2017 By Timothy Zhang
 */

package com.zts1993.spider;


import com.zts1993.spider.http.*;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.concurrent.ExecutionException;

/**
 * Created by zts1993 on 2016/11/26.
 */
@Slf4j
public class M {
    public static void main(String[] args) throws IOException, URISyntaxException, InterruptedException, ExecutionException {

        HttpClientImpl httpClient = new HttpClient();

        httpClient.init();



        HttpResponsePromise f = httpClient.send(new HttpRequest(httpClient, new HttpURL("http://cqt.njtech.edu.cn/")));
//        HttpResponsePromise f2 = httpClient.send(new HttpRequest(httpClient, new URI("http://www.hao123.com/")));

        HttpResponse httpResponse = f.get();
//        HttpResponse gseHttpResponse1 = f2.get();

        System.out.println(httpResponse.getContent());

        httpClient.close();
    }
}
