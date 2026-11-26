package com.jpmsoft.common.model.requestAndResponse.Workbench;

import java.math.BigDecimal;

/**
 * 领导看板
 */
public class WorkbenchOverviewModel {

    //质量管理 逾期
    private Integer qualityOverdue;
    //质量管理 当日新增
    private Integer qualityToday;
    //安全管理 逾期
    private Integer securityOverdue;
    //安全管理 当日新增
    private Integer securityToday;
    //风险预警 累计总数
    private Integer riskWarnCount;
    //风险预警 未处理
    private Integer riskWarnUntreated;
    //合同签订 合同数
    private Integer contractCount;
    //合同签订 签订总金额
    private BigDecimal contractAmt;
    //支付管理 本月支付次数
    private Integer payMonth;
    //支付管理 累计支付总金额
    private BigDecimal payAmt;
    //年度投资 年度计划投资金额
    private BigDecimal planYearAmt;
    //年度投资 投资完成比例
    private Float planYearRatio;
    //进度管理 逾期
    private Integer ganttPlanOverdue;
    //进度管理 临期
    private Integer ganttPlanTemporary;
    //变更管理 变更记录总数
    private Integer conEcCount;
    //变更管理 当月新增
    private Integer conEcMonth;
    //参建单位 参建单位数
    private Integer proUnitCount;
    //参建单位 项目组用户人数
    private Integer proUserCount;
     // 质量管理 本月新增
    private Integer qualityMonth;
    // 安全管理 本月新增
    private Integer securityMonth;
    // 质量管理 本月逾期
    private Integer qualityOverdueMonth;
    // 安全管理 本月逾期
    private Integer securityOverdueMonth;

    public Integer getQualityMonth() {
        return qualityMonth;
    }

    public void setQualityMonth(Integer qualityMonth) {
        this.qualityMonth = qualityMonth;
    }

    public Integer getSecurityMonth() {
        return securityMonth;
    }

    public void setSecurityMonth(Integer securityMonth) {
        this.securityMonth = securityMonth;
    }

    public Integer getQualityOverdueMonth() {
        return qualityOverdueMonth;
    }

    public void setQualityOverdueMonth(Integer qualityOverdueMonth) {
        this.qualityOverdueMonth = qualityOverdueMonth;
    }

    public Integer getSecurityOverdueMonth() {
        return securityOverdueMonth;
    }

    public void setSecurityOverdueMonth(Integer securityOverdueMonth) {
        this.securityOverdueMonth = securityOverdueMonth;
    }

    public Integer getQualityOverdue() {
        return qualityOverdue;
    }

    public void setQualityOverdue(Integer qualityOverdue) {
        this.qualityOverdue = qualityOverdue;
    }

    public Integer getQualityToday() {
        return qualityToday;
    }

    public void setQualityToday(Integer qualityToday) {
        this.qualityToday = qualityToday;
    }

    public Integer getSecurityOverdue() {
        return securityOverdue;
    }

    public void setSecurityOverdue(Integer securityOverdue) {
        this.securityOverdue = securityOverdue;
    }

    public Integer getSecurityToday() {
        return securityToday;
    }

    public void setSecurityToday(Integer securityToday) {
        this.securityToday = securityToday;
    }

    public Integer getRiskWarnCount() {
        return riskWarnCount;
    }

    public void setRiskWarnCount(Integer riskWarnCount) {
        this.riskWarnCount = riskWarnCount;
    }

    public Integer getRiskWarnUntreated() {
        return riskWarnUntreated;
    }

    public void setRiskWarnUntreated(Integer riskWarnUntreated) {
        this.riskWarnUntreated = riskWarnUntreated;
    }

    public Integer getContractCount() {
        return contractCount;
    }

    public void setContractCount(Integer contractCount) {
        this.contractCount = contractCount;
    }

    public BigDecimal getContractAmt() {
        return contractAmt;
    }

    public void setContractAmt(BigDecimal contractAmt) {
        this.contractAmt = contractAmt;
    }

    public Integer getPayMonth() {
        return payMonth;
    }

    public void setPayMonth(Integer payMonth) {
        this.payMonth = payMonth;
    }

    public BigDecimal getPayAmt() {
        return payAmt;
    }

    public void setPayAmt(BigDecimal payAmt) {
        this.payAmt = payAmt;
    }

    public BigDecimal getPlanYearAmt() {
        return planYearAmt;
    }

    public void setPlanYearAmt(BigDecimal planYearAmt) {
        this.planYearAmt = planYearAmt;
    }

    public Float getPlanYearRatio() {
        return planYearRatio;
    }

    public void setPlanYearRatio(Float planYearRatio) {
        this.planYearRatio = planYearRatio;
    }

    public Integer getGanttPlanOverdue() {
        return ganttPlanOverdue;
    }

    public void setGanttPlanOverdue(Integer ganttPlanOverdue) {
        this.ganttPlanOverdue = ganttPlanOverdue;
    }

    public Integer getGanttPlanTemporary() {
        return ganttPlanTemporary;
    }

    public void setGanttPlanTemporary(Integer ganttPlanTemporary) {
        this.ganttPlanTemporary = ganttPlanTemporary;
    }

    public Integer getConEcCount() {
        return conEcCount;
    }

    public void setConEcCount(Integer conEcCount) {
        this.conEcCount = conEcCount;
    }

    public Integer getConEcMonth() {
        return conEcMonth;
    }

    public void setConEcMonth(Integer conEcMonth) {
        this.conEcMonth = conEcMonth;
    }

    public Integer getProUnitCount() {
        return proUnitCount;
    }

    public void setProUnitCount(Integer proUnitCount) {
        this.proUnitCount = proUnitCount;
    }

    public Integer getProUserCount() {
        return proUserCount;
    }

    public void setProUserCount(Integer proUserCount) {
        this.proUserCount = proUserCount;
    }
}
