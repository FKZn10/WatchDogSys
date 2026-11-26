package com.jpmsoft.common.model.request.DesignChange;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.jpmsoft.common.model.BaseFileRequest;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 设计变更单
 */
public class ConDcoRequest extends BaseFileRequest {

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


    private String dcoSupervisionFileIds;
    private String dcoConsultingFileIds;
    private String dcoReviewFileIds;
    private String pName;
    private String conName;
    private String dcoDocIds;
    private String dcoCostBookIds;
    private String dcoDrawingIds;

    private List<String> dcoCreateTimes;
    /**
     * 是否过滤 01 dcoGrade 必须为专家论证会审批通过的
     */
    private boolean filterGrade;
    /**
     * true过滤
     * Con_Dc_Budget 表选 Con_Dco 除作废的， 仅仅只能关联一次
     */
    private boolean filterDcBudget;

    private String enisCode;
    private String enisLocation;
    private String dcoConstructionUnitName;
    private String dcoContractorName;
    private String dcoSupervisorName;
    private String dcoDesignUnitName;
    private String dcoDepName;
    private List<String> dcoEstimates;
    private List<String> dcoDates;


    /**
     * EPC单位意见
     */

    // EPC牵头单位意见
    private String epcUnitOpinion;

    // 审核金额
    private BigDecimal epcAuditAmt;

    // EPC牵头单位意见附件
    private String epcUnitFile;

    private String epcUnitFileIds;

    /**
     * 代建单位意见
     */

    // 代建单位变更名称
    private String dcoBuilderContractorName;

    // 代建单位意见合同金额
    private BigDecimal dcoBuilderContractorAmt;

    // 设计变更送审价预计
    private BigDecimal dcoBuilderContractorEstimate;

    // 现场签证送审价
    private BigDecimal dcoBuilderContractorSvis;

    // 预计备案费用
    private BigDecimal dcoBuilderContractorEstimateRecord;

    // 现场签证代建单位是否会同监理、施工单位共同确认 单选项（选项内容 是/否）
    private String dcoBuilderContractorIsConfirm;

    // 附件上传（需包含共同确认相关书面材料）
    private String dcoBuilderContractorFile;

    private String dcoBuilderContractorFileIds;

    /**
     * 造价管理部意见
     */

    // 造价单位审核成果附件
    private String dcoCostBookFile;

    private String dcoCostBookFileIds;

    /**
     * 专家论证会
     */

    // 变更原因、责任分类说明
    private String dcoExpertReason;

    // 变更原因及依据
    private String dcoExpertReasonBasis;

    // 变更范围及内容
    private String dcoExpertScope;

    // 变更方案（上传ppt）附件
    private String dcoExpertPlanFile;

    private String dcoExpertPlanFileIds;

    @JsonProperty("filterConECFee")
    private String filterConECFee;

    public String getFilterConECFee() {
        return filterConECFee;
    }

    public void setFilterConECFee(String filterConECFee) {
        this.filterConECFee = filterConECFee;
    }

    public String getDcoBuilderContractorName() {
        return dcoBuilderContractorName;
    }

    public void setDcoBuilderContractorName(String dcoBuilderContractorName) {
        this.dcoBuilderContractorName = dcoBuilderContractorName;
    }

    public String getEpcUnitOpinion() {
        return epcUnitOpinion;
    }

    public void setEpcUnitOpinion(String epcUnitOpinion) {
        this.epcUnitOpinion = epcUnitOpinion;
    }

    public BigDecimal getEpcAuditAmt() {
        return epcAuditAmt;
    }

    public void setEpcAuditAmt(BigDecimal epcAuditAmt) {
        this.epcAuditAmt = epcAuditAmt;
    }

    public String getEpcUnitFile() {
        return epcUnitFile;
    }

    public void setEpcUnitFile(String epcUnitFile) {
        this.epcUnitFile = epcUnitFile;
    }

