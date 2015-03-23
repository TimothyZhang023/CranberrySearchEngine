/*
 * Copyright (c) 2015 By Timothy Zhang
 */

package com.zts1993.gse.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class Configuration {

    /**
     * 读取properties文件
     */
    private Properties propertie;

    /**
     * 初始化Configuration类
     */
    public Configuration() {
        propertie = new Properties();
        try {
            propertie.load(getClass().getClassLoader().getResourceAsStream("configure.properties"));
        } catch (FileNotFoundException ex) {
            System.out.println("文件路径错误或者文件不存在");
            ex.printStackTrace();
        } catch (IOException ex) {
            System.out.println("装载文件失败!");
            ex.printStackTrace();
        }
    }

    /**
     * 重载函数，得到key的值
     *
     * @param key 取得其值的键
     * @return key的值
     */
    public String getValue(String key) {
        if (propertie.containsKey(key)) {
            String property = propertie.getProperty(key);//得到某一属性的值
            return property;
        } else
            return "";
    }


    public static void main(String args[]) {
    }

}
