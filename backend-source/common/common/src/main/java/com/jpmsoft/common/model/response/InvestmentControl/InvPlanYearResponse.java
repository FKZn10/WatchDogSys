package com.jpmsoft.common.model.response.InvestmentControl;

import com.jpmsoft.common.model.ProjectCommonRes;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 年度投资计划
 */
public class InvPlanYearResponse extends ProjectCommonRes {

    private String ipvId;
    private String pId;
    private String conId;
    private String conName;
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

    private List<InvMonthlyScheduleResponse> invMonthlySchedules;
    private String ipvDName;

    private String ipvPhone;

    private BigDecimal pyInvestmentAmount;

    private String ipvCName;

    private String pProjectStage;

    private String pCompetentDepartmentName;

    //责任单位字符串
    private String pCompetentDepartments;

    private String pCompetentDepartmentNames;

    //年度建设状态
    private String ipvConstructionStatus;

    private Integer imsMonth;

    private BigDecimal imsPlannedInv;

    public BigDecimal getImsPlannedInv() {
        return imsPlannedInv;
    }

    public void setImsPlannedInv(BigDecimal imsPlannedInv) {
        this.imsPlannedInv = imsPlannedInv;
    }

    public Integer getImsMonth() {
        return imsMonth;
    }

    public void setImsMonth(Integer imsMonth) {
        this.imsMonth = imsMonth;
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

    public String getIpvConstructionStatus() {
        return ipvConstructionStatus;
    }

    public void setIpvConstructionStatus(String ipvConstructionStatus) {
        this.ipvConstructionStatus = ipvConstructionStatus;
    }

    @Override
    public String getpCompetentDepartmentName() {
        return pCompetentDepartmentName;
    }

    @Override
    public void setpCompetentDepartmentName(String pCompetentDepartmentName) {
        this.pCompetentDepartmentName = pCompetentDepartmentName;
    }

    public String getpProjectStage() {
        return pProjectStage;
    }

    public void setpProjectStage(String pProjectStage) {
        this.pProjectStage = pProjectStage;
    }

    public BigDecimal getPyInvestmentAmount() {
        return pyInvestmentAmount;
    }

    public void setPyInvestmentAmount(BigDecimal pyInvestmentAmount) {
        this.pyInvestmentAmount = pyInvestmentAmount;
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

    /**
     * 上报表格编号
     */
    private String reportTableNum;

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

    public String getIpvCId() {
        return ipvCId;
    }

    public void setIpvCId(String ipvCId) {
        this.ipvCId = ipvCId;
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

    public List<InvMonthlyScheduleResponse> getInvMonthlySchedules() {
        return invMonthlySchedules;
    }

    public void setInvMonthlySchedules(List<InvMonthlyScheduleResponse> invMonthlySchedules) {
        this.invMonthlySchedules = invMonthlySchedules;
    }

    public String getIpvDName() {
        return ipvDName;
    }

    public void setIpvDName(String ipvDName) {
        this.ipvDName = ipvDName;
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