    public String getEpcUnitFileIds() {
        return epcUnitFileIds;
    }

    public void setEpcUnitFileIds(String epcUnitFileIds) {
        this.epcUnitFileIds = epcUnitFileIds;
    }

    public BigDecimal getDcoBuilderContractorAmt() {
        return dcoBuilderContractorAmt;
    }

    public void setDcoBuilderContractorAmt(BigDecimal dcoBuilderContractorAmt) {
        this.dcoBuilderContractorAmt = dcoBuilderContractorAmt;
    }

    public BigDecimal getDcoBuilderContractorEstimate() {
        return dcoBuilderContractorEstimate;
    }

    public void setDcoBuilderContractorEstimate(BigDecimal dcoBuilderContractorEstimate) {
        this.dcoBuilderContractorEstimate = dcoBuilderContractorEstimate;
    }

    public BigDecimal getDcoBuilderContractorSvis() {
        return dcoBuilderContractorSvis;
    }

    public void setDcoBuilderContractorSvis(BigDecimal dcoBuilderContractorSvis) {
        this.dcoBuilderContractorSvis = dcoBuilderContractorSvis;
    }

    public BigDecimal getDcoBuilderContractorEstimateRecord() {
        return dcoBuilderContractorEstimateRecord;
    }

    public void setDcoBuilderContractorEstimateRecord(BigDecimal dcoBuilderContractorEstimateRecord) {
        this.dcoBuilderContractorEstimateRecord = dcoBuilderContractorEstimateRecord;
    }

    public String getDcoBuilderContractorIsConfirm() {
        return dcoBuilderContractorIsConfirm;
    }

    public void setDcoBuilderContractorIsConfirm(String dcoBuilderContractorIsConfirm) {
        this.dcoBuilderContractorIsConfirm = dcoBuilderContractorIsConfirm;
    }

    public String getDcoBuilderContractorFile() {
        return dcoBuilderContractorFile;
    }

    public void setDcoBuilderContractorFile(String dcoBuilderContractorFile) {
        this.dcoBuilderContractorFile = dcoBuilderContractorFile;
    }

    public String getDcoBuilderContractorFileIds() {
        return dcoBuilderContractorFileIds;
    }

    public void setDcoBuilderContractorFileIds(String dcoBuilderContractorFileIds) {
        this.dcoBuilderContractorFileIds = dcoBuilderContractorFileIds;
    }

    public String getDcoCostBookFile() {
        return dcoCostBookFile;
    }

    public void setDcoCostBookFile(String dcoCostBookFile) {
        this.dcoCostBookFile = dcoCostBookFile;
    }

    public String getDcoCostBookFileIds() {
        return dcoCostBookFileIds;
    }

    public void setDcoCostBookFileIds(String dcoCostBookFileIds) {
        this.dcoCostBookFileIds = dcoCostBookFileIds;
    }

    public String getDcoExpertReason() {
        return dcoExpertReason;
    }

    public void setDcoExpertReason(String dcoExpertReason) {
        this.dcoExpertReason = dcoExpertReason;
    }

    public String getDcoExpertReasonBasis() {
        return dcoExpertReasonBasis;
    }

    public void setDcoExpertReasonBasis(String dcoExpertReasonBasis) {
        this.dcoExpertReasonBasis = dcoExpertReasonBasis;
    }

    public String getDcoExpertScope() {
        return dcoExpertScope;
    }

    public void setDcoExpertScope(String dcoExpertScope) {
        this.dcoExpertScope = dcoExpertScope;
    }

    public String getDcoExpertPlanFile() {
        return dcoExpertPlanFile;
    }

    public void setDcoExpertPlanFile(String dcoExpertPlanFile) {
        this.dcoExpertPlanFile = dcoExpertPlanFile;
    }

    public String getDcoExpertPlanFileIds() {
        return dcoExpertPlanFileIds;
    }

