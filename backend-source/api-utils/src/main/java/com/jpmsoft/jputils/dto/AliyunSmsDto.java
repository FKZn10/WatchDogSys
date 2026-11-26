package com.jpmsoft.jputils.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 阿里云短信
 *
 * @author: 李文涛 aaron
 * @date: 2022/8/11 9:34
 */
@Data
public class AliyunSmsDto implements Serializable {

    private static final long serialVersionUID = 8092445758422080107L;
    /**
     * 签名
     */
    private String signName;
    /**
     * 模板编码
     */
    private String tmpCode;
    /**
     * 手机号
     */
    private String phoneNumber;
    /**
     * 短信内容
     */
    private String content;
    /**
     * Access_Key
     */
    private String accessKey;
    /**
     * Secret_Key
     */
    private String keySecret;
    /**
     * 短信区域ID
     */
    private String region;

}
