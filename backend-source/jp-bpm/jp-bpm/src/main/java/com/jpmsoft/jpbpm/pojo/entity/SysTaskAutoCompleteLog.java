package com.jpmsoft.jpbpm.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("Sys_Task_Auto_Complete_Log")
public class SysTaskAutoCompleteLog {

    @TableId(type = IdType.INPUT)
    public  String id;
    public  String fileId;
    public  String taclFlowId;
    public  String actId;
    public  String taclResults;
    public  String taclNotes;
    public Date taclExecutionTime;
    public  String tpId;
    public  String taskId;
    private String teId;
}
