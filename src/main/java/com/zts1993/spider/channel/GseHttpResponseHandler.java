/*
 * Copyright (c) 2017 By Timothy Zhang
 */

package com.zts1993.spider.channel;

import com.zts1993.spider.GseHttpClient;
import com.zts1993.spider.GseHttpRequest;
import com.zts1993.spider.GseHttpResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.util.CharsetUtil;
import io.netty.util.concurrent.Promise;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.URI;
import java.nio.charset.Charset;

/**
 * GSE Component
 * Created by TimothyZhang on 2017/1/5.
 */
@Slf4j
public class GseHttpResponseHandler extends SimpleChannelInboundHandler<FullHttpResponse> {

    private static final CharSequence HTTP_HEADER_LOCATION = "Location";


    protected final GseHttpClient client;

    protected final GseHttpRequest request;

    protected Promise<GseHttpResponse> promise;

    public GseHttpResponseHandler(GseHttpClient client, GseHttpRequest request) {
        this.client = client;
        this.request = request;
        this.promise = request.getPromise().getNettyPromise();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("" , cause);
        super.exceptionCaught(ctx, cause);
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        super.channelUnregistered(ctx);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpResponse response) throws Exception {
        if (log.isDebugEnabled()) {
            log.debug(
                    "Received " + response.getStatus().code() + " for " + this.request.getMethod().name() + " "
                            + this.request.getUri());
        }

        if (response.getStatus().equals(HttpResponseStatus.MOVED_PERMANENTLY)
                || response.getStatus().equals(HttpResponseStatus.TEMPORARY_REDIRECT)) {
            if (response.headers().contains(HTTP_HEADER_LOCATION)) {
                this.request.updateUri(new URI(response.headers().get(HTTP_HEADER_LOCATION)));
                this.client.send(this.request);
                // Closing the connection which handled the previous request.
                ctx.close();
                if (log.isDebugEnabled()) {
                    log.debug("redirect for " + this.request.getHttpRequest().uri() + " to " +
                            response.headers().get(HTTP_HEADER_LOCATION));
                }

            } else {
                this.promise.setFailure(new Exception("Missing Location header on redirect"));
            }
        }


        if (response.status().equals(HttpResponseStatus.UNAUTHORIZED)) {
            this.promise.setFailure(new RuntimeException(response.content().toString(CharsetUtil.UTF_8)));
        } else {

            if (!response.content().isReadable()) {
                // If connection was accepted maybe response has to be waited for
                if (response.status().equals(HttpResponseStatus.OK)
                        || response.status().equals(HttpResponseStatus.ACCEPTED)
                        || response.status().equals(HttpResponseStatus.CREATED)) {
//                    this.client.connect(this.request);
                    return;
                }
                this.promise.setFailure(new IOException("Content was not readable. HTTP Status: "
                        + response.status()));
            }

            try {
                String res = response.content().toString(Charset.defaultCharset());
//                log.info(res);
//                log.info(response.toString());

                GseHttpResponse gseHttpResponse = new GseHttpResponse();
                gseHttpResponse.setContent(res);
                this.promise.setSuccess(gseHttpResponse);
            }
            // Catches both parsed EtcdExceptions and parsing exceptions
            catch (Exception e) {
                this.promise.setFailure(e);
            }

        }


    }
}
