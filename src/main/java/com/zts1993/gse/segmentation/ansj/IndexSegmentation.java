/*
 * Copyright (c) 2015 By Timothy Zhang
 */

package com.zts1993.gse.segmentation.ansj;

import com.zts1993.gse.segmentation.ISegmentation;
import org.ansj.domain.Result;
import org.ansj.domain.Term;
import org.ansj.splitWord.analysis.IndexAnalysis;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by TianShuo on 2015/3/22.
 */
public class IndexSegmentation implements ISegmentation {


    @Override
    public List<Term> parse(String input) {

        ArrayList<Term> terms = new ArrayList<>();
        Result parse = IndexAnalysis.parse(input);
        Iterator<Term> iterator = parse.iterator();
        while (iterator.hasNext()) {
            Term next = iterator.next();
            terms.add(next);
        }
        return terms;

    }


}
