package com.jpmsoft.common.model.requestAndResponse.SecInfo;

import com.jpmsoft.common.model.BaseFileRequest;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 招标采购备案
 */
public class SecProcurementFilingModel extends BaseFileRequest {

    private String pfId;
    private String pId;
    private BigDecimal pfPlannInvestment;
    private BigDecimal pfAmount;
    private String pfQualification;
    private String pfPermit;
    private String pfPermitDesc;
    private String pfPmName;
    private String pfTelephone;
    private Date pfPlanStart;
    private Date pfPlanEnd;
    private String pType;
    private String pfRemark;
    private String pfIntendedContractor;
    private String pfReason;
    private String pfCreateUid;
    private String pfCreateUname;
    private Date pfCreateTime;
    private Integer pfState;
    private Date pfStateTime;
    private Float pfLower;
    private String pfBidding;
    private String pfCalibration;
    private String pfCalibrationFile;
    private String pfBidQuotation;
    private String pfMeetingFile;
    private String pfProcurementFile;
    private String pfInstructionsFile;
    private String pfLawFile;
    private String pfNoticeFile;
    private String pfScopeFile;
    private String pfLowerFile;
    private String pfControlFile;
    private String pfDurationFile;
    private String pfBidLetterFile;
    private String pfApproveFile;
    private String pfAwardFile;
    private String pfSecName;
    private String pfMeetingJtFile;
    private Boolean pfAgreeChange;

    private String pName;
    private String pNameHistory;
    private BigDecimal pInvTotal;
    private String pfCalibrationFileIds;
    private String pfMeetingFileIds;
    private String pfProcurementFileIds;
    private String pfInstructionsFileIds;
    private String pfLawFileIds;
    private String pfNoticeFileIds;
    private String pfScopeFileIds;
    private String pfLowerFileIds;
    private String pfControlFileIds;
    private String pfDurationFileIds;
    private String pfBidLetterFileIds;
    private String pfApproveFileIds;
    private String pfAwardFileIds;
    private String pfMeetingJtFileIds;
    private List<SecFundingSourceModel> secFundingSources;


    public String getPfId() {
        return pfId;
    }

