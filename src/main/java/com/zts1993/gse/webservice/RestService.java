/*
 * Copyright (c) 2015 By Timothy Zhang
 */

package com.zts1993.gse.webservice;

import com.sun.jersey.api.container.httpserver.HttpServerFactory;
import com.sun.net.httpserver.HttpServer;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.IOException;

/**
 * Created by TianShuo on 2015/3/28.
 */
public class RestService {

    private static final Logger logger = LogManager.getLogger("RestService");

    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServerFactory.create("http://localhost:9998/");
        server.start();

        logger.info("Server running");
        logger.info("Visit: http://localhost:9998/");
        //server.stop(0);
        //logger.info("Server stopped");
    }
}
