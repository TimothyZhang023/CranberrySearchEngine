/*
 * Copyright (c) 2017 By Timothy Zhang
 */

package com.zts1993.spider.http.channel;

import com.zts1993.spider.http.HttpResponse;

/**
 * GSE Component
 * Created by TimothyZhang on 2017/1/9.
 */
public interface HttpRequestCallback {

    /**
     * on request complete
     * @param response response
     */
    void onSuccess(HttpResponse response);

    /**
     * on request failed
     * @param why exception
     */
    void onFailed(Throwable why);

}
