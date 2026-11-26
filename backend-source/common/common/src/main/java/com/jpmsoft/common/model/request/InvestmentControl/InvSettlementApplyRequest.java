package com.jpmsoft.common.model.request.InvestmentControl;

import com.jpmsoft.common.model.BaseFileRequest;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 合同结算申请
 */
public class InvSettlementApplyRequest extends BaseFileRequest {

    private String saId;
    private String conId;
    private BigDecimal saBudgetAmount;
    private String saContractor;
    private String saSupervisor;
    private String saCostConsult;
    private BigDecimal saContractorAmount;
    private BigDecimal saSupervisorAmount;
    private BigDecimal saCostConsultAmount;
    private Integer saSupervisorCheck;
    private Integer saHandDown;
    private String saCreateUid;
    private String saCreateUname;
    private Date saCreateTime;
    private Integer saState;
    private Date saStateTime;
    private BigDecimal saApprovedAmt;
    private BigDecimal saAuditFees;
    private Integer saCuAf;
    private String saAuditReport;
    private Boolean saHistorical;
    private String saDraft;
    private String saSupplement;
    private Date saSubmissionTime;
    private Date saApprovedTime;
    private String saApprovedDes;
    private BigDecimal saIncreaseDecrease;
    private String saSubmissionUser;
    private String saSubmissionPhone;
    private String saConCode;
    private BigDecimal saWarrantyAmt;
    private BigDecimal saWarrantyRate;
    private String saExamineUser;
    private String saExaminePhone;
    private String saOpinion;


    private String pId;
    private String pName;
    private String conName;
    private String saAuditReportIds;
    private String saDraftIds;
    private String saSupplementIds;
    private String saIsSubContract;
    private String saSupplementId;

    private Boolean haveCostConsult;

    private String saReviewUnit;
    private String saReportFile;
    private String saReportFileIds;
    private String saReviewReportUnit;
    private String saReviewReportUnitIds;

    private String saContractorName;

    // 创建时间 范围查询
    private List<String> saCreateTimes;

    public String getSaContractorName() {
        return saContractorName;
    }

    public void setSaContractorName(String saContractorName) {
        this.saContractorName = saContractorName;
    }

    public List<String> getSaCreateTimes() {
        return saCreateTimes;
    }

    public void setSaCreateTimes(List<String> saCreateTimes) {
        this.saCreateTimes = saCreateTimes;
    }

    public String getSaReportFile() {
        return saReportFile;
    }

    public void setSaReportFile(String saReportFile) {
        this.saReportFile = saReportFile;
    }

    public String getSaReviewUnit() {
        return saReviewUnit;
    }

    public void setSaReviewUnit(String saReviewUnit) {
        this.saReviewUnit = saReviewUnit;
    }

    public String getSaReportFileIds() {
        return saReportFileIds;
    }

    public void setSaReportFileIds(String saReportFileIds) {
        this.saReportFileIds = saReportFileIds;
    }

    public String getSaReviewReportUnit() {
        return saReviewReportUnit;
    }

    public void setSaReviewReportUnit(String saReviewReportUnit) {
        this.saReviewReportUnit = saReviewReportUnit;
    }

    public String getSaReviewReportUnitIds() {
        return saReviewReportUnitIds;
    }

    public void setSaReviewReportUnitIds(String saReviewReportUnitIds) {
        this.saReviewReportUnitIds = saReviewReportUnitIds;
    }

    public String getConName() {
        return conName;
    }

    public void setConName(String conName) {
        this.conName = conName;
    }

    public String getSaId() {
        return saId;
    }

    public void setSaId(String saId) {
        this.saId = saId;
    }

    public String getConId() {
        return conId;
    }

    public void setConId(String conId) {
        this.conId = conId;
    }

    public BigDecimal getSaBudgetAmount() {
        return saBudgetAmount;
    }

    public void setSaBudgetAmount(BigDecimal saBudgetAmount) {
        this.saBudgetAmount = saBudgetAmount;
    }

    public String getSaContractor() {
        return saContractor;
    }

    public void setSaContractor(String saContractor) {
        this.saContractor = saContractor;
    }

    public String getSaSupervisor() {
        return saSupervisor;
    }

    public void setSaSupervisor(String saSupervisor) {
        this.saSupervisor = saSupervisor;
    }

    public String getSaCostConsult() {
        return saCostConsult;
    }

    public void setSaCostConsult(String saCostConsult) {
        this.saCostConsult = saCostConsult;
    }

    public BigDecimal getSaContractorAmount() {
        return saContractorAmount;
    }

    public void setSaContractorAmount(BigDecimal saContractorAmount) {
        this.saContractorAmount = saContractorAmount;
    }

    public BigDecimal getSaSupervisorAmount() {
        return saSupervisorAmount;
    }

    public void setSaSupervisorAmount(BigDecimal saSupervisorAmount) {
        this.saSupervisorAmount = saSupervisorAmount;
    }

    public BigDecimal getSaCostConsultAmount() {
        return saCostConsultAmount;
    }

    public void setSaCostConsultAmount(BigDecimal saCostConsultAmount) {
        this.saCostConsultAmount = saCostConsultAmount;
    }

    public Integer getSaSupervisorCheck() {
        return saSupervisorCheck;
    }

    public void setSaSupervisorCheck(Integer saSupervisorCheck) {
        this.saSupervisorCheck = saSupervisorCheck;
    }

    public Integer getSaHandDown() {
        return saHandDown;
    }

    public void setSaHandDown(Integer saHandDown) {
        this.saHandDown = saHandDown;
    }

    public String getSaCreateUid() {
        return saCreateUid;
    }

