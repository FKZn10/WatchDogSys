package com.jpmsoft.common.model.request.Plan;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.jpmsoft.common.model.BaseModeRequest;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 任务
 */
public class GanttTasksRequest extends BaseModeRequest {

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
    private Date taskBeginTime;

    private String gsId;
    private String taskImportance;
    private BigDecimal taskAmount;
    private Boolean taskAllSection;
    private Boolean  taskIsMany;
    private String cplId;
    /*******************************/
    private String dId;
    private String conName;
    private String pName;
    private String pId;
    private String planType;
    private List<String> planTypes;
    private List<String> excludeTypes;
    /**
     * 标段名称集合数组
     */
    private List<String> gstSections;

    private String planName;
    /**
     * 用于getGanttTasksByOverdue接口 时
     * 1：查询临期
     * 2：查询逾期
     * 不传则 临期+逾期
     * 用于ganttPlanTaskStatisticsList 时
     * 1：正常未到期
     * 2：按期完成
     * 3：到期前30日历天内
     * 4：任务逾期
     * 5：逾期后完成
     * 6：暂停
     * 不传不过滤
     */
    private Integer queryType;
    /**
     * getGanttTasksByOverdue筛选
     * 0：全部
     * 1：查询已完成
     * 2：查询未完成
     */
    private Integer completed;

    /**
     * 是否需要办理
     */
    private Boolean mustHandle;

    /**
     * 重要程度是否变更
     */
    private Boolean isImportanceChange;

    @JsonProperty("taskUidB")
    private String taskUidB;
    @JsonProperty("taskUnameB")
    private String taskUnameB;

    private String supervisingLeader;
    private String supervisingLeaderName;

    private List<String> taskStartDates;

    private List<String> taskEndDates;

    // 计划开始时间 范围查询
    private List<String> planStartDates;
    // 计划结束时间 范围查询
    private List<String> planEndDates;

    private Boolean overdue;

    private Integer planState;

    public Integer getPlanState() {
        return planState;
    }

    public void setPlanState(Integer planState) {
        this.planState = planState;
    }

    public Boolean getOverdue() {
        return overdue;
    }

    public void setOverdue(Boolean overdue) {
        this.overdue = overdue;
    }

    public Boolean getImportanceChange() {
        return isImportanceChange;
    }

    public void setImportanceChange(Boolean importanceChange) {
        isImportanceChange = importanceChange;
    }

    public List<String> getTaskStartDates() {
        return taskStartDates;
    }

    public void setTaskStartDates(List<String> taskStartDates) {
        this.taskStartDates = taskStartDates;
    }

    public List<String> getTaskEndDates() {
        return taskEndDates;
    }

    public void setTaskEndDates(List<String> taskEndDates) {
        this.taskEndDates = taskEndDates;
    }

    public List<String> getPlanStartDates() {
        return planStartDates;
    }

    public void setPlanStartDates(List<String> planStartDates) {
        this.planStartDates = planStartDates;
    }

    public List<String> getPlanEndDates() {
        return planEndDates;
    }

    public void setPlanEndDates(List<String> planEndDates) {
        this.planEndDates = planEndDates;
    }

    public String getSupervisingLeader() {
        return supervisingLeader;
    }

    public void setSupervisingLeader(String supervisingLeader) {
        this.supervisingLeader = supervisingLeader;
    }

    public String getSupervisingLeaderName() {
        return supervisingLeaderName;
    }

    public void setSupervisingLeaderName(String supervisingLeaderName) {
        this.supervisingLeaderName = supervisingLeaderName;
    }

    public String getTaskUidB() {
        return taskUidB;
    }

    public void setTaskUidB(String taskUidB) {
        this.taskUidB = taskUidB;
    }

    public String getTaskUnameB() {
        return taskUnameB;
    }

    public void setTaskUnameB(String taskUnameB) {
        this.taskUnameB = taskUnameB;
    }

    public Boolean getIsImportanceChange() {
        return isImportanceChange;
    }

    public void setIsImportanceChange(Boolean isImportanceChange) {
        this.isImportanceChange = isImportanceChange;
    }

    public Boolean getMustHandle() {
        return mustHandle;
    }

