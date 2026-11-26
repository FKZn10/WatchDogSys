package com.jpmsoft.common.model.request.InvestmentControl;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.jpmsoft.common.annotations.ClassInfo;
import com.jpmsoft.common.model.BaseFileRequest;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 月完成情况
 */

public class InvCompleteMonthRequest extends BaseFileRequest {

    private String icmId;
    private String ipvId;
    private String pId;
    private String conId;
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

    private String pName;
    private String beginTime;
    private String endTime;
    private String cjJbr;

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
     * 实际开工年月
     */
    @JsonFormat(pattern = "yyyy-MM", timezone = "GMT+8")
    private Date actualStartTime;

    @ClassInfo(title = "实际开工年月", type = "datetime1", value = "actualStartTimes", dbValue = " mn.actual_start_time ", no = 10)
    private List<String> actualStartTimes;

    /**
     * 实际完工年月
     */
    @JsonFormat(pattern = "yyyy-MM", timezone = "GMT+8")
    private Date actualEndTime;

    @ClassInfo(title = "实际完工年月", type = "datetime2", value = "actualEndTimes", dbValue = " mn.actual_end_time ", no = 11)
    private List<String> actualEndTimes;

    /**
     * 项目类别
     */
    private String ipvPType;

    /**
     * 项目类别集合
     */
    private List<String> ipvTypeList;

    private String pCompetentDepartment;

    private List<String> competentDepartmentList;

    /**
     * 开工计划完成情况
     */
    private List<String> planCompleteResultList;

    /**
     * 完工计划完成情况
     */
    private List<String> completeResultList;

    private String icmProjectStage;

    @ClassInfo(title = "状态",type="dictionary",value = "transferStatusList",dbValue = "mn.transfer_status",dictionary = "JPMsoft_SJSPZT",no = 8)
    private List<String> transferStatusList;

    private String createdBy;

    private Boolean noAuth;

    @ClassInfo(title = "项目名称",type="string",value = "pNameList",dbValue = " pro.p_name ",no = 1)
    @JsonProperty("pNameList")
    private List<String> pNameList;

    @ClassInfo(title = "项目分类",type="dictionary",value = "pTypeList",dbValue = " ipv.ipv_p_type",dictionary = "JPMsoft_NDTZXMFL",no = 2)
    @JsonProperty("pTypeList")
    private List<String> pTypeList;
    @ClassInfo(title = "年度计划类别",type="dictionary",value = "ipvStageList",dbValue = " ipv.ipv_Construction_Stage",dictionary = "JPMsoft_NDJHLB",no = 4)
    @JsonProperty("ipvStageList")
    private List<String> ipvStageList;
    @ClassInfo(title = "责任单位",type="cjZrdwList",value = "pCompetentDepartmentNameList",dbValue = " prod.D_Name ",no = 5)
    @JsonProperty("pCompetentDepartmentNameList")
    private List<String> pCompetentDepartmentNameList;
    @ClassInfo(title = "事业部分分支机构",type="string",value = "pDepartmentNameList",dbValue = " psd.D_Name ",no = 6)
    @JsonProperty("pDepartmentNameList")
    private List<String> pDepartmentNameList;
    @ClassInfo(title = "发起人",type="string",value = "createUnameList",dbValue = " mn.icm_Create_Uname ",no = 7)
    private List<String> createUnameList;
    @ClassInfo(title = "集团工程部经办人",type="String",value = "cjJbrList",dbValue = "s.cjJbr",no = 9)
    private List<String> cjJbrList;

    @JsonProperty("pCompetentDepartments")
    private String pCompetentDepartments;

    public String getpCompetentDepartments() {
        return pCompetentDepartments;
    }

    public void setpCompetentDepartments(String pCompetentDepartments) {
        this.pCompetentDepartments = pCompetentDepartments;
    }

    private String warningFilter;

    @JsonFormat(pattern = "yyyy-MM", timezone = "GMT+8")
    private Date currentTimeStart;

    @JsonFormat(pattern = "yyyy-MM", timezone = "GMT+8")
    private Date currentTimeEnd;

    @ClassInfo(title = "年度建设状态", type = "dictionary", value = "ipvConstructionStatusList", dbValue = " ipv.ipv_construction_status ", dictionary = "JPMsoft_NDJSZT", no = 3)
    private List<String> ipvConstructionStatusList;

    private String type;

    public List<String> getActualStartTimes() {
        return actualStartTimes;
    }

    public void setActualStartTimes(List<String> actualStartTimes) {
        this.actualStartTimes = actualStartTimes;
    }

