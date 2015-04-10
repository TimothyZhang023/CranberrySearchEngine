/*
 * Copyright (c) 2015 By Timothy Zhang
 */

package com.zts1993.gse.html;

import org.junit.Test;

public class HtmlParserTest {

    private String html = "<!DOCTYPE html>" +
            "<html lang=\"zh-CN\">" +
            "<head>" +
            "    <meta charset=\"utf-8\">" +
            "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">" +
            "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">" +
            "    <title>Cranberry Search Engine</title>" +
            "    <meta name=\"description\" content=\"\">" +
            "    <meta name=\"author\" content=\"Timothy Zhang\">" +
            "    <!-- Bootstrap core CSS -->" +
            "    <link href=\"http://cdn.bootcss.com/bootstrap/3.3.4/css/bootstrap.min.css\" rel=\"stylesheet\">" +
            "    <!-- Custom styles for this template -->" +
            "    <!-- Just for debugging purposes. Don't actually copy these 2 lines! -->" +
            "    <!--[if lt IE 9]><script src=\"../../assets/js/ie8-responsive-file-warning.js\"></script><![endif]-->" +
            "    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->" +
            "    <!--[if lt IE 9]>" +
            "      <script src=\"http://cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js\"></script>" +
            "      <script src=\"http://cdn.bootcss.com/respond.js/1.4.2/respond.min.js\"></script>" +
            "    <![endif]-->" +
            "</head>" +
            "<body>" +
            "<footer class=\"footer\">" +
            "    <div class=\"container\">" +
            "        <p class=\"pull-right\"><a href=\"#\">Back to top</a></p>" +
            "        <p>&copy; 2015 By Timothy &middot; <a href=\"#\">Privacy</a> &middot; <a href=\"#\">Terms</a></p>" +
            "    </div>" +
            "</footer>" +
            "<!-- Bootstrap core JavaScript" +
            " ================================================== -->" +
            "<!-- Placed at the end of the document so the pages load faster -->" +
            "<script src=\"http://cdn.bootcss.com/jquery/1.11.2/jquery.min.js\"></script>" +
            "<script src=\"http://cdn.bootcss.com/bootstrap/3.3.4/js/bootstrap.min.js\"></script>" +
            "</body>" +
            "</html>";

    @Test
    public void testHtml2Text() throws Exception {

    }

    @Test
    public void testGetHtmlTitle() throws Exception {

        HtmlParser htmlParser = new HtmlParser();
        String title = htmlParser.getHtmlTitle(html);
        System.out.println(title);
    }

    @Test
    public void testHtml2SimpleText() throws Exception {

    }

    @Test
    public void testEscapeQueryChars() throws Exception {

    }
}