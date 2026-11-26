package com.jpmsoft.common.model.request.Plan;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 任务变更
 */
@Data
public class GanttTasksChangeDTO implements Serializable {

    private static final long serialVersionUID = -8736157188877866110L;

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
    private String taskChangeType;
    private Boolean taskFilling;
    private String taskSource;
    private Boolean taskAllSection;
    private Boolean  taskIsMany;
    private String cplId;


    private Date taskBeginTime;
    private String gsId;
    private String taskImportance;
    private BigDecimal taskAmount;
    /**
     * 标段名称集合数组
     */
    private List<String> gstSections;
    /**
     * 是否需要办理
     */
    private Boolean mustHandle;

    private Boolean isImportanceChange;

    @JsonProperty("taskUidB")
    private String taskUidB;
    @JsonProperty("taskUnameB")
    private String taskUnameB;

    private String supervisingLeader;
    private String supervisingLeaderName;
}
