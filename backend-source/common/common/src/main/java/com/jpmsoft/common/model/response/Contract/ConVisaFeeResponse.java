package com.jpmsoft.common.model.response.Contract;

import com.jpmsoft.common.model.ProjectCommonRes;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 签证费用申报
 */
public class ConVisaFeeResponse extends ProjectCommonRes {

    private String vfId;
    private String vrId;
    private String vfCode;
    private String vfLocation;
    private String vfDrawingNo;
    private String vfReason;
    private String vfContent;
    private String vfContractor;
    private String vfType;
    private BigDecimal vfMoney;
    private Integer vfDuration;
    private String vfResponsibleParty;
    private String vfGrade;
    private String vfRemark;
    private String vfCreateUid;
    private String vfCreateUname;
    private Date vfCreateTime;
    private Integer vfState;
    private Date vfStateTime;
    private String pId;
    private String conId;
    private String vfFollowUnit;
    private String vfDesignUnit;
    private String vfSupUnit;
    private String vfProposer;
    private String vfAmountInvolved;
    private String vfVisaCategory;
    private String vfScope;
    private String vfFileContact;
    private String vfFileEstimate;
    private String svisId;

    private String svisName;
    private String svisCode;
    private String svisProjectName;
    private String svisSubCode;
    private String clTitle;
    private String vrCode;
    private Date vrTime;
    private String vrParticipant;
    private String vrPlace;
    private String vrContent;
    private String vfContractorName;
    private String vfContractorNameHistory;
    //单位简称
    private String vfContractorAbbName;
    private String pProjectDirector;
    private String pProjectDirectorTel;
    private String conName;
    private String conCode;
    private String conMoneyTotal;
    private String conPartyBId;
    private String conPartyBName;
    private String conPartyBNameHistory;
    //单位简称
    private String conPartyBAbbName;
    private BigDecimal csbEstimateAmt;

    private String vfMoneyType;

    private Date vfDate;

    private String vfProjectManager;

    private BigDecimal vfPositiveAmount;

    private BigDecimal vfTotalMoney;

    private BigDecimal vfNegativeVariable;
    private String vfIsHistory;

    private String cmnId;

    // 预算费用
    private BigDecimal vfBudgetMoney;

    // 申请估算金额
    private BigDecimal vfApplyMoney;

    private String clId;

    private String vfClTitle;

    private String vfDataSeparateType;

    private BigDecimal conMoneyTentative;

    private BigDecimal conProvisionalPe;

    public String getVfDataSeparateType() {
        return vfDataSeparateType;
    }

    public void setVfDataSeparateType(String vfDataSeparateType) {
        this.vfDataSeparateType = vfDataSeparateType;
    }

    public BigDecimal getConMoneyTentative() {
        return conMoneyTentative;
    }

    public void setConMoneyTentative(BigDecimal conMoneyTentative) {
        this.conMoneyTentative = conMoneyTentative;
    }

    public BigDecimal getConProvisionalPe() {
        return conProvisionalPe;
    }

    public void setConProvisionalPe(BigDecimal conProvisionalPe) {
        this.conProvisionalPe = conProvisionalPe;
    }

    public String getClId() {
        return clId;
    }

    public void setClId(String clId) {
        this.clId = clId;
    }

    public String getVfClTitle() {
        return vfClTitle;
    }

    public void setVfClTitle(String vfClTitle) {
        this.vfClTitle = vfClTitle;
    }

    public BigDecimal getVfBudgetMoney() {
        return vfBudgetMoney;
    }

    public void setVfBudgetMoney(BigDecimal vfBudgetMoney) {
        this.vfBudgetMoney = vfBudgetMoney;
    }

    public BigDecimal getVfApplyMoney() {
        return vfApplyMoney;
    }

    public void setVfApplyMoney(BigDecimal vfApplyMoney) {
        this.vfApplyMoney = vfApplyMoney;
    }

    public String getVfMoneyType() {
        return vfMoneyType;
    }

    public void setVfMoneyType(String vfMoneyType) {
        this.vfMoneyType = vfMoneyType;
    }

    public Date getVfDate() {
        return vfDate;
    }

    public void setVfDate(Date vfDate) {
        this.vfDate = vfDate;
    }

    public String getVfProjectManager() {
        return vfProjectManager;
    }

    public void setVfProjectManager(String vfProjectManager) {
        this.vfProjectManager = vfProjectManager;
    }

    public BigDecimal getVfPositiveAmount() {
        return vfPositiveAmount;
    }

