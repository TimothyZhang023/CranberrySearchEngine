/*
 * Copyright (c) 2015 By Timothy Zhang
 */

package com.zts1993.gse.html;


import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.zts1993.gse.db.mongodb.MongoDB;
import org.bson.Document;

import java.util.Iterator;
import java.util.Set;

/**
 * Created by TianShuo on 2015/4/8.
 */
public class MongodbContentProvider implements IHtmlContentProvider {

    private String content;
    private String docId;

    public MongodbContentProvider(String docId) {
        this.docId = docId;
    }

    @Override
    public String fetchHtml() {

        content = "";

        MongoCollection<Document> html_detail = MongoDB.getMongoCollection("html", "html_detail");
        FindIterable<Document> it = html_detail.find(new BasicDBObject("docId", docId));

        Iterator<Document> iterator = it.iterator();
        if (iterator.hasNext()) {
            Document doc = iterator.next();
            content = doc.get("content").toString();
        }

        return content;
    }

    @Override
    public String fetchText() {
        if (this.content == null) {
            this.fetchHtml();
        }
        return new HtmlParser().html2SimpleText(content);
    }


    @Override
    public String fetchTitle() {
        if (this.content == null) {
            this.fetchHtml();
        }
        return new HtmlParser().getHtmlTitle(content);
    }


    @Override
    public String fetchMarkedText(Set<String> st) {

        String content = this.fetchText();
        for (String s : st) {
            content = content.replaceAll(s, "<em>" + s + "</em>");
        }


        return content;
    }


}
