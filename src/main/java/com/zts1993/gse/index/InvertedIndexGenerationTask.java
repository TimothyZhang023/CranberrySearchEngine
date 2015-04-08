/*
 * Copyright (c) 2015 By Timothy Zhang
 */

package com.zts1993.gse.index;

import com.zts1993.gse.bean.HtmlDoc;
import com.zts1993.gse.bean.IndexNotify;
import com.zts1993.gse.html.FetchLocalHtmlFile;
import com.zts1993.gse.html.IFetchHtml;
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

            genIndexFromFile();

        } catch (Exception e) {

            logger.error(e.getMessage());
            e.printStackTrace();
        } finally {
            InvertedIndexThreadSemaphore.incr();
        }

    }


    private void genIndexFromFile() {

        HtmlDoc htmlDoc = getHtmlDoc();

        InvertedIndexTool invertedIndexTool = new InvertedIndexTool();
        invertedIndexTool.addToInvertedIndex(htmlDoc);

    }


    public HtmlDoc getHtmlDoc() {

        IFetchHtml iFetchHtml = new FetchLocalHtmlFile(indexNotify.getHash_key());
        String htmlContent = iFetchHtml.fetch();

        HtmlDoc htmlDoc = new HtmlDoc(indexNotify.getHash_key(), indexNotify.getUrl(), indexNotify.getTitle(), htmlContent);
        htmlDoc.clean();
        htmlDoc.parse();
        htmlDoc.filter();

        return htmlDoc;
    }

}
