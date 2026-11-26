package com.jpmsoft.common.utils;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

/**
 * HmacSHA256 签名生成以及验签
 * Created By:李文涛 aaron
 * Created Time:2022/6/30 15:59
 */
public class SignatureUtils {

    private static final String ALGORITHM = "HmacSHA256";
    private static final String secret = "7M&3nP#8";

    /**
     * 验签
     *
     * @param message
     * @param signature
     * @return
     */
    public static boolean valid(String message, String signature) {
        return signature != null && signature.equals(sign(message));
    }

    /**
     * 签名生成
     *
     * @param message
     * @return
     */
    public static String sign(String message) {
        try {
            Mac hmac = Mac.getInstance(ALGORITHM);
            SecretKeySpec secret_key = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), ALGORITHM);
            hmac.init(secret_key);
            byte[] bytes = hmac.doFinal(message.getBytes());
            //app端限制长度直接截一半
            String str = byteArrayToHexString(bytes);
            return str.substring(0, str.length() / 2);
        } catch (Exception ex) {

        }
        return null;
    }

    private static String byteArrayToHexString(byte[] bytes) {
        StringBuilder hs = new StringBuilder();
        String tempStr;
        for (int index = 0; bytes != null && index < bytes.length; index++) {
            tempStr = Integer.toHexString(bytes[index] & 0XFF);
            if (tempStr.length() == 1)
                hs.append('0');
            hs.append(tempStr);
        }
        return hs.toString().toLowerCase();
    }
}
