/*
 * Copyright (c) 2017 By Timothy Zhang
 */

package com.zts1993.spider;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.stream.ChunkedWriteHandler;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.Closeable;
import java.io.IOException;


@Slf4j
public class GseHttpClient implements Closeable {

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


    @Override
    public void close() throws IOException {
        log.debug("closing GseHttpClient");
        eventLoopGroup.shutdownGracefully();

    }
}
