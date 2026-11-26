package com.jpmsoft.common.model.requestAndResponse.Sms;

import lombok.Data;

/**
 * 北林短信平台发送请求参数
 *
 * @author tianhelin
 * @date 2025/11/24
 */
@Data
public class BlSmsSendRequest {
    /**
     * 接口地址
     */
    private String url;

    /**
     * 调用的接口方法所属系统
     */
    private String sysId;

    /**
     * 调用的接口方法所属模块
     */
    private String moduleId;

    /**
     * 授权系统名称
     */
    private String tpName;

    /**
     * 授权系统的权限密钥
     */
    private String secretKey;

    /**
     * 调用的接口方法名称
     */
    private String interfaceMethod;

    /**
     * 接收人信息
     */
    private String personInfo;

    /**
     * 短信内容
     */
    private String smsInfo;

    /**
     * 发送优先级
     */
    private String sendPriority;

    /**
     * 发送时间
     */
    private String sendTime;

    /**
     * 发送人对应统一身份认证的账号
     */
    private String operatorId;

    /**
     * 发送人对应统一身份认证的账号
     */
    private String operatorIdNumber;

    /**
     * 发送人姓名
     */
    private String operatorName;

    /**
     * 使用短信配额的机构id
     */
    private String operatorUnitId;

    /**
     * 使用短信配额的机构名称
     */
    private String operatorUnitName;

    /**
     * 填写使用的模板id
     */
    private String templetId;

    /**
     * 消息签名
     */
    private String personSend;

    /**
     * 填写使用的回执id
     */
    private String receiptId;

    private String sendSysId;

    /**
     * 调用接口的系统厂商名称
     */
    private String sendSysName;

    /**
     * 用户产生消息的浏览器名称
     */
    private String userBrowser;
}