package com.jpmsoft.common.model.response.MeEnter;

import com.jpmsoft.common.model.ProjectCommonRes;

import java.util.Date;

/**
 * 检测报告
 */
public class MeInspectionReportResponse extends ProjectCommonRes {

    private String irId;
        //检测指引ID
    private String dgId;
    private String wiId;
    private String pId;
    private String conId;
    private String irName;
    private Integer irResult;
    private Date irDate;
    private String irRemark;
    private String irCreateUid;
    private String irCreateUname;
    private Date irCreateTime;
    private Integer irState;
    private Date irStateTime;


    /**********************************/
    private String conName;
    private String metName;
    private String wiUnitName;
    private String conPartyBId;
    private String conPartyBName;
    private String eaCode;
    private String wiUnitNameHistory;
    private String conPartyBNameHistory;
    //单位简称
    private String  wiUnitAbbName;
    private String  conPartyBAbbName;

    public String getIrId() {
        return irId;
    }

    public void setIrId(String irId) {
        this.irId = irId;
    }

    public String getWiId() {
        return wiId;
    }

    public void setWiId(String wiId) {
        this.wiId = wiId;
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

    public String getIrName() {
        return irName;
    }

    public void setIrName(String irName) {
        this.irName = irName;
    }

    public Integer getIrResult() {
        return irResult;
    }

    public void setIrResult(Integer irResult) {
        this.irResult = irResult;
    }

    public Date getIrDate() {
        return irDate;
    }

    public void setIrDate(Date irDate) {
        this.irDate = irDate;
    }

    public String getIrRemark() {
        return irRemark;
    }

    public void setIrRemark(String irRemark) {
        this.irRemark = irRemark;
    }

    public String getIrCreateUid() {
        return irCreateUid;
    }

    public void setIrCreateUid(String irCreateUid) {
        this.irCreateUid = irCreateUid;
    }

    public String getIrCreateUname() {
        return irCreateUname;
    }

    public void setIrCreateUname(String irCreateUname) {
        this.irCreateUname = irCreateUname;
    }

    public Date getIrCreateTime() {
        return irCreateTime;
    }

    public void setIrCreateTime(Date irCreateTime) {
        this.irCreateTime = irCreateTime;
    }

    public Integer getIrState() {
        return irState;
    }

    public void setIrState(Integer irState) {
        this.irState = irState;
    }

    public Date getIrStateTime() {
        return irStateTime;
    }

    public void setIrStateTime(Date irStateTime) {
        this.irStateTime = irStateTime;
    }

    public String getConName() {
        return conName;
    }

    public void setConName(String conName) {
        this.conName = conName;
    }


    public String getMetName() {
        return metName;
    }

    public void setMetName(String metName) {
        this.metName = metName;
    }

    public String getWiUnitName() {
        return wiUnitName;
    }

    public void setWiUnitName(String wiUnitName) {
        this.wiUnitName = wiUnitName;
    }

    public String getConPartyBId() {
        return conPartyBId;
    }

    public void setConPartyBId(String conPartyBId) {
        this.conPartyBId = conPartyBId;
    }

    public String getConPartyBName() {
        return conPartyBName;
    }

    public void setConPartyBName(String conPartyBName) {
        this.conPartyBName = conPartyBName;
    }

    public String getEaCode() {
        return eaCode;
    }

    public void setEaCode(String eaCode) {
        this.eaCode = eaCode;
    }

    public String getWiUnitNameHistory() {
        return wiUnitNameHistory;
    }

    public void setWiUnitNameHistory(String wiUnitNameHistory) {
        this.wiUnitNameHistory = wiUnitNameHistory;
    }

    public String getConPartyBNameHistory() {
        return conPartyBNameHistory;
    }

    public void setConPartyBNameHistory(String conPartyBNameHistory) {
        this.conPartyBNameHistory = conPartyBNameHistory;
    }

    public String getWiUnitAbbName() {
        return wiUnitAbbName;
    }

    public void setWiUnitAbbName(String wiUnitAbbName) {
        this.wiUnitAbbName = wiUnitAbbName;
    }

    public String getConPartyBAbbName() {
        return conPartyBAbbName;
    }

    public void setConPartyBAbbName(String conPartyBAbbName) {
        this.conPartyBAbbName = conPartyBAbbName;
    }

    public String getDgId() {
        return dgId;
    }
    public void setDgId(String dgId) {
        this.dgId = dgId;
    }
}
