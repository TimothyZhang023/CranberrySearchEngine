/*
 * Copyright (c) 2015 By Timothy Zhang
 */

package com.zts1993.gse.webservice;

import com.zts1993.gse.bean.URLInfo;
import com.zts1993.gse.util.DivideQuery;
import com.zts1993.gse.util.MergeResult;
import com.zts1993.gse.util.Pager;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import redis.clients.jedis.Tuple;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;
import java.util.*;

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

        long startMili;// 当前时间对应的毫秒数
        long endMili;
        startMili = System.currentTimeMillis();

        MultivaluedMap<String, String> queryParams = ui.getQueryParameters();
        logger.info(queryParams);

        MultivaluedMap<String, String> pathParams = ui.getPathParameters();
        logger.info(pathParams);

        DivideQuery divideQuery = new DivideQuery(keyword);
        MergeResult mergeResult = new MergeResult(divideQuery.divide());
//        ArrayList<URLInfo> urlInfoArrayList = mergeResult.queryResult();
        ArrayList<Tuple> urlHashIds = mergeResult.queryResultKeys();


        int curPage = 1;
        int pageSize = 40;
        int totalRow = urlHashIds.size();

        if (queryParams.containsKey("p")) {
            curPage = Integer.valueOf(queryParams.getFirst("p"));
        }

        Pager pager = new Pager(curPage, pageSize, totalRow);
        pager.setCurPage(curPage);

        logger.info("Pager Info:" + pager.toString());

        endMili = System.currentTimeMillis();

        StringBuilder resStringBuilder = new StringBuilder(String.format("Query %s with %s Result in %s ms :\n", keyword, totalRow, endMili - startMili));

        ArrayList<URLInfo> urlInfoArrayList=new ArrayList<URLInfo>();

        for(Tuple urlHashId:urlHashIds){
            urlInfoArrayList.add(URLInfo.getURLInfoByHash(urlHashId));
        }

        Collections.sort(urlInfoArrayList);

        int i = 0;
        for (Iterator<URLInfo> iterator = urlInfoArrayList.iterator(); iterator.hasNext() && i < pager.getStart() + pageSize; i++) {
            URLInfo urlInfo = iterator.next();
            if (i > pager.getStart()) {
                resStringBuilder.append("\n");
                resStringBuilder.append(urlInfo.toString());


            }

        }




        resStringBuilder.append(String.format("\nCurrent page %s with total %s pages", pager.getCurPage(),pager.getTotalPage()));

        return resStringBuilder.toString();
    }


}
