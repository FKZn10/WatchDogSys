package com.jpmsoft.common.model.request;

import com.jpmsoft.common.model.BaseModeRequest;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 我的事务(PM) 待办流程
 */
public class UserTaskRequest extends BaseModeRequest {
    private String taskname;
    private String pName;
    private String conName;
    private String taskSendUser;
    private String tCode;
    private String taskuser;
    private String taskSendTimeStart;
    private String taskSendTimeEnd;
    private String flowName;
    private Date currentTime;
    private BigDecimal lessThanTime;
    private List<String> minDids;
    private String minType;
    private List<String> minUids;
    private Date   taskSendTimeStartMin;
    private String typeStr;

    private String secName;
    private String fileCode;
    private String taskUserName;

    public String getFileCode() {
        return fileCode;
    }

    public void setFileCode(String fileCode) {
        this.fileCode = fileCode;
    }

    public String getTaskUserName() {
        return taskUserName;
    }

    public void setTaskUserName(String taskUserName) {
        this.taskUserName = taskUserName;
    }

    public String getSecName() {
        return secName;
    }

    public void setSecName(String secName) {
        this.secName = secName;
    }

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

    public String getTaskSendUser() {
        return taskSendUser;
    }

    public void setTaskSendUser(String taskSendUser) {
        this.taskSendUser = taskSendUser;
    }

    public String gettCode() {
        return tCode;
    }

    public void settCode(String tCode) {
        this.tCode = tCode;
    }

    public String getTaskuser() {
        return taskuser;
    }

    public void setTaskuser(String taskuser) {
        this.taskuser = taskuser;
    }

    public String getTaskSendTimeStart() {
        return taskSendTimeStart;
    }

    public void setTaskSendTimeStart(String taskSendTimeStart) {
        this.taskSendTimeStart = taskSendTimeStart;
    }

    public String getTaskSendTimeEnd() {
        return taskSendTimeEnd;
    }

    public void setTaskSendTimeEnd(String taskSendTimeEnd) {
        this.taskSendTimeEnd = taskSendTimeEnd;
    }

    public String getFlowName() {
        return flowName;
    }

    public void setFlowName(String flowName) {
        this.flowName = flowName;
    }

    public Date getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(Date currentTime) {
        this.currentTime = currentTime;
    }

    public BigDecimal getLessThanTime() {
        return lessThanTime;
    }

    public void setLessThanTime(BigDecimal lessThanTime) {
        this.lessThanTime = lessThanTime;
    }

    public List<String> getMinDids() {
        return minDids;
    }

    public void setMinDids(List<String> minDids) {
        this.minDids = minDids;
    }

    public String getMinType() {
        return minType;
    }

    public void setMinType(String minType) {
        this.minType = minType;
    }

    public List<String> getMinUids() {
        return minUids;
    }

    public void setMinUids(List<String> minUids) {
        this.minUids = minUids;
    }

    public Date getTaskSendTimeStartMin() {
        return taskSendTimeStartMin;
    }

    public void setTaskSendTimeStartMin(Date taskSendTimeStartMin) {
        this.taskSendTimeStartMin = taskSendTimeStartMin;
    }

    public String getTypeStr() {
        return typeStr;
    }

    public void setTypeStr(String typeStr) {
        this.typeStr = typeStr;
    }
}
