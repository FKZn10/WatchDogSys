package com.jpmsoft.userProducer.pojo.dto.sms;

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

    private String signName;
    private String tmpCode;
    private String phoneNumber;
    private String content;
    private String accessKey;
    private String keySecret;
    private String region;

}
