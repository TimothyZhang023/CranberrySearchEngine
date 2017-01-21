/*
 * Copyright (c) 2017 By Timothy Zhang
 */

package com.zts1993.spider.http.channel;

import com.zts1993.spider.http.GseHttpResponse;

/**
 * GSE Component
 * Created by TimothyZhang on 2017/1/9.
 */
public interface GseHttpRequestCallback {

    /**
     * on request complete
     * @param response response
     */
    void onSuccess(GseHttpResponse response);

    /**
     * on request failed
     * @param why exception
     */
    void onFailed(Throwable why);

}
