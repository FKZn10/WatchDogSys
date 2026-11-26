package com.jpmsoft.common.model.request.Supervisor;

import com.jpmsoft.common.model.BaseFileRequest;

import java.util.Date;

/**
 * 工程交底
 */
public class ConSupDisclosureRequest extends BaseFileRequest {

    private String disId;
    private String pId;
    private String conId;
    private String disUnitId;
    private String disUnitDId;
    private String disType;
    private Date disDate;
    private String disContent;
    private String disFile;
    private String disCreateUid;
    private String disCreateUname;
    private Date disCreateTime;
    private Integer disState;
    private Date disStateTime;

    private String pName;
    private String conName;
    private String disFileIds;

    public String getDisId() {
        return disId;
    }

    public void setDisId(String disId) {
        this.disId = disId;
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

    public String getDisUnitId() {
        return disUnitId;
    }

    public void setDisUnitId(String disUnitId) {
        this.disUnitId = disUnitId;
    }

    public String getDisUnitDId() {
        return disUnitDId;
    }

    public void setDisUnitDId(String disUnitDId) {
        this.disUnitDId = disUnitDId;
    }

    public String getDisType() {
        return disType;
    }

    public void setDisType(String disType) {
        this.disType = disType;
    }

    public Date getDisDate() {
        return disDate;
    }

    public void setDisDate(Date disDate) {
        this.disDate = disDate;
    }

    public String getDisContent() {
        return disContent;
    }

    public void setDisContent(String disContent) {
        this.disContent = disContent;
    }

    public String getDisFile() {
        return disFile;
    }

    public void setDisFile(String disFile) {
        this.disFile = disFile;
    }

    public String getDisCreateUid() {
        return disCreateUid;
    }

    public void setDisCreateUid(String disCreateUid) {
        this.disCreateUid = disCreateUid;
    }

    public String getDisCreateUname() {
        return disCreateUname;
    }

    public void setDisCreateUname(String disCreateUname) {
        this.disCreateUname = disCreateUname;
    }

    public Date getDisCreateTime() {
        return disCreateTime;
    }

    public void setDisCreateTime(Date disCreateTime) {
        this.disCreateTime = disCreateTime;
    }

    public Integer getDisState() {
        return disState;
    }

    public void setDisState(Integer disState) {
        this.disState = disState;
    }

    public Date getDisStateTime() {
        return disStateTime;
    }

    public void setDisStateTime(Date disStateTime) {
        this.disStateTime = disStateTime;
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

    public String getDisFileIds() {
        return disFileIds;
    }

    public void setDisFileIds(String disFileIds) {
        this.disFileIds = disFileIds;
    }
}
