/*
 * Copyright (c) 2015 By Timothy Zhang
 */

package com.zts1993.gse.index;

import com.zts1993.gse.bean.HtmlDoc;
import com.zts1993.gse.bean.IndexNotify;
import org.junit.Test;

public class InvertedIndexGenerationTaskTest {

    @Test
    public void testGetSegmentation() throws Exception {
        String url = "http://www.zts1993.com";
        String title = "Timothy Blog";
        String hash_key = "1";
        String storage_type = "local_fs";
        String page_encoding = "utf-8";
        String queue_time = "2014-4-6 14:30:00";
        IndexNotify indexNotify = new IndexNotify(url, title, hash_key, storage_type, page_encoding, queue_time);

        InvertedIndexGenerationTask invertedIndexGenerationTask =new InvertedIndexGenerationTask(indexNotify);
        HtmlDoc htmlDoc= invertedIndexGenerationTask.getHtmlDoc();
        System.out.println(htmlDoc.toString());
    }
}