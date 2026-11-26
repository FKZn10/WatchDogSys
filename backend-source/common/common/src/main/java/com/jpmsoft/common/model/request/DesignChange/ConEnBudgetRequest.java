package com.jpmsoft.common.model.request.DesignChange;

import com.jpmsoft.common.model.BaseFileRequest;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 工程洽商施工方案及预算
 */
public class ConEnBudgetRequest extends BaseFileRequest {

    private String enbId;
    private String enisId;
    private String enbContractorId;
    private String enbConstructionPlan;
    private BigDecimal enbBudget;
    private String enbRemark;
    private String enbCreateUid;
    private String enbCreateUname;
    private Date enbCreateTime;
    private Integer enbState;
    private Date enbStateTime;
    private String enbSupervisorId;
    private BigDecimal enbSupervisorAmt;
    private BigDecimal enbMajorAmt;
    private String enbCostOpinion;
    private BigDecimal enbAuditAmt;
    private BigDecimal enbEstimateAmt;
    private BigDecimal enbNotContainAmt;
    private BigDecimal enbContainAmt;
    private BigDecimal enbConRate;

    private String enbConstructionPlanIds;
    private String pName;
    private String conName;
    private String enisSubCode;
    private String enisCode;
    private String enisName;
    private String enisProjectName;

    public String getEnbId() {
        return enbId;
    }

    public void setEnbId(String enbId) {
        this.enbId = enbId;
    }

    public String getEnisId() {
        return enisId;
    }

    public void setEnisId(String enisId) {
        this.enisId = enisId;
    }

    public String getEnbContractorId() {
        return enbContractorId;
    }

    public void setEnbContractorId(String enbContractorId) {
        this.enbContractorId = enbContractorId;
    }

    public String getEnbConstructionPlan() {
        return enbConstructionPlan;
    }

    public void setEnbConstructionPlan(String enbConstructionPlan) {
        this.enbConstructionPlan = enbConstructionPlan;
    }

    public BigDecimal getEnbBudget() {
        return enbBudget;
    }

    public void setEnbBudget(BigDecimal enbBudget) {
        this.enbBudget = enbBudget;
    }

    public String getEnbRemark() {
        return enbRemark;
    }

    public void setEnbRemark(String enbRemark) {
        this.enbRemark = enbRemark;
    }

    public String getEnbCreateUid() {
        return enbCreateUid;
    }

    public void setEnbCreateUid(String enbCreateUid) {
        this.enbCreateUid = enbCreateUid;
    }

    public String getEnbCreateUname() {
        return enbCreateUname;
    }

    public void setEnbCreateUname(String enbCreateUname) {
        this.enbCreateUname = enbCreateUname;
    }

    public Date getEnbCreateTime() {
        return enbCreateTime;
    }

    public void setEnbCreateTime(Date enbCreateTime) {
        this.enbCreateTime = enbCreateTime;
    }

    public Integer getEnbState() {
        return enbState;
    }

    public void setEnbState(Integer enbState) {
        this.enbState = enbState;
    }

    public Date getEnbStateTime() {
        return enbStateTime;
    }

    public void setEnbStateTime(Date enbStateTime) {
        this.enbStateTime = enbStateTime;
    }

    public String getEnbSupervisorId() {
        return enbSupervisorId;
    }

    public void setEnbSupervisorId(String enbSupervisorId) {
        this.enbSupervisorId = enbSupervisorId;
    }

    public BigDecimal getEnbSupervisorAmt() {
        return enbSupervisorAmt;
    }

    public void setEnbSupervisorAmt(BigDecimal enbSupervisorAmt) {
        this.enbSupervisorAmt = enbSupervisorAmt;
    }

    public BigDecimal getEnbMajorAmt() {
        return enbMajorAmt;
    }

    public void setEnbMajorAmt(BigDecimal enbMajorAmt) {
        this.enbMajorAmt = enbMajorAmt;
    }

    public String getEnbCostOpinion() {
        return enbCostOpinion;
    }

    public void setEnbCostOpinion(String enbCostOpinion) {
        this.enbCostOpinion = enbCostOpinion;
    }

    public BigDecimal getEnbAuditAmt() {
        return enbAuditAmt;
    }

    public void setEnbAuditAmt(BigDecimal enbAuditAmt) {
        this.enbAuditAmt = enbAuditAmt;
    }

    public BigDecimal getEnbEstimateAmt() {
        return enbEstimateAmt;
    }

    public void setEnbEstimateAmt(BigDecimal enbEstimateAmt) {
        this.enbEstimateAmt = enbEstimateAmt;
    }

    public BigDecimal getEnbNotContainAmt() {
        return enbNotContainAmt;
    }

    public void setEnbNotContainAmt(BigDecimal enbNotContainAmt) {
        this.enbNotContainAmt = enbNotContainAmt;
    }

    public BigDecimal getEnbContainAmt() {
        return enbContainAmt;
    }

    public void setEnbContainAmt(BigDecimal enbContainAmt) {
        this.enbContainAmt = enbContainAmt;
    }

    public BigDecimal getEnbConRate() {
        return enbConRate;
    }

    public void setEnbConRate(BigDecimal enbConRate) {
        this.enbConRate = enbConRate;
    }

    public String getEnbConstructionPlanIds() {
        return enbConstructionPlanIds;
    }

    public void setEnbConstructionPlanIds(String enbConstructionPlanIds) {
        this.enbConstructionPlanIds = enbConstructionPlanIds;
    }

    public String getConName() {
        return conName;
    }

    public void setConName(String conName) {
        this.conName = conName;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public String getEnisSubCode() {
        return enisSubCode;
    }

    public void setEnisSubCode(String enisSubCode) {
        this.enisSubCode = enisSubCode;
    }

    public String getEnisCode() {
        return enisCode;
    }

    public void setEnisCode(String enisCode) {
        this.enisCode = enisCode;
    }

    public String getEnisName() {
        return enisName;
    }

    public void setEnisName(String enisName) {
        this.enisName = enisName;
    }

    public String getEnisProjectName() {
        return enisProjectName;
    }

    public void setEnisProjectName(String enisProjectName) {
        this.enisProjectName = enisProjectName;
    }
}
