/*
 * Copyright (c) 2015 By Timothy Zhang
 */

package com.zts1993.gse.webservice;

import com.sun.jersey.api.container.httpserver.HttpServerFactory;
import com.sun.net.httpserver.HttpServer;
import lombok.extern.slf4j.Slf4j;


import java.io.IOException;

/**
 * Created by TianShuo on 2015/3/28.
 */
@Slf4j
public class RestService {



    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServerFactory.create("http://localhost:9998/");
        server.start();

        log.info("Api Service Starting");
        log.info("Visit: http://localhost:9998/");
        //server.stop(0);
        //log.info("Server stopped");
    }
}
