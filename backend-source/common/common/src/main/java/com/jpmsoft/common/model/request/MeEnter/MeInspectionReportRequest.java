package com.jpmsoft.common.model.request.MeEnter;

import com.jpmsoft.common.model.BaseFileRequest;

import java.util.Date;

/**
 * 检测报告
 */
public class MeInspectionReportRequest extends BaseFileRequest {

    private String irId;
    //见证送检ID
    private String wiId;
    //检测指引ID
    private String dgId;
    private String pId;
    private String conId;
    //检测报告名称
    private String irName;
    //检测结果
    private Integer irResult;
    //检测报告日期
    private Date irDate;
    //备注
    private String irRemark;
    private String irCreateUid;
    private String irCreateUname;
    private Date irCreateTime;
    private Integer irState;
    private Date irStateTime;

    private String wiUnitId;
    private String conName;
    private String pName;
    private String metName;
    private String conPartyBId;

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

    public String getWiUnitId() {
        return wiUnitId;
    }

    public void setWiUnitId(String wiUnitId) {
        this.wiUnitId = wiUnitId;
    }

    public String getConName() {
        return conName;
    }

    public void setConName(String conName) {
        this.conName = conName;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public String getMetName() {
        return metName;
    }

    public void setMetName(String metName) {
        this.metName = metName;
    }

    public String getConPartyBId() {
        return conPartyBId;
    }

    public void setConPartyBId(String conPartyBId) {
        this.conPartyBId = conPartyBId;
    }
    public String getDgId() {
        return dgId;
    }
    public void setDgId(String dgId) {
        this.dgId = dgId;
    }
}
