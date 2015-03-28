/*
 * Copyright (c) 2015 By Timothy Zhang
 */

package com.zts1993.gse.webservice;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/**
 * Created by TianShuo on 2015/3/23.
 */

@Path("/helloworld2")
public class TestApi {
    // The Java method will process HTTP GET requests
    @GET
    // The Java method will produce content identified by the MIME Media type "text/plain"
    @Produces("text/plain")
    public String getClichedMessage() {
        // Return some cliched textual content
        return "Hello World22";
    }

    @GET @Path("/list")
    @Produces({"application/json", "application/xml"})
    public String getList() {
        return "list";
    }


}
