/*
 * Copyright (c) 2015 By Timothy Zhang
 */

package com.zts1993.gse.filter;

import java.io.*;
import java.util.Vector;

/**
 * Created by TianShuo on 2015/4/8.
 */
public class StopWordsFilter {

    private static Vector<String> vector;

    public StopWordsFilter() {
        if (vector == null) {
            initStopWordsList();
        }

    }

    public boolean isPuncOrStopWords(String words) {
        if (vector.contains(words)) {
             return true;
        }
        return false;
    }


    public void initStopWordsList() {
        try {
            vector = new Vector<String>();

            // read file content from file
            StringBuffer sb = new StringBuffer("");

            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("stopwords.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));

            String str = null;

            while ((str = br.readLine()) != null) {
                sb.append(str + "/n");

                vector.add(str);
            }

            br.close();
            inputStream.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        StopWordsFilter stopWordsFilter = new StopWordsFilter();
        System.out.println(stopWordsFilter.isPuncOrStopWords("？"));
    }

}
