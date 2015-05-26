/*
 * Copyright (c) 2015 By Timothy Zhang
 */

package com.zts1993.gse.segmentation.filter;

import com.zts1993.gse.segmentation.ISegmentation;
import com.zts1993.gse.segmentation.SegmentationFactory;
import org.ansj.domain.Term;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * Created by TianShuo on 2015/3/22.
 */
public class TermFilterForAnsj implements TermFilter {

    private int weight;
    private List<Term> termListInput;

    private HashMap<String, Integer> wordFreqMap = new HashMap<String, Integer>();
    private int wordCount = -1;



    public TermFilterForAnsj(String stringInput, int weight) {
        this.termListInput = SegmentationFactory.getIndexSegmentation().parse(stringInput);
        this.weight = weight;
    }

    public TermFilterForAnsj(String stringInput, int weight, ISegmentation segmentation) {
        this.termListInput = segmentation.parse(stringInput);
        this.weight = weight;
    }

    public TermFilterForAnsj(List<Term> termListInput, int weight) {
        this.termListInput = termListInput;
        this.weight = weight;
    }


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


    @Override
    public HashMap<String, Integer> getWordFreqMap() {
        if (wordCount == -1) {
            process();
        }
        return wordFreqMap;
    }

    @Override
    public int getWordCount() {
        if (wordCount == -1) {
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
