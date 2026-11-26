package com.jpmsoft.common.model.response.InvestmentControl;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.jpmsoft.common.model.ProjectCommonRes;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 月完成情况
 */

public class InvCompleteMonthResponse extends ProjectCommonRes {

    @JsonProperty("pDepartment")
    private String pDepartment;
    @JsonProperty("pCompetentDepartmentName")
    private String pCompetentDepartmentName;
    @JsonProperty("pDepartmentName")
    private String pDepartmentName;
    private String icmId;
    private String ipvId;
    private String pId;
    private String conId;
    private String conName;
    private String icmDId;
    private String icmReportingMonth;
    private BigDecimal icmMonthlyInvestment;
    private Float icmMonthlyProportion;
    private BigDecimal icmAnnualInvestment;
    private Float icmAnnualProportion;
    private BigDecimal icmBudgetInvestment;
    private Float icmBudgetProportion;
    private String icmImgProgress;
    private String icmCompleted;
    private String icmIncompleteReason;
    private String icmMeasures;
    private String icmImg;
    private String icmCreateUid;
    private String icmCreateUname;
    private Date icmCreateTime;
    private Integer icmState;
    private Date icmStateTime;
    private String icmImgIds;
    private String icmReportingYear;
    private BigDecimal icmLanInvAmt;
    private BigDecimal icmDeviceInvAmt;
    private BigDecimal icmInfrastructureInvAmt;
    private BigDecimal icmOtherInvAmt;
    private BigDecimal icmLanExpAmt;
    private BigDecimal icmDeviceExpAmt;
    private BigDecimal icmInfrastructureExpAmt;
    private BigDecimal icmOtherExpAmt;
    private BigDecimal icmPayComposition;
    private String icmAwards;
    private String icmRemark;
    private String icmAnalysisReport;
    private String icmImageProgress;
    private BigDecimal icmOutputValue;
    private Float icmConstructionArea;
    private Float icmNewConsArea;
    private Integer icmPersonnelNum;
    private BigDecimal icmCompletionValue;
    private Float icmCompletedArea;
    private String icmConstructionAreaUnit;
    private String icmNewConsAreaUnit;
    private String icmCompletedAreaUnit;
    private BigDecimal icmActualCompletion;
    private BigDecimal icmBuildProject;
    private BigDecimal icmInstallProject;
    private BigDecimal icmEquipmentPurchase;
    private BigDecimal icmOtherExpenses;
    private BigDecimal icmActualReport;
    private BigDecimal icmMonthInvestTask;
    private BigDecimal icmLagInvest;
    private BigDecimal icmCumulativeInvest;
    private BigDecimal icmNotReported;

    private String icmDName;
    private BigDecimal ipvBudgetTotal;
    private BigDecimal ipvPlannedInvestment;
    private BigDecimal ipvCarryOver;
    private BigDecimal ipvScheduledPlan;
    private BigDecimal imsPlannedInv;
    private BigDecimal imsCumulativePay;
    private String ipvDeclareYear;
    private BigDecimal icmAllBudgetInv;
    private BigDecimal ipvAccumulatedSchedule;
    private List<InvMonthlyScheduleResponse> invMonthlySchedules;

    private String ipvConstructionStatus;

    public String getIpvConstructionStatus() {
        return ipvConstructionStatus;
    }

    public void setIpvConstructionStatus(String ipvConstructionStatus) {
        this.ipvConstructionStatus = ipvConstructionStatus;
    }

    /**
     * 单据流转状态：01-第一节点未提交(第一节点可编辑)；02-被驳回到第一节点(第一节点可编辑)；03-提交到第二节点(第二节点可编辑)；04被驳回到第二节点(第二节点可编辑)；05-提交到第三节点(第三节点可编辑)；06完结(全部不可编辑)
     */
    private String transferStatus;

    /**
     * 当月是否开工: 0-否；1-是
     */
    private String currentMonthStart;

    /**
     * 累计是否开工: 0-否；1-是
     */
    private String cumulativeStart;
    /**
     * 城建工程经办人
     */
    private String cjJbr;

