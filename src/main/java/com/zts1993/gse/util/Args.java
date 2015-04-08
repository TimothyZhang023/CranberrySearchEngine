/*
 * Copyright (c) 2015 By Timothy Zhang
 */

package com.zts1993.gse.util;

import java.util.Hashtable;

/**
 * Created by TianShuo on 2015/4/8.
 */
public class Args {

    private static String[] args;

    private static Hashtable<String, String> settings;

    public static String[] getArgs() {
        return args;
    }

    public static void setArgs(String[] args) {
        Args.args = args;
        settings = new Hashtable<String, String>();

        for (int i = 0; i < args.length; i++) {
            if (i % 2 == 0 && i + 1 < args.length) {
                settings.put(args[i].toUpperCase(), args[i + 1].toUpperCase());
                System.out.println(String.format("key:%s,value:%s",args[i], args[i + 1]));
            }
        }
            }

    public static String getArgs(String key) {
        if (settings.containsKey("-" + key.toUpperCase())) {
            return settings.get("-" + key.toUpperCase());
        }

        return "";
    }


}
