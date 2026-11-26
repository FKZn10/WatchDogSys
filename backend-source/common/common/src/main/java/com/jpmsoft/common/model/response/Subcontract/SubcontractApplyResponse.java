package com.jpmsoft.common.model.response.Subcontract;

import com.jpmsoft.common.model.ProjectCommonRes;

import java.util.Date;

/**
 * 分包申请
 */
public class SubcontractApplyResponse extends ProjectCommonRes {

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

    private String saGcUnitName;
    private String saUnitName;
    private String saGcUnitNameHistory;
    private String saUnitNameHistory;
    //单位简称
    private String  saGcUnitAbbName;
    private String  saUnitAbbName;


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

    public String getSaGcUnitName() {
        return saGcUnitName;
    }

    public void setSaGcUnitName(String saGcUnitName) {
        this.saGcUnitName = saGcUnitName;
    }

    public String getSaUnitName() {
        return saUnitName;
    }

    public void setSaUnitName(String saUnitName) {
        this.saUnitName = saUnitName;
    }

    public String getSaGcUnitNameHistory() {
        return saGcUnitNameHistory;
    }

    public void setSaGcUnitNameHistory(String saGcUnitNameHistory) {
        this.saGcUnitNameHistory = saGcUnitNameHistory;
    }

    public String getSaUnitNameHistory() {
        return saUnitNameHistory;
    }

    public void setSaUnitNameHistory(String saUnitNameHistory) {
        this.saUnitNameHistory = saUnitNameHistory;
    }

    public String getSaGcUnitAbbName() {
        return saGcUnitAbbName;
    }

    public void setSaGcUnitAbbName(String saGcUnitAbbName) {
        this.saGcUnitAbbName = saGcUnitAbbName;
    }

    public String getSaUnitAbbName() {
        return saUnitAbbName;
    }

    public void setSaUnitAbbName(String saUnitAbbName) {
        this.saUnitAbbName = saUnitAbbName;
    }
}