    /**
     * 计划开工年月
     */
    @JsonFormat(pattern = "yyyy-MM", timezone = "GMT+8")
    private Date planStartTime;

    /**
     * 开工计划完成情况：01-滞后未完成；02-滞后完成；03-正常完成；04-提前完成
     */
    private String planCompleteResult;

    /**
     * 当月是否完工: 0-否；1-是
     */
    private String currentMonthComplete;

    /**
     * 累计是否开完工: 0-否；1-是
     */
    private String cumulativeComplete;

    /**
     * 计划完工年月
     */
    @JsonFormat(pattern = "yyyy-MM", timezone = "GMT+8")
    private Date planEndTime;

    /**
     * 完工计划完成情况：01-滞后未完成；02-滞后完成；03-正常完成；04-提前完成
     */
    private String completeResult;

    /**
     * X月年度累计计划投资(单位元)
     */
    private BigDecimal monthCumulativeInv;

    /**
     * 投资计划完成率
     */
    private Float monthCumulativeInvProportion;

    /**
     * 项目类别
     */
    private String ipvPType;

    /**
     *  年度计划类别
     */
    private String ipvConstructionStage;

    public void setIpvConstructionStage(String ipvConstructionStage) {
        this.ipvConstructionStage = ipvConstructionStage;
    }

    public String getIpvConstructionStage() {
        return this.ipvConstructionStage;
    }

    /**
     * 实际开工年月
     */
    @JsonFormat(pattern = "yyyy-MM", timezone = "GMT+8")
    private Date actualStartTime;

    /**
     * 实际完工年月
     */
    @JsonFormat(pattern = "yyyy-MM", timezone = "GMT+8")
    private Date actualEndTime;

    /**
     * 上报表格编号
     */
    private String reportTableNum;

    @JsonProperty("pCompetentDepartments")
    private String pCompetentDepartments;

    @JsonProperty("pCompetentDepartmentNames")
    private String pCompetentDepartmentNames;

    @JsonFormat(pattern = "yyyy-MM", timezone = "GMT+8")
    private Date currentTimeStart;

    @JsonFormat(pattern = "yyyy-MM", timezone = "GMT+8")
    private Date currentTimeEnd;

    @JsonFormat(pattern = "yyyy-MM", timezone = "GMT+8")
    private Date ipvStart;

    @JsonFormat(pattern = "yyyy-MM", timezone = "GMT+8")
    private Date ipvEnd;

    @JsonProperty("pProjectFormType")
    private String pProjectFormType;

    @JsonProperty("pApproveBudget")
    private BigDecimal pApproveBudget;

    @JsonProperty("pRecordAmount")
    private BigDecimal pRecordAmount;

    @JsonProperty("pApprovalAmount")
    private BigDecimal pApprovalAmount;

    /**
     * 当年是否完工
     */
    private Boolean ipvCompleted;

    public Boolean getIpvCompleted() {
        return ipvCompleted;
    }

    public void setIpvCompleted(Boolean ipvCompleted) {
        this.ipvCompleted = ipvCompleted;
    }

    public BigDecimal getpApprovalAmount() {
        return pApprovalAmount;
    }

    public void setpApprovalAmount(BigDecimal pApprovalAmount) {
        this.pApprovalAmount = pApprovalAmount;
    }

    public BigDecimal getpRecordAmount() {
        return pRecordAmount;
    }

    public void setpRecordAmount(BigDecimal pRecordAmount) {
        this.pRecordAmount = pRecordAmount;
    }

    public BigDecimal getpApproveBudget() {
        return pApproveBudget;
    }

    public void setpApproveBudget(BigDecimal pApproveBudget) {
        this.pApproveBudget = pApproveBudget;
    }

    public String getpProjectFormType() {
        return pProjectFormType;
    }

    public void setpProjectFormType(String pProjectFormType) {
        this.pProjectFormType = pProjectFormType;
    }

    public Date getIpvEnd() {
        return ipvEnd;
    }

    public void setIpvEnd(Date ipvEnd) {
        this.ipvEnd = ipvEnd;
    }

