/*
 * Copyright (c) 2015 By Timothy Zhang
 */

package com.zts1993.gse.segmentation.ansj;

import com.zts1993.gse.filter.TermFilter;
import com.zts1993.gse.segmentation.util.ISegmentation;
import org.ansj.domain.Term;
import org.ansj.splitWord.analysis.NlpAnalysis;

import java.util.List;

/**
 * Created by TianShuo on 2015/3/22.
 */
public class NlpSegmentation implements ISegmentation {


    @Override
    public List<Term> parse(String input) {
        return TermFilter.process(NlpAnalysis.parse(input));
    }


}
