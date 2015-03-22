/*
 * Copyright (c) 2015 By Timothy Zhang
 */

package com.zts1993.gse.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by TianShuo on 2015/3/22.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface SegResultProcess {

}
