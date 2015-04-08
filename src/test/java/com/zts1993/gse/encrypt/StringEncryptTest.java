/*
 * Copyright (c) 2015 By Timothy Zhang
 */

package com.zts1993.gse.encrypt;

import com.zts1993.gse.util.StringEncrypt;

/**
 * Created by TianShuo on 2015/3/21.
 */
public class StringEncryptTest {

    public static void main(String args[]) {
        StringEncrypt stringEncrypt = new StringEncrypt(StringEncrypt.SHA_256);
        String s;
        s = stringEncrypt.encrypt("timothy");
        System.out.println(s);

        stringEncrypt.setEncMethod(StringEncrypt.MD5);
        s = stringEncrypt.encrypt("timothy");
        System.out.println(s);

        stringEncrypt.setEncMethod(StringEncrypt.SHA_1);
        s = stringEncrypt.encrypt("timothy");
        System.out.println(s);

        stringEncrypt.setEncMethod(StringEncrypt.SHA_256);
        s = stringEncrypt.encrypt("timothy");
        System.out.println(s);

        stringEncrypt.setEncMethod(StringEncrypt.SHA_384);
        s = stringEncrypt.encrypt("timothy");
        System.out.println(s);

        stringEncrypt.setEncMethod(StringEncrypt.SHA_512);
        s = stringEncrypt.encrypt("timothy");
        System.out.println(s);
    }
}
