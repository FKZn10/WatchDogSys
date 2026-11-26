package com.jpmsoft.userProducer.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/5/26 11:46
 */
@Data
@TableName("Sys_Pwd_Dictionary")
public class SysPwdDictionary  {

    @TableId(type = IdType.INPUT)
    private String id;
    private String pdPwd;
    private String createUid;
    private String createUname;
    private Date createTime;
}
