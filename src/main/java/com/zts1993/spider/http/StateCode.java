/*
 * Copyright (c) 2017 By Timothy Zhang
 */

package com.zts1993.spider.http;

/**
 * GSE Component
 * Created by TimothyZhang on 2017/1/20.
 */
public enum StateCode {
    /**
     * A connection has not been made yet.
     */
    Connecting,
    /**
     * A connection has been made.
     */
    Connected,
    /**
     * About to send a request
     */
    SendRequest,
    /**
     * The request has been sent.
     */
    AwaitingResponse,
    /**
     * The response headers have been received, but the response body has not
     * yet (or there will not be one).
     */
    HeadersReceived,
    /**
     * One chunk of content has been received - not necessarily the entire
     * response, but some content.
     */
    ContentReceived,
    /**
     * The response was a 300-307 HTTP redirect and the redirect is being
     * followed. Note this event will only be seen if the HttpClient is set to
     * follow redirects - otherwise, you will just see the redirect headers and
     * body.
     */
    Redirect,
    /**
     * The entire content of the response has arrived.
     */
    FullContentReceived,
    /**
     * The connection was closed.
     */
    Closed,
    /**
     * Similar to FullContentReceived, this event gives you a Netty
     * FullHttpRequest with the entire response.
     */
    Finished,
    /**
     * An exception was thrown.
     */
    Error,
    /**
     * Called when a timeout occurs.
     */
    Timeout,
    /**
     * The call was cancelled; useful for cleaning up resources.
     */
    Cancelled;


    public boolean isResponseComplete() {
        switch (this) {
            case AwaitingResponse:
            case Connected:
            case Connecting:
            case ContentReceived:
            case HeadersReceived:
            case SendRequest:
                return false;
        }
        return true;
    }

    public boolean isFailure() {
        switch (this) {
            case Cancelled:
            case Closed:
            case Error:
            case Timeout:
                return true;
        }
        return false;
    }





}