    public void setVfPositiveAmount(BigDecimal vfPositiveAmount) {
        this.vfPositiveAmount = vfPositiveAmount;
    }

    public BigDecimal getVfTotalMoney() {
        return vfTotalMoney;
    }

    public void setVfTotalMoney(BigDecimal vfTotalMoney) {
        this.vfTotalMoney = vfTotalMoney;
    }

    public BigDecimal getVfNegativeVariable() {
        return vfNegativeVariable;
    }

    public void setVfNegativeVariable(BigDecimal vfNegativeVariable) {
        this.vfNegativeVariable = vfNegativeVariable;
    }

    public String getVfIsHistory() {
        return vfIsHistory;
    }

    public void setVfIsHistory(String vfIsHistory) {
        this.vfIsHistory = vfIsHistory;
    }

    public String getCmnId() {
        return cmnId;
    }

    public void setCmnId(String cmnId) {
        this.cmnId = cmnId;
    }

    public String getVfId() {
        return vfId;
    }

    public void setVfId(String vfId) {
        this.vfId = vfId;
    }

    public String getVrId() {
        return vrId;
    }

    public void setVrId(String vrId) {
        this.vrId = vrId;
    }

    public String getVfCode() {
        return vfCode;
    }

    public void setVfCode(String vfCode) {
        this.vfCode = vfCode;
    }

    public String getVfLocation() {
        return vfLocation;
    }

    public void setVfLocation(String vfLocation) {
        this.vfLocation = vfLocation;
    }

    public String getVfDrawingNo() {
        return vfDrawingNo;
    }

    public void setVfDrawingNo(String vfDrawingNo) {
        this.vfDrawingNo = vfDrawingNo;
    }

    public String getVfReason() {
        return vfReason;
    }

    public void setVfReason(String vfReason) {
        this.vfReason = vfReason;
    }

    public String getVfContent() {
        return vfContent;
    }

    public void setVfContent(String vfContent) {
        this.vfContent = vfContent;
    }

    public String getVfContractor() {
        return vfContractor;
    }

    public void setVfContractor(String vfContractor) {
        this.vfContractor = vfContractor;
    }

    public String getVfType() {
        return vfType;
    }

    public void setVfType(String vfType) {
        this.vfType = vfType;
    }

    public BigDecimal getVfMoney() {
        return vfMoney;
    }

    public void setVfMoney(BigDecimal vfMoney) {
        this.vfMoney = vfMoney;
    }

    public Integer getVfDuration() {
        return vfDuration;
    }

    public void setVfDuration(Integer vfDuration) {
        this.vfDuration = vfDuration;
    }

    public String getVfResponsibleParty() {
        return vfResponsibleParty;
    }

    public void setVfResponsibleParty(String vfResponsibleParty) {
        this.vfResponsibleParty = vfResponsibleParty;
    }

    public String getVfGrade() {
        return vfGrade;
    }

    public void setVfGrade(String vfGrade) {
        this.vfGrade = vfGrade;
    }

    public String getVfRemark() {
        return vfRemark;
    }

    public void setVfRemark(String vfRemark) {
        this.vfRemark = vfRemark;
    }

    public String getVfCreateUid() {
        return vfCreateUid;
    }

    public void setVfCreateUid(String vfCreateUid) {
        this.vfCreateUid = vfCreateUid;
    }

    public String getVfCreateUname() {
        return vfCreateUname;
    }

    public void setVfCreateUname(String vfCreateUname) {
        this.vfCreateUname = vfCreateUname;
    }

    public Date getVfCreateTime() {
        return vfCreateTime;
    }

    public void setVfCreateTime(Date vfCreateTime) {
        this.vfCreateTime = vfCreateTime;
    }

    public Integer getVfState() {
        return vfState;
    }

    public void setVfState(Integer vfState) {
        this.vfState = vfState;
    }

    public Date getVfStateTime() {
        return vfStateTime;
    }

