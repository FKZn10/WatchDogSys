package com.jpmsoft.common.model.request.project;

import com.jpmsoft.common.model.BaseFileRequest;
import com.jpmsoft.common.model.requestAndResponse.Project.PreProjectRegisterDtlModel;
import com.jpmsoft.common.model.requestAndResponse.Project.ProSourcesFundsRegModel;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 项目注册
 */

public class PreProjectRegisterRequest extends BaseFileRequest {

    private String prId;
    private String prName;
    private String prCode;
    private String prCategory;
    private String prType;
    private String prProjectStatus;
    private String prSourcesOfBunds;
    private Float prBuildingScale;
    private BigDecimal prEstimatedAmount;
    private String prReason;
    private String prUseUnit;
    private String prCommitteeProjectCode;
    private String prCommitteeApprovaDocument;
    private String prCityLeaderMeetingMinutes;
    private String prCityLeaderInstructions;
    private String prUseUnitLetter;
    private String prLandApproval;
    private String prOther;
    private String prRemark;
    private String prApplicantId;
    private Date prCreateTime;
    private Integer prApprovalStatus;
    private Date prApprovalTime;
    private String prStatus;
    private String prCreateUserName;
    private String prNature;
    private BigDecimal prReportedAmount;
    private String prApprovaDocument;
    private String prLeaderMeetingMinutes;
    private String prLeaderInstructions;
    private BigDecimal prConstructionCost;
    private BigDecimal prContractPrice;
    private BigDecimal prPurchasePrice;
    private String prConstructionUnitId;
    private Date prPlanStart;
    private Date prPlanEnd;
    private String prPmName;
    private String prPmTel;
    private String prEstimateFile;
    private String prInvNature;
    private String prBuildingUnit;
    private String prMajorType;
    private String prCompetentDepartment;
    private Integer prInterventionYear;
    private String prProjectStage;
    private String prSafetyFiling;
    private String cplId;
    private String prApplyName;
    private BigDecimal prFiscalBudget;
    private String prAddress;
    private String prCampus;
    private Integer prYear;
    private String prOpinionFile;
    private String prPlanFile;
    private String prFeasibilityFile;
    private String prApproveFile;

    private String prOpinionFileIds;
    private String prPlanFileIds;
    private String prFeasibilityFileIds;
    private String prApproveFileIds;
    private String prEstimateFileIds;
    private String prCommitteeApprovaDocIds;
    private String prCityLeaderInstructionsIds;
    private String prCityLeaderMeetingMinutesIds;
    private String prOtherIds;
    private String prApprovaDocumentIds;
    private String prLeaderMeetingMinutesIds;
    private String prLeaderInstructionsIds;
    private String prScaleDescribe;
    private Float prLength;
    private Float prWidth;
    private Float prLandArea;
    private List<ProSourcesFundsRegModel> proSourcesFundsRegs;

    private List<PreProjectRegisterDtlModel> preProjectRegisterDtl;

    //估算
    private BigDecimal prEstimateAmount;

    //项目总投资批复金额
    private BigDecimal prApproveBudget;

    private String PrAbbreviation;
    private BigDecimal PrGcjsqtAmt;
    private BigDecimal PrTdAmount;
    private BigDecimal PrYbAmt;
    private BigDecimal PrJsqdkLx;
    private Date PrPlanStartDate;
    private Date PrPlanCompleteDate;

    public String getPrId() {
        return prId;
    }

    public void setPrId(String prId) {
        this.prId = prId;
    }

    public String getPrName() {
        return prName;
    }

    public void setPrName(String prName) {
        this.prName = prName;
    }

    public String getPrCode() {
        return prCode;
    }

    public void setPrCode(String prCode) {
        this.prCode = prCode;
    }

    public String getPrCategory() {
        return prCategory;
    }

    public void setPrCategory(String prCategory) {
        this.prCategory = prCategory;
    }

    public String getPrType() {
        return prType;
    }

    public void setPrType(String prType) {
        this.prType = prType;
    }

    public String getPrProjectStatus() {
        return prProjectStatus;
    }

