package com.jpmsoft.common.model.response.Supervisor;

import com.jpmsoft.common.model.ProjectCommonRes;

import java.util.Date;

/**
 * 工程交底
 */
public class ConSupDisclosureResponse extends ProjectCommonRes {

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

    private String conName;
    private String disUnitName;
    private String disUnitDName;
    private String disUnitNameHistory;
    private String disUnitDNameHistory;
    //单位简称
    private String disUnitAbbName;
    private String disUnitDAbbName;

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

    public String getConName() {
        return conName;
    }

    public void setConName(String conName) {
        this.conName = conName;
    }

    public String getDisUnitName() {
        return disUnitName;
    }

    public void setDisUnitName(String disUnitName) {
        this.disUnitName = disUnitName;
    }

    public String getDisUnitDName() {
        return disUnitDName;
    }

    public void setDisUnitDName(String disUnitDName) {
        this.disUnitDName = disUnitDName;
    }

    public String getDisUnitNameHistory() {
        return disUnitNameHistory;
    }

    public void setDisUnitNameHistory(String disUnitNameHistory) {
        this.disUnitNameHistory = disUnitNameHistory;
    }

    public String getDisUnitDNameHistory() {
        return disUnitDNameHistory;
    }

    public void setDisUnitDNameHistory(String disUnitDNameHistory) {
        this.disUnitDNameHistory = disUnitDNameHistory;
    }

    public String getDisUnitAbbName() {
        return disUnitAbbName;
    }

    public void setDisUnitAbbName(String disUnitAbbName) {
        this.disUnitAbbName = disUnitAbbName;
    }

    public String getDisUnitDAbbName() {
        return disUnitDAbbName;
    }

    public void setDisUnitDAbbName(String disUnitDAbbName) {
        this.disUnitDAbbName = disUnitDAbbName;
    }
}
