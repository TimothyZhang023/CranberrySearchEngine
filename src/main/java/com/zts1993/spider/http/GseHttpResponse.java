/*
 * Copyright (c) 2017 By Timothy Zhang
 */

package com.zts1993.spider.http;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/**
 * GSE Component
 * Created by TimothyZhang on 2017/1/5.
 */
@RequiredArgsConstructor
public class GseHttpResponse {

    @Getter
     private final GseHttpRequest request;

    @Getter
    private final String content;





}
