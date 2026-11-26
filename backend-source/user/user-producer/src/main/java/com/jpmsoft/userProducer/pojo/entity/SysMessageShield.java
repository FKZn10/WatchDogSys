package com.jpmsoft.userProducer.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @author: 李文涛 aaron
 * @date: 2022/11/15 9:39
 */
@Data
@TableName("Sys_Message_Shield")
public class SysMessageShield {

    @TableId(type = IdType.INPUT)
    public String id;
    public String smtId;
    public String uId;
    public Boolean msSendSys;
    public Boolean msSendSms;
    public String createUid;
    public String createUname;
    public Date createTime;
}