    public void setSaCreateUid(String saCreateUid) {
        this.saCreateUid = saCreateUid;
    }

    public String getSaCreateUname() {
        return saCreateUname;
    }

    public void setSaCreateUname(String saCreateUname) {
        this.saCreateUname = saCreateUname;
    }

    public Date getSaCreateTime() {
        return saCreateTime;
    }

    public void setSaCreateTime(Date saCreateTime) {
        this.saCreateTime = saCreateTime;
    }

    public Integer getSaState() {
        return saState;
    }

    public void setSaState(Integer saState) {
        this.saState = saState;
    }

    public Date getSaStateTime() {
        return saStateTime;
    }

    public void setSaStateTime(Date saStateTime) {
        this.saStateTime = saStateTime;
    }

    public BigDecimal getSaApprovedAmt() {
        return saApprovedAmt;
    }

    public void setSaApprovedAmt(BigDecimal saApprovedAmt) {
        this.saApprovedAmt = saApprovedAmt;
    }

    public BigDecimal getSaAuditFees() {
        return saAuditFees;
    }

    public void setSaAuditFees(BigDecimal saAuditFees) {
        this.saAuditFees = saAuditFees;
    }

    public Integer getSaCuAf() {
        return saCuAf;
    }

    public void setSaCuAf(Integer saCuAf) {
        this.saCuAf = saCuAf;
    }

    public String getSaAuditReport() {
        return saAuditReport;
    }

    public void setSaAuditReport(String saAuditReport) {
        this.saAuditReport = saAuditReport;
    }

    public Boolean getSaHistorical() {
        return saHistorical;
    }

    public void setSaHistorical(Boolean saHistorical) {
        this.saHistorical = saHistorical;
    }

    public String getSaDraft() {
        return saDraft;
    }

    public void setSaDraft(String saDraft) {
        this.saDraft = saDraft;
    }

    public String getSaSupplement() {
        return saSupplement;
    }

    public void setSaSupplement(String saSupplement) {
        this.saSupplement = saSupplement;
    }

    public Date getSaSubmissionTime() {
        return saSubmissionTime;
    }

    public void setSaSubmissionTime(Date saSubmissionTime) {
        this.saSubmissionTime = saSubmissionTime;
    }

    public Date getSaApprovedTime() {
        return saApprovedTime;
    }

    public void setSaApprovedTime(Date saApprovedTime) {
        this.saApprovedTime = saApprovedTime;
    }

    public String getSaApprovedDes() {
        return saApprovedDes;
    }

    public void setSaApprovedDes(String saApprovedDes) {
        this.saApprovedDes = saApprovedDes;
    }

    public BigDecimal getSaIncreaseDecrease() {
        return saIncreaseDecrease;
    }

    public void setSaIncreaseDecrease(BigDecimal saIncreaseDecrease) {
        this.saIncreaseDecrease = saIncreaseDecrease;
    }

    public String getSaSubmissionUser() {
        return saSubmissionUser;
    }

    public void setSaSubmissionUser(String saSubmissionUser) {
        this.saSubmissionUser = saSubmissionUser;
    }

    public String getSaSubmissionPhone() {
        return saSubmissionPhone;
    }

    public void setSaSubmissionPhone(String saSubmissionPhone) {
        this.saSubmissionPhone = saSubmissionPhone;
    }

    public String getSaConCode() {
        return saConCode;
    }

    public void setSaConCode(String saConCode) {
        this.saConCode = saConCode;
    }

    public BigDecimal getSaWarrantyAmt() {
        return saWarrantyAmt;
    }

    public void setSaWarrantyAmt(BigDecimal saWarrantyAmt) {
        this.saWarrantyAmt = saWarrantyAmt;
    }

    public BigDecimal getSaWarrantyRate() {
        return saWarrantyRate;
    }

    public void setSaWarrantyRate(BigDecimal saWarrantyRate) {
        this.saWarrantyRate = saWarrantyRate;
    }

    public String getSaExamineUser() {
        return saExamineUser;
    }

    public void setSaExamineUser(String saExamineUser) {
        this.saExamineUser = saExamineUser;
    }

    public String getSaExaminePhone() {
        return saExaminePhone;
    }

    public void setSaExaminePhone(String saExaminePhone) {
        this.saExaminePhone = saExaminePhone;
    }

    public String getSaOpinion() {
        return saOpinion;
    }

    public void setSaOpinion(String saOpinion) {
        this.saOpinion = saOpinion;
    }

    public String getSaDraftIds() {
        return saDraftIds;
    }

    public void setSaDraftIds(String saDraftIds) {
        this.saDraftIds = saDraftIds;
    }

    public String getSaSupplementIds() {
        return saSupplementIds;
    }

    public void setSaSupplementIds(String saSupplementIds) {
        this.saSupplementIds = saSupplementIds;
    }

    public String getSaAuditReportIds() {
        return saAuditReportIds;
    }

    public void setSaAuditReportIds(String saAuditReportIds) {
        this.saAuditReportIds = saAuditReportIds;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public String getSaIsSubContract() {
        return saIsSubContract;
    }

    public void setSaIsSubContract(String saIsSubContract) {
        this.saIsSubContract = saIsSubContract;
    }

    public String getSaSupplementId() {
        return saSupplementId;
    }

    public void setSaSupplementId(String saSupplementId) {
        this.saSupplementId = saSupplementId;
    }

    public Boolean getHaveCostConsult() {
        return haveCostConsult;
    }

    public void setHaveCostConsult(Boolean haveCostConsult) {
        this.haveCostConsult = haveCostConsult;
    }
}
