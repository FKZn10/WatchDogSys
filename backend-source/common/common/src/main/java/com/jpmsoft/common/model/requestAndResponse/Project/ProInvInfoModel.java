package com.jpmsoft.common.model.requestAndResponse.Project;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 部分项目信息以及投资信息
 */
public class ProInvInfoModel {

    private String pId;
    private String pName;
    private String pCode;
    private String pUseUnit;
    private Float pLongitude;
    private Float pLatitude;
    private Date pActualStartTime;
    private Date pActualCompleteDate;
    private BigDecimal pInvTotal;
    private BigDecimal ipvScheduledPlan;
    private BigDecimal ipvPlannedInvestment;
    private BigDecimal ipvPlannedPay;
    private BigDecimal ipvScheduledSum;
    private Date pPlanStartDate;
    private Date pPlanCompleteDate;
    private String pCompetentDepartment;
    private String pCompetentDepartmentName;
    private String  pConsName;
    private String  pConsAbbName;

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

    public String getpCode() {
        return pCode;
    }

    public void setpCode(String pCode) {
        this.pCode = pCode;
    }

    public String getpUseUnit() {
        return pUseUnit;
    }

    public void setpUseUnit(String pUseUnit) {
        this.pUseUnit = pUseUnit;
    }

    public Float getpLongitude() {
        return pLongitude;
    }

    public void setpLongitude(Float pLongitude) {
        this.pLongitude = pLongitude;
    }

    public Float getpLatitude() {
        return pLatitude;
    }

    public void setpLatitude(Float pLatitude) {
        this.pLatitude = pLatitude;
    }

    public Date getpActualStartTime() {
        return pActualStartTime;
    }

    public void setpActualStartTime(Date pActualStartTime) {
        this.pActualStartTime = pActualStartTime;
    }

    public Date getpActualCompleteDate() {
        return pActualCompleteDate;
    }

    public void setpActualCompleteDate(Date pActualCompleteDate) {
        this.pActualCompleteDate = pActualCompleteDate;
    }

    public BigDecimal getpInvTotal() {
        return pInvTotal;
    }

    public void setpInvTotal(BigDecimal pInvTotal) {
        this.pInvTotal = pInvTotal;
    }

    public BigDecimal getIpvScheduledPlan() {
        return ipvScheduledPlan;
    }

    public void setIpvScheduledPlan(BigDecimal ipvScheduledPlan) {
        this.ipvScheduledPlan = ipvScheduledPlan;
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

    public BigDecimal getIpvScheduledSum() {
        return ipvScheduledSum;
    }

    public void setIpvScheduledSum(BigDecimal ipvScheduledSum) {
        this.ipvScheduledSum = ipvScheduledSum;
    }

    public Date getpPlanStartDate() {
        return pPlanStartDate;
    }

    public void setpPlanStartDate(Date pPlanStartDate) {
        this.pPlanStartDate = pPlanStartDate;
    }

    public Date getpPlanCompleteDate() {
        return pPlanCompleteDate;
    }

    public void setpPlanCompleteDate(Date pPlanCompleteDate) {
        this.pPlanCompleteDate = pPlanCompleteDate;
    }

    public String getpCompetentDepartment() {
        return pCompetentDepartment;
    }

    public void setpCompetentDepartment(String pCompetentDepartment) {
        this.pCompetentDepartment = pCompetentDepartment;
    }

    public String getpCompetentDepartmentName() {
        return pCompetentDepartmentName;
    }

    public void setpCompetentDepartmentName(String pCompetentDepartmentName) {
        this.pCompetentDepartmentName = pCompetentDepartmentName;
    }

    public String getpConsName() {
        return pConsName;
    }

    public void setpConsName(String pConsName) {
        this.pConsName = pConsName;
    }

    public String getpConsAbbName() {
        return pConsAbbName;
    }

    public void setpConsAbbName(String pConsAbbName) {
        this.pConsAbbName = pConsAbbName;
    }
}
