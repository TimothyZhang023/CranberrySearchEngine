package com.zts1993.gse.encrypt;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by TianShuo on 2015/3/21.
 */
public class StringEncrypt {

    public final static String MD5 = "MD5";
    public final static String SHA_256 = "SHA-256";
    public final static String SHA_512 = "SHA-512";
    public final static String SHA_384 = "SHA-384";
    public final static String SHA_1 = "SHA-1";

    /**
     * 对字符串加密 Hash ,加密算法使用MD5,SHA-1,SHA-256,默认使用SHA-256
     *
     * @param strSrc  要加密的字符串
     * @param encName 加密类型
     * @return
     */

    private String encMethod;

    public StringEncrypt(String encMethod) {
        this.encMethod = encMethod;
    }

    public StringEncrypt() {
        setEncMethod("SHA-256");
    }

    public String getEncMethod() {
        return encMethod;
    }

    public void setEncMethod(String encMethod) {
        this.encMethod = encMethod;
    }

    public String encrypt(String strSrc) {
        MessageDigest md = null;
        String strDes = null;

        byte[] bt = strSrc.getBytes();
        try {

            md = MessageDigest.getInstance(getEncMethod());
            md.update(bt);
            strDes = bytes2Hex(md.digest()); // to HexString
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
        return strDes;
    }

    public String bytes2Hex(byte[] bts) {
        String des = "";
        String tmp = null;
        for (int i = 0; i < bts.length; i++) {
            tmp = (Integer.toHexString(bts[i] & 0xFF));
            if (tmp.length() == 1) {
                des += "0";
            }
            des += tmp;
        }
        return des;
    }

    public static void main(String args[]) {

    }
}