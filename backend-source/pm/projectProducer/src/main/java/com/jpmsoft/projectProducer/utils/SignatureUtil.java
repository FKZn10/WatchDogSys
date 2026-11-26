package com.jpmsoft.projectProducer.utils;

import org.apache.commons.lang3.StringUtils;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class SignatureUtil {
    private static final String HMAC_SHA_1 = "HmacSHA1";
    private static final String CHARSET_UTF8 = "UTF-8";
    private static final String KEY = "iU5GSex1N81mzccVtns/3w==";

    public static String signature(String appId, String appKey, String timeStamp) throws Exception{
        String message = appId + appKey + timeStamp;
        return signature(message, appKey);
    }
    /**
     * 生成签名
     *
     * @param message
     * @return
     * @throws Exception
     */
    public static String signature(String message) throws Exception {
        return signature(message, KEY);
    }

    /**
     * 签名
     *
     * @param message 签名的字符串
     * @param key     密钥
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     */
    public static String signature(String message, String key) throws Exception {
        byte[] byteMsg = message.getBytes(CHARSET_UTF8);
        byte[] byteKey = key.getBytes(CHARSET_UTF8);
        SecretKeySpec signKey = new SecretKeySpec(byteKey, HMAC_SHA_1);
        Mac mac = Mac.getInstance(HMAC_SHA_1);
        mac.init(signKey);
        byte[] encodeArray = mac.doFinal(byteMsg);
        return byte2HexString(encodeArray);
    }

    /**
     * 二进制转十六进制
     *
     * @param b
     * @return
     */
    public static String byte2HexString(byte[] b) {
        String r = "";
        for (int i = 0; i < b.length; i++) {
            String hex = Integer.toHexString(b[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            r = r + hex;
        }
        return r;
    }
}
