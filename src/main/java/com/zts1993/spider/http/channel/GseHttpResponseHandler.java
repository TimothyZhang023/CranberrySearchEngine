/*
 * Copyright (c) 2017 By Timothy Zhang
 */

package com.zts1993.spider.http.channel;

import com.zts1993.spider.http.GseHttpClientImpl;
import com.zts1993.spider.http.GseHttpRequest;
import com.zts1993.spider.http.GseHttpResponse;
import com.zts1993.spider.util.HtmlUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpUtil;
import io.netty.util.CharsetUtil;
import io.netty.util.concurrent.Promise;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;

/**
 * GSE Component
 * Created by TimothyZhang on 2017/1/5.
 */
@Slf4j
public class GseHttpResponseHandler extends SimpleChannelInboundHandler<FullHttpResponse> {

    private static final CharSequence HTTP_HEADER_LOCATION = "Location";


    protected final GseHttpClientImpl client;

    protected final GseHttpRequest request;

    protected Promise<GseHttpResponse> promise;

    public GseHttpResponseHandler(GseHttpRequest request) {
        this.client = request.getHttpClient();
        this.request = request;
        this.promise = request.getPromise().getNettyPromise();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error(request.toString(), cause);
        ctx.close();
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        super.channelUnregistered(ctx);
    }


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpResponse response) throws Exception {
        if (log.isDebugEnabled()) {
            log.debug(
                    "Received " + response.status().code() + " for " + this.request.getMethod().name() + " "
                            + this.request.getUri());
        }

        if (response.status().equals(HttpResponseStatus.MOVED_PERMANENTLY)
                || response.status().equals(HttpResponseStatus.TEMPORARY_REDIRECT)
                || response.status().equals(HttpResponseStatus.FOUND)
                ) {
            if (response.headers().contains(HTTP_HEADER_LOCATION)) {
                //TODO
//                this.request.updateUri(new URI(response.headers().get(HTTP_HEADER_LOCATION)));
//                GseHttpResponsePromise send = this.request.send();
//                this.promise = send.getNettyPromise();
//                this.request.setPromise(send);
//
//                // Closing the connection which handled the previous request.
//                ctx.close();
                if (log.isDebugEnabled()) {
                    log.debug("redirect for " + this.request.getHttpRequest().uri() + " to " +
                            response.headers().get(HTTP_HEADER_LOCATION));
                }

            } else {
                this.promise.setFailure(new Exception("Missing Location header on redirect"));
            }

            return;
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

                Charset charset = null;
                CharSequence charsetCharSequence = HttpUtil.getCharsetAsString(response);
                if (charsetCharSequence != null) {
                    try {
                        charset = Charset.forName(charsetCharSequence.toString());
                    } catch (UnsupportedCharsetException unsupportedException) {

                    }
                }


                if (charset == null) {
                    String s = HtmlUtil.detectCharset(response.content().toString(Charset.defaultCharset()));
                    if (s != null) {
                        charset = Charset.forName(s);
                    }
                }


                if (charset == null) {
                    charset = Charset.defaultCharset();
                }

//                String res = response.content().toString();

                String res = response.content().toString(charset);
//                String res = new String(ByteBufUtil.getBytes(response.content()),"GBK");
//                String res = new String(response.content().);
//                log.info(res);
//                log.info(response.toString());

                GseHttpResponse gseHttpResponse = new GseHttpResponse(request, res);
                this.promise.setSuccess(gseHttpResponse);

                if (request.getChannelCallback() != null) {
                    request.getChannelCallback().onSuccess(gseHttpResponse);
                }

                //channelCallback
            }
            // Catches both parsed EtcdExceptions and parsing exceptions
            catch (Exception e) {
                this.promise.setFailure(e);
            }

        }


    }
}