    public Date getIpvStart() {
        return ipvStart;
    }

    public void setIpvStart(Date ipvStart) {
        this.ipvStart = ipvStart;
    }

    public Date getCurrentTimeStart() {
        return currentTimeStart;
    }

    public void setCurrentTimeStart(Date currentTimeStart) {
        this.currentTimeStart = currentTimeStart;
    }

    public Date getCurrentTimeEnd() {
        return currentTimeEnd;
    }

    public void setCurrentTimeEnd(Date currentTimeEnd) {
        this.currentTimeEnd = currentTimeEnd;
    }

    public String getpCompetentDepartmentNames() {
        return pCompetentDepartmentNames;
    }

    public void setpCompetentDepartmentNames(String pCompetentDepartmentNames) {
        this.pCompetentDepartmentNames = pCompetentDepartmentNames;
    }

    public String getpCompetentDepartments() {
        return pCompetentDepartments;
    }

    public void setpCompetentDepartments(String pCompetentDepartments) {
        this.pCompetentDepartments = pCompetentDepartments;
    }

    public String getReportTableNum() {
        return reportTableNum;
    }

    public void setReportTableNum(String reportTableNum) {
        this.reportTableNum = reportTableNum;
    }

    private BigDecimal imsCumulativeInv;

    private Float imsCumulativeInvProportion;

    private  String icmProjectStage;

    public String getIcmProjectStage() {
        return icmProjectStage;
    }

    public void setIcmProjectStage(String icmProjectStage) {
        this.icmProjectStage = icmProjectStage;
    }

    public BigDecimal getImsCumulativeInv() {
        return imsCumulativeInv;
    }

    public void setImsCumulativeInv(BigDecimal imsCumulativeInv) {
        this.imsCumulativeInv = imsCumulativeInv;
    }

    public Float getImsCumulativeInvProportion() {
        return imsCumulativeInvProportion;
    }

    public void setImsCumulativeInvProportion(Float imsCumulativeInvProportion) {
        this.imsCumulativeInvProportion = imsCumulativeInvProportion;
    }

    public Date getActualStartTime() {
        return actualStartTime;
    }

    public void setActualStartTime(Date actualStartTime) {
        this.actualStartTime = actualStartTime;
    }

    public Date getActualEndTime() {
        return actualEndTime;
    }

    public void setActualEndTime(Date actualEndTime) {
        this.actualEndTime = actualEndTime;
    }

    public String getIpvPType() {
        return ipvPType;
    }

    public void setIpvPType(String ipvPType) {
        this.ipvPType = ipvPType;
    }

    public String getCurrentMonthStart() {
        return currentMonthStart;
    }

    public void setCurrentMonthStart(String currentMonthStart) {
        this.currentMonthStart = currentMonthStart;
    }

    public String getCumulativeStart() {
        return cumulativeStart;
    }

    public void setCumulativeStart(String cumulativeStart) {
        this.cumulativeStart = cumulativeStart;
    }

    public String getCjJbr() {
        return cjJbr;
    }

    public void setCjJbr(String cjJbr) {
        this.cjJbr = cjJbr;
    }

    public Date getPlanStartTime() {
        return planStartTime;
    }

    public void setPlanStartTime(Date planStartTime) {
        this.planStartTime = planStartTime;
    }

    public String getPlanCompleteResult() {
        return planCompleteResult;
    }

    public void setPlanCompleteResult(String planCompleteResult) {
        this.planCompleteResult = planCompleteResult;
    }

    public String getCurrentMonthComplete() {
        return currentMonthComplete;
    }

    public void setCurrentMonthComplete(String currentMonthComplete) {
        this.currentMonthComplete = currentMonthComplete;
    }

    public String getCumulativeComplete() {
        return cumulativeComplete;
    }

    public void setCumulativeComplete(String cumulativeComplete) {
        this.cumulativeComplete = cumulativeComplete;
    }

    public Date getPlanEndTime() {
        return planEndTime;
    }

    public void setPlanEndTime(Date planEndTime) {
        this.planEndTime = planEndTime;
    }