    public void setDcoExpertPlanFileIds(String dcoExpertPlanFileIds) {
        this.dcoExpertPlanFileIds = dcoExpertPlanFileIds;
    }

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

    public String getDcoSupervisionFileIds() {
        return dcoSupervisionFileIds;
    }

    public void setDcoSupervisionFileIds(String dcoSupervisionFileIds) {
        this.dcoSupervisionFileIds = dcoSupervisionFileIds;
    }

    public String getDcoConsultingFileIds() {
        return dcoConsultingFileIds;
    }

    public void setDcoConsultingFileIds(String dcoConsultingFileIds) {
        this.dcoConsultingFileIds = dcoConsultingFileIds;
    }

    public String getDcoReviewFileIds() {
        return dcoReviewFileIds;
    }

    public void setDcoReviewFileIds(String dcoReviewFileIds) {
        this.dcoReviewFileIds = dcoReviewFileIds;
    }

    public String getDcoCostBookIds() {
        return dcoCostBookIds;
    }

    public void setDcoCostBookIds(String dcoCostBookIds) {
        this.dcoCostBookIds = dcoCostBookIds;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public String getConName() {
        return conName;
    }

    public void setConName(String conName) {
        this.conName = conName;
    }

    public String getDcoDocIds() {
        return dcoDocIds;
    }

    public void setDcoDocIds(String dcoDocIds) {
        this.dcoDocIds = dcoDocIds;
    }

    public String getDcoDrawingIds() {
        return dcoDrawingIds;
    }

    public void setDcoDrawingIds(String dcoDrawingIds) {
        this.dcoDrawingIds = dcoDrawingIds;
    }

    public List<String> getDcoCreateTimes() {
        return dcoCreateTimes;
    }

    public void setDcoCreateTimes(List<String> dcoCreateTimes) {
        this.dcoCreateTimes = dcoCreateTimes;
    }

    public boolean isFilterGrade() {
        return filterGrade;
    }

    public void setFilterGrade(boolean filterGrade) {
        this.filterGrade = filterGrade;
    }

    public boolean isFilterDcBudget() {
        return filterDcBudget;
    }

    public void setFilterDcBudget(boolean filterDcBudget) {
        this.filterDcBudget = filterDcBudget;
    }

    public String getEnisCode() {
        return enisCode;
    }

    public void setEnisCode(String enisCode) {
        this.enisCode = enisCode;
    }

    public String getEnisLocation() {
        return enisLocation;
    }

    public void setEnisLocation(String enisLocation) {
        this.enisLocation = enisLocation;
    }

    public String getDcoConstructionUnitName() {
        return dcoConstructionUnitName;
    }

    public void setDcoConstructionUnitName(String dcoConstructionUnitName) {
        this.dcoConstructionUnitName = dcoConstructionUnitName;
    }

    public String getDcoContractorName() {
        return dcoContractorName;
    }

    public void setDcoContractorName(String dcoContractorName) {
        this.dcoContractorName = dcoContractorName;
    }

    public String getDcoSupervisorName() {
        return dcoSupervisorName;
    }

    public void setDcoSupervisorName(String dcoSupervisorName) {
        this.dcoSupervisorName = dcoSupervisorName;
    }

    public String getDcoDesignUnitName() {
        return dcoDesignUnitName;
    }

    public void setDcoDesignUnitName(String dcoDesignUnitName) {
        this.dcoDesignUnitName = dcoDesignUnitName;
    }

    public String getDcoDepName() {
        return dcoDepName;
    }

    public void setDcoDepName(String dcoDepName) {
        this.dcoDepName = dcoDepName;
    }

    public List<String> getDcoEstimates() {
        return dcoEstimates;
    }

    public void setDcoEstimates(List<String> dcoEstimates) {
        this.dcoEstimates = dcoEstimates;
    }

    public List<String> getDcoDates() {
        return dcoDates;
    }

    public void setDcoDates(List<String> dcoDates) {
        this.dcoDates = dcoDates;
    }
}
