/*
 * Copyright (c) 2015 By Timothy Zhang
 */

package com.zts1993.wc.common;


import com.zts1993.wc.ansj.*;
import com.zts1993.wc.util.ISegmentation;

/**
 * Created by TianShuo on 2015/3/22.
 */
public class SegmentationFactory {


    public static ISegmentation getDefaultSegmentation() {
        return getIndexSegmentation();
    }


    public static ISegmentation getCseSegmentation() {
        return new CseSegmentation();
    }


    public static ISegmentation getBaseSegmentation() {
        return new BaseSegmentation();
    }


    public static ISegmentation getIndexSegmentation() {
        return new IndexSegmentation();
    }

    public static ISegmentation getNlpSegmentation() {
        return new NlpSegmentation();
    }


    public static ISegmentation getToSegmentation() {
        return new ToSegmentation();
    }


}
