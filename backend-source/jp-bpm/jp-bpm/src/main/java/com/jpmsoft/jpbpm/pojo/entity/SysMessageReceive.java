package com.jpmsoft.jpbpm.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("Sys_Message_Receive")
public class SysMessageReceive {
    @TableId(type = IdType.INPUT)
    public String id;
    public String smId;
    public String smrUser;
    public String smrSendStatus;
    public Date smrSendTime;
    public boolean smrRead;
    public Date smrReadTime;
}
