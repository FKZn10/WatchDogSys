package com.jpmsoft.jpbpm.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("SYS_FileTemplate_Dtl")
public class SysFileTemplateDtl {
    @TableId(type = IdType.INPUT)
    public String id;
    public String tId;
    public String tdFlowpage;
    public String tdInfopage;
    public String tdUpdatepage;
    public String tdAppFlowpage;
    public String tdAppInfopage;
    public String tdAppUpdatepage;
    public String tdCode;
    public String dId;
    public String pId;
    public String status;
    public String createTime;
    public String statusTime;
    public String formId;
    public String listSettings;
    public String otherSettings;

}
