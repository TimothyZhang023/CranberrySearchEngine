/*
 * Copyright (c) 2015 By Timothy Zhang
 */

package com.zts1993.gse.segmentation.filter;

import com.zts1993.gse.segmentation.SegmentationFactory;
import org.ansj.domain.Term;
import org.junit.Test;

import java.util.List;

public class TermFilterForAnsjTest {


    @Test
    public void testProcess() throws Exception {
        String demo2 = "让战士们过一个欢乐祥和的新春佳节。百度为您找到相关结果约3,350,000个\n" +
                "java过滤内容只保留数字、字母、中文、标点符号|jqueryschool-...\n" +
                "2014年7月9日 - java正则表达式过滤内容只保留数字、字母、中文、标点符号,使用场景如用户留言等操作。。。正则表达式: [^0-9a-zA-Z\\u4e00-\\u9fa5.,,。?“”]+用法: ...\n" +
                "www.jq-school.com/Sh.....  - 百度快照 - 97%好评\n" +
                "删除/清除/过滤标点(所有中英文标点)的正则表达式——D..._红黑联盟\n" +
                "2013年9月22日 - 在进行文本分析的时候我们经常需要过滤掉停用词,标点等,本文给大家介绍一下如何识别并删除文本中的所有标点符号。下面是三个可行的正则表达式方案,童...\n" +
                "www.2cto.com/kf/201309...  - 百度快照 - 64%好评\n" +
                "java正则过滤只保留汉字、字母、数字和常用标点符号-CS..._CSDN论坛\n" +
                "10条回复 - 发帖时间: 2009年12月29日\n" +
                "java正则过滤只保留汉字、字母、数字和常用标点符号 [问题点数:100分,无满意结帖,结帖人maosenmin] 收藏 maosenmin maosenmin maosenmin 本版等级:T3 本版专家分...\n" +
                "bbs.csdn.net/topics/33...  - 百度快照 - 88%好评\n" +
                "用java过滤器解决中文乱码_沙子_新浪博客\n" +
                "用java过滤器解决中文乱码(2012-07-12 13:57:26) 转载▼标签: it 哎,今天终于明白,干什么事都他妈得细心,学java更得如此,Java对大小写要求,标点符号要求真...\n" +
                "blog.sina.com.cn/s/blo...  - 百度快照 - 88%好评\n" +
                "删除/清除/过滤标点(所有中英文标点)的正则表达式——Des..._希赛网\n" +
                "2013年12月12日 - 文章标题:删除/清除/过滤标点(所有中英文标点)的正则表达式——DesktopSearch开发笔记[经验积累]。希赛网JAVA频道是一个专业的JAVA技术平台,着眼于业...\n" +
                "www.educity.cn/java/50...  - 百度快照 - 75%好评";

        List<Term> parse = SegmentationFactory.getNlpSegmentation().parse(demo2);


        TermFilter termFilter = new TermFilterForAnsj(parse, 1).process();

        System.out.println(termFilter.toString());

    }
}