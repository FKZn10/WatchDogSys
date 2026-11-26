package com.jpmsoft.userProducer.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.jpmsoft.common.annotations.SecretField;
import lombok.Data;

import java.util.Date;

/**
 * 表字段修改记录
 */
@Data
@TableName("Sys_Filed_Change_Record")
public class FiledChangeRecord {
    @SecretField
    @TableId(type = IdType.INPUT)
    private String fcrId;
    @SecretField
    private String fcrTable;
    @SecretField
    private String fcrFiled;
    @SecretField
    private String fcrType;
    @SecretField
    private String fcrKey;
    @SecretField
    private String fcrOld;
    @SecretField
    private String fcrNew;
    @SecretField
    private String fcrUpdateUid;
    @SecretField
    private String fcrUpdateUname;
    @SecretField
    private Date fcrUpdateTime;

    @SecretField
    private String actId;
    @SecretField
    private String actName;
    private String sign;
}
