package com.jpmsoft.userProducer.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 消息模板
 *
 * @author: 李文涛 aaron
 * @date: 2022/11/11 13:56
 */
@Data
@TableName("Sys_Message_Temp")
public class SysMessageTemp {

    @TableId(type = IdType.INPUT)
    public String id;
    public String smtName;
    public String smtCode;
    public String smtType;
    public String smtContent;
    public String smtSql;
    public Date createTime;
    public String createUid;
    public Date updateTime;
    public String updateUid;
    public boolean smtState;
    public String smtFrequency;
    public boolean smtNotHoliday;
    public String smtSmCode;
    public String smtSmContent;
}
