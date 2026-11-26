package com.jpmsoft.common.model.response.Contract;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.jpmsoft.common.annotations.ClassInfo;
import com.jpmsoft.common.model.ProjectCommonRes;
import com.jpmsoft.common.model.request.Contract.ConEcFeeRecordDto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 变更费用申报
 */
public class ConEcFeeResponse extends ProjectCommonRes {

    private String ecfId;
    private String ecId;
    private String ecfContractor;
    private String ecfSupervisor;
    private String ecfCode;
    private String ecfImplementation;
    private String ecfGrade;
    private Integer ecfApplyMoneyType;
    private BigDecimal ecfApplyMoney;
    private Integer ecfDurationType;
    private Integer ecfDuration;
    private String ecfRemark;
    private String ecfCreateUid;
    private String ecfCreateUname;
    private Date ecfCreateTime;
    private Integer ecfState;
    private Date ecfStateTime;
    private String ecfAnnex;
    private Float ecfProportion;
    private String ecfPm;
    private String ecfApplyUnit;
    private String pId;
    private String conId;
    private String ecfContent;
    private BigDecimal ecfApplyMoneyTotal;
    private String ecfChangeType;
    private String dcoId;
    /************************************/
    private BigDecimal dcbEstimateAmt;
    private String dcoName;
    private String dcoCode;
    private String dcoProjectName;
    private String dcoSubCode;
    private String dcoMajor;
    private String ecCode;
    private BigDecimal ecApplyMoney;
    private String ecMainCause;
    private String ecPart;
    private String ecReason;
    private String ecfApplyUnitName;
    private String ecfApplyUnitAbbName;
    private String conName;
    private String conCode;
    private String conMoneyTotal;
    private String ecfContractorName;
    private String ecfSupervisorName;
    private String ecfContractorNameHistory;
    private String ecfSupervisorNameHistory;
    //单位简称
    private String ecfContractorAbbName;
    private String ecfSupervisorAbbName;
    private String conPartyBName;

    private BigDecimal ecfPreliminaryAmount;

    private String ecConstructionName;

    private String ecChangeType;

    private String ecGrade;


    private List<ConEcFeeRecordDto> conMeasureRecords;


    public String getEcConstructionName() {
        return ecConstructionName;
    }

    public void setEcConstructionName(String ecConstructionName) {
        this.ecConstructionName = ecConstructionName;
    }

    public String getEcChangeType() {
        return ecChangeType;
    }

    public void setEcChangeType(String ecChangeType) {
        this.ecChangeType = ecChangeType;
    }

    public String getEcGrade() {
        return ecGrade;
    }

    public void setEcGrade(String ecGrade) {
        this.ecGrade = ecGrade;
    }

    public List<ConEcFeeRecordDto> getConMeasureRecords() {
        return conMeasureRecords;
    }

    public void setConMeasureRecords(List<ConEcFeeRecordDto> conMeasureRecords) {
        this.conMeasureRecords = conMeasureRecords;
    }

    public BigDecimal getEcfPreliminaryAmount() {
        return ecfPreliminaryAmount;
    }

    public void setEcfPreliminaryAmount(BigDecimal ecfPreliminaryAmount) {
        this.ecfPreliminaryAmount = ecfPreliminaryAmount;
    }

    public String getEcfId() {
        return ecfId;
    }

    public void setEcfId(String ecfId) {
        this.ecfId = ecfId;
    }

    public String getEcId() {
        return ecId;
    }

    public void setEcId(String ecId) {
        this.ecId = ecId;
    }

    public String getEcfContractor() {
        return ecfContractor;
    }

    public void setEcfContractor(String ecfContractor) {
        this.ecfContractor = ecfContractor;
    }

    public String getEcfSupervisor() {
        return ecfSupervisor;
    }

    public void setEcfSupervisor(String ecfSupervisor) {
        this.ecfSupervisor = ecfSupervisor;
    }

    public String getEcfCode() {
        return ecfCode;
    }

    public void setEcfCode(String ecfCode) {
        this.ecfCode = ecfCode;
    }

