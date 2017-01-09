/*
 * Copyright (c) 2017 By Timothy Zhang
 */

package com.zts1993.spider.http;

import com.sun.istack.internal.NotNull;
import com.zts1993.spider.channel.GseHttpResponseHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.util.concurrent.DefaultPromise;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;


@Slf4j
@RequiredArgsConstructor
public class GseHttpClient implements GseHttpClientImpl {

    public GseHttpClient() {
        this(new GseHttpClientConfig());
    }

    @NotNull
    final private GseHttpClientConfig clientConfig;

    @Getter
    private EventLoopGroup eventLoopGroup;

    @Getter
    private Bootstrap bootstrap;

    private volatile boolean _closed = false;


    public void init() {
        eventLoopGroup = new NioEventLoopGroup();
        bootstrap = new Bootstrap()
                .group(eventLoopGroup)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                .option(ChannelOption.TCP_NODELAY, true)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 2000)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    public void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline p = ch.pipeline();
                        p.addLast("codec", new HttpClientCodec());
                        p.addLast("chunkedWriter", new ChunkedWriteHandler());
                        p.addLast("aggregate", new HttpObjectAggregator(1024 * 1000));
                    }
                });

    }


    public GseHttpResponsePromise send(GseHttpRequest request) throws InterruptedException {

        ChannelFuture f = bootstrap.connect(request.getHost(), request.getPort()).sync();
        Channel c = f.channel();

        request.setPromise(new GseHttpResponsePromise());
        request.getPromise().attachNettyPromise(new DefaultPromise<>(c.eventLoop()));

        c.pipeline().addLast("handler", new GseHttpResponseHandler(this, request));
        c.write(request.getHttpRequest());
        c.flush();

        f.addListener((ChannelFutureListener) future -> {

        });

        c.closeFuture().addListener((ChannelFutureListener) future -> {
            if (log.isDebugEnabled()) {
                log.debug("Connection closed for request " + request.getMethod().name() + " " + request.getUri());
            }
        });


        return request.getPromise();
    }


    @Override
    public void close() throws IOException {

        if (_closed) {
            return;
        }

        log.debug("closing GseHttpClient");
        synchronized (this) {
            if (_closed) {
                return;
            }

            this._closed = true;
            eventLoopGroup.shutdownGracefully();
        }

    }
}
