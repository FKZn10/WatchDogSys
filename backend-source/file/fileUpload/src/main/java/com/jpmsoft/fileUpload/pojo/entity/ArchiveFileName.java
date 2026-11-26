package com.jpmsoft.fileUpload.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 归档文件名称配置
 *
 * @Author: 李文涛 aaron
 * @date: 2024/8/29 14:01
 */
@Data
@TableName("Archive_File_Name")
public class ArchiveFileName {

    @TableId(type = IdType.INPUT)
    private String id;
    private String afnName;
    private Boolean afnStatus;
    private String tableCode;
    private String columnCode;
    private String createUid;
    private String createUname;
    private Date createTime;
    private String updateUid;
    private String updateUname;
    private Date updateTime;
    private String afnDelimiter;

}
