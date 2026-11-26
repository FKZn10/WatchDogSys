package com.jpmsoft.common.model.response.MeEnter;


import com.jpmsoft.common.model.ProjectCommonRes;

import java.util.Date;

/**
 * 使用报审
 */
public class MeUseApplyResponse extends ProjectCommonRes {


    private String uaId;
    private String eaId;
    private String pId;
    private String conId;
    private String uaRemark;
    private String uaCreateUid;
    private String uaCreateUname;
    private Date uaCreateTime;
    private Integer uaState;
    private Date uaStateTime;


    private String conName;
    private String eaCode;

    public String getUaId() {
        return uaId;
    }

    public void setUaId(String uaId) {
        this.uaId = uaId;
    }

    public String getEaId() {
        return eaId;
    }

    public void setEaId(String eaId) {
        this.eaId = eaId;
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

    public String getUaRemark() {
        return uaRemark;
    }

    public void setUaRemark(String uaRemark) {
        this.uaRemark = uaRemark;
    }

    public String getUaCreateUid() {
        return uaCreateUid;
    }

    public void setUaCreateUid(String uaCreateUid) {
        this.uaCreateUid = uaCreateUid;
    }

    public String getUaCreateUname() {
        return uaCreateUname;
    }

    public void setUaCreateUname(String uaCreateUname) {
        this.uaCreateUname = uaCreateUname;
    }

    public Date getUaCreateTime() {
        return uaCreateTime;
    }

    public void setUaCreateTime(Date uaCreateTime) {
        this.uaCreateTime = uaCreateTime;
    }

    public Integer getUaState() {
        return uaState;
    }

    public void setUaState(Integer uaState) {
        this.uaState = uaState;
    }

    public Date getUaStateTime() {
        return uaStateTime;
    }

    public void setUaStateTime(Date uaStateTime) {
        this.uaStateTime = uaStateTime;
    }


    public String getConName() {
        return conName;
    }

    public void setConName(String conName) {
        this.conName = conName;
    }

    public String getEaCode() {
        return eaCode;
    }

    public void setEaCode(String eaCode) {
        this.eaCode = eaCode;
    }

}
