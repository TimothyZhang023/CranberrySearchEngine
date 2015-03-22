/*
 * Copyright (c) 2015 By Timothy Zhang
 */

package com.zts1993.gse;

import com.zts1993.gse.db.RedisClientTest;
import com.zts1993.gse.encrypt.StringEncryptTest;
import com.zts1993.gse.util.ConfigurationTest;
import com.zts1993.wc.ansj.AnsjSegmentationTest;

/**
 * Created by TianShuo on 2015/3/22.
 */
public class MainTest {

    public static void main(String[] args) {

        try{
            RedisClientTest.main(args);
        }catch (Exception ex){
            System.out.println("RedisClient Test failed");
        }

        try{
            StringEncryptTest.main(args);
        }catch (Exception ex){
            System.out.println("StringEncrypt Test failed");

        }

        try{
            ConfigurationTest.main(args);
        }catch (Exception ex){
            System.out.println("Configuration Test failed");
        }


        try{
            AnsjSegmentationTest.main(args);
        }catch (Exception ex){
            System.out.println("AnsjSegmentation Test failed");
        }

    }

}
