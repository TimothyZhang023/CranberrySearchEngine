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
        List<Term> termList1;
        termList1 = removeSpace(termList);
        termList1 = removeKeyword(termList1);
        return termList1;

    }

    private static List<Term> removeSpace(List<Term> termList) {
        Term term;
        Iterator<Term> termIterator = termList.iterator();
        StopWordsFilter stopWordsFilter = new StopWordsFilter();
        while (termIterator.hasNext()) {
            term = termIterator.next();
            if (term.getRealName().trim().equals("")) {
                termIterator.remove();
                continue;
            }
            if (stopWordsFilter.isPuncOrStopWords(term.getRealName().trim())) {
                termIterator.remove();
                continue;
            }
//            System.out.println(String.format("%s : %s",term.getRealName(),term.score()));
        }

        return termList;
    }

    private static List<Term> removeKeyword(List<Term> termList) {


        return termList;
    }


}
