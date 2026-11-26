package com.jpmsoft.common.model.response.Plan;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.jpmsoft.common.model.ProjectCommonRes;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 任务
 */
public class GanttTasksResponse extends ProjectCommonRes {

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
    private Boolean taskAllSection;
    private Boolean taskIsMany;
    private String cplId;
    /**
     * 是否需要办理
     */
    private Boolean mustHandle;

    /**
     * 重要程度是否变更
     */
    private Boolean isImportanceChange;

    private String gsId;
    private String taskImportance;
    private BigDecimal taskAmount;
    private String gsSection;
    private String gsDescribe;

    private List<GanttTasksResponse> childNodes;
    private List<GanttLinksResponse> ganttLinks;
    private List<String> gstSections;
    private String conName;
    private String dName;
    private String planName;
    private Integer alertDays;
    private Boolean overdue;
    /**
     * GanttTasksStatusEnum
     */
    private Integer taskStatus;
    private String uMtel;
    private String uTel;
    private String uEmail;
    private String uCNameHistory;
    private String uCName;
    //单位简称
    private String uCAbbName;
    private boolean taskFilling;
    private int taskLevel;
    private String taskStartTName;
    private String taskEndTName;
    /**
     * 填报数量 未作废的
     */
    private Integer tasksProcess;
    private List<GanttTasksCancelResponse> tasksCancels;
    private Object tasksOverdues;
    private List<GanttTasksProcessResponse> tasksProcesses;

    @JsonProperty("pCategory")
    private String pCategory;
    @JsonProperty("pProjectManager")
    private String pProjectManager;
    @JsonProperty("pProjectManagerName")
    private String pProjectManagerName;
    @JsonProperty("pEngineer")
    private String pEngineer;
    @JsonProperty("pEngineerName")
    private String pEngineerName;

    @JsonProperty("taskUidB")
    private String taskUidB;
    @JsonProperty("taskUnameB")
    private String taskUnameB;

    private String supervisingLeader;
    private String supervisingLeaderName;

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

    public String getpCategory() {
        return pCategory;
    }

    public void setpCategory(String pCategory) {
        this.pCategory = pCategory;
    }

    public String getpProjectManager() {
        return pProjectManager;
    }

    public void setpProjectManager(String pProjectManager) {
        this.pProjectManager = pProjectManager;
    }

    public String getpProjectManagerName() {
        return pProjectManagerName;
    }

    public void setpProjectManagerName(String pProjectManagerName) {
        this.pProjectManagerName = pProjectManagerName;
    }

    public String getpEngineer() {
        return pEngineer;
    }

    public void setpEngineer(String pEngineer) {
        this.pEngineer = pEngineer;
    }

    public String getpEngineerName() {
        return pEngineerName;
    }

    public void setpEngineerName(String pEngineerName) {
        this.pEngineerName = pEngineerName;
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

    public String getGsSection() {
        return gsSection;
    }

    public void setGsSection(String gsSection) {
        this.gsSection = gsSection;
    }

    public String getGsDescribe() {
        return gsDescribe;
    }

    public void setGsDescribe(String gsDescribe) {
        this.gsDescribe = gsDescribe;
    }

    public List<GanttTasksResponse> getChildNodes() {
        return childNodes;
    }

    public void setChildNodes(List<GanttTasksResponse> childNodes) {
        this.childNodes = childNodes;
    }

    public List<GanttLinksResponse> getGanttLinks() {
        return ganttLinks;
    }

    public void setGanttLinks(List<GanttLinksResponse> ganttLinks) {
        this.ganttLinks = ganttLinks;
    }

    public List<String> getGstSections() {
        return gstSections;
    }

    public void setGstSections(List<String> gstSections) {
        this.gstSections = gstSections;
    }

    public String getConName() {
        return conName;
    }

    public void setConName(String conName) {
        this.conName = conName;
    }


    public String getdName() {
        return dName;
    }

    public void setdName(String dName) {
        this.dName = dName;
    }


    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public Integer getAlertDays() {
        return alertDays;
    }

    public void setAlertDays(Integer alertDays) {
        this.alertDays = alertDays;
    }

    public Boolean getOverdue() {
        return overdue;
    }

    public void setOverdue(Boolean overdue) {
        this.overdue = overdue;
    }

    public Integer getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(Integer taskStatus) {
        this.taskStatus = taskStatus;
    }

    public String getuMtel() {
        return uMtel;
    }

    public void setuMtel(String uMtel) {
        this.uMtel = uMtel;
    }

    public String getuTel() {
        return uTel;
    }

    public void setuTel(String uTel) {
        this.uTel = uTel;
    }

    public String getuEmail() {
        return uEmail;
    }

    public void setuEmail(String uEmail) {
        this.uEmail = uEmail;
    }

    public String getuCNameHistory() {
        return uCNameHistory;
    }

    public void setuCNameHistory(String uCNameHistory) {
        this.uCNameHistory = uCNameHistory;
    }

    public String getuCName() {
        return uCName;
    }

    public void setuCName(String uCName) {
        this.uCName = uCName;
    }

    public String getuCAbbName() {
        return uCAbbName;
    }

    public void setuCAbbName(String uCAbbName) {
        this.uCAbbName = uCAbbName;
    }

    public boolean isTaskFilling() {
        return taskFilling;
    }

    public void setTaskFilling(boolean taskFilling) {
        this.taskFilling = taskFilling;
    }

    public int getTaskLevel() {
        return taskLevel;
    }

    public void setTaskLevel(int taskLevel) {
        this.taskLevel = taskLevel;
    }

    public String getTaskStartTName() {
        return taskStartTName;
    }

    public void setTaskStartTName(String taskStartTName) {
        this.taskStartTName = taskStartTName;
    }

    public String getTaskEndTName() {
        return taskEndTName;
    }

    public void setTaskEndTName(String taskEndTName) {
        this.taskEndTName = taskEndTName;
    }

    public Integer getTasksProcess() {
        return tasksProcess;
    }

    public void setTasksProcess(Integer tasksProcess) {
        this.tasksProcess = tasksProcess;
    }

    public List<GanttTasksCancelResponse> getTasksCancels() {
        return tasksCancels;
    }

    public void setTasksCancels(List<GanttTasksCancelResponse> tasksCancels) {
        this.tasksCancels = tasksCancels;
    }

    public Object getTasksOverdues() {
        return tasksOverdues;
    }

    public void setTasksOverdues(Object tasksOverdues) {
        this.tasksOverdues = tasksOverdues;
    }

    public List<GanttTasksProcessResponse> getTasksProcesses() {
        return tasksProcesses;
    }

    public void setTasksProcesses(List<GanttTasksProcessResponse> tasksProcesses) {
        this.tasksProcesses = tasksProcesses;
    }
}