    public String getCompleteResult() {
        return completeResult;
    }

    public void setCompleteResult(String completeResult) {
        this.completeResult = completeResult;
    }

    public BigDecimal getMonthCumulativeInv() {
        return monthCumulativeInv;
    }

    public void setMonthCumulativeInv(BigDecimal monthCumulativeInv) {
        this.monthCumulativeInv = monthCumulativeInv;
    }

    public Float getMonthCumulativeInvProportion() {
        return monthCumulativeInvProportion;
    }

    public void setMonthCumulativeInvProportion(Float monthCumulativeInvProportion) {
        this.monthCumulativeInvProportion = monthCumulativeInvProportion;
    }

    public String getTransferStatus() {
        return transferStatus;
    }

    public void setTransferStatus(String transferStatus) {
        this.transferStatus = transferStatus;
    }

    public String getpDepartment() {
        return pDepartment;
    }

    public void setpDepartment(String pDepartment) {
        this.pDepartment = pDepartment;
    }

    @Override
    public String getpCompetentDepartmentName() {
        return pCompetentDepartmentName;
    }

    @Override
    public void setpCompetentDepartmentName(String pCompetentDepartmentName) {
        this.pCompetentDepartmentName = pCompetentDepartmentName;
    }

    public String getpDepartmentName() {
        return pDepartmentName;
    }

    public void setpDepartmentName(String pDepartmentName) {
        this.pDepartmentName = pDepartmentName;
    }

    public String getIcmId() {
        return icmId;
    }

    public void setIcmId(String icmId) {
        this.icmId = icmId;
    }

    public String getIpvId() {
        return ipvId;
    }

