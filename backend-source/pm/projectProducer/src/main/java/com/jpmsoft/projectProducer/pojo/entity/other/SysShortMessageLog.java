package com.jpmsoft.projectProducer.pojo.entity.other;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 短信发送日志
 */
@Data
@TableName("SYS_Short_Message_Log")
public class SysShortMessageLog {

    @TableId(type = IdType.INPUT)
    private String smlId;
    private String smId;
    private String smlMobilePhone;
    private String smlUid;
    private String smlUname;
    private String smlContent;
    private String smlFileid;
    private String smlFlowid;
    private String smlActid;
    private Date smlCreateTime;
    private String smlCreateMethod;
    private String smlCreateUid;
    private String smlCreateUname;
    private String smlFrom;
    private Date smlSendTime;
    private String smlSendUrl;
    private String smlSendResult;
    private String smlSendResultMsg;

}