    public void setPfId(String pfId) {
        this.pfId = pfId;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public BigDecimal getPfPlannInvestment() {
        return pfPlannInvestment;
    }

    public void setPfPlannInvestment(BigDecimal pfPlannInvestment) {
        this.pfPlannInvestment = pfPlannInvestment;
    }

    public BigDecimal getPfAmount() {
        return pfAmount;
    }

    public void setPfAmount(BigDecimal pfAmount) {
        this.pfAmount = pfAmount;
    }

    public String getPfQualification() {
        return pfQualification;
    }

    public void setPfQualification(String pfQualification) {
        this.pfQualification = pfQualification;
    }

    public String getPfPermit() {
        return pfPermit;
    }

    public void setPfPermit(String pfPermit) {
        this.pfPermit = pfPermit;
    }

    public String getPfPermitDesc() {
        return pfPermitDesc;
    }

    public void setPfPermitDesc(String pfPermitDesc) {
        this.pfPermitDesc = pfPermitDesc;
    }

    public String getPfPmName() {
        return pfPmName;
    }

    public void setPfPmName(String pfPmName) {
        this.pfPmName = pfPmName;
    }

    public String getPfTelephone() {
        return pfTelephone;
    }

    public void setPfTelephone(String pfTelephone) {
        this.pfTelephone = pfTelephone;
    }

    public Date getPfPlanStart() {
        return pfPlanStart;
    }

    public void setPfPlanStart(Date pfPlanStart) {
        this.pfPlanStart = pfPlanStart;
    }

    public Date getPfPlanEnd() {
        return pfPlanEnd;
    }

    public void setPfPlanEnd(Date pfPlanEnd) {
        this.pfPlanEnd = pfPlanEnd;
    }

    public String getpType() {
        return pType;
    }

    public void setpType(String pType) {
        this.pType = pType;
    }

    public String getPfRemark() {
        return pfRemark;
    }

    public void setPfRemark(String pfRemark) {
        this.pfRemark = pfRemark;
    }

    public String getPfIntendedContractor() {
        return pfIntendedContractor;
    }

    public void setPfIntendedContractor(String pfIntendedContractor) {
        this.pfIntendedContractor = pfIntendedContractor;
    }

    public String getPfReason() {
        return pfReason;
    }

    public void setPfReason(String pfReason) {
        this.pfReason = pfReason;
    }

    public String getPfCreateUid() {
        return pfCreateUid;
    }

    public void setPfCreateUid(String pfCreateUid) {
        this.pfCreateUid = pfCreateUid;
    }

    public String getPfCreateUname() {
        return pfCreateUname;
    }

    public void setPfCreateUname(String pfCreateUname) {
        this.pfCreateUname = pfCreateUname;
    }

    public Date getPfCreateTime() {
        return pfCreateTime;
    }

    public void setPfCreateTime(Date pfCreateTime) {
        this.pfCreateTime = pfCreateTime;
    }

    public Integer getPfState() {
        return pfState;
    }

    public void setPfState(Integer pfState) {
        this.pfState = pfState;
    }

    public Date getPfStateTime() {
        return pfStateTime;
    }

    public void setPfStateTime(Date pfStateTime) {
        this.pfStateTime = pfStateTime;
    }

    public Float getPfLower() {
        return pfLower;
    }

    public void setPfLower(Float pfLower) {
        this.pfLower = pfLower;
    }

    public String getPfBidding() {
        return pfBidding;
    }

    public void setPfBidding(String pfBidding) {
        this.pfBidding = pfBidding;
    }

    public String getPfCalibration() {
        return pfCalibration;
    }

    public void setPfCalibration(String pfCalibration) {
        this.pfCalibration = pfCalibration;
    }

    public Boolean getPfAgreeChange() {
        return pfAgreeChange;
    }

    public void setPfAgreeChange(Boolean pfAgreeChange) {
        this.pfAgreeChange = pfAgreeChange;
    }

    public String getPfCalibrationFile() {
        return pfCalibrationFile;
    }

    public void setPfCalibrationFile(String pfCalibrationFile) {
        this.pfCalibrationFile = pfCalibrationFile;
    }

    public String getPfCalibrationFileIds() {
        return pfCalibrationFileIds;
    }

    public void setPfCalibrationFileIds(String pfCalibrationFileIds) {
        this.pfCalibrationFileIds = pfCalibrationFileIds;
    }

    public String getPfBidQuotation() {
        return pfBidQuotation;
    }

    public void setPfBidQuotation(String pfBidQuotation) {
        this.pfBidQuotation = pfBidQuotation;
    }

    public String getPfMeetingFile() {
        return pfMeetingFile;
    }

    public void setPfMeetingFile(String pfMeetingFile) {
        this.pfMeetingFile = pfMeetingFile;
    }

    public String getPfProcurementFile() {
        return pfProcurementFile;
    }

    public void setPfProcurementFile(String pfProcurementFile) {
        this.pfProcurementFile = pfProcurementFile;
    }

    public String getPfInstructionsFile() {
        return pfInstructionsFile;
    }

    public void setPfInstructionsFile(String pfInstructionsFile) {
        this.pfInstructionsFile = pfInstructionsFile;
    }

    public String getPfLawFile() {
        return pfLawFile;
    }

    public void setPfLawFile(String pfLawFile) {
        this.pfLawFile = pfLawFile;
    }

    public String getPfNoticeFile() {
        return pfNoticeFile;
    }

    public void setPfNoticeFile(String pfNoticeFile) {
        this.pfNoticeFile = pfNoticeFile;
    }

    public String getPfScopeFile() {
        return pfScopeFile;
    }

    public void setPfScopeFile(String pfScopeFile) {
        this.pfScopeFile = pfScopeFile;
    }

    public String getPfLowerFile() {
        return pfLowerFile;
    }

    public void setPfLowerFile(String pfLowerFile) {
        this.pfLowerFile = pfLowerFile;
    }

    public String getPfControlFile() {
        return pfControlFile;
    }

    public void setPfControlFile(String pfControlFile) {
        this.pfControlFile = pfControlFile;
    }

    public String getPfDurationFile() {
        return pfDurationFile;
    }

    public void setPfDurationFile(String pfDurationFile) {
        this.pfDurationFile = pfDurationFile;
    }



    public String getPfApproveFile() {
        return pfApproveFile;
    }

    public void setPfApproveFile(String pfApproveFile) {
        this.pfApproveFile = pfApproveFile;
    }

    public String getPfAwardFile() {
        return pfAwardFile;
    }

    public void setPfAwardFile(String pfAwardFile) {
        this.pfAwardFile = pfAwardFile;
    }

    public String getPfMeetingFileIds() {
        return pfMeetingFileIds;
    }

    public void setPfMeetingFileIds(String pfMeetingFileIds) {
        this.pfMeetingFileIds = pfMeetingFileIds;
    }

    public String getPfProcurementFileIds() {
        return pfProcurementFileIds;
    }

    public void setPfProcurementFileIds(String pfProcurementFileIds) {
        this.pfProcurementFileIds = pfProcurementFileIds;
    }

    public String getPfInstructionsFileIds() {
        return pfInstructionsFileIds;
    }

    public void setPfInstructionsFileIds(String pfInstructionsFileIds) {
        this.pfInstructionsFileIds = pfInstructionsFileIds;
    }

    public String getPfLawFileIds() {
        return pfLawFileIds;
    }

    public void setPfLawFileIds(String pfLawFileIds) {
        this.pfLawFileIds = pfLawFileIds;
    }

    public String getPfNoticeFileIds() {
        return pfNoticeFileIds;
    }

    public void setPfNoticeFileIds(String pfNoticeFileIds) {
        this.pfNoticeFileIds = pfNoticeFileIds;
    }

    public String getPfScopeFileIds() {
        return pfScopeFileIds;
    }

    public void setPfScopeFileIds(String pfScopeFileIds) {
        this.pfScopeFileIds = pfScopeFileIds;
    }

    public String getPfBidLetterFile() {
        return pfBidLetterFile;
    }

    public void setPfBidLetterFile(String pfBidLetterFile) {
        this.pfBidLetterFile = pfBidLetterFile;
    }

    public String getPfLowerFileIds() {
        return pfLowerFileIds;
    }

    public void setPfLowerFileIds(String pfLowerFileIds) {
        this.pfLowerFileIds = pfLowerFileIds;
    }

    public String getPfControlFileIds() {
        return pfControlFileIds;
    }

    public void setPfControlFileIds(String pfControlFileIds) {
        this.pfControlFileIds = pfControlFileIds;
    }

    public String getPfDurationFileIds() {
        return pfDurationFileIds;
    }

    public void setPfDurationFileIds(String pfDurationFileIds) {
        this.pfDurationFileIds = pfDurationFileIds;
    }

    public String getPfBidLetterFileIds() {
        return pfBidLetterFileIds;
    }

    public void setPfBidLetterFileIds(String pfBidLetterFileIds) {
        this.pfBidLetterFileIds = pfBidLetterFileIds;
    }

    public String getPfApproveFileIds() {
        return pfApproveFileIds;
    }

    public void setPfApproveFileIds(String pfApproveFileIds) {
        this.pfApproveFileIds = pfApproveFileIds;
    }

    public String getPfAwardFileIds() {
        return pfAwardFileIds;
    }

    public void setPfAwardFileIds(String pfAwardFileIds) {
        this.pfAwardFileIds = pfAwardFileIds;
    }

    public String getPfMeetingJtFile() {
        return pfMeetingJtFile;
    }

    public void setPfMeetingJtFile(String pfMeetingJtFile) {
        this.pfMeetingJtFile = pfMeetingJtFile;
    }

    public String getPfMeetingJtFileIds() {
        return pfMeetingJtFileIds;
    }

    public void setPfMeetingJtFileIds(String pfMeetingJtFileIds) {
        this.pfMeetingJtFileIds = pfMeetingJtFileIds;
    }

    public String getPfSecName() {
        return pfSecName;
    }

    public void setPfSecName(String pfSecName) {
        this.pfSecName = pfSecName;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public String getpNameHistory() {
        return pNameHistory;
    }

    public void setpNameHistory(String pNameHistory) {
        this.pNameHistory = pNameHistory;
    }

    public BigDecimal getpInvTotal() {
        return pInvTotal;
    }

    public void setpInvTotal(BigDecimal pInvTotal) {
        this.pInvTotal = pInvTotal;
    }

    public List<SecFundingSourceModel> getSecFundingSources() {
        return secFundingSources;
    }

    public void setSecFundingSources(List<SecFundingSourceModel> secFundingSources) {
        this.secFundingSources = secFundingSources;
    }
}
