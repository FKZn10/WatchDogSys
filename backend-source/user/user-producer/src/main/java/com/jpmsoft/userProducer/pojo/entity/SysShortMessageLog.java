package com.jpmsoft.userProducer.pojo.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.jpmsoft.common.annotations.SecretField;
import lombok.Data;

import java.util.Date;

/**
 * 短信发送日志
 */
@Data
@TableName("SYS_Short_Message_Log")
public class SysShortMessageLog {
    @SecretField
    @TableId(type = IdType.INPUT)
    private String smlId;
    @SecretField
    private String smId;
    @SecretField
    private String smlMobilePhone;
    @SecretField
    private String smlUid;
    @SecretField
    private String smlUname;
    @SecretField
    private String smlContent;
    @SecretField
    private String smlFileid;
    @SecretField
    private String smlFlowid;
    @SecretField
    private String smlActid;
    @SecretField
    private Date smlCreateTime;
    @SecretField
    private String smlCreateMethod;
    @SecretField
    private String smlCreateUid;
    @SecretField
    private String smlCreateUname;
    @SecretField
    private String smlFrom;
    @SecretField
    private Date smlSendTime;
    @SecretField
    private String smlSendUrl;
    @SecretField
    private String smlSendResult;
    @SecretField
    private String smlSendResultMsg;

    private String sign;
}
