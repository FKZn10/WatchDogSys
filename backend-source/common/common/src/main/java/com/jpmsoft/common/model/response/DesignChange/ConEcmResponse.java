package com.jpmsoft.common.model.response.DesignChange;

import com.jpmsoft.common.model.ProjectCommonRes;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 专家论证会申请
 */
public class ConEcmResponse extends ProjectCommonRes {

    private String ecmId;
    private String pId;
    private String enisId;
    private String ecmName;
    private String ecmType;
    private String ecmOtherNote;
    private String ecmReason;
    private String ecmContent;
    private BigDecimal ecmAmount;
    private String ecmCreateUid;
    private String ecmCreateUname;
    private Date ecmCreateTime;
    private Integer ecmState;
    private Date ecmStateTime;
    private String dcoId;
    private Boolean ecmYesNo;
    private Date ecmAdoptTime;
    private Boolean ecmIsHistory;
    private String ecmDep;
    private String ecmApprovalMatter;

    private String ecmDepName;
    private String enisCode;
    private String  dcoCode;
    private String  dcoProjectName;
    private String dcoName;
    private String  dcoMajor;

    public String getEcmId() {
        return ecmId;
    }

    public void setEcmId(String ecmId) {
        this.ecmId = ecmId;
    }

    @Override
    public String getpId() {
        return pId;
    }

    @Override
    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getEnisId() {
        return enisId;
    }

    public void setEnisId(String enisId) {
        this.enisId = enisId;
    }

    public String getEcmName() {
        return ecmName;
    }

    public void setEcmName(String ecmName) {
        this.ecmName = ecmName;
    }

    public String getEcmType() {
        return ecmType;
    }

    public void setEcmType(String ecmType) {
        this.ecmType = ecmType;
    }

    public String getEcmOtherNote() {
        return ecmOtherNote;
    }

    public void setEcmOtherNote(String ecmOtherNote) {
        this.ecmOtherNote = ecmOtherNote;
    }

    public String getEcmReason() {
        return ecmReason;
    }

    public void setEcmReason(String ecmReason) {
        this.ecmReason = ecmReason;
    }

    public String getEcmContent() {
        return ecmContent;
    }

    public void setEcmContent(String ecmContent) {
        this.ecmContent = ecmContent;
    }

    public BigDecimal getEcmAmount() {
        return ecmAmount;
    }

    public void setEcmAmount(BigDecimal ecmAmount) {
        this.ecmAmount = ecmAmount;
    }

    public String getEcmCreateUid() {
        return ecmCreateUid;
    }

    public void setEcmCreateUid(String ecmCreateUid) {
        this.ecmCreateUid = ecmCreateUid;
    }

    public String getEcmCreateUname() {
        return ecmCreateUname;
    }

    public void setEcmCreateUname(String ecmCreateUname) {
        this.ecmCreateUname = ecmCreateUname;
    }

    public Date getEcmCreateTime() {
        return ecmCreateTime;
    }

    public void setEcmCreateTime(Date ecmCreateTime) {
        this.ecmCreateTime = ecmCreateTime;
    }

    public Integer getEcmState() {
        return ecmState;
    }

    public void setEcmState(Integer ecmState) {
        this.ecmState = ecmState;
    }

    public Date getEcmStateTime() {
        return ecmStateTime;
    }

    public void setEcmStateTime(Date ecmStateTime) {
        this.ecmStateTime = ecmStateTime;
    }

    public String getDcoId() {
        return dcoId;
    }

    public void setDcoId(String dcoId) {
        this.dcoId = dcoId;
    }

    public Boolean getEcmYesNo() {
        return ecmYesNo;
    }

    public void setEcmYesNo(Boolean ecmYesNo) {
        this.ecmYesNo = ecmYesNo;
    }

    public Date getEcmAdoptTime() {
        return ecmAdoptTime;
    }

    public void setEcmAdoptTime(Date ecmAdoptTime) {
        this.ecmAdoptTime = ecmAdoptTime;
    }

    public Boolean getEcmIsHistory() {
        return ecmIsHistory;
    }

    public void setEcmIsHistory(Boolean ecmIsHistory) {
        this.ecmIsHistory = ecmIsHistory;
    }

    public String getEcmDep() {
        return ecmDep;
    }

    public void setEcmDep(String ecmDep) {
        this.ecmDep = ecmDep;
    }

    public String getEcmApprovalMatter() {
        return ecmApprovalMatter;
    }

    public void setEcmApprovalMatter(String ecmApprovalMatter) {
        this.ecmApprovalMatter = ecmApprovalMatter;
    }

    public String getEcmDepName() {
        return ecmDepName;
    }

    public void setEcmDepName(String ecmDepName) {
        this.ecmDepName = ecmDepName;
    }

    public String getEnisCode() {
        return enisCode;
    }

    public void setEnisCode(String enisCode) {
        this.enisCode = enisCode;
    }

    public String getDcoCode() {
        return dcoCode;
    }

    public void setDcoCode(String dcoCode) {
        this.dcoCode = dcoCode;
    }

    public String getDcoProjectName() {
        return dcoProjectName;
    }

    public void setDcoProjectName(String dcoProjectName) {
        this.dcoProjectName = dcoProjectName;
    }

    public String getDcoName() {
        return dcoName;
    }

    public void setDcoName(String dcoName) {
        this.dcoName = dcoName;
    }

    public String getDcoMajor() {
        return dcoMajor;
    }

    public void setDcoMajor(String dcoMajor) {
        this.dcoMajor = dcoMajor;
    }
}
