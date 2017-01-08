/*
 * Copyright (c) 2015 By Timothy Zhang
 */

package com.zts1993.gse.index;

import com.zts1993.gse.bean.HtmlDoc;
import com.zts1993.gse.bean.IndexNotify;
import com.zts1993.gse.html.HtmlContentProvider;
import com.zts1993.gse.html.IHtmlContentProvider;
import lombok.extern.slf4j.Slf4j;


/**
 * Created by TianShuo on 2015/3/29.
 */
@Slf4j
public class InvertedIndexGenerationTask implements Runnable {




    private IndexNotify indexNotify;


    public InvertedIndexGenerationTask(IndexNotify indexNotify) {
        this.indexNotify = indexNotify;
    }

    public void run() {
        try {

            InvertedIndexThreadSemaphore.decr();


            generateIndex();


        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
        } finally {
            InvertedIndexThreadSemaphore.incr();
        }
    }

    private void generateIndex() {
        long taskTimeStart = System.currentTimeMillis();

        HtmlDoc htmlDoc = getHtmlDoc();

        InvertedIndexGenerationTool invertedIndexGenerationTool = new InvertedIndexGenerationTool();
        invertedIndexGenerationTool.addToInvertedIndex(htmlDoc);

        long taskTimeStop = System.currentTimeMillis();

        log.info("Costs " + (taskTimeStop - taskTimeStart) + " ms to process: "+htmlDoc.getUrl());
    }

    public HtmlDoc getHtmlDoc() {

        IHtmlContentProvider iHtmlContentProvider = HtmlContentProvider.getHtmlContentProvider(indexNotify.getHash_key());

        String htmlContent = iHtmlContentProvider.fetchText();
        String title = iHtmlContentProvider.fetchTitle();

        return new HtmlDoc(indexNotify.getHash_key(), indexNotify.getUrl(), title, htmlContent);
    }

}
