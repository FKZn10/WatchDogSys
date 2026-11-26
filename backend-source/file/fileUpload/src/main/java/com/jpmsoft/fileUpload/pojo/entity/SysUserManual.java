package com.jpmsoft.fileUpload.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

/**
 * 用户使用手册
 *
 * @Author: 李文涛 aaron
 * @date: 2023/12/26 9:05
 */
@Data
@TableName("Sys_User_Manual")
public class SysUserManual {

    @TableId(type = IdType.INPUT)
    private String id;
    private String umTitle;
    private String umContent;
    private String umCode;
    private String umKeys;
    private String umParentId;
    private Integer umState;
    private Integer umSort;
    private String createUname;
    private String createUid;
    private Date createTime;

    private String tCode;
    private String powerCode;
}
