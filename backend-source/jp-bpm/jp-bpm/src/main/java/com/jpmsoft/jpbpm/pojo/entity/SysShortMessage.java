package com.jpmsoft.jpbpm.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("SYS_Short_Message")
public class SysShortMessage {

    @TableId(type = IdType.INPUT)
    private String smId;
    private String smMobilePhone;
    private String smUid;
    private String smUname;
    private String smContent;
    private String smFileid;
    private String smFlowid;
    private String smActid;
    private Date smCreateTime;
    private String smCreateMethod;
    private String smCreateUid;
    private String smCreateUname;
    private String smFrom;
    private String teId;
}