    public void setIpvId(String ipvId) {
        this.ipvId = ipvId;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getIcmDId() {
        return icmDId;
    }

    public void setIcmDId(String icmDId) {
        this.icmDId = icmDId;
    }

    public String getIcmReportingMonth() {
        return icmReportingMonth;
    }

    public void setIcmReportingMonth(String icmReportingMonth) {
        this.icmReportingMonth = icmReportingMonth;
    }

    public BigDecimal getIcmMonthlyInvestment() {
        return icmMonthlyInvestment;
    }

    public void setIcmMonthlyInvestment(BigDecimal icmMonthlyInvestment) {
        this.icmMonthlyInvestment = icmMonthlyInvestment;
    }

    public Float getIcmMonthlyProportion() {
        return icmMonthlyProportion;
    }

    public void setIcmMonthlyProportion(Float icmMonthlyProportion) {
        this.icmMonthlyProportion = icmMonthlyProportion;
    }

    public BigDecimal getIcmAnnualInvestment() {
        return icmAnnualInvestment;
    }

    public void setIcmAnnualInvestment(BigDecimal icmAnnualInvestment) {
        this.icmAnnualInvestment = icmAnnualInvestment;
    }

    public Float getIcmAnnualProportion() {
        return icmAnnualProportion;
    }

    public void setIcmAnnualProportion(Float icmAnnualProportion) {
        this.icmAnnualProportion = icmAnnualProportion;
    }

    public BigDecimal getIcmBudgetInvestment() {
        return icmBudgetInvestment;
    }

    public void setIcmBudgetInvestment(BigDecimal icmBudgetInvestment) {
        this.icmBudgetInvestment = icmBudgetInvestment;
    }

    public Float getIcmBudgetProportion() {
        return icmBudgetProportion;
    }

    public void setIcmBudgetProportion(Float icmBudgetProportion) {
        this.icmBudgetProportion = icmBudgetProportion;
    }

    public String getIcmImgProgress() {
        return icmImgProgress;
    }

    public void setIcmImgProgress(String icmImgProgress) {
        this.icmImgProgress = icmImgProgress;
    }

    public String getIcmCompleted() {
        return icmCompleted;
    }

    public void setIcmCompleted(String icmCompleted) {
        this.icmCompleted = icmCompleted;
    }

    public String getIcmIncompleteReason() {
        return icmIncompleteReason;
    }

    public void setIcmIncompleteReason(String icmIncompleteReason) {
        this.icmIncompleteReason = icmIncompleteReason;
    }

    public String getIcmMeasures() {
        return icmMeasures;
    }

    public void setIcmMeasures(String icmMeasures) {
        this.icmMeasures = icmMeasures;
    }

    public String getIcmImg() {
        return icmImg;
    }

    public void setIcmImg(String icmImg) {
        this.icmImg = icmImg;
    }

    public String getIcmCreateUid() {
        return icmCreateUid;
    }

    public void setIcmCreateUid(String icmCreateUid) {
        this.icmCreateUid = icmCreateUid;
    }

    public String getIcmCreateUname() {
        return icmCreateUname;
    }

    public void setIcmCreateUname(String icmCreateUname) {
        this.icmCreateUname = icmCreateUname;
    }

    public Date getIcmCreateTime() {
        return icmCreateTime;
    }

    public void setIcmCreateTime(Date icmCreateTime) {
        this.icmCreateTime = icmCreateTime;
    }

    public Integer getIcmState() {
        return icmState;
    }

    public void setIcmState(Integer icmState) {
        this.icmState = icmState;
    }

    public Date getIcmStateTime() {
        return icmStateTime;
    }

    public void setIcmStateTime(Date icmStateTime) {
        this.icmStateTime = icmStateTime;
    }

    public String getIcmImgIds() {
        return icmImgIds;
    }

    public void setIcmImgIds(String icmImgIds) {
        this.icmImgIds = icmImgIds;
    }

    public String getIcmReportingYear() {
        return icmReportingYear;
    }

    public void setIcmReportingYear(String icmReportingYear) {
        this.icmReportingYear = icmReportingYear;
    }

    public BigDecimal getIcmLanInvAmt() {
        return icmLanInvAmt;
    }

    public void setIcmLanInvAmt(BigDecimal icmLanInvAmt) {
        this.icmLanInvAmt = icmLanInvAmt;
    }

    public BigDecimal getIcmDeviceInvAmt() {
        return icmDeviceInvAmt;
    }

    public void setIcmDeviceInvAmt(BigDecimal icmDeviceInvAmt) {
        this.icmDeviceInvAmt = icmDeviceInvAmt;
    }

    public BigDecimal getIcmInfrastructureInvAmt() {
        return icmInfrastructureInvAmt;
    }

    public void setIcmInfrastructureInvAmt(BigDecimal icmInfrastructureInvAmt) {
        this.icmInfrastructureInvAmt = icmInfrastructureInvAmt;
    }

    public BigDecimal getIcmOtherInvAmt() {
        return icmOtherInvAmt;
    }

    public void setIcmOtherInvAmt(BigDecimal icmOtherInvAmt) {
        this.icmOtherInvAmt = icmOtherInvAmt;
    }

    public BigDecimal getIcmLanExpAmt() {
        return icmLanExpAmt;
    }

    public void setIcmLanExpAmt(BigDecimal icmLanExpAmt) {
        this.icmLanExpAmt = icmLanExpAmt;
    }

    public BigDecimal getIcmDeviceExpAmt() {
        return icmDeviceExpAmt;
    }

    public void setIcmDeviceExpAmt(BigDecimal icmDeviceExpAmt) {
        this.icmDeviceExpAmt = icmDeviceExpAmt;
    }

    public BigDecimal getIcmInfrastructureExpAmt() {
        return icmInfrastructureExpAmt;
    }

    public void setIcmInfrastructureExpAmt(BigDecimal icmInfrastructureExpAmt) {
        this.icmInfrastructureExpAmt = icmInfrastructureExpAmt;
    }

    public BigDecimal getIcmOtherExpAmt() {
        return icmOtherExpAmt;
    }

    public void setIcmOtherExpAmt(BigDecimal icmOtherExpAmt) {
        this.icmOtherExpAmt = icmOtherExpAmt;
    }

    public BigDecimal getIcmPayComposition() {
        return icmPayComposition;
    }

    public void setIcmPayComposition(BigDecimal icmPayComposition) {
        this.icmPayComposition = icmPayComposition;
    }

    public String getIcmAwards() {
        return icmAwards;
    }

    public void setIcmAwards(String icmAwards) {
        this.icmAwards = icmAwards;
    }

    public String getIcmRemark() {
        return icmRemark;
    }

    public void setIcmRemark(String icmRemark) {
        this.icmRemark = icmRemark;
    }

    public String getIcmAnalysisReport() {
        return icmAnalysisReport;
    }

    public void setIcmAnalysisReport(String icmAnalysisReport) {
        this.icmAnalysisReport = icmAnalysisReport;
    }

    public String getIcmImageProgress() {
        return icmImageProgress;
    }

    public void setIcmImageProgress(String icmImageProgress) {
        this.icmImageProgress = icmImageProgress;
    }

    public BigDecimal getIcmOutputValue() {
        return icmOutputValue;
    }

    public void setIcmOutputValue(BigDecimal icmOutputValue) {
        this.icmOutputValue = icmOutputValue;
    }

    public Float getIcmConstructionArea() {
        return icmConstructionArea;
    }

    public void setIcmConstructionArea(Float icmConstructionArea) {
        this.icmConstructionArea = icmConstructionArea;
    }

    public Float getIcmNewConsArea() {
        return icmNewConsArea;
    }

    public void setIcmNewConsArea(Float icmNewConsArea) {
        this.icmNewConsArea = icmNewConsArea;
    }

    public Integer getIcmPersonnelNum() {
        return icmPersonnelNum;
    }

    public void setIcmPersonnelNum(Integer icmPersonnelNum) {
        this.icmPersonnelNum = icmPersonnelNum;
    }

    public BigDecimal getIcmCompletionValue() {
        return icmCompletionValue;
    }

    public void setIcmCompletionValue(BigDecimal icmCompletionValue) {
        this.icmCompletionValue = icmCompletionValue;
    }

    public Float getIcmCompletedArea() {
        return icmCompletedArea;
    }

    public void setIcmCompletedArea(Float icmCompletedArea) {
        this.icmCompletedArea = icmCompletedArea;
    }

    public String getIcmConstructionAreaUnit() {
        return icmConstructionAreaUnit;
    }

    public void setIcmConstructionAreaUnit(String icmConstructionAreaUnit) {
        this.icmConstructionAreaUnit = icmConstructionAreaUnit;
    }

    public String getIcmNewConsAreaUnit() {
        return icmNewConsAreaUnit;
    }

    public void setIcmNewConsAreaUnit(String icmNewConsAreaUnit) {
        this.icmNewConsAreaUnit = icmNewConsAreaUnit;
    }

    public String getIcmCompletedAreaUnit() {
        return icmCompletedAreaUnit;
    }

    public void setIcmCompletedAreaUnit(String icmCompletedAreaUnit) {
        this.icmCompletedAreaUnit = icmCompletedAreaUnit;
    }

    public BigDecimal getIcmActualCompletion() {
        return icmActualCompletion;
    }

    public void setIcmActualCompletion(BigDecimal icmActualCompletion) {
        this.icmActualCompletion = icmActualCompletion;
    }

    public BigDecimal getIcmBuildProject() {
        return icmBuildProject;
    }

    public void setIcmBuildProject(BigDecimal icmBuildProject) {
        this.icmBuildProject = icmBuildProject;
    }

    public BigDecimal getIcmInstallProject() {
        return icmInstallProject;
    }

    public void setIcmInstallProject(BigDecimal icmInstallProject) {
        this.icmInstallProject = icmInstallProject;
    }

    public BigDecimal getIcmEquipmentPurchase() {
        return icmEquipmentPurchase;
    }

    public void setIcmEquipmentPurchase(BigDecimal icmEquipmentPurchase) {
        this.icmEquipmentPurchase = icmEquipmentPurchase;
    }

    public BigDecimal getIcmOtherExpenses() {
        return icmOtherExpenses;
    }

    public void setIcmOtherExpenses(BigDecimal icmOtherExpenses) {
        this.icmOtherExpenses = icmOtherExpenses;
    }

    public BigDecimal getIcmActualReport() {
        return icmActualReport;
    }

    public void setIcmActualReport(BigDecimal icmActualReport) {
        this.icmActualReport = icmActualReport;
    }

    public BigDecimal getIcmMonthInvestTask() {
        return icmMonthInvestTask;
    }

    public void setIcmMonthInvestTask(BigDecimal icmMonthInvestTask) {
        this.icmMonthInvestTask = icmMonthInvestTask;
    }

    public BigDecimal getIcmLagInvest() {
        return icmLagInvest;
    }

    public void setIcmLagInvest(BigDecimal icmLagInvest) {
        this.icmLagInvest = icmLagInvest;
    }

    public BigDecimal getIcmCumulativeInvest() {
        return icmCumulativeInvest;
    }

    public void setIcmCumulativeInvest(BigDecimal icmCumulativeInvest) {
        this.icmCumulativeInvest = icmCumulativeInvest;
    }

    public BigDecimal getIcmNotReported() {
        return icmNotReported;
    }

    public void setIcmNotReported(BigDecimal icmNotReported) {
        this.icmNotReported = icmNotReported;
    }

    public String getIcmDName() {
        return icmDName;
    }

    public void setIcmDName(String icmDName) {
        this.icmDName = icmDName;
    }

    public BigDecimal getIpvBudgetTotal() {
        return ipvBudgetTotal;
    }

    public void setIpvBudgetTotal(BigDecimal ipvBudgetTotal) {
        this.ipvBudgetTotal = ipvBudgetTotal;
    }

    public BigDecimal getIpvPlannedInvestment() {
        return ipvPlannedInvestment;
    }

    public void setIpvPlannedInvestment(BigDecimal ipvPlannedInvestment) {
        this.ipvPlannedInvestment = ipvPlannedInvestment;
    }

    public BigDecimal getIpvCarryOver() {
        return ipvCarryOver;
    }

    public void setIpvCarryOver(BigDecimal ipvCarryOver) {
        this.ipvCarryOver = ipvCarryOver;
    }

    public BigDecimal getIpvScheduledPlan() {
        return ipvScheduledPlan;
    }

    public void setIpvScheduledPlan(BigDecimal ipvScheduledPlan) {
        this.ipvScheduledPlan = ipvScheduledPlan;
    }

    public BigDecimal getImsPlannedInv() {
        return imsPlannedInv;
    }

    public void setImsPlannedInv(BigDecimal imsPlannedInv) {
        this.imsPlannedInv = imsPlannedInv;
    }

    public BigDecimal getImsCumulativePay() {
        return imsCumulativePay;
    }

    public void setImsCumulativePay(BigDecimal imsCumulativePay) {
        this.imsCumulativePay = imsCumulativePay;
    }

    public String getIpvDeclareYear() {
        return ipvDeclareYear;
    }

    public void setIpvDeclareYear(String ipvDeclareYear) {
        this.ipvDeclareYear = ipvDeclareYear;
    }

    public BigDecimal getIcmAllBudgetInv() {
        return icmAllBudgetInv;
    }

    public void setIcmAllBudgetInv(BigDecimal icmAllBudgetInv) {
        this.icmAllBudgetInv = icmAllBudgetInv;
    }

    public BigDecimal getIpvAccumulatedSchedule() {
        return ipvAccumulatedSchedule;
    }

    public void setIpvAccumulatedSchedule(BigDecimal ipvAccumulatedSchedule) {
        this.ipvAccumulatedSchedule = ipvAccumulatedSchedule;
    }

    public List<InvMonthlyScheduleResponse> getInvMonthlySchedules() {
        return invMonthlySchedules;
    }

    public void setInvMonthlySchedules(List<InvMonthlyScheduleResponse> invMonthlySchedules) {
        this.invMonthlySchedules = invMonthlySchedules;
    }

    @Override
    public String getConId() {
        return conId;
    }

    @Override
    public void setConId(String conId) {
        this.conId = conId;
    }

    public String getConName() {
        return conName;
    }

    public void setConName(String conName) {
        this.conName = conName;
    }
}