    public String getEcfImplementation() {
        return ecfImplementation;
    }

    public void setEcfImplementation(String ecfImplementation) {
        this.ecfImplementation = ecfImplementation;
    }

    public String getEcfGrade() {
        return ecfGrade;
    }

    public void setEcfGrade(String ecfGrade) {
        this.ecfGrade = ecfGrade;
    }

    public Integer getEcfApplyMoneyType() {
        return ecfApplyMoneyType;
    }

    public void setEcfApplyMoneyType(Integer ecfApplyMoneyType) {
        this.ecfApplyMoneyType = ecfApplyMoneyType;
    }

    public BigDecimal getEcfApplyMoney() {
        return ecfApplyMoney;
    }

    public void setEcfApplyMoney(BigDecimal ecfApplyMoney) {
        this.ecfApplyMoney = ecfApplyMoney;
    }

    public Integer getEcfDurationType() {
        return ecfDurationType;
    }

    public void setEcfDurationType(Integer ecfDurationType) {
        this.ecfDurationType = ecfDurationType;
    }

    public Integer getEcfDuration() {
        return ecfDuration;
    }

    public void setEcfDuration(Integer ecfDuration) {
        this.ecfDuration = ecfDuration;
    }

    public String getEcfRemark() {
        return ecfRemark;
    }

    public void setEcfRemark(String ecfRemark) {
        this.ecfRemark = ecfRemark;
    }

    public String getEcfCreateUid() {
        return ecfCreateUid;
    }

    public void setEcfCreateUid(String ecfCreateUid) {
        this.ecfCreateUid = ecfCreateUid;
    }

    public String getEcfCreateUname() {
        return ecfCreateUname;
    }

    public void setEcfCreateUname(String ecfCreateUname) {
        this.ecfCreateUname = ecfCreateUname;
    }

    public Date getEcfCreateTime() {
        return ecfCreateTime;
    }

    public void setEcfCreateTime(Date ecfCreateTime) {
        this.ecfCreateTime = ecfCreateTime;
    }

    public Integer getEcfState() {
        return ecfState;
    }

    public void setEcfState(Integer ecfState) {
        this.ecfState = ecfState;
    }

    public Date getEcfStateTime() {
        return ecfStateTime;
    }

    public void setEcfStateTime(Date ecfStateTime) {
        this.ecfStateTime = ecfStateTime;
    }

    public String getEcfAnnex() {
        return ecfAnnex;
    }

    public void setEcfAnnex(String ecfAnnex) {
        this.ecfAnnex = ecfAnnex;
    }

    public Float getEcfProportion() {
        return ecfProportion;
    }

    public void setEcfProportion(Float ecfProportion) {
        this.ecfProportion = ecfProportion;
    }

    public String getEcfPm() {
        return ecfPm;
    }

    public void setEcfPm(String ecfPm) {
        this.ecfPm = ecfPm;
    }

    public String getEcfApplyUnit() {
        return ecfApplyUnit;
    }

    public void setEcfApplyUnit(String ecfApplyUnit) {
        this.ecfApplyUnit = ecfApplyUnit;
    }

    public BigDecimal getDcbEstimateAmt() {
        return dcbEstimateAmt;
    }

    public void setDcbEstimateAmt(BigDecimal dcbEstimateAmt) {
        this.dcbEstimateAmt = dcbEstimateAmt;
    }

    public String getDcoName() {
        return dcoName;
    }

