/*
 * Copyright (c) 2017 By Timothy Zhang
 */

package com.zts1993.spider.pipe;

import com.zts1993.spider.task.TaskImpl;

/**
 * GSE Component
 * Created by TimothyZhang on 2017/1/9.
 */
public interface PipeUnit {

    void procTask(TaskImpl task);

}
