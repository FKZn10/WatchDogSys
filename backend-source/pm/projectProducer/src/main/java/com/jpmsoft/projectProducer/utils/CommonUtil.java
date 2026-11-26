package com.jpmsoft.projectProducer.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 常用校验
 */
public class CommonUtil {

    // 手机号码的正则表达式
    private static final String PHONE_NUMBER_REGEX = "^1[3-9]\\d{9}$";

    /**
     * 校验手机号码是否合法
     *
     * @param phoneNumber 要校验的手机号码
     * @return 如果手机号码合法则返回true，否则返回false
     */
    public static boolean validatePhoneNumber(String phoneNumber) {
        // 创建手机号码正则表达式的模式
        Pattern pattern = Pattern.compile(PHONE_NUMBER_REGEX);
        // 使用模式匹配手机号码
        Matcher matcher = pattern.matcher(phoneNumber);
        // 返回匹配结果
        return matcher.matches();
    }

    public static void main(String[] args) {
        // 测试手机号码
        String phoneNumber = "12345678901";
        // 校验手机号码是否合法
        boolean isValid = validatePhoneNumber(phoneNumber);
        // 打印结果
        System.out.println("Is " + phoneNumber + " a valid phone number? " + isValid);
    }


}