    public void setDcoName(String dcoName) {
        this.dcoName = dcoName;
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

    public String getDcoSubCode() {
        return dcoSubCode;
    }

    public void setDcoSubCode(String dcoSubCode) {
        this.dcoSubCode = dcoSubCode;
    }

    public String getDcoMajor() {
        return dcoMajor;
    }

    public void setDcoMajor(String dcoMajor) {
        this.dcoMajor = dcoMajor;
    }

    public String getEcCode() {
        return ecCode;
    }

    public void setEcCode(String ecCode) {
        this.ecCode = ecCode;
    }

    public BigDecimal getEcApplyMoney() {
        return ecApplyMoney;
    }

    public void setEcApplyMoney(BigDecimal ecApplyMoney) {
        this.ecApplyMoney = ecApplyMoney;
    }

    public String getEcMainCause() {
        return ecMainCause;
    }

    public void setEcMainCause(String ecMainCause) {
        this.ecMainCause = ecMainCause;
    }

    public String getEcPart() {
        return ecPart;
    }

    public void setEcPart(String ecPart) {
        this.ecPart = ecPart;
    }

    public String getEcReason() {
        return ecReason;
    }

    public void setEcReason(String ecReason) {
        this.ecReason = ecReason;
    }

    public String getEcfApplyUnitName() {
        return ecfApplyUnitName;
    }

    public void setEcfApplyUnitName(String ecfApplyUnitName) {
        this.ecfApplyUnitName = ecfApplyUnitName;
    }

    public String getEcfApplyUnitAbbName() {
        return ecfApplyUnitAbbName;
    }

    public void setEcfApplyUnitAbbName(String ecfApplyUnitAbbName) {
        this.ecfApplyUnitAbbName = ecfApplyUnitAbbName;
    }

    public String getConName() {
        return conName;
    }

    public void setConName(String conName) {
        this.conName = conName;
    }

    public String getConCode() {
        return conCode;
    }

    public void setConCode(String conCode) {
        this.conCode = conCode;
    }

    public String getConMoneyTotal() {
        return conMoneyTotal;
    }

    public void setConMoneyTotal(String conMoneyTotal) {
        this.conMoneyTotal = conMoneyTotal;
    }

    public String getEcfContractorName() {
        return ecfContractorName;
    }

    public void setEcfContractorName(String ecfContractorName) {
        this.ecfContractorName = ecfContractorName;
    }

    public String getEcfSupervisorName() {
        return ecfSupervisorName;
    }

    public void setEcfSupervisorName(String ecfSupervisorName) {
        this.ecfSupervisorName = ecfSupervisorName;
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

    public String getEcfContent() {
        return ecfContent;
    }

    public void setEcfContent(String ecfContent) {
        this.ecfContent = ecfContent;
    }

    public BigDecimal getEcfApplyMoneyTotal() {
        return ecfApplyMoneyTotal;
    }

    public void setEcfApplyMoneyTotal(BigDecimal ecfApplyMoneyTotal) {
        this.ecfApplyMoneyTotal = ecfApplyMoneyTotal;
    }

    public String getEcfChangeType() {
        return ecfChangeType;
    }

    public void setEcfChangeType(String ecfChangeType) {
        this.ecfChangeType = ecfChangeType;
    }

    public String getDcoId() {
        return dcoId;
    }

    public void setDcoId(String dcoId) {
        this.dcoId = dcoId;
    }

    public String getEcfContractorNameHistory() {
        return ecfContractorNameHistory;
    }

    public void setEcfContractorNameHistory(String ecfContractorNameHistory) {
        this.ecfContractorNameHistory = ecfContractorNameHistory;
    }

    public String getEcfSupervisorNameHistory() {
        return ecfSupervisorNameHistory;
    }

    public void setEcfSupervisorNameHistory(String ecfSupervisorNameHistory) {
        this.ecfSupervisorNameHistory = ecfSupervisorNameHistory;
    }

    public String getEcfContractorAbbName() {
        return ecfContractorAbbName;
    }

    public void setEcfContractorAbbName(String ecfContractorAbbName) {
        this.ecfContractorAbbName = ecfContractorAbbName;
    }

    public String getEcfSupervisorAbbName() {
        return ecfSupervisorAbbName;
    }

    public void setEcfSupervisorAbbName(String ecfSupervisorAbbName) {
        this.ecfSupervisorAbbName = ecfSupervisorAbbName;
    }

    public String getConPartyBName() {
        return conPartyBName;
    }

    public void setConPartyBName(String conPartyBName) {
        this.conPartyBName = conPartyBName;
    }
}