    public void setPrProjectStatus(String prProjectStatus) {
        this.prProjectStatus = prProjectStatus;
    }

    public String getPrSourcesOfBunds() {
        return prSourcesOfBunds;
    }

    public void setPrSourcesOfBunds(String prSourcesOfBunds) {
        this.prSourcesOfBunds = prSourcesOfBunds;
    }

    public Float getPrBuildingScale() {
        return prBuildingScale;
    }

    public void setPrBuildingScale(Float prBuildingScale) {
        this.prBuildingScale = prBuildingScale;
    }

    public BigDecimal getPrEstimatedAmount() {
        return prEstimatedAmount;
    }

    public void setPrEstimatedAmount(BigDecimal prEstimatedAmount) {
        this.prEstimatedAmount = prEstimatedAmount;
    }

    public String getPrReason() {
        return prReason;
    }

    public void setPrReason(String prReason) {
        this.prReason = prReason;
    }

    public String getPrUseUnit() {
        return prUseUnit;
    }

    public void setPrUseUnit(String prUseUnit) {
        this.prUseUnit = prUseUnit;
    }

    public String getPrCommitteeProjectCode() {
        return prCommitteeProjectCode;
    }

    public void setPrCommitteeProjectCode(String prCommitteeProjectCode) {
        this.prCommitteeProjectCode = prCommitteeProjectCode;
    }

    public String getPrCommitteeApprovaDocument() {
        return prCommitteeApprovaDocument;
    }

    public void setPrCommitteeApprovaDocument(String prCommitteeApprovaDocument) {
        this.prCommitteeApprovaDocument = prCommitteeApprovaDocument;
    }

    public String getPrCityLeaderMeetingMinutes() {
        return prCityLeaderMeetingMinutes;
    }

    public void setPrCityLeaderMeetingMinutes(String prCityLeaderMeetingMinutes) {
        this.prCityLeaderMeetingMinutes = prCityLeaderMeetingMinutes;
    }

    public String getPrCityLeaderInstructions() {
        return prCityLeaderInstructions;
    }

    public void setPrCityLeaderInstructions(String prCityLeaderInstructions) {
        this.prCityLeaderInstructions = prCityLeaderInstructions;
    }

    public String getPrUseUnitLetter() {
        return prUseUnitLetter;
    }

    public void setPrUseUnitLetter(String prUseUnitLetter) {
        this.prUseUnitLetter = prUseUnitLetter;
    }

    public String getPrLandApproval() {
        return prLandApproval;
    }

    public void setPrLandApproval(String prLandApproval) {
        this.prLandApproval = prLandApproval;
    }

    public String getPrOther() {
        return prOther;
    }

    public void setPrOther(String prOther) {
        this.prOther = prOther;
    }

    public String getPrRemark() {
        return prRemark;
    }

    public void setPrRemark(String prRemark) {
        this.prRemark = prRemark;
    }

    public String getPrApplicantId() {
        return prApplicantId;
    }

    public void setPrApplicantId(String prApplicantId) {
        this.prApplicantId = prApplicantId;
    }

    public Date getPrCreateTime() {
        return prCreateTime;
    }

    public void setPrCreateTime(Date prCreateTime) {
        this.prCreateTime = prCreateTime;
    }

    public Integer getPrApprovalStatus() {
        return prApprovalStatus;
    }

    public void setPrApprovalStatus(Integer prApprovalStatus) {
        this.prApprovalStatus = prApprovalStatus;
    }

    public Date getPrApprovalTime() {
        return prApprovalTime;
    }

    public void setPrApprovalTime(Date prApprovalTime) {
        this.prApprovalTime = prApprovalTime;
    }

    public String getPrStatus() {
        return prStatus;
    }

    public void setPrStatus(String prStatus) {
        this.prStatus = prStatus;
    }

    public String getPrCreateUserName() {
        return prCreateUserName;
    }

    public void setPrCreateUserName(String prCreateUserName) {
        this.prCreateUserName = prCreateUserName;
    }

    public String getPrNature() {
        return prNature;
    }

