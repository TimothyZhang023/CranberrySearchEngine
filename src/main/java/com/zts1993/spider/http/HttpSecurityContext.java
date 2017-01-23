/*
 * Copyright (c) 2017 By Timothy Zhang
 */

package com.zts1993.spider.http;

import io.netty.handler.ssl.SslContext;
import lombok.Getter;
import lombok.Setter;

/**
 * GSE Component
 * Created by TimothyZhang on 2017/1/22.
 */
public class HttpSecurityContext {

    @Setter
    @Getter
    private String name = null;

    @Setter
    @Getter
    private String password = null;

    @Setter
    @Getter
    private SslContext context;


}
