package com.jpmsoft.jputils.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 中国移动短信入参
 *
 * @Author: 李文涛 aaron
 * @date: 2024/8/6 17:24
 */
@Data
public class ChinaYdSmsDTO implements Serializable {


    private String url;
    private String userName;
    private String userPwd;
    private String ecName;
    private String phone;
    private String content;
    private String sign;


}
