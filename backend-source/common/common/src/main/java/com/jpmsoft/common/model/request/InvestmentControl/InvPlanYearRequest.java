package com.jpmsoft.common.model.request.InvestmentControl;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jpmsoft.common.model.BaseFileRequest;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 年度投资计划
 */

public class InvPlanYearRequest extends BaseFileRequest {
    private String ipvId;
    private String pId;
    private String conId;
    private String ipvConstructionStage;
    private BigDecimal ipvBudgetTotal;
    private String ipvPType;
    private String ipvDId;
    private String ipvCId;
    private String ipvExpenseDetails;
    private String ipvDeclareYear;
    private BigDecimal ipvDoneLastYear;
    private BigDecimal ipvCarryOver;
    private BigDecimal ipvScheduledPlan;
    private BigDecimal ipvReleasedPlan;
    private BigDecimal ipvTotalFunds;
    private BigDecimal ipvPlannedInvestment;
    private BigDecimal ipvPlannedPay;
    private BigDecimal ipvCumulativePay;
    private BigDecimal ipvAnnualInv;
    private BigDecimal ipvAnnualPay;
    private String ipvLImgProgress;
    private String ipvTImgProgress;
    private Date ipvCreateTime;
    private String ipvCreateUid;
    private String ipvCreateUname;
    private Integer ipvState;
    private Date ipvStateTime;
    private BigDecimal ipvAccumulatedSchedule;
    private String ipvConstStatus;
    private String ipvSegments;
    private String ipvStreet;
    private String ipvArea;
    private Date ipvStart;
    private Date ipvEnd;
    private Boolean ipvLibrary;
    private Boolean ipvNewly;
    private Boolean ipvCompleted;

    private List<InvMonthlyScheduleRequest> invMonthlySchedules;
    private String pName;
    private String pCompetentDepartment;

    //责任单位
    private String pCompetentDepartments;

    //年度建设状态
    private String ipvConstructionStatus;

    public String getpCompetentDepartments() {
        return pCompetentDepartments;
    }

    public void setpCompetentDepartments(String pCompetentDepartments) {
        this.pCompetentDepartments = pCompetentDepartments;
    }

    public String getIpvConstructionStatus() {
        return ipvConstructionStatus;
    }

    public void setIpvConstructionStatus(String ipvConstructionStatus) {
        this.ipvConstructionStatus = ipvConstructionStatus;
    }

    /**
     * 上报表格编号
     */
    private String reportTableNum;

    private String ipvPhone;

    private String ipvCName;

    @JsonProperty("conName")
    private String conName;
    // '是否领导看板：0-非领导看板，1-领导看板'
    private Integer isLeaderBoard;
    private String projectName;
    private String contractName;

    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContractName() {
        return contractName;
    }