    public void setMustHandle(Boolean mustHandle) {
        this.mustHandle = mustHandle;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getPlanId() {
        return planId;
    }

    public void setPlanId(String planId) {
        this.planId = planId;
    }

    public String getTaskParent() {
        return taskParent;
    }

    public void setTaskParent(String taskParent) {
        this.taskParent = taskParent;
    }

    public Date getTaskStartDate() {
        return taskStartDate;
    }

    public void setTaskStartDate(Date taskStartDate) {
        this.taskStartDate = taskStartDate;
    }

    public Date getTaskEndDate() {
        return taskEndDate;
    }

    public void setTaskEndDate(Date taskEndDate) {
        this.taskEndDate = taskEndDate;
    }

    public String getTaskText() {
        return taskText;
    }

    public void setTaskText(String taskText) {
        this.taskText = taskText;
    }

    public Float getTaskProgress() {
        return taskProgress;
    }

    public void setTaskProgress(Float taskProgress) {
        this.taskProgress = taskProgress;
    }

    public Float getTaskDuration() {
        return taskDuration;
    }

    public void setTaskDuration(Float taskDuration) {
        this.taskDuration = taskDuration;
    }

    public Date getTaskFinishTime() {
        return taskFinishTime;
    }

    public void setTaskFinishTime(Date taskFinishTime) {
        this.taskFinishTime = taskFinishTime;
    }

    public String getTaskDoc() {
        return taskDoc;
    }

    public void setTaskDoc(String taskDoc) {
        this.taskDoc = taskDoc;
    }

    public String getTaskUid() {
        return taskUid;
    }

    public void setTaskUid(String taskUid) {
        this.taskUid = taskUid;
    }

    public String getTaskUname() {
        return taskUname;
    }

    public void setTaskUname(String taskUname) {
        this.taskUname = taskUname;
    }

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public Integer getTaskState() {
        return taskState;
    }

    public void setTaskState(Integer taskState) {
        this.taskState = taskState;
    }

    public String getTaskPriority() {
        return taskPriority;
    }

    public void setTaskPriority(String taskPriority) {
        this.taskPriority = taskPriority;
    }

    public String getdId() {
        return dId;
    }

    public void setdId(String dId) {
        this.dId = dId;
    }

    public String getConName() {
        return conName;
    }

    public void setConName(String conName) {
        this.conName = conName;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getPlanType() {
        return planType;
    }

    public void setPlanType(String planType) {
        this.planType = planType;
    }

    public List<String> getPlanTypes() {
        return planTypes;
    }

    public void setPlanTypes(List<String> planTypes) {
        this.planTypes = planTypes;
    }

    public List<String> getExcludeTypes() {
        return excludeTypes;
    }

    public void setExcludeTypes(List<String> excludeTypes) {
        this.excludeTypes = excludeTypes;
    }

    public List<String> getGstSections() {
        return gstSections;
    }

    public void setGstSections(List<String> gstSections) {
        this.gstSections = gstSections;
    }

    public Integer getQueryType() {
        return queryType;
    }

    public void setQueryType(Integer queryType) {
        this.queryType = queryType;
    }

    public Integer getCompleted() {
        return completed;
    }

    public void setCompleted(Integer completed) {
        this.completed = completed;
    }

    public String getTaskPreTaskId() {
        return taskPreTaskId;
    }

    public void setTaskPreTaskId(String taskPreTaskId) {
        this.taskPreTaskId = taskPreTaskId;
    }

    public String getTaskOutlineLevel() {
        return taskOutlineLevel;
    }

    public void setTaskOutlineLevel(String taskOutlineLevel) {
        this.taskOutlineLevel = taskOutlineLevel;
    }

    public String getTaskRemark() {
        return taskRemark;
    }

    public void setTaskRemark(String taskRemark) {
        this.taskRemark = taskRemark;
    }

    public String getTaskMode() {
        return taskMode;
    }

    public void setTaskMode(String taskMode) {
        this.taskMode = taskMode;
    }

    public Integer getTaskSort() {
        return taskSort;
    }

    public void setTaskSort(Integer taskSort) {
        this.taskSort = taskSort;
    }

    public Integer getTaskMilestone() {
        return taskMilestone;
    }

    public void setTaskMilestone(Integer taskMilestone) {
        this.taskMilestone = taskMilestone;
    }

    public String getTaskStartTCode() {
        return taskStartTCode;
    }

    public void setTaskStartTCode(String taskStartTCode) {
        this.taskStartTCode = taskStartTCode;
    }

    public String getTaskStartConditions() {
        return taskStartConditions;
    }

    public void setTaskStartConditions(String taskStartConditions) {
        this.taskStartConditions = taskStartConditions;
    }

    public String getTaskEndTCode() {
        return taskEndTCode;
    }

    public void setTaskEndTCode(String taskEndTCode) {
        this.taskEndTCode = taskEndTCode;
    }

    public String getTaskEndConditions() {
        return taskEndConditions;
    }

    public void setTaskEndConditions(String taskEndConditions) {
        this.taskEndConditions = taskEndConditions;
    }

    public Date getTaskBeginTime() {
        return taskBeginTime;
    }

    public void setTaskBeginTime(Date taskBeginTime) {
        this.taskBeginTime = taskBeginTime;
    }

    public String getGsId() {
        return gsId;
    }

    public void setGsId(String gsId) {
        this.gsId = gsId;
    }

    public String getTaskImportance() {
        return taskImportance;
    }

    public void setTaskImportance(String taskImportance) {
        this.taskImportance = taskImportance;
    }

    public BigDecimal getTaskAmount() {
        return taskAmount;
    }

    public void setTaskAmount(BigDecimal taskAmount) {
        this.taskAmount = taskAmount;
    }

    public Boolean getTaskAllSection() {
        return taskAllSection;
    }

    public void setTaskAllSection(Boolean taskAllSection) {
        this.taskAllSection = taskAllSection;
    }

    public Boolean getTaskIsMany() {
        return taskIsMany;
    }

    public void setTaskIsMany(Boolean taskIsMany) {
        this.taskIsMany = taskIsMany;
    }

    public String getCplId() {
        return cplId;
    }

    public void setCplId(String cplId) {
        this.cplId = cplId;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }
}
