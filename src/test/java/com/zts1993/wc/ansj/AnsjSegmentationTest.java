/*
 * Copyright (c) 2015 By Timothy Zhang
 */

package com.zts1993.wc.ansj;

import com.zts1993.wc.common.SegmentationFactory;
import org.ansj.domain.Term;

import java.util.List;

/**
 * Created by TianShuo on 2015/3/22.
 */
public class AnsjSegmentationTest {

    private static String demo1="挑战中，共创辉煌";
    private static String demo2="让战士们过一个欢乐祥和的新春佳节。";
    private static String demo3="洁面仪配合洁面深层清洁毛孔 清洁鼻孔面膜碎觉使劲挤才能出一点点皱纹 脸颊毛孔修复的看不见啦 草莓鼻历史遗留问题没辙 脸和脖子差不多颜色的皮肤才是健康的 长期使用安全健康的比同龄人显小五到十岁 28岁的妹子看看你们的鱼尾纹";
    private static String demo4="让战士们过一个happy new year";

    public static void main(String[] args) {

          testAllSegmentation(demo1);
          testAllSegmentation(demo2);
          testAllSegmentation(demo3);
          testAllSegmentation(demo4);
    }


    public static void testAllSegmentation(String demo){

        List<Term> parse;

        System.out.println("Try to Parse : "+ demo);

        parse = SegmentationFactory.getBaseSegmentation().parse(demo);
        System.out.println("BaseSegmentation : "+parse);

        parse = SegmentationFactory.getToSegmentation().parse(demo);
        System.out.println("ToSegmentation : "+parse);

        parse = SegmentationFactory.getNlpSegmentation().parse(demo);
        System.out.println("NlpSegmentation : "+parse);

        parse = SegmentationFactory.getIndexSegmentation().parse(demo);
        System.out.println("IndexSegmentation : "+parse);

    }
}
