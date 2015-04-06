/*
 * Copyright (c) 2015 By Timothy Zhang
 */

package com.zts1993.gse;

import com.zts1993.gse.db.RedisClientTest;
import com.zts1993.gse.encrypt.StringEncryptTest;
import com.zts1993.gse.integration.WordSegIntegrationTest;
import com.zts1993.gse.util.ConfigurationTest;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.junit.Test;

/**
 * Created by TianShuo on 2015/3/22.
 */
public class MainTest {

    private static final Logger logger = LogManager.getLogger("MainTest");

    @Test
    public void test() {

        logger.info("============================");
        logger.info("   MainTest Test Start     ");
        logger.info("============================");


        String[] args = new String[0];

        try {
            RedisClientTest.main(args);
        } catch (Exception ex) {
            logger.info("RedisClient Test failed");
        }

        try {
            StringEncryptTest.main(args);
        } catch (Exception ex) {
            logger.info("StringEncrypt Test failed");

        }

        try {
            ConfigurationTest.main(args);
        } catch (Exception ex) {
            logger.info("Configuration Test failed");
        }



        try {
            WordSegIntegrationTest.main(args);
        } catch (Exception ex) {
            logger.info("WordSegIntegration Test failed");
        }

//        try {
//            InvertedIndexTest.main(args);
//        } catch (Exception ex) {
//            logger.info(ex.getMessage());
//            logger.info(ex.getStackTrace());
//            logger.info("InvertedIndex Test failed");
//        }


        logger.info("============================");
        logger.info("MainTest Test End");
        logger.info("============================");

    }

    public static void main(String[] args) {
        MainTest mainTest=new MainTest();
        mainTest.test();

    }

}
