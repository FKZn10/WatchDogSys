package com.jpmsoft.common.model.request.proCp;

import com.jpmsoft.common.model.BaseFileRequest;

import java.util.Date;


public class ProjectCpSubtaskRequest extends BaseFileRequest {
    public String pcsId;
    public String cpdId;
    public String pcsTaskName;
    public String pcsHandler;
    public Date pcsPlanStartTime;
    public Date pcsPlanEndTime;
    public Date pcsActualStartTime;
    public Date pcsActualOkTime;
    public String pcsTaskContent;
    public String pcsOkStatus;
    public String pcsRemark;
    public String pcsProve;
    public String pcsProveIds;
    public String pcsCreateUname;
    public Date pcsCreateTime;
    public String pcsCreateUid;
    public String cpId;
    public String cpdName;
    public String pName;
    /**
     * 任务处理类型
     * 0：未处理
     */
    public Integer pending;


    public String getPcsCreateUid() {
        return pcsCreateUid;
    }

    public void setPcsCreateUid(String pcsCreateUid) {
        this.pcsCreateUid = pcsCreateUid;
    }

    public String getPcsProveIds() {
        return pcsProveIds;
    }

    public void setPcsProveIds(String pcsProveIds) {
        this.pcsProveIds = pcsProveIds;
    }

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

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public Integer getPending() {
        return pending;
    }

    public void setPending(Integer pending) {
        this.pending = pending;
    }
}
