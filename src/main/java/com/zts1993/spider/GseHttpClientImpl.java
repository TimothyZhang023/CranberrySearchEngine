/*
 * Copyright (c) 2017 By Timothy Zhang
 */

package com.zts1993.spider;

import io.netty.channel.ChannelFuture;

import java.io.Closeable;

/**
 * GSE Component
 * Created by TimothyZhang on 2017/1/5.
 */
interface GseHttpClientImpl extends Closeable {

    ChannelFuture send(GseHttpRequest gseHttpRequest) throws InterruptedException;

}
