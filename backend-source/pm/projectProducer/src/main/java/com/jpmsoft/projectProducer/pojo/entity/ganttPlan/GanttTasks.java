package com.jpmsoft.projectProducer.pojo.entity.ganttPlan;


import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 任务
 */
@Data
@TableName("Gantt_Tasks")
public class GanttTasks {

    @TableId(type = IdType.INPUT)
    private String taskId;
    private String planId;
    private String taskParent;
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private Date taskStartDate;
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private Date taskEndDate;
    private String taskText;
    private Float taskProgress;
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private Float taskDuration;
    @TableField(updateStrategy = FieldStrategy.IGNORED)
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
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private Date taskBeginTime;

    private String gsId;
    private String taskImportance;
    private BigDecimal taskAmount;
    private Boolean taskAllSection;
    private Boolean  taskIsMany;
    private String cplId;
    private Boolean mustHandle;
    private Boolean isImportanceChange;

    @TableField(exist = false)
    private List<GanttTasks> childNodes;

    private String taskUidB;
    private String taskUnameB;
    private String supervisingLeader;
    private String supervisingLeaderName;
}
