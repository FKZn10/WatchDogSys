package com.jpmsoft.common.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jpmsoft.common.model.BaseModeRequest;

import java.util.List;

/**
 * 流程管理
 * 流程实例
 */
public class SysFileRequest extends BaseModeRequest {
    private String filetitle;
    private List<String> filetitles;
    private String cName;
    private List<String> cNames;
    private String tName;
    private List<String> tNames;
    private String secName;
    private List<String> secNames;
    private String pName;
    private List<String> pNames;
    private String conName;
    private List<String> conNames;
    //是否已启动流程
    private Boolean started;
    private String uId;
    private String uName;
    private List<String> uNames;
    private String fileCreateDate;
    private List<String> fileCreateDates;
    private String fileflowstatus;
    private List<String> fileflowstatuses;
    private String taskName;
    private List<String> taskNames;
    private String filecode;
    private String pBuildModel;
    private String pMajorType;
    private String pType;
    public  String pmType;

    private String fileid;

    @JsonProperty("tCode")
    private String tCode;

    private String startTime;
    private String endTime;

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String gettCode() {
        return tCode;
    }

    public void settCode(String tCode) {
        this.tCode = tCode;
    }

    public String getFileid() {
        return fileid;
    }

    public void setFileid(String fileid) {
        this.fileid = fileid;
    }

    public String getFiletitle() {
        return filetitle;
    }

    public void setFiletitle(String filetitle) {
        this.filetitle = filetitle;
    }

    public String getcName() {
        return cName;
    }

    public void setcName(String cName) {
        this.cName = cName;
    }

    public String gettName() {
        return tName;
    }

    public void settName(String tName) {
        this.tName = tName;
    }

    public String getSecName() {
        return secName;
    }

    public void setSecName(String secName) {
        this.secName = secName;
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

    public Boolean getStarted() {
        return started;
    }

    public void setStarted(Boolean started) {
        this.started = started;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getuName() {
        return uName;
    }

    public void setuName(String uName) {
        this.uName = uName;
    }

    public String getFileCreateDate() {
        return fileCreateDate;
    }

    public void setFileCreateDate(String fileCreateDate) {
        this.fileCreateDate = fileCreateDate;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getFileflowstatus() {
        return fileflowstatus;
    }

    public void setFileflowstatus(String fileflowstatus) {
        this.fileflowstatus = fileflowstatus;
    }

    public String getFilecode() {
        return filecode;
    }

    public void setFilecode(String filecode) {
        this.filecode = filecode;
    }

    public String getpBuildModel() {
        return pBuildModel;
    }

    public void setpBuildModel(String pBuildModel) {
        this.pBuildModel = pBuildModel;
    }

    public String getpMajorType() {
        return pMajorType;
    }

    public void setpMajorType(String pMajorType) {
        this.pMajorType = pMajorType;
    }

    public String getpType() {
        return pType;
    }

    public void setpType(String pType) {
        this.pType = pType;
    }

    public List<String> getFileCreateDates() {
        return fileCreateDates;
    }

    public void setFileCreateDates(List<String> fileCreateDates) {
        this.fileCreateDates = fileCreateDates;
    }

    public List<String> getFiletitles() {
        return filetitles;
    }

    public void setFiletitles(List<String> filetitles) {
        this.filetitles = filetitles;
    }

    public String getPmType() {
        return pmType;
    }

    public void setPmType(String pmType) {
        this.pmType = pmType;
    }

    public List<String> getcNames() {
        return cNames;
    }

    public void setcNames(List<String> cNames) {
        this.cNames = cNames;
    }

    public List<String> gettNames() {
        return tNames;
    }

    public void settNames(List<String> tNames) {
        this.tNames = tNames;
    }

    public List<String> getSecNames() {
        return secNames;
    }

    public void setSecNames(List<String> secNames) {
        this.secNames = secNames;
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

    public List<String> getuNames() {
        return uNames;
    }

    public void setuNames(List<String> uNames) {
        this.uNames = uNames;
    }

    public List<String> getTaskNames() {
        return taskNames;
    }

    public void setTaskNames(List<String> taskNames) {
        this.taskNames = taskNames;
    }

    public List<String> getFileflowstatuses() {
        return fileflowstatuses;
    }

    public void setFileflowstatuses(List<String> fileflowstatuses) {
        this.fileflowstatuses = fileflowstatuses;
    }
}
