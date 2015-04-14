/*
 * Copyright (c) 2015 By Timothy Zhang
 */

package com.zts1993.gse.segmentation.filter;

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
         return vector.contains(words);
    }


    public void initStopWordsList() {
        try {
            vector = new Vector<String>();

            // read file content from file
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("stopwords.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));

            String str;

            while ((str = br.readLine()) != null) {
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


}
