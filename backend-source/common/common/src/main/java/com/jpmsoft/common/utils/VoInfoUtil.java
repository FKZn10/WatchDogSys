package com.jpmsoft.common.utils;

import com.jpmsoft.common.model.request.InterfaceEncDecDTO;

/**
 * 返回信息处理工具
 * Created By:李文涛 aaron
 * Created Time:2022/6/17 11:44
 */
public class VoInfoUtil {


    //控制身份证掩码 需要显示前几位
    private static final Integer idCardFront = 8;
    //控制身份证掩码 需要显示末几位
    private static final Integer idCardBehind = 4;

    /**
     * 手机号脱敏
     *
     * @param phone
     * @return
     */
    public static String phoneHandle(String phone) {
        if (ValidateUtil.isEmpty(phone)) return phone;
        StringBuilder sb = new StringBuilder();
        if (phone.length() >= 11) {
            sb.append(phone, 0, phone.length() - 8);
            sb.append("******");
            sb.append(phone.substring(phone.length() - 2));
        }
        if (phone.length() < 11 && phone.length() > 4) {
            for (int i = 0; i < phone.length() - 4; i++)
                sb.append("*");
            sb.append(phone.substring(phone.length() - 4));
        }
        return sb.toString();
    }

    public static String handleUserIdCard(String idCardNum) {
        return handleUserIdCard(idCardNum, idCardFront, idCardBehind);
    }

    /**
     * 用户身份证号码的打码隐藏加星号加*
     * <p>18位和非18位身份证处理均可成功处理</p>
     * <p>参数异常直接返回null</p>
     *
     * @param idCardNum 身份证号码SM2加密密文
     * @param front     需要显示前几位
     * @param end       需要显示末几位
     * @return 处理完成的身份证
     */
    public static String handleUserIdCard(String idCardNum, int front, int end) {
        //身份证不能为空
        if (ValidateUtil.isEmpty(idCardNum)) {
            return null;
        }
        //需要截取的长度不能大于身份证号长度
        if ((front + end) > idCardNum.length()) {
            return null;
        }
        //需要截取的不能小于0
        if (front < 0 || end < 0) {
            return null;
        }
        //计算*的数量
        int asteriskCount = idCardNum.length() - (front + end);
        StringBuffer asteriskStr = new StringBuffer();
        for (int i = 0; i < asteriskCount; i++) {
            asteriskStr.append("*");
        }
        String regex = "(\\w{" + String.valueOf(front) + "})(\\w+)(\\w{" + String.valueOf(end) + "})";
        return idCardNum.replaceAll(regex, "$1" + asteriskStr + "$3");
    }

    /**
     * 数据库字段转为小驼峰字段
     *
     * @param input
     * @return
     */
    public static String toLowerCamelCase(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }
        String[] words = input.toLowerCase().split("_|-");
        StringBuilder camelCase = new StringBuilder();
        for (int i = 0; i < words.length; i++) {
            if (i > 0) {
                camelCase.append(" ");
            }
            camelCase.append(Character.toUpperCase(words[i].charAt(0)));
            camelCase.append(words[i].substring(1));
        }
        return camelCase.toString();
    }
}
