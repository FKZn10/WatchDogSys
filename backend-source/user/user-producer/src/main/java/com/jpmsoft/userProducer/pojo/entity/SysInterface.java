package com.jpmsoft.userProducer.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 接口管理
 *
 * @Author: 李文涛 aaron
 * @date: 2024/11/19 10:34
 */
@Data
@TableName("Sys_Interface")
public class SysInterface {

    @TableId(type = IdType.INPUT)
    private String id;
    private String ifUrl;
    private String ifPower;
    private String ifStatus;

    private String ifRecord;

    private String ifType;
    private String ifName;
    private Date createTime;
    private String createUid;
    private String createUname;
    private Date updateTime;
    private String updateUid;
    private String updateUname;

}
