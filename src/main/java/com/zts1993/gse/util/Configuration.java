/*
 * Copyright (c) 2015 By Timothy Zhang
 */

package com.zts1993.gse.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class Configuration {

    /**
     * 读取properties文件
     */
    private Properties properties;

    /**
     * 初始化Configuration类
     */
    public Configuration() {
        properties = new Properties();
        try {
            properties.load(getClass().getClassLoader().getResourceAsStream("configure.properties"));
        } catch (FileNotFoundException ex) {
            System.out.println("No such file configure.properties");
            ex.printStackTrace();
        } catch (IOException ex) {
            System.out.println("load configure.properties failed!");
            ex.printStackTrace();
        }
    }

    /**
     * get value by key
     *
     * @param key String
     * @return value String
     */
    public String getValue(String key) {
        if (properties.containsKey(key)) {
            return properties.getProperty(key);
        } else
            return "";
    }


}
