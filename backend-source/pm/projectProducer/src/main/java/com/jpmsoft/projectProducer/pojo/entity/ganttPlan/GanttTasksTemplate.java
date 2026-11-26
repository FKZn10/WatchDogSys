package com.jpmsoft.projectProducer.pojo.entity.ganttPlan;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 任务
 */
@Data
@TableName("Gantt_Tasks_Template")
public class GanttTasksTemplate {

    @TableId(type = IdType.INPUT)
    private String taskId;
    private String ptId;
    private String taskParent;
    private Date taskStartDate;
    private Date taskEndDate;
    private String taskText;
    private Float taskProgress;
    private Float taskDuration;
    private Date taskFinishTime;
    private String taskDoc;
    private String taskUid;
    private String taskUname;
    private String taskType;
    private Integer taskState;
    private String taskPriority;
    private String taskPreTaskId;
    private String taskOutlineLevel;
    private String taskRemark;
    private String taskMode;
    private Integer taskSort=0;
    private Integer taskMilestone;
    private String taskStartTCode;
    private String taskStartConditions;
    private String taskEndTCode;
    private String taskEndConditions;
    private Boolean taskMust;
    private String taskImportance;

    private String pProjectStage;
    private String conExecution;
    private String cplId;
}
