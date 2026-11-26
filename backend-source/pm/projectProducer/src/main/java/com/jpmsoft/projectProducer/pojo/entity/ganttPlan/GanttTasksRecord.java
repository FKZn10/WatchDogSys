package com.jpmsoft.projectProducer.pojo.entity.ganttPlan;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 任务
 */
@Data
@TableName("Gantt_Tasks_Record")
public class GanttTasksRecord {

    @TableId(type = IdType.INPUT)
    private String taskId;
    private String planId;
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
    private Integer taskSort;
    private Integer taskMilestone;
    private String taskStartTCode;
    private String taskStartConditions;
    private String taskEndTCode;
    private String taskEndConditions;
    private String taskSource;

    private Date taskBeginTime;
    private String gsId;
    private String taskImportance;
    private BigDecimal taskAmount;
    private Boolean taskAllSection;
    private Boolean  taskIsMany;
    private String cplId;

    /**
     * 是否需要办理
     */
    private Boolean mustHandle;

    private Boolean isImportanceChange;

    private String taskUidB;
    private String taskUnameB;
    private String supervisingLeader;
    private String supervisingLeaderName;
}