    public void setVfStateTime(Date vfStateTime) {
        this.vfStateTime = vfStateTime;
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

    public String getVfFollowUnit() {
        return vfFollowUnit;
    }

    public void setVfFollowUnit(String vfFollowUnit) {
        this.vfFollowUnit = vfFollowUnit;
    }

    public String getVfDesignUnit() {
        return vfDesignUnit;
    }

    public void setVfDesignUnit(String vfDesignUnit) {
        this.vfDesignUnit = vfDesignUnit;
    }

    public String getVfSupUnit() {
        return vfSupUnit;
    }

    public void setVfSupUnit(String vfSupUnit) {
        this.vfSupUnit = vfSupUnit;
    }

    public String getVfProposer() {
        return vfProposer;
    }

    public void setVfProposer(String vfProposer) {
        this.vfProposer = vfProposer;
    }

    public String getVfAmountInvolved() {
        return vfAmountInvolved;
    }

    public void setVfAmountInvolved(String vfAmountInvolved) {
        this.vfAmountInvolved = vfAmountInvolved;
    }

    public String getVfVisaCategory() {
        return vfVisaCategory;
    }

    public void setVfVisaCategory(String vfVisaCategory) {
        this.vfVisaCategory = vfVisaCategory;
    }

    public String getVfScope() {
        return vfScope;
    }

    public void setVfScope(String vfScope) {
        this.vfScope = vfScope;
    }

    public String getVfFileContact() {
        return vfFileContact;
    }

    public void setVfFileContact(String vfFileContact) {
        this.vfFileContact = vfFileContact;
    }

    public String getVfFileEstimate() {
        return vfFileEstimate;
    }

    public void setVfFileEstimate(String vfFileEstimate) {
        this.vfFileEstimate = vfFileEstimate;
    }

    public String getSvisId() {
        return svisId;
    }

    public void setSvisId(String svisId) {
        this.svisId = svisId;
    }

    public String getSvisName() {
        return svisName;
    }

    public void setSvisName(String svisName) {
        this.svisName = svisName;
    }

    public String getVrCode() {
        return vrCode;
    }

    public void setVrCode(String vrCode) {
        this.vrCode = vrCode;
    }

    public String getClTitle() {
        return clTitle;
    }

    public void setClTitle(String clTitle) {
        this.clTitle = clTitle;
    }

    public Date getVrTime() {
        return vrTime;
    }

    public void setVrTime(Date vrTime) {
        this.vrTime = vrTime;
    }

    public String getVrParticipant() {
        return vrParticipant;
    }

    public void setVrParticipant(String vrParticipant) {
        this.vrParticipant = vrParticipant;
    }

    public String getVrPlace() {
        return vrPlace;
    }

    public void setVrPlace(String vrPlace) {
        this.vrPlace = vrPlace;
    }

    public String getVrContent() {
        return vrContent;
    }

    public void setVrContent(String vrContent) {
        this.vrContent = vrContent;
    }

    public String getVfContractorName() {
        return vfContractorName;
    }

    public void setVfContractorName(String vfContractorName) {
        this.vfContractorName = vfContractorName;
    }

    public String getVfContractorNameHistory() {
        return vfContractorNameHistory;
    }

    public void setVfContractorNameHistory(String vfContractorNameHistory) {
        this.vfContractorNameHistory = vfContractorNameHistory;
    }

    public String getVfContractorAbbName() {
        return vfContractorAbbName;
    }

    public void setVfContractorAbbName(String vfContractorAbbName) {
        this.vfContractorAbbName = vfContractorAbbName;
    }

    public String getpProjectDirector() {
        return pProjectDirector;
    }

    public void setpProjectDirector(String pProjectDirector) {
        this.pProjectDirector = pProjectDirector;
    }

    public String getpProjectDirectorTel() {
        return pProjectDirectorTel;
    }

    public void setpProjectDirectorTel(String pProjectDirectorTel) {
        this.pProjectDirectorTel = pProjectDirectorTel;
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

    public String getConPartyBNameHistory() {
        return conPartyBNameHistory;
    }

    public void setConPartyBNameHistory(String conPartyBNameHistory) {
        this.conPartyBNameHistory = conPartyBNameHistory;
    }

    public String getConPartyBAbbName() {
        return conPartyBAbbName;
    }

    public void setConPartyBAbbName(String conPartyBAbbName) {
        this.conPartyBAbbName = conPartyBAbbName;
    }

    public String getSvisCode() {
        return svisCode;
    }

    public void setSvisCode(String svisCode) {
        this.svisCode = svisCode;
    }

    public String getSvisProjectName() {
        return svisProjectName;
    }

    public void setSvisProjectName(String svisProjectName) {
        this.svisProjectName = svisProjectName;
    }

    public String getSvisSubCode() {
        return svisSubCode;
    }

    public void setSvisSubCode(String svisSubCode) {
        this.svisSubCode = svisSubCode;
    }

    public BigDecimal getCsbEstimateAmt() {
        return csbEstimateAmt;
    }

    public void setCsbEstimateAmt(BigDecimal csbEstimateAmt) {
        this.csbEstimateAmt = csbEstimateAmt;
    }
}
