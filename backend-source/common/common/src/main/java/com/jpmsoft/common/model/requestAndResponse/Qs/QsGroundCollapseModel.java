package com.jpmsoft.common.model.requestAndResponse.Qs;

import com.jpmsoft.common.model.ProjectCommonRes;

import java.util.Date;

/**
 * 地面坍塌情况
 */
public class QsGroundCollapseModel extends ProjectCommonRes {

    private String gcId;
    private String pId;
    private String gcHas;
    private String gcExplain;
    private Date gcTime;
    private Date gcHandleTime;
    private String gcRemark;
    private String gcCreateUid;
    private String gcCreateUname;
    private Date gcCreateTime;
    private Integer gcState;
    private Date gcStateTime;


    public String getGcId() {
        return gcId;
    }

    public void setGcId(String gcId) {
        this.gcId = gcId;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getGcHas() {
        return gcHas;
    }

    public void setGcHas(String gcHas) {
        this.gcHas = gcHas;
    }

    public String getGcExplain() {
        return gcExplain;
    }

    public void setGcExplain(String gcExplain) {
        this.gcExplain = gcExplain;
    }

    public Date getGcTime() {
        return gcTime;
    }

    public void setGcTime(Date gcTime) {
        this.gcTime = gcTime;
    }

    public Date getGcHandleTime() {
        return gcHandleTime;
    }

    public void setGcHandleTime(Date gcHandleTime) {
        this.gcHandleTime = gcHandleTime;
    }

    public String getGcRemark() {
        return gcRemark;
    }

    public void setGcRemark(String gcRemark) {
        this.gcRemark = gcRemark;
    }

    public String getGcCreateUid() {
        return gcCreateUid;
    }

    public void setGcCreateUid(String gcCreateUid) {
        this.gcCreateUid = gcCreateUid;
    }

    public String getGcCreateUname() {
        return gcCreateUname;
    }

    public void setGcCreateUname(String gcCreateUname) {
        this.gcCreateUname = gcCreateUname;
    }

    public Date getGcCreateTime() {
        return gcCreateTime;
    }

    public void setGcCreateTime(Date gcCreateTime) {
        this.gcCreateTime = gcCreateTime;
    }

    public Integer getGcState() {
        return gcState;
    }

    public void setGcState(Integer gcState) {
        this.gcState = gcState;
    }

    public Date getGcStateTime() {
        return gcStateTime;
    }

    public void setGcStateTime(Date gcStateTime) {
        this.gcStateTime = gcStateTime;
    }
}
