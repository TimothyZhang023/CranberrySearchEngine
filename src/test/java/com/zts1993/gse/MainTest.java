/*
 * Copyright (c) 2015 By Timothy Zhang
 */

package com.zts1993.gse;

import com.zts1993.gse.db.RedisClientTest;
import com.zts1993.gse.encrypt.StringEncryptTest;
import com.zts1993.gse.util.ConfigurationTest;


import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * Created by TianShuo on 2015/3/22.
 */
@Slf4j
public class MainTest {



    @Test
    public void test() {

        log.info("============================");
        log.info("   MainTest Test Start     ");
        log.info("============================");


        String[] args = new String[0];

        try {
            RedisClientTest.main(args);
        } catch (Exception ex) {
            log.info("RedisClient Test failed");
        }

        try {
            StringEncryptTest.main(args);
        } catch (Exception ex) {
            log.info("StringEncrypt Test failed");

        }

        try {
            ConfigurationTest.main(args);
        } catch (Exception ex) {
            log.info("Configuration Test failed");
        }




//        try {
//            InvertedIndexTest.main(args);
//        } catch (Exception ex) {
//            log.info(ex.getMessage());
//            log.info(ex.getStackTrace());
//            log.info("InvertedIndex Test failed");
//        }


        log.info("============================");
        log.info("MainTest Test End");
        log.info("============================");

    }

    public static void main(String[] args) {
        MainTest mainTest=new MainTest();
        mainTest.test();

    }

}
