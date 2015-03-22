/*
 * Copyright (c) 2015 By Timothy Zhang
 */

package com.zts1993.gse.filter;

import org.ansj.domain.Term;

import java.util.Iterator;
import java.util.List;

/**
 * Created by TianShuo on 2015/3/22.
 */
public class TermFilter {

    public static List<Term> process(List<Term> termList) {

        return removeSpace(termList);

    }

    public static List<Term> removeSpace(List<Term> termList) {
        Term term;
        Iterator<Term> termIterator = termList.iterator();
        while (termIterator.hasNext()) {
            term = termIterator.next();
            if (term.getRealName().trim().equals("")) {
                termIterator.remove();
//                continue;
            }
            // System.out.println(term.getRealName());
        }

        return termList;
    }


}
