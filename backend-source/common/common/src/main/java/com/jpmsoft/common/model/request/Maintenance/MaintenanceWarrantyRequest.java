package com.jpmsoft.common.model.request.Maintenance;

import com.jpmsoft.common.model.BaseFileRequest;

import java.util.Date;
import java.util.List;

/**
 * 质量保修书
 */
public class MaintenanceWarrantyRequest extends BaseFileRequest {

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

    private String pName;
    private String conName;
    private String mwCName;
    private List<String> mwCreateTimes;

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

    public String getMwCName() {
        return mwCName;
    }

    public void setMwCName(String mwCName) {
        this.mwCName = mwCName;
    }

    public List<String> getMwCreateTimes() {
        return mwCreateTimes;
    }

    public void setMwCreateTimes(List<String> mwCreateTimes) {
        this.mwCreateTimes = mwCreateTimes;
    }
}
