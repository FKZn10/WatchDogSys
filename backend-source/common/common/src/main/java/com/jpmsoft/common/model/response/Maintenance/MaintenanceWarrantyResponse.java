package com.jpmsoft.common.model.response.Maintenance;

import com.jpmsoft.common.model.ProjectCommonRes;

import java.util.Date;

/**
 * 质量保修书
 */
public class MaintenanceWarrantyResponse extends ProjectCommonRes {

    private String mwId;
    private String pId;
    private String secId;
    private String conId;
    private String mwCId;
    private String mwRemark;
    private String mwCreateUid;
    private String mwCreateUname;
    private Date mwCreateTime;
    private Integer mwState;
    private Date mwStateTime;

    private String conName;
    private String secName;
    private String mwCName;
    private String mwCNameHistory;
    //单位简称
    private String mwAbbName;


    public String getMwId() {
        return mwId;
    }

    public void setMwId(String mwId) {
        this.mwId = mwId;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getSecId() {
        return secId;
    }

    public void setSecId(String secId) {
        this.secId = secId;
    }

    public String getConId() {
        return conId;
    }

    public void setConId(String conId) {
        this.conId = conId;
    }

    public String getMwCId() {
        return mwCId;
    }

    public void setMwCId(String mwCId) {
        this.mwCId = mwCId;
    }

    public String getMwRemark() {
        return mwRemark;
    }

    public void setMwRemark(String mwRemark) {
        this.mwRemark = mwRemark;
    }

    public String getMwCreateUid() {
        return mwCreateUid;
    }

    public void setMwCreateUid(String mwCreateUid) {
        this.mwCreateUid = mwCreateUid;
    }

    public String getMwCreateUname() {
        return mwCreateUname;
    }

    public void setMwCreateUname(String mwCreateUname) {
        this.mwCreateUname = mwCreateUname;
    }

    public Date getMwCreateTime() {
        return mwCreateTime;
    }

    public void setMwCreateTime(Date mwCreateTime) {
        this.mwCreateTime = mwCreateTime;
    }

    public Integer getMwState() {
        return mwState;
    }

    public void setMwState(Integer mwState) {
        this.mwState = mwState;
    }

    public Date getMwStateTime() {
        return mwStateTime;
    }

    public void setMwStateTime(Date mwStateTime) {
        this.mwStateTime = mwStateTime;
    }


    public String getConName() {
        return conName;
    }

    public void setConName(String conName) {
        this.conName = conName;
    }

    public String getSecName() {
        return secName;
    }

    public void setSecName(String secName) {
        this.secName = secName;
    }

    public String getMwCName() {
        return mwCName;
    }

    public void setMwCName(String mwCName) {
        this.mwCName = mwCName;
    }


    public String getMwCNameHistory() {
        return mwCNameHistory;
    }

    public void setMwCNameHistory(String mwCNameHistory) {
        this.mwCNameHistory = mwCNameHistory;
    }

    public String getMwAbbName() {
        return mwAbbName;
    }

    public void setMwAbbName(String mwAbbName) {
        this.mwAbbName = mwAbbName;
    }
}
