/*
 * Copyright (c) 2017 By Timothy Zhang
 */

package com.zts1993.spider.http;

/**
 * GSE Component
 * Created by TimothyZhang on 2017/1/19.
 */
public class State<T> {


    private final Class<T> type;
    private final StateCode name;
    private final T state;

    State(Class<T> type, StateCode name, T state) {
        this.type = type;
        this.name = name;
        this.state = state;
    }

    public Class<T> type() {
        return type;
    }

    public String name() {
        return name.name();
    }

    public StateCode StateCode() {
        return name;
    }

    public T get() {
        return state;
    }

    @Override
    public String toString() {
        return name();
    }



}
