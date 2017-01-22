/*
 * Copyright (c) 2017 By Timothy Zhang
 */

package com.zts1993.spider.http;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * GSE Component
 * Created by TimothyZhang on 2017/1/21.
 */
public class URLBuilder {

    private String link;

    public URLBuilder(String base) {
        this.link = base;
    }

    public URI build() throws URISyntaxException {
        return new URI(link);
    }





}
