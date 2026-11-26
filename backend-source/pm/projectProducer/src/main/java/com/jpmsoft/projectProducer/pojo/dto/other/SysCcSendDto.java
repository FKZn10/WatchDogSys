package com.jpmsoft.projectProducer.pojo.dto.other;

import java.util.List;

public class SysCcSendDto {
    public String taskid;
    public String fileId;
    public String userId;
    public String explain;
    public List<String> ccIds;
    public String taskname;
    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }

    public List<String> getCcIds() {
        return ccIds;
    }

    public void setCcIds(List<String> ccIds) {
        this.ccIds = ccIds;
    }

    public String getTaskid() {
        return taskid;
    }

    public void setTaskid(String taskid) {
        this.taskid = taskid;
    }

    public String getTaskname() {
        return taskname;
    }

    public void setTaskname(String taskname) {
        this.taskname = taskname;
    }
}
