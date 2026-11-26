package com.jpmsoft.common.model.response.DesignChange;


import com.jpmsoft.common.model.ProjectCommonRes;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 设计变更单
 */
public class ConDcoResponse extends ProjectCommonRes {

    private String dcoId;
    private String pId;
    private String conId;
    private String dcoCode;
    private String dcoProjectName;
    private String dcoConstructionUnitId;
    private String dcoDesignUnitId;
    private String dcoSupervisorId;
    private String dcoContractorId;
    private String dcoMajor;
    private Date dcoDate;
    private String dcoContent;
    private String dcoDoc;
    private String dcoDrawing;
    private String dcoCreateUid;
    private String dcoCreateUname;
    private Date dcoCreateTime;
    private Integer dcoState;
    private Date dcoStateTime;
    private String enisId;
    private String dcoCostBook;
    private String dcoGrade;
    private BigDecimal dcoEstimate;
    private Boolean dcoYesNo;
    private Date dcoAdoptTime;
    private Boolean dcoIsHistory;
    private String dcoDep;
    private String dcoName;
    private String dcoApprovalMatter;

    private String dcoSupervisionOpinion;
    private BigDecimal dcoSupervisionAmt;
    private String dcoSupervisionFile;
    private String dcoConsultingOpinion;
    private BigDecimal dcoConsultingAmt;
    private String dcoConsultingFile;
    private BigDecimal dcoFinalAmt;
    private BigDecimal dcoEstimatedAmt;
    private BigDecimal dcoConstructionAmt;
    private String dcoExceedBudget;
    private BigDecimal dcoIncrease;
    private Date dcoDirectorTime;
    private String dcoSummaryMust;
    private String dcoSummary;
    private Date dcoAuthorizeTime;
    private String dcoReviewFile;
    private BigDecimal dcoContractorAmt;
    private String dcoProposeId;
    private String dcoMainCode;
    private String dcoSubCode;

    private String dcoProposeName;
    private String dcoDepName;
    private String conName;
    private String dcoConstructionUnitName;
    private String dcoDesignUnitName;
    private String dcoSupervisorName;
    private String dcoContractorName;
    private String dcoDesignUnitNameHistory;
    private String dcoSupervisorNameHistory;
    private String dcoContractorNameHistory;
    private String enisCode;
    private String enisType;
    private String enisLocation;
    //单位简称
    private String dcoConsAbbName;
    private String dcoDesignAbbName;
    private String dcoSupervisorAbbName;
    private String dcoContractorAbbName;

    private String ecmId;
    private String ecmName;
    private BigDecimal dcbEstimateAmt;
    private String conCode;
    private BigDecimal conMoneyTotal;

    public String getDcoId() {
        return dcoId;
    }

