/*
 * Copyright (c) 2015 By Timothy Zhang
 */

package com.zts1993.gse.webservice;

import com.zts1993.gse.bean.URLInfo;
import com.zts1993.gse.util.DivideQuery;
import com.zts1993.gse.util.MergeResult;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;
import java.util.ArrayList;

/**
 * Created by TianShuo on 2015/3/23.
 */
// The Java class will be hosted at the URI path "/helloworld"
@Path("/query/{keyword}")
public class QueryApi {

    private static final Logger logger = LogManager.getLogger("QueryApi");

    // The Java method will process HTTP GET requests
    @GET
    // The Java method will produce content identified by the MIME Media type "text/plain"
    @Produces("text/plain")
    public String getQueryResult(@Context UriInfo ui, @PathParam("keyword") String keyword) {

        MultivaluedMap<String, String> queryParams = ui.getQueryParameters();
        logger.info(queryParams);

        MultivaluedMap<String, String> pathParams = ui.getPathParameters();
        logger.info(pathParams);

        DivideQuery divideQuery = new DivideQuery(keyword);
        MergeResult mergeResult = new MergeResult(divideQuery.divide());
        ArrayList<URLInfo> urlInfoArrayList=mergeResult.queryResult();

        System.out.println(urlInfoArrayList);


        StringBuilder resStringBuilder=new StringBuilder(String.format("Query %s Result :\n",keyword) );
        for (URLInfo urlInfo:urlInfoArrayList){
            resStringBuilder.append("\n");
            resStringBuilder.append(urlInfo.toString());
        }

        return resStringBuilder.toString();
    }



}
