package com.jpmsoft.gmgws.utils;

/**
 * 生成短信验证码
 *
 * @author: 李文涛 aaron
 * @date: 2022/8/18 15:41
 */
public final class VerifiactionCodeUtil {

    /**
     * 生成6位随机数
     *
     * @return
     */
    public static int code() {
        //6位随机数
        return (int) ((Math.random() * 9 + 1) * 100000);
    }
}
