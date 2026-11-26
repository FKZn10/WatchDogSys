package com.jpmsoft.userProducer.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.jpmsoft.common.annotations.SecretField;
import lombok.Data;

import java.util.Date;

/**
 * 前端审计日志
 *
 * @author: 李文涛 aaron
 * @date: 2022/8/25 20:53
 */
@Data
@TableName("Web_Audit_Log")
public class WebAuditLog {
    @SecretField
    @TableId(type = IdType.ASSIGN_UUID)
    private String id;
    @SecretField
    private String salContent;
    @SecretField
    private String salType;
    @SecretField
    private String salFormId;
    @SecretField
    private String createUid;
    @SecretField
    private String createUname;
    @SecretField
    private Date createTime;
    private String sign;
}
