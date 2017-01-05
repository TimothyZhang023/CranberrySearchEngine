/*
 * Copyright (c) 2017 By Timothy Zhang
 */

package com.zts1993.spider.util;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.concurrent.TimeUnit;

/**
 * GSE Component
 * Created by TimothyZhang on 2017/1/5.
 */
@AllArgsConstructor
@NoArgsConstructor
public class Timeout {

    @Setter
    long timeout = 2L;

    @Setter
    TimeUnit timeoutUnit = TimeUnit.SECONDS;


}
