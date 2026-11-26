package com.jpmsoft.common.model.requestAndResponse.Project;

import com.jpmsoft.common.model.ProjectCommonRes;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 项目分派单
 */
public class ProAssignmentModel extends ProjectCommonRes {

    private String paId;
    private String pId;
    private String paCode;
    private String paImplementationStatus;
    private String paUserUnitName;
    private String paApprovalNo;
    private Date paApprovalDate;
    private String paApprovalUnit;
    private String paConcentrateUnitId;
    private BigDecimal paInvestment;
    private Date paStartDate;
    private Date paEndDate;
    private String paProjectOverview;
    private String paUnitId;
    private String paContent;
    private String paProposal;
    private String paRemark;
    private String paCreateUid;
    private String paCreateUname;
    private Date paCreateTime;
    private Integer paState;
    private Date paStateTime;
    private String paContentOther;
    private Integer paStartYear;
    private Integer paEndYear;
    private Boolean paPayAudit;
    private Boolean paBidCompiling;
    private String paAgentWay;
    private String paAddress;
    private String paDuration;

    private String paApprovalUnitName;
    private String paConcentrateUnitName;
    private String paUnitName;
    //单位简称
    private String paApprovalAbbName;
    private String paConcentrateAbbName;
    private String paUnitAbbName;

    public String getPaId() {
        return paId;
    }

    public void setPaId(String paId) {
        this.paId = paId;
    }

    @Override
    public String getpId() {
        return pId;
    }

    @Override
    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getPaCode() {
        return paCode;
    }

    public void setPaCode(String paCode) {
        this.paCode = paCode;
    }

    public String getPaImplementationStatus() {
        return paImplementationStatus;
    }

    public void setPaImplementationStatus(String paImplementationStatus) {
        this.paImplementationStatus = paImplementationStatus;
    }

    public String getPaUserUnitName() {
        return paUserUnitName;
    }

    public void setPaUserUnitName(String paUserUnitName) {
        this.paUserUnitName = paUserUnitName;
    }

    public String getPaApprovalNo() {
        return paApprovalNo;
    }

    public void setPaApprovalNo(String paApprovalNo) {
        this.paApprovalNo = paApprovalNo;
    }

    public Date getPaApprovalDate() {
        return paApprovalDate;
    }

    public void setPaApprovalDate(Date paApprovalDate) {
        this.paApprovalDate = paApprovalDate;
    }

    public String getPaApprovalUnit() {
        return paApprovalUnit;
    }

    public void setPaApprovalUnit(String paApprovalUnit) {
        this.paApprovalUnit = paApprovalUnit;
    }

    public String getPaConcentrateUnitId() {
        return paConcentrateUnitId;
    }

    public void setPaConcentrateUnitId(String paConcentrateUnitId) {
        this.paConcentrateUnitId = paConcentrateUnitId;
    }

    public BigDecimal getPaInvestment() {
        return paInvestment;
    }

    public void setPaInvestment(BigDecimal paInvestment) {
        this.paInvestment = paInvestment;
    }

    public Date getPaStartDate() {
        return paStartDate;
    }

    public void setPaStartDate(Date paStartDate) {
        this.paStartDate = paStartDate;
    }

    public Date getPaEndDate() {
        return paEndDate;
    }

    public void setPaEndDate(Date paEndDate) {
        this.paEndDate = paEndDate;
    }

    public String getPaProjectOverview() {
        return paProjectOverview;
    }

    public void setPaProjectOverview(String paProjectOverview) {
        this.paProjectOverview = paProjectOverview;
    }

    public String getPaUnitId() {
        return paUnitId;
    }

    public void setPaUnitId(String paUnitId) {
        this.paUnitId = paUnitId;
    }

    public String getPaContent() {
        return paContent;
    }

    public void setPaContent(String paContent) {
        this.paContent = paContent;
    }

    public String getPaProposal() {
        return paProposal;
    }

    public void setPaProposal(String paProposal) {
        this.paProposal = paProposal;
    }

    public String getPaRemark() {
        return paRemark;
    }

    public void setPaRemark(String paRemark) {
        this.paRemark = paRemark;
    }

    public String getPaCreateUid() {
        return paCreateUid;
    }

    public void setPaCreateUid(String paCreateUid) {
        this.paCreateUid = paCreateUid;
    }

    public String getPaCreateUname() {
        return paCreateUname;
    }

    public void setPaCreateUname(String paCreateUname) {
        this.paCreateUname = paCreateUname;
    }

    public Date getPaCreateTime() {
        return paCreateTime;
    }

    public void setPaCreateTime(Date paCreateTime) {
        this.paCreateTime = paCreateTime;
    }

    public Integer getPaState() {
        return paState;
    }

    public void setPaState(Integer paState) {
        this.paState = paState;
    }

    public Date getPaStateTime() {
        return paStateTime;
    }

    public void setPaStateTime(Date paStateTime) {
        this.paStateTime = paStateTime;
    }

    public String getPaContentOther() {
        return paContentOther;
    }

    public void setPaContentOther(String paContentOther) {
        this.paContentOther = paContentOther;
    }

    public Integer getPaStartYear() {
        return paStartYear;
    }

    public void setPaStartYear(Integer paStartYear) {
        this.paStartYear = paStartYear;
    }

    public Integer getPaEndYear() {
        return paEndYear;
    }

    public void setPaEndYear(Integer paEndYear) {
        this.paEndYear = paEndYear;
    }

    public Boolean getPaPayAudit() {
        return paPayAudit;
    }

    public void setPaPayAudit(Boolean paPayAudit) {
        this.paPayAudit = paPayAudit;
    }

    public Boolean getPaBidCompiling() {
        return paBidCompiling;
    }

    public void setPaBidCompiling(Boolean paBidCompiling) {
        this.paBidCompiling = paBidCompiling;
    }

    public String getPaAgentWay() {
        return paAgentWay;
    }

    public void setPaAgentWay(String paAgentWay) {
        this.paAgentWay = paAgentWay;
    }

    public String getPaAddress() {
        return paAddress;
    }

    public void setPaAddress(String paAddress) {
        this.paAddress = paAddress;
    }

    public String getPaDuration() {
        return paDuration;
    }

    public void setPaDuration(String paDuration) {
        this.paDuration = paDuration;
    }

    public String getPaApprovalUnitName() {
        return paApprovalUnitName;
    }

    public void setPaApprovalUnitName(String paApprovalUnitName) {
        this.paApprovalUnitName = paApprovalUnitName;
    }

    public String getPaConcentrateUnitName() {
        return paConcentrateUnitName;
    }

    public void setPaConcentrateUnitName(String paConcentrateUnitName) {
        this.paConcentrateUnitName = paConcentrateUnitName;
    }

    public String getPaUnitName() {
        return paUnitName;
    }

    public void setPaUnitName(String paUnitName) {
        this.paUnitName = paUnitName;
    }

    public String getPaApprovalAbbName() {
        return paApprovalAbbName;
    }

    public void setPaApprovalAbbName(String paApprovalAbbName) {
        this.paApprovalAbbName = paApprovalAbbName;
    }

    public String getPaConcentrateAbbName() {
        return paConcentrateAbbName;
    }

    public void setPaConcentrateAbbName(String paConcentrateAbbName) {
        this.paConcentrateAbbName = paConcentrateAbbName;
    }

    public String getPaUnitAbbName() {
        return paUnitAbbName;
    }

    public void setPaUnitAbbName(String paUnitAbbName) {
        this.paUnitAbbName = paUnitAbbName;
    }
}
