/*
 * Copyright (c) 2017 By Timothy Zhang
 */

package com.zts1993.spider.http;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * GSE Component
 * Created by TimothyZhang on 2017/1/5.
 */
@Getter
@Setter
@Builder
public class GseHttpClientConfig {

    private int threads = 8;

    private int timeout = 2000;




}
