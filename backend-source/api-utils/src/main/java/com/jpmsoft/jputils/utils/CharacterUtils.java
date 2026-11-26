package com.jpmsoft.jputils.utils;

import java.util.Random;

/**
 * @author: 李文涛 aaron
 * @date: 2022/9/6 11:07
 */
public class CharacterUtils {

    public static String getRandomString(int length) {
        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; ++i) {
            int number = random.nextInt(62);//[0,62)
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }
}
