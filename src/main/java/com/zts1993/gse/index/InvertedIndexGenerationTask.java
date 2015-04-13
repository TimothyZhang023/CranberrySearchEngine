/*
 * Copyright (c) 2015 By Timothy Zhang
 */

package com.zts1993.gse.index;

import com.zts1993.gse.bean.HtmlDoc;
import com.zts1993.gse.bean.IndexNotify;
import com.zts1993.gse.html.HtmlContentProvider;
import com.zts1993.gse.html.IHtmlContentProvider;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * Created by TianShuo on 2015/3/29.
 */
public class InvertedIndexGenerationTask implements Runnable {

    private static final Logger logger = LogManager.getLogger("InvertedIndexGenerationTask");


    private IndexNotify indexNotify;


    public InvertedIndexGenerationTask(IndexNotify indexNotify) {
        this.indexNotify = indexNotify;
    }

    public void run() {

        try {
            InvertedIndexThreadSemaphore.decr();

            generateIndex();

        } catch (Exception e) {

            logger.error(e.getMessage());
            e.printStackTrace();
        } finally {
            InvertedIndexThreadSemaphore.incr();
        }

    }


    private void generateIndex() {

        HtmlDoc htmlDoc = getHtmlDoc();

        InvertedIndexGenerationTool invertedIndexGenerationTool = new InvertedIndexGenerationTool();
        invertedIndexGenerationTool.addToInvertedIndex(htmlDoc);

    }


    public HtmlDoc getHtmlDoc() {

        IHtmlContentProvider iHtmlContentProvider = HtmlContentProvider.getHtmlContentProvider(indexNotify.getHash_key());

        String htmlContent = iHtmlContentProvider.fetchText();
        String title = iHtmlContentProvider.fetchTitle();

        HtmlDoc htmlDoc = new HtmlDoc(indexNotify.getHash_key(), indexNotify.getUrl(), title, htmlContent);
        htmlDoc.parse();
        htmlDoc.filter();

        return htmlDoc;
    }

}
