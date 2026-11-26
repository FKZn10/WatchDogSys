package com.jpmsoft.projectProducer.pojo.dto.bim;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: 沈金安 Shen Jinan
 * @Date: 2022/6/22 14:49
 * @Description:
 * @Version: 1.0
 */
@Data
public class PlanTaskBimPageDto{

    /**
     * 任务id
     */
    private String taskId;

    /**
     * 计划id
     */
    private String planId;

    /**
     * 计划名称
     */
    private String planName;
    private String taskParent;

    /**
     * 任务开始时间
     */
    private Date taskStartDate;

    /**
     * 任务结束时间
     */
    private Date taskEndDate;

    /**
     * 任务名称
     */
    private String taskText;

    /**
     * 完成进度
     */
    private Float taskProgress;

    /**
     * 工期
     */
    private Float taskDuration;

    private Date taskFinishTime;
    private String taskDoc;

    /**
     * 责任人id
     */
    private String taskUid;

    /**
     * 责任人名称
     */
    private String taskUname;

    private String taskType;
    private Integer taskState;

    /**
     * 优先级
     */
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

    private Date taskBeginTime;
}
