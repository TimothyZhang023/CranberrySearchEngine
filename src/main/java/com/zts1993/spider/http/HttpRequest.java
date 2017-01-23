/*
 * Copyright (c) 2017 By Timothy Zhang
 */

package com.zts1993.spider.http;

import com.google.common.net.MediaType;
import com.zts1993.spider.util.Timeout;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.imageio.ImageIO;
import java.awt.image.RenderedImage;
import java.io.IOException;
import java.io.InputStream;

/**
 * GSE Component
 * Created by TimothyZhang on 2017/1/5.
 */
@ToString
public class HttpRequest {

    private static final int DEFAULT_COPY_BUF = 4096;

    @Getter
    private final HttpClientImpl httpClient;


    /**
     * callback when processing request
     */
    @Getter
    @Setter
    protected HttpRequestCallback channelCallback;

    @Getter
    @Setter
    HttpSecurityContext securityContext;

    @Getter
    @Setter
    protected CookieSet cookies;

    @Getter
    private HttpURL httpUrl;

    private ByteBuf body = Unpooled.buffer(0);

    private ByteBufAllocator alloc = ByteBufAllocator.DEFAULT;

    @Getter
    @Setter
    private boolean compress = false;

    @Getter
    @Setter
    private HttpMethod method = HttpMethod.GET;

    @Setter
    private HttpVersion httpVersion = HttpVersion.HTTP_1_1;


    public HttpRequest(HttpClientImpl httpClient, HttpURL httpUrl) {
        this.httpClient = httpClient;
        this.httpUrl = httpUrl;
    }

    @Setter
    private volatile io.netty.handler.codec.http.HttpRequest nettyHttpRequest;

    @Setter
    private String fakeIp = null;

    @Getter
    @Setter
    private HttpResponsePromise promise = null;

    public synchronized io.netty.handler.codec.http.HttpRequest getNettyHttpRequest() {
        if (nettyHttpRequest == null) {
            nettyHttpRequest = new DefaultFullHttpRequest(httpVersion, method, httpUrl.getUri().toASCIIString(), body);
            nettyHttpRequest.headers().set(HttpHeaderNames.HOST, httpUrl.getHost());
            nettyHttpRequest.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);

            if (fakeIp != null) {
                nettyHttpRequest.headers().set("X-Forwarded-For", fakeIp);
                nettyHttpRequest.headers().set("X-Real-IP", fakeIp);
            }
        }

        return nettyHttpRequest;
    }


    /*
        optional args
     */
    @Setter
    Timeout timeout = new Timeout();  //default 2s


    public HttpHeaders addHeader(CharSequence name, Object value) {
        return getNettyHttpRequest().headers().set(name, value);
    }


    public void setBody(Object o, MediaType contentType) throws IOException {
        if (o instanceof CharSequence) {
            CharSequence seq = (CharSequence) o;
            setBody(seq.toString().getBytes(CharsetUtil.UTF_8), contentType);
        } else if (o instanceof byte[]) {
            byte[] b = (byte[]) o;
            ByteBuf buffer = alloc.buffer(b.length).writeBytes(b);
            setBody(buffer, contentType);
        } else if (o instanceof ByteBuf) {
            body = (ByteBuf) o;
            addHeader(HttpHeaderNames.EXPECT, HttpHeaderValues.CONTINUE); //http100
            addHeader(HttpHeaderNames.CONTENT_LENGTH, (long) body.readableBytes());
            addHeader(HttpHeaderNames.CONTENT_TYPE, contentType);
        } else if (o instanceof InputStream) {
            ByteBuf buf = newByteBuf();
            try (ByteBufOutputStream out = new ByteBufOutputStream(buf)) {
                try (InputStream in = (InputStream) o) {
                    final byte[] buffer = new byte[DEFAULT_COPY_BUF];
                    for (; ; ) {
                        int byteCount = in.read(buffer, 0, buffer.length);
                        if (byteCount <= 0) {
                            break;
                        } else {
                            out.write(buffer, 0, byteCount);
                        }
                    }
                }
            }
            setBody(buf, contentType);
        } else if (o instanceof RenderedImage) {
            ByteBuf buf = newByteBuf();
            try (ByteBufOutputStream out = new ByteBufOutputStream(buf)) {
                String type = contentType.subtype();
                if ("jpeg".equals(type)) {
                    type = "jpg";
                }
                ImageIO.write((RenderedImage) o, type, out);
            }
            setBody(buf, contentType);
        } else {
            //todo
            throw new IllegalArgumentException();
        }
    }

    protected ByteBuf newByteBuf() {
        return alloc.buffer();
    }


}