    public void setDcoId(String dcoId) {
        this.dcoId = dcoId;
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

    public String getDcoConstructionUnitId() {
        return dcoConstructionUnitId;
    }

    public void setDcoConstructionUnitId(String dcoConstructionUnitId) {
        this.dcoConstructionUnitId = dcoConstructionUnitId;
    }

    public String getDcoDesignUnitId() {
        return dcoDesignUnitId;
    }

    public void setDcoDesignUnitId(String dcoDesignUnitId) {
        this.dcoDesignUnitId = dcoDesignUnitId;
    }

    public String getDcoSupervisorId() {
        return dcoSupervisorId;
    }

    public void setDcoSupervisorId(String dcoSupervisorId) {
        this.dcoSupervisorId = dcoSupervisorId;
    }

    public String getDcoContractorId() {
        return dcoContractorId;
    }

    public void setDcoContractorId(String dcoContractorId) {
        this.dcoContractorId = dcoContractorId;
    }

    public String getDcoMajor() {
        return dcoMajor;
    }

    public void setDcoMajor(String dcoMajor) {
        this.dcoMajor = dcoMajor;
    }

    public Date getDcoDate() {
        return dcoDate;
    }

    public void setDcoDate(Date dcoDate) {
        this.dcoDate = dcoDate;
    }

    public String getDcoContent() {
        return dcoContent;
    }

    public void setDcoContent(String dcoContent) {
        this.dcoContent = dcoContent;
    }

    public String getDcoDoc() {
        return dcoDoc;
    }

    public void setDcoDoc(String dcoDoc) {
        this.dcoDoc = dcoDoc;
    }

    public String getDcoDrawing() {
        return dcoDrawing;
    }

    public void setDcoDrawing(String dcoDrawing) {
        this.dcoDrawing = dcoDrawing;
    }

    public String getDcoCreateUid() {
        return dcoCreateUid;
    }

    public void setDcoCreateUid(String dcoCreateUid) {
        this.dcoCreateUid = dcoCreateUid;
    }

    public String getDcoCreateUname() {
        return dcoCreateUname;
    }

    public void setDcoCreateUname(String dcoCreateUname) {
        this.dcoCreateUname = dcoCreateUname;
    }

    public Date getDcoCreateTime() {
        return dcoCreateTime;
    }

    public void setDcoCreateTime(Date dcoCreateTime) {
        this.dcoCreateTime = dcoCreateTime;
    }

    public Integer getDcoState() {
        return dcoState;
    }

    public void setDcoState(Integer dcoState) {
        this.dcoState = dcoState;
    }

    public Date getDcoStateTime() {
        return dcoStateTime;
    }

    public void setDcoStateTime(Date dcoStateTime) {
        this.dcoStateTime = dcoStateTime;
    }

    public String getEnisId() {
        return enisId;
    }

    public void setEnisId(String enisId) {
        this.enisId = enisId;
    }

    public String getDcoCostBook() {
        return dcoCostBook;
    }

    public void setDcoCostBook(String dcoCostBook) {
        this.dcoCostBook = dcoCostBook;
    }

    public String getDcoGrade() {
        return dcoGrade;
    }

    public void setDcoGrade(String dcoGrade) {
        this.dcoGrade = dcoGrade;
    }

    public BigDecimal getDcoEstimate() {
        return dcoEstimate;
    }

    public void setDcoEstimate(BigDecimal dcoEstimate) {
        this.dcoEstimate = dcoEstimate;
    }

    public Boolean getDcoYesNo() {
        return dcoYesNo;
    }

    public void setDcoYesNo(Boolean dcoYesNo) {
        this.dcoYesNo = dcoYesNo;
    }

    public Date getDcoAdoptTime() {
        return dcoAdoptTime;
    }

    public void setDcoAdoptTime(Date dcoAdoptTime) {
        this.dcoAdoptTime = dcoAdoptTime;
    }

    public Boolean getDcoIsHistory() {
        return dcoIsHistory;
    }

    public void setDcoIsHistory(Boolean dcoIsHistory) {
        this.dcoIsHistory = dcoIsHistory;
    }

    public String getDcoDep() {
        return dcoDep;
    }

    public void setDcoDep(String dcoDep) {
        this.dcoDep = dcoDep;
    }

    public String getDcoName() {
        return dcoName;
    }

    public void setDcoName(String dcoName) {
        this.dcoName = dcoName;
    }

    public String getDcoApprovalMatter() {
        return dcoApprovalMatter;
    }

    public void setDcoApprovalMatter(String dcoApprovalMatter) {
        this.dcoApprovalMatter = dcoApprovalMatter;
    }

    public String getDcoSupervisionOpinion() {
        return dcoSupervisionOpinion;
    }

    public void setDcoSupervisionOpinion(String dcoSupervisionOpinion) {
        this.dcoSupervisionOpinion = dcoSupervisionOpinion;
    }

    public BigDecimal getDcoSupervisionAmt() {
        return dcoSupervisionAmt;
    }

    public void setDcoSupervisionAmt(BigDecimal dcoSupervisionAmt) {
        this.dcoSupervisionAmt = dcoSupervisionAmt;
    }

    public String getDcoSupervisionFile() {
        return dcoSupervisionFile;
    }

    public void setDcoSupervisionFile(String dcoSupervisionFile) {
        this.dcoSupervisionFile = dcoSupervisionFile;
    }

    public String getDcoConsultingOpinion() {
        return dcoConsultingOpinion;
    }

    public void setDcoConsultingOpinion(String dcoConsultingOpinion) {
        this.dcoConsultingOpinion = dcoConsultingOpinion;
    }

    public BigDecimal getDcoConsultingAmt() {
        return dcoConsultingAmt;
    }

    public void setDcoConsultingAmt(BigDecimal dcoConsultingAmt) {
        this.dcoConsultingAmt = dcoConsultingAmt;
    }

    public String getDcoConsultingFile() {
        return dcoConsultingFile;
    }

    public void setDcoConsultingFile(String dcoConsultingFile) {
        this.dcoConsultingFile = dcoConsultingFile;
    }

    public BigDecimal getDcoFinalAmt() {
        return dcoFinalAmt;
    }

    public void setDcoFinalAmt(BigDecimal dcoFinalAmt) {
        this.dcoFinalAmt = dcoFinalAmt;
    }

    public BigDecimal getDcoEstimatedAmt() {
        return dcoEstimatedAmt;
    }

    public void setDcoEstimatedAmt(BigDecimal dcoEstimatedAmt) {
        this.dcoEstimatedAmt = dcoEstimatedAmt;
    }

    public BigDecimal getDcoConstructionAmt() {
        return dcoConstructionAmt;
    }

    public void setDcoConstructionAmt(BigDecimal dcoConstructionAmt) {
        this.dcoConstructionAmt = dcoConstructionAmt;
    }

    public String getDcoExceedBudget() {
        return dcoExceedBudget;
    }

    public void setDcoExceedBudget(String dcoExceedBudget) {
        this.dcoExceedBudget = dcoExceedBudget;
    }

    public BigDecimal getDcoIncrease() {
        return dcoIncrease;
    }

    public void setDcoIncrease(BigDecimal dcoIncrease) {
        this.dcoIncrease = dcoIncrease;
    }

    public Date getDcoDirectorTime() {
        return dcoDirectorTime;
    }

    public void setDcoDirectorTime(Date dcoDirectorTime) {
        this.dcoDirectorTime = dcoDirectorTime;
    }

    public String getDcoSummaryMust() {
        return dcoSummaryMust;
    }

    public void setDcoSummaryMust(String dcoSummaryMust) {
        this.dcoSummaryMust = dcoSummaryMust;
    }

    public String getDcoSummary() {
        return dcoSummary;
    }

    public void setDcoSummary(String dcoSummary) {
        this.dcoSummary = dcoSummary;
    }

    public Date getDcoAuthorizeTime() {
        return dcoAuthorizeTime;
    }

    public void setDcoAuthorizeTime(Date dcoAuthorizeTime) {
        this.dcoAuthorizeTime = dcoAuthorizeTime;
    }

    public String getDcoReviewFile() {
        return dcoReviewFile;
    }

    public void setDcoReviewFile(String dcoReviewFile) {
        this.dcoReviewFile = dcoReviewFile;
    }

    public BigDecimal getDcoContractorAmt() {
        return dcoContractorAmt;
    }

    public void setDcoContractorAmt(BigDecimal dcoContractorAmt) {
        this.dcoContractorAmt = dcoContractorAmt;
    }

    public String getDcoProposeId() {
        return dcoProposeId;
    }

    public void setDcoProposeId(String dcoProposeId) {
        this.dcoProposeId = dcoProposeId;
    }

    public String getDcoMainCode() {
        return dcoMainCode;
    }

    public void setDcoMainCode(String dcoMainCode) {
        this.dcoMainCode = dcoMainCode;
    }

    public String getDcoSubCode() {
        return dcoSubCode;
    }

    public void setDcoSubCode(String dcoSubCode) {
        this.dcoSubCode = dcoSubCode;
    }

    public String getDcoProposeName() {
        return dcoProposeName;
    }

    public void setDcoProposeName(String dcoProposeName) {
        this.dcoProposeName = dcoProposeName;
    }

    public String getDcoDepName() {
        return dcoDepName;
    }

    public void setDcoDepName(String dcoDepName) {
        this.dcoDepName = dcoDepName;
    }


    public String getConName() {
        return conName;
    }

    public void setConName(String conName) {
        this.conName = conName;
    }

    public String getDcoConstructionUnitName() {
        return dcoConstructionUnitName;
    }

    public void setDcoConstructionUnitName(String dcoConstructionUnitName) {
        this.dcoConstructionUnitName = dcoConstructionUnitName;
    }

    public String getDcoDesignUnitName() {
        return dcoDesignUnitName;
    }

    public void setDcoDesignUnitName(String dcoDesignUnitName) {
        this.dcoDesignUnitName = dcoDesignUnitName;
    }

    public String getDcoSupervisorName() {
        return dcoSupervisorName;
    }

    public void setDcoSupervisorName(String dcoSupervisorName) {
        this.dcoSupervisorName = dcoSupervisorName;
    }

    public String getDcoContractorName() {
        return dcoContractorName;
    }

    public void setDcoContractorName(String dcoContractorName) {
        this.dcoContractorName = dcoContractorName;
    }


    public String getDcoDesignUnitNameHistory() {
        return dcoDesignUnitNameHistory;
    }

    public void setDcoDesignUnitNameHistory(String dcoDesignUnitNameHistory) {
        this.dcoDesignUnitNameHistory = dcoDesignUnitNameHistory;
    }

    public String getDcoSupervisorNameHistory() {
        return dcoSupervisorNameHistory;
    }

    public void setDcoSupervisorNameHistory(String dcoSupervisorNameHistory) {
        this.dcoSupervisorNameHistory = dcoSupervisorNameHistory;
    }

    public String getDcoContractorNameHistory() {
        return dcoContractorNameHistory;
    }

    public void setDcoContractorNameHistory(String dcoContractorNameHistory) {
        this.dcoContractorNameHistory = dcoContractorNameHistory;
    }

    public String getEnisCode() {
        return enisCode;
    }

    public void setEnisCode(String enisCode) {
        this.enisCode = enisCode;
    }

    public String getEnisType() {
        return enisType;
    }

    public void setEnisType(String enisType) {
        this.enisType = enisType;
    }

    public String getEnisLocation() {
        return enisLocation;
    }

    public void setEnisLocation(String enisLocation) {
        this.enisLocation = enisLocation;
    }

    public String getDcoConsAbbName() {
        return dcoConsAbbName;
    }

    public void setDcoConsAbbName(String dcoConsAbbName) {
        this.dcoConsAbbName = dcoConsAbbName;
    }

    public String getDcoDesignAbbName() {
        return dcoDesignAbbName;
    }

    public void setDcoDesignAbbName(String dcoDesignAbbName) {
        this.dcoDesignAbbName = dcoDesignAbbName;
    }

    public String getDcoSupervisorAbbName() {
        return dcoSupervisorAbbName;
    }

    public void setDcoSupervisorAbbName(String dcoSupervisorAbbName) {
        this.dcoSupervisorAbbName = dcoSupervisorAbbName;
    }

    public String getDcoContractorAbbName() {
        return dcoContractorAbbName;
    }

    public void setDcoContractorAbbName(String dcoContractorAbbName) {
        this.dcoContractorAbbName = dcoContractorAbbName;
    }

    public String getEcmId() {
        return ecmId;
    }

    public void setEcmId(String ecmId) {
        this.ecmId = ecmId;
    }

    public String getEcmName() {
        return ecmName;
    }

    public void setEcmName(String ecmName) {
        this.ecmName = ecmName;
    }

    public BigDecimal getDcbEstimateAmt() {
        return dcbEstimateAmt;
    }

    public void setDcbEstimateAmt(BigDecimal dcbEstimateAmt) {
        this.dcbEstimateAmt = dcbEstimateAmt;
    }

    public String getConCode() {
        return conCode;
    }

    public void setConCode(String conCode) {
        this.conCode = conCode;
    }

    public BigDecimal getConMoneyTotal() {
        return conMoneyTotal;
    }

    public void setConMoneyTotal(BigDecimal conMoneyTotal) {
        this.conMoneyTotal = conMoneyTotal;
    }
}
