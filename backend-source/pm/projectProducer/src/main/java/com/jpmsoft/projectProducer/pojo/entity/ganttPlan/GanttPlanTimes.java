package com.jpmsoft.projectProducer.pojo.entity.ganttPlan;

import lombok.Data;

import java.util.Date;

@Data
public class GanttPlanTimes {
    private String pId;
    private String planId;
    private String gsId;

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

    private int completeRecordsNeed;
    private int pRemoveNeed;
}
