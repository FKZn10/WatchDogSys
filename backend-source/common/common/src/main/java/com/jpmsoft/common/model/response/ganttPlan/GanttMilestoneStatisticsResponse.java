package com.jpmsoft.common.model.response.ganttPlan;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author tianhelin
 * @date 2025/5/23
 */
@Data
public class GanttMilestoneStatisticsResponse {
    private String id;
    private String planId;
    @JsonProperty("pId")
    private String pId;
    @JsonProperty("pName")
    private String pName;
    private String gsId;
    private String gsName;
    private Date setTime;
    private Date startTime;
    private Date finishTime;
    private Date completeTime;
    private Date completeRecordsTime;
    @JsonProperty("pRemoveTime")
    private Date pRemoveTime;
    private Date accountSubmitTime;
    private Date accountReplyTime;
    private Integer completeRecordsNeed;
    @JsonProperty("pRemoveNeed")
    private Integer pRemoveNeed;
    @JsonProperty("pCategory")
    private String pCategory;
    @JsonProperty("pCompetentDepartment")
    private String pCompetentDepartment;
    @JsonProperty("pCompetentDepartmentName")
    private String pCompetentDepartmentName;
    @JsonProperty("pDepartment")
    private String pDepartment;
    @JsonProperty("pDepartmentName")
    private String pDepartmentName;
    @JsonProperty("pProjectStage")
    private String pProjectStage;
    @JsonProperty("pInvTotal")
    private BigDecimal pInvTotal;
    private String setTimeStatus;
    private String startTimeStatus;
    private String finishTimeStatus;
    private String completeTimeStatus;
    private String completeRecordsTimeStatus;
    @JsonProperty("pRemoveTimeStatus")
    private String pRemoveTimeStatus;
    private String accountSubmitTimeStatus;
    private String accountReplyTimeStatus;
    private String remark;

    private Date setTimeSchedule;
    private Date startTimeSchedule;
    private Date finishTimeSchedule;
    private Date completeTimeSchedule;
    private Date completeRecordsTimeSchedule;
    @JsonProperty("pRemoveTimeSchedule")
    private Date pRemoveTimeSchedule;
    private Date accountSubmitTimeSchedule;
    private Date accountReplyTimeSchedule;
    private Date setTimeFinish;
    private Date startTimeFinish;
    private Date finishTimeFinish;
    private Date completeTimeFinish;
    private Date completeRecordsTimeFinish;
    @JsonProperty("pRemoveTimeFinish")
    private Date pRemoveTimeFinish;
    private Date accountSubmitTimeFinish;
    private Date accountReplyTimeFinish;

    @JsonIgnore
    private Date pCreateTime;
    @JsonIgnore
    private Boolean isPlan;

    private Boolean setTimeHandle;
    private Boolean startTimeHandle;
    private Boolean finishTimeHandle;
    private Boolean completeTimeHandle;
    private Boolean completeRecordsTimeHandle;
    @JsonProperty("pRemoveTimeHandle")
    private Boolean pRemoveTimeHandle;
    private Boolean accountSubmitTimeHandle;
    private Boolean accountReplyTimeHandle;
}
