/*
 * Copyright (c) 2017 By Timothy Zhang
 */

package com.zts1993.spider.pipe;

import com.zts1993.spider.task.TaskImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * GSE Component
 * Created by TimothyZhang on 2017/1/9.
 */
public class PipeChan {

    private List<PipeUnit> pipeUnits = new ArrayList<>();

    public PipeChan() {
    }

    public PipeChan addLast(PipeUnit pipeUnit) {
        pipeUnits.add(pipeUnit);
        return this;
    }

    public PipeChan addFirst(PipeUnit pipeUnit) {
        pipeUnits.add(0, pipeUnit);
        return this;
    }

    public PipeChan add(int index, PipeUnit pipeUnit) {
        pipeUnits.add(index, pipeUnit);
        return this;
    }

    public void procTask(TaskImpl task) {

        for (PipeUnit pipeUnit : pipeUnits) {
            pipeUnit.procTask(task);
        }

    }


}
