/*
 * Copyright (c) 2015 By Timothy Zhang
 */

package com.zts1993.gse.bean;

/**
 * Created by TianShuo on 2015/4/1.
 */
public class WordFreq {

    private String word;

    private int count;

    public WordFreq(String word) {
        this.word = word;
        this.count = 0;
    }

    public String getWord() {
        return word;
    }

    public int setIncr() {
        return count++;
    }

    public int getCount() {
        return count;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof WordFreq)) return false;

        WordFreq wordFreq = (WordFreq) o;

        if (word != null ? !word.equals(wordFreq.word) : wordFreq.word != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return word != null ? word.hashCode() : 0;
    }


    @Override
    public String toString() {
        return "WordFreq{" +
                "word='" + word + '\'' +
                ", count=" + count +
                '}';
    }
}
