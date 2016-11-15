/*
 * Copyright (c) 2015 By Timothy Zhang
 */

package com.zts1993.gse.bean;

import lombok.*;

/**
 * Created by TianShuo on 2015/3/31.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class IndexNotify {
    private String url;
    private String title;
    private String hash_key;
    private String storage_type;
    private String page_encoding;
    private String queue_time;
}
