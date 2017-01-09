/*
 * Copyright (c) 2017 By Timothy Zhang
 */

package com.zts1993.spider.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * GSE Component
 * Created by TimothyZhang on 2017/1/9.
 */
public class HtmlUtil {

    public static String detectCharset(String input) {
        Pattern pattern = Pattern.compile("charset=\"?([\\w\\d-]+)\"?;?", Pattern.CASE_INSENSITIVE);
        if (input != null && !input.equals("")) {
            Matcher matcher = pattern.matcher(input);
            if (matcher.find()) {
                return matcher.group(1);
            }
        }
        return null;
    }
}
