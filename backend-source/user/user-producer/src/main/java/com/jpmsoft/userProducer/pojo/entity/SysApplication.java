package com.jpmsoft.userProducer.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 应用
 *
 * @Author: 李文涛 aaron
 * @date: 2023/3/6 17:51
 */
@Data
@TableName("SYS_Application")
public class SysApplication {
    @TableId(type = IdType.INPUT)
    private String appId;
    private String appName;
    private String appCode;
    private String appType;
    private String appStatus;
    private String appRemark;
    private String createUid;
    private String createUname;
    private Date createTime;
    private Date stateTime;
    private Integer state;
    private String appIcon;
}
