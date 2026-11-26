package com.jpmsoft.common.model.request;

import lombok.Data;

import java.io.Serializable;

/**
 * 加解密配置项
 *
 * @Author: 李文涛 aaron
 * @date: 2024/8/6 13:40
 */
@Data
public class InterfaceEncDecDTO implements Serializable {

    private static final long serialVersionUID = -8084243834867575441L;

    /**
     * 加密接口地址
     */
    private String encInterfaceUrl;

    /**
     * 解密接口地址
     */
    private String decInterfaceUrl;

    /**
     * 日志加签接口
     * POST接口接收日志实体
     */
    private String logSignUrl;

    /**
     * 日志验签接口
     */
    private String logVerifyUrl;

    /**
     * 加签接口
     * post 入参固定
     */
    private String signUrl;

    /**
     * 验签接口
     * post 入参固定
     */
    private String verifyUrl;

    /**
     * 用户信息是否加签 完整性存储
     */
    private Boolean userSign = false;
    /**
     * 用户列表查询是否开启完整性验证
     */
    private Boolean userListVerify = false;

    /**
     * 角色信息是否加签 完整性存储
     */
    private Boolean roleSign = false;
    /**
     * 角色列表查询是否开启完整性验证
     */
    private Boolean roleListVerify = false;
    /**
     * 登录是否验证完整性
     */
    private Boolean loginVerify = false;

    /**
     * 招标计划信息是否加签 完整性存储
     */
    private Boolean secSign = false;
    /**
     * 招标计划列表查询是否开启完整性验证
     */
    private Boolean secListVerify = false;


    /**
     * 项目注册信息是否加签 完整性存储
     */
    private Boolean prSign = false;
    /**
     * 项目注册列表查询是否开启完整性验证
     */
    private Boolean prListVerify = false;

    /**
     * 项目信息是否加签 完整性存储
     */
    private Boolean projectSign = false;
    /**
     * 项目列表查询是否开启完整性验证
     */
    private Boolean projectListVerify = false;


    /**
     * 审计日志是否加签 完整性存储
     */
    private Boolean auditSign = false;
    /**
     * 审计日志列表查询是否开启完整性验证
     */
    private Boolean auditListVerify = false;


    /**
     * 字段修改日志是否加签 完整性存储
     */
    private Boolean fieldChangeSign = false;
    /**
     * 字段修改日志列表查询是否开启完整性验证
     */
    private Boolean fieldChangeListVerify = false;

    /**
     * 登录日志是否加签 完整性存储
     */
    private Boolean loginLogSign = false;
    /**
     * 登录日志列表查询是否开启完整性验证
     */
    private Boolean loginLogListVerify = false;


    /**
     * 短信日志是否加签 完整性存储
     */
    private Boolean smsSign = false;
    /**
     * 短信发送列表查询是否开启完整性验证
     */
    private Boolean smsListVerify = false;


    /**
     * 附件下载日志是否加签 完整性存储
     */
    private Boolean downloadSign = false;
    /**
     * 附件下载列表查询是否开启完整性验证
     */
    private Boolean downloadListVerify = false;

    /**
     * 手机号/身份证/密码是否通过密码机加密
     */
    private Boolean encrypt = false;
}
