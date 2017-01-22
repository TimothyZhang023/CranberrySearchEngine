/*
 * Copyright (c) 2017 By Timothy Zhang
 */

package com.zts1993.spider.http;

import lombok.Getter;
import lombok.Setter;

/**
 * GSE Component
 * Created by TimothyZhang on 2017/1/22.
 */
public class HttpSecurityContext {

    @Setter
    @Getter
    String name = null;

    @Setter
    @Getter
    String password = null;


}
