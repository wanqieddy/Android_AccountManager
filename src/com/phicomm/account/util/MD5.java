package com.phicomm.account.util;

import java.security.MessageDigest;

import android.util.Base64;

public class MD5 {
    /**
     * @param password
     * @return
     */
    public static String getMD5Passwored(String password) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            // BASE64Encoder b64 = new BASE64Encoder();
            byte[] se = Base64.encode(md5.digest(password.getBytes("UTF-8")),
                    Base64.DEFAULT);
            return new String(se);
        } catch (Exception e) {
            e.printStackTrace();
            return "ERROR";
        }
    }
}
