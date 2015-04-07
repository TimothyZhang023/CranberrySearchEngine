/*
 * Copyright (c) 2015 By Timothy Zhang
 */

package com.zts1993.gse.bean;

/**
 * Created by TianShuo on 2015/3/22.
 */
public class URLInfo implements Comparable<Object> {

    private String hash;
    private String url;
    private String date;
    private double rank;
    private int wordCount;

    private int hits = 1;


    public URLInfo(String hash, String url, String date, double rank, int wordCount) {
        this.hash = hash;
        this.url = url;
        this.date = date;
        this.rank = rank;
        this.wordCount = wordCount;
    }

    public void addHit() {
        this.hits++;
    }

    public int getHits() {
        return hits;
    }

    public double getRank() {
        return rank;
    }

    public double addRank(double rank1) {
        this.rank = rank1 + rank;
        return this.rank;
    }



    public void setRank(double rank) {
        this.rank = rank;
    }

    public String getHash() {
        return hash;
    }

    @Override
    public String toString() {
        return "URLInfo{" +
                "hash='" + hash + '\'' +
                ", url='" + url + '\'' +
                ", date='" + date + '\'' +
                ", rank='" + rank + '\'' +
                ", wordCount='" + wordCount + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof URLInfo)) return false;

        URLInfo urlInfo = (URLInfo) o;

        return hash.equals(urlInfo.hash);

    }

    @Override
    public int hashCode() {
        int result = hash.hashCode();
        result = 31 * result;
        return result;
    }


    @Override
    public int compareTo(Object o) {
        if (this == o) {
            return 0;
        } else if (o != null && o instanceof URLInfo) {
            URLInfo u = (URLInfo) o;

            if (rank > u.rank) {
                return -1;
            } else if (rank == u.rank) {
                return 0;
            } else {
                return 1;
            }
        } else {
            return -1;
        }
    }
}
