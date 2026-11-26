package com.jpmsoft.jputils.dto;

import com.jpmsoft.jputils.utils.CharacterUtils;
import lombok.Data;

import java.io.Serializable;

/**
 * 苏相短信入参数
 *
 * @author: 李文涛 aaron
 * @date: 2022/9/6 10:35
 */
@Data
public class SxSmsDto implements Serializable {
    private static final long serialVersionUID = 19972942577832974L;

    /**
     * 请求地址
     */
    private String sscUrl;
    /**
     * 区标识
     */
    private String sscRegion;
    /**
     * 短信签名
     */
    private String sscSign;
    /**
     * 短信模板编码
     */
    private String sscTempCode;
    /**
     * 手机号
     */
    private String phoneNumber;
    /**
     * 加密盐
     */
    private String salt;
    /**
     * 短信参数
     * 例如：验证码 {"code":"134551"}
     */
    private String tempParam;
    /**
     * 一个随机生成的包含 a-zA-Z0-9的16位的字符串
     */
    private String nonce = CharacterUtils.getRandomString(16);
    /**
     * Unix时间戳
     */
    private String timestamp = Long.toString(System.currentTimeMillis() / 1000L);
}
