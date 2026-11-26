package com.jpmsoft.jpbpm.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("SYS_Task")
public class Task {
    
    @TableId(type = IdType.INPUT)
    private String taskid;
    private String taskflow;
    private String taskrule;
    private String taskentityid;
    private String taskclient;
    private String taskproject;
    private String taskcurrentactid;
    private String taskpreactid;
    private String taskuser;
    private Date tasksendtime;
    private String taskname;
    private String tasksenduser;
    private String taskparenttaskid;
    private String taskparentflowid;
    private String taskparentactid;
    /**
     * 未发起协同：null
     * 正在协同："YES"
     * 协同结束："NO"
     */
    private String taskcoordination;
    private Date taskstarttime;
    private Date taskendtime;
    private String taskrb;
    //未读
    //已读
    private String taskread;
    private Date taskreadtime;
    private String tasksection;
    private String taskcontract;
    //是否原路返回（是：否）
    private String taskgetflagstr;
    //退回发起人
    private String taskReturnType;
    private String teId;
    private String taskType;


    //分表新增任务关键信息
    private String projectId;
    private String contractId;
    private String flowId;
    private String secId;
    private String tCode;

}
