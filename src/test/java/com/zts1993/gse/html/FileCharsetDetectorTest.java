/*
 * Copyright (c) 2015 By Timothy Zhang
 */

package com.zts1993.gse.html;

import com.zts1993.gse.util.ConfigurationUtil;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.junit.Test;

import java.io.File;

public class FileCharsetDetectorTest {

    private static final Logger logger = LogManager.getLogger("FileCharsetDetectorTest");

    @Test
    public void testGetFileEncode() throws Exception {

        File root = new File(ConfigurationUtil.getValue("HTMLPATH"));
        File[] fs = root.listFiles();

        if (fs != null) {
            logger.debug("fetchHtml files to be processed : " + fs.length);

            for (File f : fs) {
                if (!f.isDirectory()) {
                    try {

                        String path = f.getAbsolutePath();
                        logger.debug(String.format("Path: %s Encode %s", path, FileCharsetDetector.getFileEncode(path)));

                    } catch (Exception e) {
                        logger.error(e.getMessage());
                        logger.error(e.getStackTrace());
                    }

                }


            }
        }


    }
}