    public List<String> getActualEndTimes() {
        return actualEndTimes;
    }

    public void setActualEndTimes(List<String> actualEndTimes) {
        this.actualEndTimes = actualEndTimes;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<String> getIpvConstructionStatusList() {
        return ipvConstructionStatusList;
    }

    public void setIpvConstructionStatusList(List<String> ipvConstructionStatusList) {
        this.ipvConstructionStatusList = ipvConstructionStatusList;
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

    public String getWarningFilter() {
        return warningFilter;
    }

    public void setWarningFilter(String warningFilter) {
        this.warningFilter = warningFilter;
    }
    /*    *//**
     * 年度计划类别
     *//*
    private String ipvStage;

    public String getIpvStage() {
        return icmProjectStage;
    }

    public void setIpvStage(String icmProjectStage) {
        this.icmProjectStage = icmProjectStage;
    }*/

    public String getIcmProjectStage() {
        return icmProjectStage;
    }

    public void setIcmProjectStage(String icmProjectStage) {
        this.icmProjectStage = icmProjectStage;
    }

    public List<String> getCompetentDepartmentList() {
        return competentDepartmentList;
    }

    public void setCompetentDepartmentList(List<String> competentDepartmentList) {
        this.competentDepartmentList = competentDepartmentList;
    }

    public List<String> getPlanCompleteResultList() {
        return planCompleteResultList;
    }

    public void setPlanCompleteResultList(List<String> planCompleteResultList) {
        this.planCompleteResultList = planCompleteResultList;
    }

    public List<String> getCompleteResultList() {
        return completeResultList;
    }

    public void setCompleteResultList(List<String> completeResultList) {
        this.completeResultList = completeResultList;
    }

    public String getpCompetentDepartment() {
        return pCompetentDepartment;
    }

    public void setpCompetentDepartment(String pCompetentDepartment) {
        this.pCompetentDepartment = pCompetentDepartment;
    }

    public List<String> getIpvTypeList() {
        return ipvTypeList;
    }

    public void setIpvTypeList(List<String> ipvTypeList) {
        this.ipvTypeList = ipvTypeList;
    }

    public String getIpvPType() {
        return ipvPType;
    }

    public void setIpvPType(String ipvPType) {
        this.ipvPType = ipvPType;
    }


    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Boolean getNoAuth() {
        return noAuth;
    }

    public void setNoAuth(Boolean noAuth) {
        this.noAuth = noAuth;
    }

    public String getCjJbr() {
        return cjJbr;
    }

    public void setCjJbr(String cjJbr) {
        this.cjJbr = cjJbr;
    }

    public List<String> getpNameList() {
        return pNameList;
    }

    public void setpNameList(List<String> pNameList) {
        this.pNameList = pNameList;
    }

    public List<String> getpTypeList() {
        return pTypeList;
    }

    public void setpTypeList(List<String> pTypeList) {
        this.pTypeList = pTypeList;
    }

    public List<String> getIpvStageList() {
        return ipvStageList;
    }

    public void setIpvStageList(List<String> ipvStageList) {
        this.ipvStageList = ipvStageList;
    }

    public List<String> getpCompetentDepartmentNameList() {
        return pCompetentDepartmentNameList;
    }

    public void setpCompetentDepartmentNameList(List<String> pCompetentDepartmentNameList) {
        this.pCompetentDepartmentNameList = pCompetentDepartmentNameList;
    }

    public List<String> getpDepartmentNameList() {
        return pDepartmentNameList;
    }

    public void setpDepartmentNameList(List<String> pDepartmentNameList) {
        this.pDepartmentNameList = pDepartmentNameList;
    }

    public List<String> getCreateUnameList() {
        return createUnameList;
    }

    public void setCreateUnameList(List<String> createUnameList) {
        this.createUnameList = createUnameList;
    }


    public List<String> getCjJbrList() {
        return cjJbrList;
    }

    public void setCjJbrList(List<String> cjJbrList) {
        this.cjJbrList = cjJbrList;
    }

    public List<String> getTransferStatusList() {
        return transferStatusList;
    }

    public void setTransferStatusList(List<String> transferStatusList) {
        this.transferStatusList = transferStatusList;
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

    public String getTransferStatus() {
        return transferStatus;
    }

    public void setTransferStatus(String transferStatus) {
        this.transferStatus = transferStatus;
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

    @Override
    public String getpId() {
        return pId;
    }

    @Override
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

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    @Override
    public String getConId() {
        return conId;
    }

    @Override
    public void setConId(String conId) {
        this.conId = conId;
    }
}
