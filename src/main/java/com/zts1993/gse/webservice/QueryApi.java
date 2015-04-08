/*
 * Copyright (c) 2015 By Timothy Zhang
 */

package com.zts1993.gse.webservice;

import com.zts1993.gse.bean.URLInfo;
import com.zts1993.gse.index.InvertedIndex;
import com.zts1993.gse.util.Pager;
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
import java.util.Collections;
import java.util.List;

/**
 * Created by TianShuo on 2015/3/23.
 */
// The Java class will be hosted at the URI path "/helloworld"
@Path("/query/{keyword}")
public class QueryApi {

    private int curPage = 1;
    private int pageSize = 40;

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
        if (queryParams.containsKey("p")) {
            curPage = Integer.valueOf(queryParams.getFirst("p"));
        }



        InvertedIndex invertedIndex = new InvertedIndex();
        ArrayList<URLInfo> urlInfoArrayList = invertedIndex.query(keyword);


        endMili = System.currentTimeMillis();


        Collections.sort(urlInfoArrayList);

        //render page
        StringBuilder resStringBuilder = getRenderedResult(keyword, startMili, endMili, pageSize, urlInfoArrayList.size(), curPage, urlInfoArrayList);
        return resStringBuilder.toString();
    }

    private StringBuilder getRenderedResult(String keyword, long startMili, long endMili, int pageSize, int totalRow, int curPage, ArrayList<URLInfo> urlInfoArrayList) {

        Pager pager = new Pager(curPage, pageSize, totalRow);
        pager.setCurPage(curPage);

        logger.info("Pager Info:" + pager.toString());

        StringBuilder resStringBuilder = new StringBuilder(String.format("Query %s with %s Result in %s ms :\n", keyword, totalRow, endMili - startMili));


        List<URLInfo> urlInfoList=   urlInfoArrayList.subList(pager.getStart(), pager.getStart() + pageSize);

        for (URLInfo urlInfo : urlInfoList) {
            resStringBuilder.append("\n");
            resStringBuilder.append(urlInfo.toString());
        }
        resStringBuilder.append(String.format("\nCurrent page %s with total %s pages", pager.getCurPage(), pager.getTotalPage()));
        return resStringBuilder;
    }


}
