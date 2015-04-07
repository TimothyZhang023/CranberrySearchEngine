/*
 * Copyright (c) 2015 By Timothy Zhang
 */

package com.zts1993.gse.util;

import com.zts1993.gse.filter.TermFilter;
import com.zts1993.gse.segmentation.common.SegmentationFactory;
import com.zts1993.gse.segmentation.common.ISegmentation;
import org.ansj.domain.Term;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Created by TianShuo on 2015/3/28.
 */
public class DivideQuery {

    private static final Logger logger = LogManager.getLogger("DivideQuery");

    private String queryKey;


    public DivideQuery(String queryKey) {
        this.queryKey = queryKey;
    }


    public Set<String> divide() {

        StringBuilder sb= new StringBuilder("Segmentation of :");
        sb.append(queryKey);

        ISegmentation iSegmentation = SegmentationFactory.getDefaultSegmentation();
        List<Term> termList = iSegmentation.parse(queryKey);
        termList = TermFilter.process(termList);

        Set<String> qyeryKeySet = new HashSet<String>();
        Term term;
        Iterator<Term> termIterator = termList.iterator();
        while (termIterator.hasNext()) {
            term = termIterator.next();
            if (term.getRealName().trim().equals("")) {
                termIterator.remove();
//              continue;
            } else {
                sb.append(" ");
                sb.append(term.getRealName());
                qyeryKeySet.add(term.getRealName());
            }

        }

        logger.info(sb.toString());


        return qyeryKeySet;


    }


}