    public void setPrNature(String prNature) {
        this.prNature = prNature;
    }

    public BigDecimal getPrReportedAmount() {
        return prReportedAmount;
    }

    public void setPrReportedAmount(BigDecimal prReportedAmount) {
        this.prReportedAmount = prReportedAmount;
    }

    public String getPrApprovaDocument() {
        return prApprovaDocument;
    }

    public void setPrApprovaDocument(String prApprovaDocument) {
        this.prApprovaDocument = prApprovaDocument;
    }

    public String getPrLeaderMeetingMinutes() {
        return prLeaderMeetingMinutes;
    }

    public void setPrLeaderMeetingMinutes(String prLeaderMeetingMinutes) {
        this.prLeaderMeetingMinutes = prLeaderMeetingMinutes;
    }

    public String getPrLeaderInstructions() {
        return prLeaderInstructions;
    }

    public void setPrLeaderInstructions(String prLeaderInstructions) {
        this.prLeaderInstructions = prLeaderInstructions;
    }

    public BigDecimal getPrConstructionCost() {
        return prConstructionCost;
    }

    public void setPrConstructionCost(BigDecimal prConstructionCost) {
        this.prConstructionCost = prConstructionCost;
    }

    public BigDecimal getPrContractPrice() {
        return prContractPrice;
    }

    public void setPrContractPrice(BigDecimal prContractPrice) {
        this.prContractPrice = prContractPrice;
    }

    public BigDecimal getPrPurchasePrice() {
        return prPurchasePrice;
    }

    public void setPrPurchasePrice(BigDecimal prPurchasePrice) {
        this.prPurchasePrice = prPurchasePrice;
    }

    public String getPrConstructionUnitId() {
        return prConstructionUnitId;
    }

    public void setPrConstructionUnitId(String prConstructionUnitId) {
        this.prConstructionUnitId = prConstructionUnitId;
    }

    public Date getPrPlanStart() {
        return prPlanStart;
    }

    public void setPrPlanStart(Date prPlanStart) {
        this.prPlanStart = prPlanStart;
    }

    public Date getPrPlanEnd() {
        return prPlanEnd;
    }

    public void setPrPlanEnd(Date prPlanEnd) {
        this.prPlanEnd = prPlanEnd;
    }

    public String getPrPmName() {
        return prPmName;
    }

    public void setPrPmName(String prPmName) {
        this.prPmName = prPmName;
    }

    public String getPrPmTel() {
        return prPmTel;
    }

    public void setPrPmTel(String prPmTel) {
        this.prPmTel = prPmTel;
    }

    public String getPrEstimateFile() {
        return prEstimateFile;
    }

    public void setPrEstimateFile(String prEstimateFile) {
        this.prEstimateFile = prEstimateFile;
    }

    public String getPrInvNature() {
        return prInvNature;
    }

    public void setPrInvNature(String prInvNature) {
        this.prInvNature = prInvNature;
    }

    public String getPrBuildingUnit() {
        return prBuildingUnit;
    }

    public void setPrBuildingUnit(String prBuildingUnit) {
        this.prBuildingUnit = prBuildingUnit;
    }

    public String getPrMajorType() {
        return prMajorType;
    }

    public void setPrMajorType(String prMajorType) {
        this.prMajorType = prMajorType;
    }

    public String getPrCompetentDepartment() {
        return prCompetentDepartment;
    }

    public void setPrCompetentDepartment(String prCompetentDepartment) {
        this.prCompetentDepartment = prCompetentDepartment;
    }

    public Integer getPrInterventionYear() {
        return prInterventionYear;
    }

    public void setPrInterventionYear(Integer prInterventionYear) {
        this.prInterventionYear = prInterventionYear;
    }

    public String getPrProjectStage() {
        return prProjectStage;
    }

    public void setPrProjectStage(String prProjectStage) {
        this.prProjectStage = prProjectStage;
    }

    public String getPrSafetyFiling() {
        return prSafetyFiling;
    }

    public void setPrSafetyFiling(String prSafetyFiling) {
        this.prSafetyFiling = prSafetyFiling;
    }

