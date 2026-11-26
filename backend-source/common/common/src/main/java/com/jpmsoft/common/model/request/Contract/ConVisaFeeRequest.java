package com.jpmsoft.common.model.request.Contract;

import com.jpmsoft.common.model.BaseFileRequest;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 签证费用申报
 */
public class ConVisaFeeRequest extends BaseFileRequest {

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

    private String vfFileContactIds;
    private String vfFileEstimateIds;
    private String conName;
    private String pName;
    private String svisCode;
    private String svisProjectName;
    private String svisSubCode;
    //区分链表查询合同是是查询本表单还是签证计量备案
    //0:签证备案 1：本表单 默认 0
    private Integer conWhere;


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

    private String vfDataSeparateType;

    public String getVfDataSeparateType() {
        return vfDataSeparateType;
    }

    public void setVfDataSeparateType(String vfDataSeparateType) {
        this.vfDataSeparateType = vfDataSeparateType;
    }

    public String getClId() {
        return clId;
    }

    public void setClId(String clId) {
        this.clId = clId;
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

    public String getVfFileContactIds() {
        return vfFileContactIds;
    }

    public void setVfFileContactIds(String vfFileContactIds) {
        this.vfFileContactIds = vfFileContactIds;
    }

    public String getVfFileEstimateIds() {
        return vfFileEstimateIds;
    }

    public void setVfFileEstimateIds(String vfFileEstimateIds) {
        this.vfFileEstimateIds = vfFileEstimateIds;
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

    public Integer getConWhere() {
        return conWhere;
    }

    public void setConWhere(Integer conWhere) {
        this.conWhere = conWhere;
    }
}
