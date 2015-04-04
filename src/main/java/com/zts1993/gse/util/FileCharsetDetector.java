/*
 * Copyright (c) 2015 By Timothy Zhang
 */

package com.zts1993.gse.util;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.mozilla.intl.chardet.HtmlCharsetDetector;
import org.mozilla.intl.chardet.nsDetector;
import org.mozilla.intl.chardet.nsICharsetDetectionObserver;
import org.mozilla.intl.chardet.nsPSMDetector;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;


/**
 * Created by TianShuo on 2015/4/1.
 */
public class FileCharsetDetector {

    private static final Logger logger = LogManager.getLogger("FileCharsetDetector");


    public static String getFileEncode(String fileName) throws IOException {

        // Initalize the nsDetector() ;
        nsDetector det = new nsDetector(nsPSMDetector.ALL);

        // Set an observer...
        // The Notify() will be called when a matching charset is found.

        det.Init(new nsICharsetDetectionObserver() {
            public void Notify(String charset) {
                HtmlCharsetDetector.found = true;
                System.out.println("CHARSET = " + charset);
            }
        });

        BufferedInputStream imp = new BufferedInputStream(new FileInputStream(fileName));

        byte[] buf = new byte[1024];
        int len;
        boolean done = false;
        boolean isAscii = true;
        boolean found = false;

        while ((len = imp.read(buf, 0, buf.length)) != -1) {

            // Check if the stream is only ascii.
            if (isAscii)
                isAscii = det.isAscii(buf, len);

            // DoIt if non-ascii and not done yet.
            if (!isAscii && !done)
                done = det.DoIt(buf, len, false);
        }
        det.DataEnd();

        if (isAscii) {
            System.out.println("CHARSET = ASCII");
            found = true;
        }

        if (!found) {//如果没找到，则找到最可能的那些字符集
            String probs[] = det.getProbableCharsets();

//            for (String prob : probs) {
//                System.out.println("Probable Charset = " + prob);
//
//            }

            return probs[0];
        }

        return "ASCII";

    }


    public static void main(String[] args) {

        File root = new File(ConfigurationUtil.getValue("HTMLPATH"));
        File[] fs = root.listFiles();

        logger.info("Html files to be processed : " + fs.length);


        for (int i = 0; i < fs.length; i++) {


            if (!fs[i].isDirectory()) {
                try {

                    String path = fs[i].getAbsolutePath();

                    logger.info(String.format("Path: %s Encode %s", path, FileCharsetDetector.getFileEncode(path)));


                } catch (Exception e) {
                    logger.error(e.getMessage());
                    logger.error(e.getStackTrace());
                }

            }


        }
    }
}