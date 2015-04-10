/*
 * Copyright (c) 2015 By Timothy Zhang
 */

package com.zts1993.gse.html;

import java.util.Set;

/**
 * Created by TianShuo on 2015/4/8.
 */
public interface IHtmlContentProvider {

    String fetchHtml();

    String fetchText();

    String fetchTitle();

    String fetchMarkedText(Set<String> st);


}
