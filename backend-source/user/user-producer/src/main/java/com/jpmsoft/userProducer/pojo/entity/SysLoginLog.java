package com.jpmsoft.userProducer.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.jpmsoft.common.annotations.SecretField;
import lombok.Data;

import java.util.Date;

/**
 * 登录日志
 * Created By:李文涛 aaron
 * Created Time:2022/6/15 10:23
 */
@Data
@TableName("Sys_Login_Log")
public class SysLoginLog {
    @SecretField
    @TableId(type = IdType.INPUT)
    private String sllId;
    @SecretField
    private String uId;
    @SecretField
    private String sllIp;
    @SecretField
    private String sllLoginType;
    @SecretField
    private Date sllLoginTime;
    @SecretField
    private String appVersion;
    @SecretField
    private String appDeviceType;
    /**
     * 01 登录
     * 02 登出
     */
    @SecretField
    private String sllAction;

    private String sign;
}
