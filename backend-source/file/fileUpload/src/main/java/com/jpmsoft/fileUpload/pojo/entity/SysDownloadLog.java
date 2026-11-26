package com.jpmsoft.fileUpload.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.jpmsoft.common.annotations.SecretField;
import lombok.Data;

import java.util.Date;

/**
 * 附件下载日志
 *
 * @Author: 李文涛 aaron
 * @date: 2023/9/28 9:28
 */
@Data
@TableName("Sys_Download_Log")
public class SysDownloadLog {

    @SecretField
    @TableId(type = IdType.INPUT)
    private String id;
    @SecretField
    private String dlUid;
    @SecretField
    private String dlUname;
    @SecretField
    private String dlFileId;
    @SecretField
    private String dlFileName;
    @SecretField
    private Date dlTime;
    @SecretField
    private String dlFormId;

    private String sign;

}
