/*
 * Copyright (c) 2015 By Timothy Zhang
 */

package com.zts1993.gse.encrypt;

/**
 * Created by TianShuo on 2015/3/21.
 */
public class StringEncryptTest {

    public static void main(String args[]) {
        StringEncrypt stringEncrypt = new StringEncrypt(StringEncrypt.SHA_256);
        String s;
        s = stringEncrypt.encrypt("timothy");
        System.out.println(s);

        stringEncrypt.setEncMethod("MD5");
        s = stringEncrypt.encrypt("timothy");
        System.out.println(s);

        stringEncrypt.setEncMethod("SHA-1");
        s = stringEncrypt.encrypt("timothy");
        System.out.println(s);

        stringEncrypt.setEncMethod("SHA-512");
        s = stringEncrypt.encrypt("timothy");
        System.out.println(s);
    }
}
