package com.jpmsoft.common.model.response.proCp;

import com.jpmsoft.common.model.ProjectCommonRes;

import java.util.Date;

public class ProjectCpSubtaskResponse extends ProjectCommonRes {
    public  String pcsId;
    public  String cpdId;
    public  String pcsTaskName;
    public  String pcsHandler;
    public  String pcsHandlerName;
    public  Date pcsPlanStartTime;
    public  Date pcsPlanEndTime;
    public  Date pcsActualStartTime;
    public  Date pcsActualOkTime;
    public  String pcsTaskContent;
    public  String pcsOkStatus;
    public  String pcsRemark;
    public  String pcsProve;
    public  String pcsCreateUname;
    public  Date pcsCreateTime;
    public  String cpId;
    public  String cpdName;


    public String getPcsId() {
        return pcsId;
    }

    public void setPcsId(String pcsId) {
        this.pcsId = pcsId;
    }

    public String getCpdId() {
        return cpdId;
    }

    public void setCpdId(String cpdId) {
        this.cpdId = cpdId;
    }

    public String getPcsTaskName() {
        return pcsTaskName;
    }

    public void setPcsTaskName(String pcsTaskName) {
        this.pcsTaskName = pcsTaskName;
    }

    public String getPcsHandler() {
        return pcsHandler;
    }

    public void setPcsHandler(String pcsHandler) {
        this.pcsHandler = pcsHandler;
    }

    public String getPcsHandlerName() {
        return pcsHandlerName;
    }

    public void setPcsHandlerName(String pcsHandlerName) {
        this.pcsHandlerName = pcsHandlerName;
    }

    public Date getPcsPlanStartTime() {
        return pcsPlanStartTime;
    }

    public void setPcsPlanStartTime(Date pcsPlanStartTime) {
        this.pcsPlanStartTime = pcsPlanStartTime;
    }

    public Date getPcsPlanEndTime() {
        return pcsPlanEndTime;
    }

    public void setPcsPlanEndTime(Date pcsPlanEndTime) {
        this.pcsPlanEndTime = pcsPlanEndTime;
    }

    public Date getPcsActualStartTime() {
        return pcsActualStartTime;
    }

    public void setPcsActualStartTime(Date pcsActualStartTime) {
        this.pcsActualStartTime = pcsActualStartTime;
    }

    public Date getPcsActualOkTime() {
        return pcsActualOkTime;
    }

    public void setPcsActualOkTime(Date pcsActualOkTime) {
        this.pcsActualOkTime = pcsActualOkTime;
    }

    public String getPcsTaskContent() {
        return pcsTaskContent;
    }

    public void setPcsTaskContent(String pcsTaskContent) {
        this.pcsTaskContent = pcsTaskContent;
    }

    public String getPcsOkStatus() {
        return pcsOkStatus;
    }

    public void setPcsOkStatus(String pcsOkStatus) {
        this.pcsOkStatus = pcsOkStatus;
    }

    public String getPcsRemark() {
        return pcsRemark;
    }

    public void setPcsRemark(String pcsRemark) {
        this.pcsRemark = pcsRemark;
    }

    public String getPcsProve() {
        return pcsProve;
    }

    public void setPcsProve(String pcsProve) {
        this.pcsProve = pcsProve;
    }

    public String getPcsCreateUname() {
        return pcsCreateUname;
    }

    public void setPcsCreateUname(String pcsCreateUname) {
        this.pcsCreateUname = pcsCreateUname;
    }

    public Date getPcsCreateTime() {
        return pcsCreateTime;
    }

    public void setPcsCreateTime(Date pcsCreateTime) {
        this.pcsCreateTime = pcsCreateTime;
    }

    public String getCpId() {
        return cpId;
    }

    public void setCpId(String cpId) {
        this.cpId = cpId;
    }

    public String getCpdName() {
        return cpdName;
    }

    public void setCpdName(String cpdName) {
        this.cpdName = cpdName;
    }

}
