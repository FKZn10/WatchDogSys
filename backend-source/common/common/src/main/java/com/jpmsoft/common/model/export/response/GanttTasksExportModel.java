package com.jpmsoft.common.model.export.response;

import java.util.Date;

public class GanttTasksExportModel {

    private String pName;
    private String taskId;
    private String taskParent;
    private Float taskProgress;
    private Integer taskState;
    private String taskText;
    private Date taskStartDate;
    private Date taskEndDate;
    private String dName;
    private String pConsName;
    private String pConsAbbName;

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getTaskParent() {
        return taskParent;
    }

    public void setTaskParent(String taskParent) {
        this.taskParent = taskParent;
    }

    public Float getTaskProgress() {
        return taskProgress;
    }

    public void setTaskProgress(Float taskProgress) {
        this.taskProgress = taskProgress;
    }

    public Integer getTaskState() {
        return taskState;
    }

    public void setTaskState(Integer taskState) {
        this.taskState = taskState;
    }

    public String getTaskText() {
        return taskText;
    }

    public void setTaskText(String taskText) {
        this.taskText = taskText;
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

    public String getdName() {
        return dName;
    }

    public void setdName(String dName) {
        this.dName = dName;
    }

    public String getpConsName() {
        return pConsName;
    }

    public void setpConsName(String pConsName) {
        this.pConsName = pConsName;
    }

    public String getpConsAbbName() {
        return pConsAbbName;
    }

    public void setpConsAbbName(String pConsAbbName) {
        this.pConsAbbName = pConsAbbName;
    }
}
