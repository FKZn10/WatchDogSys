package com.jpmsoft.projectProducer.pojo.response;

import java.util.Date;

public class ProjectCpRprResponse {
    private String id;
    private String cpdId;
    private String fileName;
    private String fileCode;
    private Date fileApprovalTime;
    private Date fileExpirationDate;
    private Boolean manyTimes;
    private Boolean isEnd;
    private String pcrDoc;
    private String createUname;
    private String createUid;
    private Date createTime;
    private Integer state;
    private Date stateTime;
    private String operationType;
    private String notes;
    private String taskId;
    private String cpId;
    private String pId;
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCpdId() {
        return cpdId;
    }

    public void setCpdId(String cpdId) {
        this.cpdId = cpdId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileCode() {
        return fileCode;
    }

    public void setFileCode(String fileCode) {
        this.fileCode = fileCode;
    }

    public Date getFileApprovalTime() {
        return fileApprovalTime;
    }

    public void setFileApprovalTime(Date fileApprovalTime) {
        this.fileApprovalTime = fileApprovalTime;
    }

    public Date getFileExpirationDate() {
        return fileExpirationDate;
    }

    public void setFileExpirationDate(Date fileExpirationDate) {
        this.fileExpirationDate = fileExpirationDate;
    }

    public Boolean getManyTimes() {
        return manyTimes;
    }

    public void setManyTimes(Boolean manyTimes) {
        this.manyTimes = manyTimes;
    }

    public Boolean getEnd() {
        return isEnd;
    }

    public void setEnd(Boolean end) {
        isEnd = end;
    }

    public String getPcrDoc() {
        return pcrDoc;
    }

    public void setPcrDoc(String pcrDoc) {
        this.pcrDoc = pcrDoc;
    }

    public String getCreateUname() {
        return createUname;
    }

    public void setCreateUname(String createUname) {
        this.createUname = createUname;
    }

    public String getCreateUid() {
        return createUid;
    }

    public void setCreateUid(String createUid) {
        this.createUid = createUid;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Date getStateTime() {
        return stateTime;
    }

    public void setStateTime(Date stateTime) {
        this.stateTime = stateTime;
    }

    public String getOperationType() {
        return operationType;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getCpId() {
        return cpId;
    }

    public void setCpId(String cpId) {
        this.cpId = cpId;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }
}
