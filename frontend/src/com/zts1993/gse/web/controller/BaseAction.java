/*
 * Copyright (c) 2015 By Timothy Zhang
 */

package com.zts1993.gse.web.controller;


import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.interceptor.ServletResponseAware;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by TianShuo on 2015/1/10.
 */
public class BaseAction extends ActionSupport implements ServletResponseAware{

    private HttpServletResponse response;

    public BaseAction() {

    }


    @Override
    public void setServletResponse(HttpServletResponse httpServletResponse) {
        this.response=httpServletResponse;
    }






}
