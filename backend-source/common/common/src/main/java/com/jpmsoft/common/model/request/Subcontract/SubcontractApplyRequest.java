package com.jpmsoft.common.model.request.Subcontract;

import com.jpmsoft.common.model.BaseFileRequest;

import java.util.Date;

/**
 * 分包申请
 */
public class SubcontractApplyRequest extends BaseFileRequest {

    private String saId;
    private String saTitle;
    private String pId;
    private String saGcUnit;
    private String saUnit;
    private String saRemark;
    private String saCreateUid;
    private String saCreateUname;
    private Date saCreateTime;
    private Integer saState;
    private Date saStateTime;

    private String pName;

    public String getSaId() {
        return saId;
    }

    public void setSaId(String saId) {
        this.saId = saId;
    }

    public String getSaTitle() {
        return saTitle;
    }

    public void setSaTitle(String saTitle) {
        this.saTitle = saTitle;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getSaGcUnit() {
        return saGcUnit;
    }

    public void setSaGcUnit(String saGcUnit) {
        this.saGcUnit = saGcUnit;
    }

    public String getSaUnit() {
        return saUnit;
    }

    public void setSaUnit(String saUnit) {
        this.saUnit = saUnit;
    }

    public String getSaRemark() {
        return saRemark;
    }

    public void setSaRemark(String saRemark) {
        this.saRemark = saRemark;
    }

    public String getSaCreateUid() {
        return saCreateUid;
    }

    public void setSaCreateUid(String saCreateUid) {
        this.saCreateUid = saCreateUid;
    }

    public String getSaCreateUname() {
        return saCreateUname;
    }

    public void setSaCreateUname(String saCreateUname) {
        this.saCreateUname = saCreateUname;
    }

    public Date getSaCreateTime() {
        return saCreateTime;
    }

    public void setSaCreateTime(Date saCreateTime) {
        this.saCreateTime = saCreateTime;
    }

    public Integer getSaState() {
        return saState;
    }

    public void setSaState(Integer saState) {
        this.saState = saState;
    }

    public Date getSaStateTime() {
        return saStateTime;
    }

    public void setSaStateTime(Date saStateTime) {
        this.saStateTime = saStateTime;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }
}
