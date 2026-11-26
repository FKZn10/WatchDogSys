package com.jpmsoft.projectProducer.pojo.entity.ganttPlan;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

// 进度里程碑统计表实体
@Data
public class GanttMilestoneStatistics {
    private String id;
    private String planId;
    private String pId;
    private String pName;
    private String gsId;
    private String gsName;
    private Date setTime;
    private Date startTime;
    private Date finishTime;
    private Date completeTime;
    private Date completeRecordsTime;
    private Date pRemoveTime;
    private Date accountSubmitTime;
    private Date accountReplyTime;
    private int completeRecordsNeed;
    private int pRemoveNeed;
    private String pCategory;
    private String pCompetentDepartment;
    private String pDepartment;
    private String pProjectStage;
    private BigDecimal pInvTotal;
    private String setTimeStatus;
    private String startTimeStatus;
    private String finishTimeStatus;
    private String completeTimeStatus;
    private String completeRecordsTimeStatus;
    private String pRemoveTimeStatus;
    private String accountSubmitTimeStatus;
    private String accountReplyTimeStatus;

    private Date setTimeSchedule;
    private Date startTimeSchedule;
    private Date finishTimeSchedule;
    private Date completeTimeSchedule;
    private Date completeRecordsTimeSchedule;
    private Date pRemoveTimeSchedule;
    private Date accountSubmitTimeSchedule;
    private Date accountReplyTimeSchedule;

    private Date setTimeFinish;
    private Date startTimeFinish;
    private Date finishTimeFinish;
    private Date completeTimeFinish;
    private Date completeRecordsTimeFinish;
    private Date pRemoveTimeFinish;
    private Date accountSubmitTimeFinish;
    private Date accountReplyTimeFinish;

    private Boolean setTimeHandle;
    private Boolean startTimeHandle;
    private Boolean finishTimeHandle;
    private Boolean completeTimeHandle;
    private Boolean completeRecordsTimeHandle;
    private Boolean pRemoveTimeHandle;
    private Boolean accountSubmitTimeHandle;
    private Boolean accountReplyTimeHandle;
}