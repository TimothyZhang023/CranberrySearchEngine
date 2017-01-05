/*
 * Copyright (c) 2017 By Timothy Zhang
 */

package com.zts1993.spider;

import com.sun.istack.internal.NotNull;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.stream.ChunkedWriteHandler;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;


@Slf4j
@RequiredArgsConstructor
public class GseHttpClient implements GseHttpClientImpl {

    @NotNull
    final private GseHttpClientConfig clientConfig;

    @Getter
    private EventLoopGroup eventLoopGroup;

    @Getter
    private Bootstrap bootstrap;


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
                        p.addLast("aggregate", new HttpObjectAggregator(1024 * 100));
                    }
                });

    }


    public ChannelFuture send(GseHttpRequest gseHttpRequest) throws InterruptedException {

        ChannelFuture f = getBootstrap().connect(gseHttpRequest.getRequestUri().getHost(), 80).sync();
        String msg = "";
        // 发送http请求


        GseHttpRequest request = new GseHttpRequest(this, gseHttpRequest.getRequestUri());


        f.channel().pipeline().addLast("handler", new GseHttpResponseHandler(this, request));
        f.channel().write(request.getHttpRequest());
        f.channel().flush();

        return f;
    }


    @Override
    public void close() throws IOException {
        log.debug("closing GseHttpClient");
        eventLoopGroup.shutdownGracefully();

    }
}
