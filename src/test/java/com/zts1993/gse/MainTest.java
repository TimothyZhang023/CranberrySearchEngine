package com.zts1993.gse;

import com.zts1993.gse.db.RedisClientTest;
import com.zts1993.gse.encrypt.StringEncryptTest;
import com.zts1993.gse.util.ConfigurationTest;

/**
 * Created by TianShuo on 2015/3/22.
 */
public class MainTest {

    public static void main(String[] args) {

        try{
            RedisClientTest.main(args);
        }catch (Exception ex){
            System.out.println("RedisClientTest failed");
        }

        try{
            StringEncryptTest.main(args);
        }catch (Exception ex){
            System.out.println("StringEncryptTest failed");

        }

        try{
            ConfigurationTest.main(args);
        }catch (Exception ex){
            System.out.println("RedisConfigurationTestClientTest failed");
        }

    }

}
