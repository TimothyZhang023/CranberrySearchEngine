/*
 * Copyright (c) 2015 By Timothy Zhang
 */

package com.zts1993.gse.segmentation.common;


import com.zts1993.gse.segmentation.ansj.BaseSegmentation;
import com.zts1993.gse.segmentation.ansj.IndexSegmentation;
import com.zts1993.gse.segmentation.ansj.NlpSegmentation;
import com.zts1993.gse.segmentation.ansj.ToSegmentation;

/**
 * Created by TianShuo on 2015/3/22.
 */
public class SegmentationFactory {

    public static ISegmentation getDefaultSegmentation() {
        return getNlpSegmentation();
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