    public String getCplId() {
        return cplId;
    }

    public void setCplId(String cplId) {
        this.cplId = cplId;
    }

    public String getPrApplyName() {
        return prApplyName;
    }

    public void setPrApplyName(String prApplyName) {
        this.prApplyName = prApplyName;
    }

    public BigDecimal getPrFiscalBudget() {
        return prFiscalBudget;
    }

    public void setPrFiscalBudget(BigDecimal prFiscalBudget) {
        this.prFiscalBudget = prFiscalBudget;
    }

    public String getPrAddress() {
        return prAddress;
    }

    public void setPrAddress(String prAddress) {
        this.prAddress = prAddress;
    }

    public String getPrCampus() {
        return prCampus;
    }

    public void setPrCampus(String prCampus) {
        this.prCampus = prCampus;
    }

    public Integer getPrYear() {
        return prYear;
    }

    public void setPrYear(Integer prYear) {
        this.prYear = prYear;
    }

    public String getPrOpinionFile() {
        return prOpinionFile;
    }

    public void setPrOpinionFile(String prOpinionFile) {
        this.prOpinionFile = prOpinionFile;
    }

    public String getPrPlanFile() {
        return prPlanFile;
    }

    public void setPrPlanFile(String prPlanFile) {
        this.prPlanFile = prPlanFile;
    }

    public String getPrFeasibilityFile() {
        return prFeasibilityFile;
    }

    public void setPrFeasibilityFile(String prFeasibilityFile) {
        this.prFeasibilityFile = prFeasibilityFile;
    }

    public String getPrApproveFile() {
        return prApproveFile;
    }

    public void setPrApproveFile(String prApproveFile) {
        this.prApproveFile = prApproveFile;
    }

    public String getPrApproveFileIds() {
        return prApproveFileIds;
    }

    public void setPrApproveFileIds(String prApproveFileIds) {
        this.prApproveFileIds = prApproveFileIds;
    }

    public String getPrOpinionFileIds() {
        return prOpinionFileIds;
    }

    public void setPrOpinionFileIds(String prOpinionFileIds) {
        this.prOpinionFileIds = prOpinionFileIds;
    }

    public String getPrPlanFileIds() {
        return prPlanFileIds;
    }

    public void setPrPlanFileIds(String prPlanFileIds) {
        this.prPlanFileIds = prPlanFileIds;
    }

    public String getPrFeasibilityFileIds() {
        return prFeasibilityFileIds;
    }

    public void setPrFeasibilityFileIds(String prFeasibilityFileIds) {
        this.prFeasibilityFileIds = prFeasibilityFileIds;
    }

    public String getPrEstimateFileIds() {
        return prEstimateFileIds;
    }

    public void setPrEstimateFileIds(String prEstimateFileIds) {
        this.prEstimateFileIds = prEstimateFileIds;
    }

    public String getPrCommitteeApprovaDocIds() {
        return prCommitteeApprovaDocIds;
    }

    public void setPrCommitteeApprovaDocIds(String prCommitteeApprovaDocIds) {
        this.prCommitteeApprovaDocIds = prCommitteeApprovaDocIds;
    }

    public String getPrCityLeaderInstructionsIds() {
        return prCityLeaderInstructionsIds;
    }

    public void setPrCityLeaderInstructionsIds(String prCityLeaderInstructionsIds) {
        this.prCityLeaderInstructionsIds = prCityLeaderInstructionsIds;
    }

    public String getPrCityLeaderMeetingMinutesIds() {
        return prCityLeaderMeetingMinutesIds;
    }

    public void setPrCityLeaderMeetingMinutesIds(String prCityLeaderMeetingMinutesIds) {
        this.prCityLeaderMeetingMinutesIds = prCityLeaderMeetingMinutesIds;
    }

    public String getPrOtherIds() {
        return prOtherIds;
    }

    public void setPrOtherIds(String prOtherIds) {
        this.prOtherIds = prOtherIds;
    }

    public String getPrApprovaDocumentIds() {
        return prApprovaDocumentIds;
    }

