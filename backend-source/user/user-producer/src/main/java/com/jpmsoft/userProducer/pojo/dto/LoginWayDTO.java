package com.jpmsoft.userProducer.pojo.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 登录配置基础配置信息
 *
 * @Author: 李文涛 aaron
 * @date: 2023/8/1 13:57
 */
@Data
public class LoginWayDTO implements Serializable {

    /**
     * 前端账户密码登录是否需要校验验证码
     */
    private Boolean checkSmsCode = false;
    /**
     * admin端账户密码登录是否需要校验验证码
     */
    private Boolean checkSmsCodeAdmin = false;
    /**
     * 是否支持手机验证码登录
     */
    private Boolean mobile = true;
    /**
     * 是否支持账号密码登录
     */
    private Boolean account = true;
    /**
     * 是否支持ukey登录
     */
    private Boolean ukey = true;
    /**
     * 是否校验验证码
     * 用户端
     */
    private Boolean checkAuthCode = true;
    /**
     * 是否校验验证码
     * admin端
     */
    private Boolean checkAuthCodeAdmin = true;
}
