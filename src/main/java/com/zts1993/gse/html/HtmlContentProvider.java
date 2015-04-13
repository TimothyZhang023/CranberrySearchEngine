/*
 * Copyright (c) 2015 By Timothy Zhang
 */

package com.zts1993.gse.html;

/**
 * Created by TianShuo on 2015/4/12.
 */
public class HtmlContentProvider {

    public static IHtmlContentProvider getHtmlContentProvider(String docId) {
        return new MongodbContentProvider(docId);
    }
}
