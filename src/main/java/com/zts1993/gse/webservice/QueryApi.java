/*
 * Copyright (c) 2015 By Timothy Zhang
 */

package com.zts1993.gse.webservice;

import com.zts1993.gse.bean.Pager;
import com.zts1993.gse.bean.URLInfo;
import com.zts1993.gse.html.FetchLocalHtmlFile;
import com.zts1993.gse.html.HtmlParser;
import com.zts1993.gse.html.IFetchHtml;
import com.zts1993.gse.index.InvertedIndexTool;
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


        MultivaluedMap<String, String> queryParams = ui.getQueryParameters();
        if (queryParams.containsKey("p")) {
            curPage = Integer.valueOf(queryParams.getFirst("p"));
        }


        InvertedIndexTool invertedIndexTool = new InvertedIndexTool();
        ArrayList<URLInfo> urlInfoArrayList = invertedIndexTool.query(keyword);


        //render page
        StringBuilder resStringBuilder = getRenderedResult(keyword, pageSize, curPage, urlInfoArrayList);
        return resStringBuilder.toString();
    }

    private StringBuilder getRenderedResult(String keyword, int pageSize, int curPage, ArrayList<URLInfo> urlInfoArrayList) {
        int totalRow = urlInfoArrayList.size();
        Pager pager = new Pager(pageSize, totalRow);
        pager.setCurPage(curPage);

        logger.info("Pager Info:" + pager.toString());

        StringBuilder resStringBuilder = new StringBuilder(

        );
        resStringBuilder.append( String.format("Query %s with %s Result :\n", keyword, totalRow) );

        List<URLInfo> urlInfoList = urlInfoArrayList.subList(pager.getStart(), pager.getEnd());

        for (URLInfo urlInfo : urlInfoList) {
            resStringBuilder.append("\n\n");
            resStringBuilder.append(urlInfo.toString());

            IFetchHtml iFetchHtml = new FetchLocalHtmlFile(urlInfo.getDocId());
            resStringBuilder.append(new HtmlParser().html2SimpleText(iFetchHtml.fetch()));

        }
        resStringBuilder.append(String.format("\nCurrent page %s with total %s pages", pager.getCurPage(), pager.getTotalPage()));
        return resStringBuilder;
    }


}