    public void setPrApprovaDocumentIds(String prApprovaDocumentIds) {
        this.prApprovaDocumentIds = prApprovaDocumentIds;
    }

    public String getPrLeaderMeetingMinutesIds() {
        return prLeaderMeetingMinutesIds;
    }

    public void setPrLeaderMeetingMinutesIds(String prLeaderMeetingMinutesIds) {
        this.prLeaderMeetingMinutesIds = prLeaderMeetingMinutesIds;
    }

    public String getPrLeaderInstructionsIds() {
        return prLeaderInstructionsIds;
    }

    public void setPrLeaderInstructionsIds(String prLeaderInstructionsIds) {
        this.prLeaderInstructionsIds = prLeaderInstructionsIds;
    }

    public String getPrScaleDescribe() {
        return prScaleDescribe;
    }

    public void setPrScaleDescribe(String prScaleDescribe) {
        this.prScaleDescribe = prScaleDescribe;
    }

    public Float getPrLength() {
        return prLength;
    }

    public void setPrLength(Float prLength) {
        this.prLength = prLength;
    }

    public Float getPrWidth() {
        return prWidth;
    }

    public void setPrWidth(Float prWidth) {
        this.prWidth = prWidth;
    }

    public Float getPrLandArea() {
        return prLandArea;
    }

    public void setPrLandArea(Float prLandArea) {
        this.prLandArea = prLandArea;
    }

    public List<ProSourcesFundsRegModel> getProSourcesFundsRegs() {
        return proSourcesFundsRegs;
    }

    public void setProSourcesFundsRegs(List<ProSourcesFundsRegModel> proSourcesFundsRegs) {
        this.proSourcesFundsRegs = proSourcesFundsRegs;
    }

    public List<PreProjectRegisterDtlModel> getPreProjectRegisterDtl() {
        return preProjectRegisterDtl;
    }

    public void setPreProjectRegisterDtl(List<PreProjectRegisterDtlModel> preProjectRegisterDtl) {
        this.preProjectRegisterDtl = preProjectRegisterDtl;
    }

    public BigDecimal getPrEstimateAmount() {
        return prEstimateAmount;
    }

    public void setPrEstimateAmount(BigDecimal prEstimateAmount) {
        this.prEstimateAmount = prEstimateAmount;
    }

    public BigDecimal getPrApproveBudget() {
        return prApproveBudget;
    }

    public void setPrApproveBudget(BigDecimal prApproveBudget) {
        this.prApproveBudget = prApproveBudget;
    }

    public String getPrAbbreviation() {
        return PrAbbreviation;
    }

    public void setPrAbbreviation(String prAbbreviation) {
        PrAbbreviation = prAbbreviation;
    }

    public BigDecimal getPrGcjsqtAmt() {
        return PrGcjsqtAmt;
    }

    public void setPrGcjsqtAmt(BigDecimal prGcjsqtAmt) {
        PrGcjsqtAmt = prGcjsqtAmt;
    }

    public BigDecimal getPrTdAmount() {
        return PrTdAmount;
    }

    public void setPrTdAmount(BigDecimal prTdAmount) {
        PrTdAmount = prTdAmount;
    }

    public BigDecimal getPrYbAmt() {
        return PrYbAmt;
    }

    public void setPrYbAmt(BigDecimal prYbAmt) {
        PrYbAmt = prYbAmt;
    }

    public BigDecimal getPrJsqdkLx() {
        return PrJsqdkLx;
    }

    public void setPrJsqdkLx(BigDecimal prJsqdkLx) {
        PrJsqdkLx = prJsqdkLx;
    }

    public Date getPrPlanStartDate() {
        return PrPlanStartDate;
    }

    public void setPrPlanStartDate(Date prPlanStartDate) {
        PrPlanStartDate = prPlanStartDate;
    }

    public Date getPrPlanCompleteDate() {
        return PrPlanCompleteDate;
    }

    public void setPrPlanCompleteDate(Date prPlanCompleteDate) {
        PrPlanCompleteDate = prPlanCompleteDate;
    }
}
