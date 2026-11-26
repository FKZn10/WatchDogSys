package com.jpmsoft.userProducer.pojo.dto.sms;

import lombok.Data;

/**
 *
 * wudz
 * 2024.08.07
 */
@Data
public class YdSmsDto {


    private String url;
    private String userName;
    private String userPwd;
    private String ecName;
    private String phone;  //手机号
    private String content;
    private String sign;
}
