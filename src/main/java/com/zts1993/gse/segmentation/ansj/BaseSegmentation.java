/*
 * Copyright (c) 2015 By Timothy Zhang
 */

package com.zts1993.gse.segmentation.ansj;

import com.zts1993.gse.segmentation.ISegmentation;
import org.ansj.domain.Term;
import org.ansj.splitWord.analysis.BaseAnalysis;

import java.util.List;

/**
 * Created by TianShuo on 2015/3/22.
 */
public class BaseSegmentation implements ISegmentation {

    @Override
    public List<Term> parse(String input) {
        return BaseAnalysis.parse(input);
    }

}
