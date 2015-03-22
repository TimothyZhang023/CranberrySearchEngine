package com.zts1993.gse.util;

/**
 * Created by TianShuo on 2015/3/21.
 */
public class ConfigurationTest {


    public static void main(String[] args) {
        Configuration conf = new Configuration();

        String rawsPath = conf.getValue("RAWSPATH");
        String dictPath = conf.getValue("DICTIONARYPATH");
        String mysqlPath = conf.getValue("MYSQLLIBPATH");

        System.out.println("the rawsPath is " + rawsPath);
        System.out.println("the dictPath is " + dictPath);
        System.out.println("the mysqlPath is " + mysqlPath);
    }

}
