package com.jpmsoft.jpbpm.pojo.vo;

import com.jpmsoft.common.model.BaseModeRequest;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class TaskCoordinationTaskVo  extends BaseModeRequest implements Serializable {
    public String taskId;
    public String taskName;
    public List<String> taskNames;
    public String pName;
    public List<String> pNames;
    public String conNmae;
    public List<String> conNames;
    public String secName;
    public List<String> secNames;
    public String createName;
    public List<String> createNames;
    public String createId;
    public Date createtime;
    public String doUserId;
    public String doUserName;
    public List<String> doUserNames;
    public Date doTime;
    public String processStatus;
    public String tpEntityid;
    public String taskentityid;
    public String fileId;
    public String taskType;
    public String fileCreateId;
    public String fileCreateName;
    public List<String> fileCreateNames;
    public String fileFlowStatusc;
    public String taskNowUserId;
    public String taskNowUserName;
    public String taskReturnType;
    public String explainStr;
    public String actId;
    public String ctTaskid;
    public String tpId;
    private List<Map<String, Object>> taskUsetAndActList;
    private String tCode;
    private String startTime;
    private String endTime;
    private String startDoTime;
    private String endDoTime;

    private List<String> createtimes;

    private List<String> doTimes;

    public List<String> getCreatetimes() {
        return createtimes;
    }

    public void setCreatetimes(List<String> createtimes) {
        this.createtimes = createtimes;
    }

    public List<String> getDoTimes() {
        return doTimes;
    }

    public void setDoTimes(List<String> doTimes) {
        this.doTimes = doTimes;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public String getConNmae() {
        return conNmae;
    }

    public void setConNmae(String conNmae) {
        this.conNmae = conNmae;
    }

    public String getSecName() {
        return secName;
    }

    public void setSecName(String secName) {
        this.secName = secName;
    }

    public String getCreateName() {
        return createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName;
    }

    public String getCreateId() {
        return createId;
    }

    public void setCreateId(String createId) {
        this.createId = createId;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getDoUserId() {
        return doUserId;
    }

    public void setDoUserId(String doUserId) {
        this.doUserId = doUserId;
    }

    public String getDoUserName() {
        return doUserName;
    }

    public void setDoUserName(String doUserName) {
        this.doUserName = doUserName;
    }

    public Date getDoTime() {
        return doTime;
    }

    public void setDoTime(Date doTime) {
        this.doTime = doTime;
    }

    public String getProcessStatus() {
        return processStatus;
    }

    public void setProcessStatus(String processStatus) {
        this.processStatus = processStatus;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public String getFileCreateId() {
        return fileCreateId;
    }

    public void setFileCreateId(String fileCreateId) {
        this.fileCreateId = fileCreateId;
    }

    public String getFileCreateName() {
        return fileCreateName;
    }

    public void setFileCreateName(String fileCreateName) {
        this.fileCreateName = fileCreateName;
    }

    public String getTaskNowUserId() {
        return taskNowUserId;
    }

    public void setTaskNowUserId(String taskNowUserId) {
        this.taskNowUserId = taskNowUserId;
    }

    public String getTaskNowUserName() {
        return taskNowUserName;
    }

    public void setTaskNowUserName(String taskNowUserName) {
        this.taskNowUserName = taskNowUserName;
    }

    public String getFileFlowStatusc() {
        return fileFlowStatusc;
    }

    public void setFileFlowStatusc(String fileFlowStatusc) {
        this.fileFlowStatusc = fileFlowStatusc;
    }

    public String getActId() {
        return actId;
    }

    public void setActId(String actId) {
        this.actId = actId;
    }

    public List<Map<String, Object>> getTaskUsetAndActList() {
        return taskUsetAndActList;
    }

    public void setTaskUsetAndActList(List<Map<String, Object>> taskUsetAndActList) {
        this.taskUsetAndActList = taskUsetAndActList;
    }

    public List<String> getTaskNames() {
        return taskNames;
    }

    public void setTaskNames(List<String> taskNames) {
        this.taskNames = taskNames;
    }

    public String getTaskReturnType() {
        return taskReturnType;
    }

    public void setTaskReturnType(String taskReturnType) {
        this.taskReturnType = taskReturnType;
    }

    public String getCtTaskid() {
        return ctTaskid;
    }

    public void setCtTaskid(String ctTaskid) {
        this.ctTaskid = ctTaskid;
    }

    public String getExplainStr() {
        return explainStr;
    }

    public void setExplainStr(String explainStr) {
        this.explainStr = explainStr;
    }

    public String gettCode() {
        return tCode;
    }

    public void settCode(String tCode) {
        this.tCode = tCode;
    }

    public String getTpEntityid() {
        return tpEntityid;
    }

    public void setTpEntityid(String tpEntityid) {
        this.tpEntityid = tpEntityid;
    }

    public String getTpId() {
        return tpId;
    }

    public void setTpId(String tpId) {
        this.tpId = tpId;
    }

    public String getTaskentityid() {
        return taskentityid;
    }

    public void setTaskentityid(String taskentityid) {
        this.taskentityid = taskentityid;
    }

    public List<String> getpNames() {
        return pNames;
    }

    public void setpNames(List<String> pNames) {
        this.pNames = pNames;
    }

    public List<String> getConNames() {
        return conNames;
    }

    public void setConNames(List<String> conNames) {
        this.conNames = conNames;
    }

    public List<String> getSecNames() {
        return secNames;
    }

    public void setSecNames(List<String> secNames) {
        this.secNames = secNames;
    }

    public List<String> getCreateNames() {
        return createNames;
    }

    public void setCreateNames(List<String> createNames) {
        this.createNames = createNames;
    }

    public List<String> getDoUserNames() {
        return doUserNames;
    }

    public void setDoUserNames(List<String> doUserNames) {
        this.doUserNames = doUserNames;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public List<String> getFileCreateNames() {
        return fileCreateNames;
    }

    public void setFileCreateNames(List<String> fileCreateNames) {
        this.fileCreateNames = fileCreateNames;
    }

    public String getStartDoTime() {
        return startDoTime;
    }

    public void setStartDoTime(String startDoTime) {
        this.startDoTime = startDoTime;
    }

    public String getEndDoTime() {
        return endDoTime;
    }

    public void setEndDoTime(String endDoTime) {
        this.endDoTime = endDoTime;
    }
}
