/*
 * Copyright (c) 2017 By Timothy Zhang
 */

package com.zts1993.spider.component;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import io.netty.handler.codec.base64.Base64;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.util.CharsetUtil;
import lombok.AllArgsConstructor;

/**
 * GSE Component
 * Created by TimothyZhang on 2017/1/5.
 */
@AllArgsConstructor
public class HttpBasicAuthHandler extends ChannelOutboundHandlerAdapter {

    private String username;
    private String password;

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        if (msg instanceof HttpRequest) {
            addBasicAuthHeader((HttpRequest) msg);
        }

        ctx.write(msg, promise);
    }

    private void addBasicAuthHeader(HttpRequest request) {
        final String auth = Base64.encode(Unpooled.copiedBuffer(username + ":" + password, CharsetUtil.UTF_8))
                .toString(CharsetUtil.UTF_8);

        request.headers().add("authorization", "Basic " + auth);
    }
}