    public void setContractName(String contractName) {
        this.contractName = contractName;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Integer getIsLeaderBoard() {
        return isLeaderBoard;
    }

    public void setIsLeaderBoard(Integer isLeaderBoard) {
        this.isLeaderBoard = isLeaderBoard;
    }

    public String getConName() {
        return conName;
    }

    public void setConName(String conName) {
        this.conName = conName;
    }

    public List<InvMonthlyScheduleRequest> getInvMonthlySchedules() {
        return invMonthlySchedules;
    }

    public void setInvMonthlySchedules(List<InvMonthlyScheduleRequest> invMonthlySchedules) {
        this.invMonthlySchedules = invMonthlySchedules;
    }

    public String getIpvCName() {
        return ipvCName;
    }

    public void setIpvCName(String ipvCName) {
        this.ipvCName = ipvCName;
    }

    public String getIpvPhone() {
        return ipvPhone;
    }

    public void setIpvPhone(String ipvPhone) {
        this.ipvPhone = ipvPhone;
    }

    public String getReportTableNum() {
        return reportTableNum;
    }

    public void setReportTableNum(String reportTableNum) {
        this.reportTableNum = reportTableNum;
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

    public String getIpvConstructionStage() {
        return ipvConstructionStage;
    }

    public void setIpvConstructionStage(String ipvConstructionStage) {
        this.ipvConstructionStage = ipvConstructionStage;
    }

    public BigDecimal getIpvBudgetTotal() {
        return ipvBudgetTotal;
    }

    public void setIpvBudgetTotal(BigDecimal ipvBudgetTotal) {
        this.ipvBudgetTotal = ipvBudgetTotal;
    }

    public String getIpvPType() {
        return ipvPType;
    }

    public void setIpvPType(String ipvPType) {
        this.ipvPType = ipvPType;
    }

    public String getIpvDId() {
        return ipvDId;
    }

    public void setIpvDId(String ipvDId) {
        this.ipvDId = ipvDId;
    }

    public String getIpvExpenseDetails() {
        return ipvExpenseDetails;
    }

    public void setIpvExpenseDetails(String ipvExpenseDetails) {
        this.ipvExpenseDetails = ipvExpenseDetails;
    }

    public String getIpvDeclareYear() {
        return ipvDeclareYear;
    }

    public void setIpvDeclareYear(String ipvDeclareYear) {
        this.ipvDeclareYear = ipvDeclareYear;
    }

    public BigDecimal getIpvDoneLastYear() {
        return ipvDoneLastYear;
    }

    public void setIpvDoneLastYear(BigDecimal ipvDoneLastYear) {
        this.ipvDoneLastYear = ipvDoneLastYear;
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

    public BigDecimal getIpvReleasedPlan() {
        return ipvReleasedPlan;
    }

    public void setIpvReleasedPlan(BigDecimal ipvReleasedPlan) {
        this.ipvReleasedPlan = ipvReleasedPlan;
    }

    public BigDecimal getIpvTotalFunds() {
        return ipvTotalFunds;
    }

    public void setIpvTotalFunds(BigDecimal ipvTotalFunds) {
        this.ipvTotalFunds = ipvTotalFunds;
    }

    public BigDecimal getIpvPlannedInvestment() {
        return ipvPlannedInvestment;
    }

    public void setIpvPlannedInvestment(BigDecimal ipvPlannedInvestment) {
        this.ipvPlannedInvestment = ipvPlannedInvestment;
    }

    public BigDecimal getIpvPlannedPay() {
        return ipvPlannedPay;
    }

    public void setIpvPlannedPay(BigDecimal ipvPlannedPay) {
        this.ipvPlannedPay = ipvPlannedPay;
    }

    public BigDecimal getIpvCumulativePay() {
        return ipvCumulativePay;
    }

    public void setIpvCumulativePay(BigDecimal ipvCumulativePay) {
        this.ipvCumulativePay = ipvCumulativePay;
    }

    public BigDecimal getIpvAnnualInv() {
        return ipvAnnualInv;
    }

    public void setIpvAnnualInv(BigDecimal ipvAnnualInv) {
        this.ipvAnnualInv = ipvAnnualInv;
    }

    public BigDecimal getIpvAnnualPay() {
        return ipvAnnualPay;
    }

    public void setIpvAnnualPay(BigDecimal ipvAnnualPay) {
        this.ipvAnnualPay = ipvAnnualPay;
    }

    public String getIpvLImgProgress() {
        return ipvLImgProgress;
    }

    public void setIpvLImgProgress(String ipvLImgProgress) {
        this.ipvLImgProgress = ipvLImgProgress;
    }

    public String getIpvTImgProgress() {
        return ipvTImgProgress;
    }

    public void setIpvTImgProgress(String ipvTImgProgress) {
        this.ipvTImgProgress = ipvTImgProgress;
    }

    public Date getIpvCreateTime() {
        return ipvCreateTime;
    }

    public void setIpvCreateTime(Date ipvCreateTime) {
        this.ipvCreateTime = ipvCreateTime;
    }

    public String getIpvCreateUid() {
        return ipvCreateUid;
    }

    public void setIpvCreateUid(String ipvCreateUid) {
        this.ipvCreateUid = ipvCreateUid;
    }

    public String getIpvCreateUname() {
        return ipvCreateUname;
    }

    public void setIpvCreateUname(String ipvCreateUname) {
        this.ipvCreateUname = ipvCreateUname;
    }

    public Integer getIpvState() {
        return ipvState;
    }

    public void setIpvState(Integer ipvState) {
        this.ipvState = ipvState;
    }

    public Date getIpvStateTime() {
        return ipvStateTime;
    }

    public String getIpvCId() {
        return ipvCId;
    }

    public void setIpvCId(String ipvCId) {
        this.ipvCId = ipvCId;
    }

    public void setIpvStateTime(Date ipvStateTime) {
        this.ipvStateTime = ipvStateTime;
    }

    public BigDecimal getIpvAccumulatedSchedule() {
        return ipvAccumulatedSchedule;
    }

    public void setIpvAccumulatedSchedule(BigDecimal ipvAccumulatedSchedule) {
        this.ipvAccumulatedSchedule = ipvAccumulatedSchedule;
    }

    public String getIpvConstStatus() {
        return ipvConstStatus;
    }

    public void setIpvConstStatus(String ipvConstStatus) {
        this.ipvConstStatus = ipvConstStatus;
    }

    public String getIpvSegments() {
        return ipvSegments;
    }

    public void setIpvSegments(String ipvSegments) {
        this.ipvSegments = ipvSegments;
    }

    public String getIpvStreet() {
        return ipvStreet;
    }

    public void setIpvStreet(String ipvStreet) {
        this.ipvStreet = ipvStreet;
    }

    public String getIpvArea() {
        return ipvArea;
    }

    public void setIpvArea(String ipvArea) {
        this.ipvArea = ipvArea;
    }

    public Date getIpvStart() {
        return ipvStart;
    }

    public void setIpvStart(Date ipvStart) {
        this.ipvStart = ipvStart;
    }

    public Date getIpvEnd() {
        return ipvEnd;
    }

    public void setIpvEnd(Date ipvEnd) {
        this.ipvEnd = ipvEnd;
    }

    public Boolean getIpvLibrary() {
        return ipvLibrary;
    }

    public void setIpvLibrary(Boolean ipvLibrary) {
        this.ipvLibrary = ipvLibrary;
    }

    public Boolean getIpvNewly() {
        return ipvNewly;
    }

    public void setIpvNewly(Boolean ipvNewly) {
        this.ipvNewly = ipvNewly;
    }

    public Boolean getIpvCompleted() {
        return ipvCompleted;
    }

    public void setIpvCompleted(Boolean ipvCompleted) {
        this.ipvCompleted = ipvCompleted;
    }


    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public String getpCompetentDepartment() {
        return pCompetentDepartment;
    }

    public void setpCompetentDepartment(String pCompetentDepartment) {
        this.pCompetentDepartment = pCompetentDepartment;
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