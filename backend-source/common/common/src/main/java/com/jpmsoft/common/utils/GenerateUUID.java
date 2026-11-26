package com.jpmsoft.common.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class GenerateUUID {
    public static String getUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     * 校验是否有ID 有则返回当前ID否则生成新ID
     *
     * @param oldId
     * @return
     */
    public static String getUUID(String oldId) {
        if (ValidateUtil.isEmpty(oldId)) return getUUID();
        else return oldId;
    }

    public static String createMsgId() {
        String msgId = dateFormat(new Date(), "yyyyMMddHHmm");
        msgId += (int) ((Math.random() * 9 + 1) * 100000);
        return "JPM" + msgId;
    }

    /**
     * token拼接签名
     * 临时拼接签名增强token
     * 后期再改造
     *
     * @return
     */
    public static String getToekn() {
        String uuid = getUUID().replaceAll("-", "");
        String token = uuid + "." + SignatureUtils.sign(uuid);
        return token;
    }

    /**
     * 验证token后半部分签名
     *
     * @param token
     * @return
     */
    public static Boolean validToken(String token) {
        if (!token.contains(".")) return false;
        String[] tokenSp = token.split("\\.");
        String uuid = tokenSp[0];
        return SignatureUtils.valid(uuid, tokenSp[1]);
    }

    private static String dateFormat(Date time, String format) {
        if (time == null)
            return null;
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(time);
    }
}
