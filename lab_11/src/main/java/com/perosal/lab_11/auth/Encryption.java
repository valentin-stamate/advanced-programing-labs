package com.perosal.lab_11.auth;

import org.apache.commons.codec.digest.DigestUtils;

public abstract class Encryption {

    public static String encrypt(String string) {
        return DigestUtils.sha256Hex(string);
    }

}
