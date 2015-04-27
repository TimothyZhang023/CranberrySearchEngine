/*
 * Copyright (c) 2015 By Timothy Zhang
 */

package com.zts1993.gse.webservice;

import com.alibaba.fastjson.JSON;
import com.zts1993.gse.bean.HtmlItem;
import com.zts1993.gse.html.HtmlContentProvider;
import com.zts1993.gse.html.IHtmlContentProvider;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

/**
 * Created by zts1993 on 15-4-25.
 */
@Path("/webcache/{docId}")
public class WebCacheApi {


    // The Java method will process HTTP GET requests
    @GET
    // The Java method will produce content identified by the MIME Media type "text/plain"
    @Produces("application/json")
    public String getQueryResult(@Context UriInfo ui, @PathParam("docId") String docId) {

        IHtmlContentProvider iHtmlContentProvider = HtmlContentProvider.getHtmlContentProvider(docId);

        String content = iHtmlContentProvider.fetchHtml();
        String title = iHtmlContentProvider.fetchTitle();
        String url = iHtmlContentProvider.fetchUrl();
        HtmlItem htmlItem = new HtmlItem(docId, url, title, content, 0);

        String jsonRes = JSON.toJSONString(htmlItem);
        return jsonRes;

    }
    
}
