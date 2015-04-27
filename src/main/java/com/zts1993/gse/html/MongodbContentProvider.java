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

    private String url="";
    private String content="";
    private String title="";
    private String docId;

    public MongodbContentProvider(String docId) {
        this.docId = docId;
        preProcess();
    }

    public void preProcess(){
        MongoCollection<Document> html_detail = MongoDB.getMongoCollection("html", "html_detail");
        FindIterable<Document> it = html_detail.find(new BasicDBObject("docId", docId));

        Iterator<Document> iterator = it.iterator();
        if (iterator.hasNext()) {
            Document doc = iterator.next();
            content = doc.get("content").toString();
            url = doc.get("url").toString();
        }
        title=new HtmlParser().getHtmlTitle(content);
    }

    @Override
    public String fetchUrl() {
        return url;
    }

    @Override
    public String fetchHtml() {
        return content;
    }

    @Override
    public String fetchText() {
        return new HtmlParser().html2SimpleText(content);
    }

    @Override
    public String fetchTitle() {
        return title;
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
