/*
 * Copyright (c) 2015 By Timothy Zhang
 */

package com.zts1993.gse.html;


import com.zts1993.gse.util.ConfigurationUtil;

import java.io.*;
import java.util.Set;

/**
 * Created by TianShuo on 2015/4/8.
 */
public class LocalFsHtmlContentProvider implements IHtmlContentProvider {

    private String filePath;

    public LocalFsHtmlContentProvider(String docId) {
        this.filePath = ConfigurationUtil.getValue("HTMLPATH") + docId + ".html";
    }

    @Override
    public String fetchHtml() {

        BufferedReader br;
        StringBuilder buffer = new StringBuilder();
        String line;

        try {
//            System.out.println(filePath);
            String fileEncode = FileCharsetDetector.getFileEncode(filePath);
            br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), fileEncode));
            while ((line = br.readLine()) != null) {
                buffer.append(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return buffer.toString();
    }

    @Override
    public String fetchText() {
        return new HtmlParser().html2SimpleText(this.fetchHtml());
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
