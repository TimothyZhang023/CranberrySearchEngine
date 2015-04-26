/*
 * Copyright (c) 2015 By Timothy Zhang
 */

package com.zts1993.gse.webservice;

import com.alibaba.fastjson.JSON;
import com.zts1993.gse.util.Factors;
import com.zts1993.gse.bean.Pagination;
import com.zts1993.gse.bean.QueryResult;
import com.zts1993.gse.bean.HtmlItem;
import com.zts1993.gse.index.InvertedIndexQueryTool;
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

    private int curPage = 1;
    private int pageSize = Factors.PageSize;

    long timeQueryStart = System.currentTimeMillis();

    private static final Logger logger = LogManager.getLogger("QueryApi");

    // The Java method will process HTTP GET requests
    @GET
    // The Java method will produce content identified by the MIME Media type "text/plain"
    @Produces("application/json")
    public String getQueryResult(@Context UriInfo ui, @PathParam("keyword") String keyword) {

        logger.info("Query words: " + keyword);

        String jsonRes;

        getCurrentPage(ui);

        InvertedIndexQueryTool invertedIndexQueryTool = new InvertedIndexQueryTool(keyword);

        invertedIndexQueryTool.divide();
        invertedIndexQueryTool.preQueryProcess();

//        Set<String> keyWordsSet = invertedIndexQueryTool.getQueryWordsSet();
        long totalResultCount = invertedIndexQueryTool.getTotalResultCount();


        Pagination pagination = new Pagination(pageSize, (int) totalResultCount);
        pagination.setCurPage(curPage);

        invertedIndexQueryTool.processQuery(pagination.getStart(), pagination.getEnd());

        ArrayList<HtmlItem> htmlItems = invertedIndexQueryTool.getHtmlItems();


        long timeSpend = System.currentTimeMillis() - timeQueryStart;


        QueryResult queryResult = new QueryResult(keyword, totalResultCount, timeSpend, pagination, htmlItems);
        jsonRes = JSON.toJSONString(queryResult);


        return jsonRes;
    }

    private void getCurrentPage(UriInfo ui) {
        MultivaluedMap<String, String> queryParams = ui.getQueryParameters();
        if (queryParams.containsKey("p")) {
            curPage = Integer.valueOf(queryParams.getFirst("p"));
        }
    }

}
