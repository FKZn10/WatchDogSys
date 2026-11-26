package com.jpmsoft.userProducer.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 系统消息
 *
 * @author: 李文涛 aaron
 * @date: 2022/11/11 14:25
 */
@Data
@TableName("Sys_Message")
public class SysMessage {
    @TableId(type = IdType.INPUT)
    public String id;
    public String smTitle;
    public String smType;
    public String smContent;
    public String smtCode;
    public String smSendUser;
    public String smSendType;
    public String smRemark;
    public Date createTime;
    public String createUid;
}
