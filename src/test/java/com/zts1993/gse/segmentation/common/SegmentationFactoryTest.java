/*
 * Copyright (c) 2015 By Timothy Zhang
 */

package com.zts1993.gse.segmentation.common;

import com.zts1993.gse.segmentation.SegmentationFactory;
import org.ansj.domain.Term;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class SegmentationFactoryTest {

    final private static Logger logger = LogManager.getLogger("SegmentationFactoryTest");

    String demo;


    List<Term> parse;

    @Before
    public void setUp() throws Exception {

        String demo1 = "挑战中，共创辉煌";
        String demo2 = "让战士们过一个欢乐祥和的新春佳节。";
        String demo3 = "洁面仪配合洁面深层清洁毛孔 清洁鼻孔面膜碎觉使劲挤才能出一点点皱纹 脸颊毛孔修复的看不见啦 草莓鼻历史遗留问题没辙 脸和脖子差不多颜色的皮肤才是健康的 长期使用安全健康的比同龄人显小五到十岁 28岁的妹子看看你们的鱼尾纹";
        String demo4 = "让战士们过一个happy new year";

        this.demo = demo1 + demo2 + demo3 + demo4;
    }

    @After
    public void tearDown() throws Exception {
        logger.info("Segmentation-"+Thread.currentThread().getStackTrace()[2].getMethodName()+ parse.toString());
    }

    @Test
    public void testGetDefaultSegmentation() throws Exception {
        parse = SegmentationFactory.getDefaultSegmentation().parse(demo);

    }

    @Test
    public void testGetBaseSegmentation() throws Exception {
        parse = SegmentationFactory.getBaseSegmentation().parse(demo);

    }

    @Test
    public void testGetIndexSegmentation() throws Exception {
        parse = SegmentationFactory.getIndexSegmentation().parse(demo);

    }

    @Test
    public void testGetNlpSegmentation() throws Exception {
        parse = SegmentationFactory.getNlpSegmentation().parse(demo);

    }

    @Test
    public void testGetToSegmentation() throws Exception {
        parse = SegmentationFactory.getToSegmentation().parse(demo);

    }
}