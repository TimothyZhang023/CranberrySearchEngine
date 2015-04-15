/*
 * Copyright (c) 2015 By Timothy Zhang
 */

package com.zts1993.gse.segmentation.filter;

import org.ansj.domain.Term;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * Created by TianShuo on 2015/3/22.
 */
public class TermFilterForAnsj {

    private int weight;
    private List<Term> termListInput;


    public TermFilterForAnsj(List<Term> termListInput, int weight) {
        this.termListInput = termListInput;
        this.weight = weight;
    }

    private HashMap<String, Integer> wordFreqMap = new HashMap<String, Integer>();
    private int wordCount=0;

    public TermFilterForAnsj process() {

        Term term;
        String currentWord;
        Iterator<Term> termIterator = termListInput.iterator();
        StopWordsFilter stopWordsFilter = new StopWordsFilter();

        while (termIterator.hasNext()) {
            term = termIterator.next();
            currentWord = term.getRealName().trim().toLowerCase();

            if (currentWord.equals("")) {
                continue;
            }

            wordCount++;

            if (stopWordsFilter.isPuncOrStopWords(currentWord)) {
                continue;
            }

            if (wordFreqMap.containsKey(currentWord)) {
                int newCount = wordFreqMap.get(currentWord) + weight;
                wordFreqMap.put(currentWord, newCount);
            } else {
                wordFreqMap.put(currentWord, weight);
            }


        }

        return this;
    }


    public HashMap<String, Integer> getWordFreqMap() {
        if(wordFreqMap.size()==0){
            process();
        }
        return wordFreqMap;
    }

    public int getWordCount() {
        if(wordCount==0){
            process();
        }
        return wordCount;
    }


    @Override
    public String toString() {
        return "TermFilterForAnsj{" +
                "weight=" + weight +
                ", wordFreqMap=" + wordFreqMap +
                ", wordCount=" + wordCount +
                '}';
    }




}
