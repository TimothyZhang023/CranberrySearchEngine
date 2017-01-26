/*
 * Copyright (c) 2017 By Timothy Zhang
 */

package com.zts1993.spider.http;

import com.zts1993.spider.http.channel.ExceptionForwardingSslHandler;
import com.zts1993.spider.http.channel.HttpMessageHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpContentDecompressor;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.util.concurrent.DefaultPromise;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.net.ssl.SSLException;
import java.io.IOException;


@Slf4j
public class HttpClient implements HttpClientImpl {

    final private HttpClientConfig clientConfig;


    public HttpClient() {
        this(HttpClientConfig.builder().build());
    }

    public HttpClient(HttpClientConfig clientConfig) {
        this.clientConfig = clientConfig;
    }


    /**
     * setter for external event loop
     */
    @Getter
    @Setter
    private EventLoopGroup eventLoopGroup = null;

    @Getter
    private Bootstrap bootstrap;

    private volatile boolean _closed = false;


    public synchronized void init() {
        if (eventLoopGroup == null) {
            eventLoopGroup = new NioEventLoopGroup(clientConfig.getThreads());
        }

        bootstrap = new Bootstrap()
                .group(eventLoopGroup)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                .option(ChannelOption.TCP_NODELAY, true)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, clientConfig.getTimeout())
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    public void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline p = ch.pipeline();
                        p.addLast("codec", new HttpClientCodec());
//                        p.addLast("decompressor",new HttpContentDecompressor());
                        p.addLast("chunkedWriter", new ChunkedWriteHandler());
                        p.addLast("aggregate", new HttpObjectAggregator(1024 * 1000));
                    }
                });

    }


    public HttpResponsePromise send(HttpRequest request) throws InterruptedException, SSLException {

        ChannelFuture f = bootstrap.connect(request.getHttpUrl().getHost(), request.getHttpUrl().getPort()).sync();
        Channel c = f.channel();

        request.setPromise(new HttpResponsePromise().attachNettyPromise(new DefaultPromise<>(c.eventLoop())));

        if (request.getSecurityContext() != null) {
            SslContext clientContext = request.getSecurityContext().getContext() == null ? SslContextBuilder.forClient()
                    .trustManager(InsecureTrustManagerFactory.INSTANCE).build() : request.getSecurityContext().getContext();
            c.pipeline().addLast("ssl", new ExceptionForwardingSslHandler(
                    clientContext.newEngine(ByteBufAllocator.DEFAULT,
                            request.getHttpUrl().getHost(), request.getHttpUrl().getPort()), request));
        }

        if (request.isCompress()) {
            c.pipeline().addLast("decompressor", new HttpContentDecompressor());
        }

        c.pipeline().addLast("http-codec", new HttpClientCodec());
        c.pipeline().addLast("GseHttpHandler", new HttpMessageHandler(this, request));


        c.write(request.getNettyHttpRequest());
        c.flush();

        f.addListener((ChannelFutureListener) future -> {

        });

        c.closeFuture().addListener((ChannelFutureListener) future -> {
            //close connection
            log.debug("Connection closed for request {} {} ", request.getMethod().name(), request.getHttpUrl().getUri());
        });

        return request.getPromise();
    }


    /**
     * will close event loop
     *
     * @throws IOException from shutdownGracefully()
     */
    @Override
    public void close() throws IOException {

        if (_closed) {
            return;
        }

        log.debug("closing HttpClient");
        synchronized (this) {
            if (_closed) {
                return;
            }

            this._closed = true;
            eventLoopGroup.shutdownGracefully();
        }

    }
}
