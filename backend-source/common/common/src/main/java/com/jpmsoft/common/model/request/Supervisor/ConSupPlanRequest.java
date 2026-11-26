package com.jpmsoft.common.model.request.Supervisor;

import com.jpmsoft.common.model.BaseFileRequest;

import java.util.Date;

/**
 * 监理规划及细则
 */
public class ConSupPlanRequest extends BaseFileRequest {
    private String spId;
    private String pId;
    private String conId;
    private String spTitle;
    private String spContent;
    private String spRemark;
    private String spFile;
    private String spImplementFile;
    private String spCreateUid;
    private String spCreateUname;
    private Date spCreateTime;
    private Integer spState;
    private Date spStateTime;

    private String pName;
    private String conName;
    private String spFileIds;
    private String spImplementFileIds;

    /**
     * 类型：01：旧，02：监理现场用表的监理规划
     */
    private String spType;

    public String getSpType() {
        return spType;
    }

    public void setSpType(String spType) {
        this.spType = spType;
    }

    public String getSpId() {
        return spId;
    }

    public void setSpId(String spId) {
        this.spId = spId;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getConId() {
        return conId;
    }

    public void setConId(String conId) {
        this.conId = conId;
    }

    public String getSpTitle() {
        return spTitle;
    }

    public void setSpTitle(String spTitle) {
        this.spTitle = spTitle;
    }

    public String getSpContent() {
        return spContent;
    }

    public void setSpContent(String spContent) {
        this.spContent = spContent;
    }

    public String getSpRemark() {
        return spRemark;
    }

    public void setSpRemark(String spRemark) {
        this.spRemark = spRemark;
    }

    public String getSpFile() {
        return spFile;
    }

    public void setSpFile(String spFile) {
        this.spFile = spFile;
    }

    public String getSpImplementFile() {
        return spImplementFile;
    }

    public void setSpImplementFile(String spImplementFile) {
        this.spImplementFile = spImplementFile;
    }

    public String getSpCreateUid() {
        return spCreateUid;
    }

    public void setSpCreateUid(String spCreateUid) {
        this.spCreateUid = spCreateUid;
    }

    public String getSpCreateUname() {
        return spCreateUname;
    }

    public void setSpCreateUname(String spCreateUname) {
        this.spCreateUname = spCreateUname;
    }

    public Date getSpCreateTime() {
        return spCreateTime;
    }

    public void setSpCreateTime(Date spCreateTime) {
        this.spCreateTime = spCreateTime;
    }

    public Integer getSpState() {
        return spState;
    }

    public void setSpState(Integer spState) {
        this.spState = spState;
    }

    public Date getSpStateTime() {
        return spStateTime;
    }

    public void setSpStateTime(Date spStateTime) {
        this.spStateTime = spStateTime;
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

    public String getSpFileIds() {
        return spFileIds;
    }

    public void setSpFileIds(String spFileIds) {
        this.spFileIds = spFileIds;
    }

    public String getSpImplementFileIds() {
        return spImplementFileIds;
    }

    public void setSpImplementFileIds(String spImplementFileIds) {
        this.spImplementFileIds = spImplementFileIds;
    }
}
