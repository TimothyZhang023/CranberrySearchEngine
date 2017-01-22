/*
 * Copyright (c) 2017 By Timothy Zhang
 */

package com.zts1993.spider.http;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * GSE Component
 * Created by TimothyZhang on 2017/1/5.
 */
@RequiredArgsConstructor
public class HttpResponse {

    @Getter
    private final HttpRequest request;

    @Getter
    private final String content;


}
