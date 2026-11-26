package com.jpmsoft.userProducer.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.jpmsoft.common.annotations.SecretField;
import lombok.Data;

import java.util.Date;

/**
 * 推送记录
 */
@Data
@TableName("Push_Record")
public class PushRecord {

    @SecretField
    @TableId(type = IdType.INPUT)
    private String prId;
    @SecretField
    private Date prTime;
    @SecretField
    private Integer prState;
    @SecretField
    private String prDataId;
    @SecretField
    private String prType;
    @SecretField
    private String prUrl;
    @SecretField
    private String prRequest;
    @SecretField
    private String prResponse;
    @SecretField
    private String prMsgId;

    private String sign;
}
