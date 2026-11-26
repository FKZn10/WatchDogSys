package com.jpmsoft.common.model.request;

import com.jpmsoft.common.model.BaseModeRequest;

import java.util.Date;

/**
 * 流程管理
 * 进行中流程
 */
public class SysTaskRequest extends BaseModeRequest {

    private String taskname;
    private String pName;
    private String conName;
    private String secName;
    private String taskUser;
    private String taskSendUser;
    private String fileId;
    private String taskSendUserId;
    private Date currentTime;

    public String getTaskname() {
        return taskname;
    }

    public void setTaskname(String taskname) {
        this.taskname = taskname;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public String getConName() {
        return conName;
    }

    public void setConName(String conName) {
        this.conName = conName;
    }

    public String getTaskUser() {
        return taskUser;
    }

    public void setTaskUser(String taskUser) {
        this.taskUser = taskUser;
    }

    public String getTaskSendUser() {
        return taskSendUser;
    }

    public void setTaskSendUser(String taskSendUser) {
        this.taskSendUser = taskSendUser;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getTaskSendUserId() {
        return taskSendUserId;
    }

    public void setTaskSendUserId(String taskSendUserId) {
        this.taskSendUserId = taskSendUserId;
    }

    public String getSecName() {
        return secName;
    }

    public void setSecName(String secName) {
        this.secName = secName;
    }

    public Date getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(Date currentTime) {
        this.currentTime = currentTime;
    }
}
