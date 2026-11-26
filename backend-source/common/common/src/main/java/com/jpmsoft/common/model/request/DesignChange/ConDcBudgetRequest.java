package com.jpmsoft.common.model.request.DesignChange;

import com.jpmsoft.common.base.ProcessFields;
import com.jpmsoft.common.enums.ProcessFieldEnum;
import com.jpmsoft.common.model.BaseFileRequest;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 施工变更预算
 */
public class ConDcBudgetRequest extends BaseFileRequest {

    @ProcessFields(type = ProcessFieldEnum.id)
    private String dcbId;
    private String dcoId;
    private String dcbType;
    private BigDecimal dcbAmount;
    private String dcbRemark;
    @ProcessFields(type = ProcessFieldEnum.createUid)
    private String dcbCreateUid;
    @ProcessFields(type = ProcessFieldEnum.createUname)
    private String dcbCreateUname;
    @ProcessFields(type = ProcessFieldEnum.createTime)
    private Date dcbCreateTime;
    @ProcessFields(type = ProcessFieldEnum.state)
    private Integer dcbState;
    @ProcessFields(type = ProcessFieldEnum.stateTime)
    private Date dcbStateTime;
    private String dcbSupervisorId;
    private String dcbContractorId;
    private BigDecimal dcbSupervisorAmt;
    private BigDecimal dcbContractorAmt;
    private BigDecimal dcbMajorAmt;
    private BigDecimal dcbCostAmt;
    private BigDecimal dcbAuditAmt;
    private BigDecimal dcbEstimateAmt;
    private BigDecimal dcbNotContainAmt;
    private BigDecimal dcbContainAmt;
    private BigDecimal dcbConRate;

    private String pName;
    private String conName;
    private String dcoName;
    private String dcoCode;
    private String dcoProjectName;
    private String dcoSubCode;


    public String getDcbId() {
        return dcbId;
    }

    public void setDcbId(String dcbId) {
        this.dcbId = dcbId;
    }

    public String getDcoId() {
        return dcoId;
    }

    public void setDcoId(String dcoId) {
        this.dcoId = dcoId;
    }

    public String getDcbType() {
        return dcbType;
    }

    public void setDcbType(String dcbType) {
        this.dcbType = dcbType;
    }

    public BigDecimal getDcbAmount() {
        return dcbAmount;
    }

    public void setDcbAmount(BigDecimal dcbAmount) {
        this.dcbAmount = dcbAmount;
    }

    public String getDcbRemark() {
        return dcbRemark;
    }

    public void setDcbRemark(String dcbRemark) {
        this.dcbRemark = dcbRemark;
    }

    public String getDcbCreateUid() {
        return dcbCreateUid;
    }

    public void setDcbCreateUid(String dcbCreateUid) {
        this.dcbCreateUid = dcbCreateUid;
    }

    public String getDcbCreateUname() {
        return dcbCreateUname;
    }

    public void setDcbCreateUname(String dcbCreateUname) {
        this.dcbCreateUname = dcbCreateUname;
    }

    public Date getDcbCreateTime() {
        return dcbCreateTime;
    }

    public void setDcbCreateTime(Date dcbCreateTime) {
        this.dcbCreateTime = dcbCreateTime;
    }

    public Integer getDcbState() {
        return dcbState;
    }

    public void setDcbState(Integer dcbState) {
        this.dcbState = dcbState;
    }

    public Date getDcbStateTime() {
        return dcbStateTime;
    }

    public void setDcbStateTime(Date dcbStateTime) {
        this.dcbStateTime = dcbStateTime;
    }

    public String getDcbSupervisorId() {
        return dcbSupervisorId;
    }

    public void setDcbSupervisorId(String dcbSupervisorId) {
        this.dcbSupervisorId = dcbSupervisorId;
    }

    public String getDcbContractorId() {
        return dcbContractorId;
    }

    public void setDcbContractorId(String dcbContractorId) {
        this.dcbContractorId = dcbContractorId;
    }

    public BigDecimal getDcbSupervisorAmt() {
        return dcbSupervisorAmt;
    }

    public void setDcbSupervisorAmt(BigDecimal dcbSupervisorAmt) {
        this.dcbSupervisorAmt = dcbSupervisorAmt;
    }

    public BigDecimal getDcbContractorAmt() {
        return dcbContractorAmt;
    }

    public void setDcbContractorAmt(BigDecimal dcbContractorAmt) {
        this.dcbContractorAmt = dcbContractorAmt;
    }

    public BigDecimal getDcbMajorAmt() {
        return dcbMajorAmt;
    }

    public void setDcbMajorAmt(BigDecimal dcbMajorAmt) {
        this.dcbMajorAmt = dcbMajorAmt;
    }

    public BigDecimal getDcbCostAmt() {
        return dcbCostAmt;
    }

    public void setDcbCostAmt(BigDecimal dcbCostAmt) {
        this.dcbCostAmt = dcbCostAmt;
    }

    public BigDecimal getDcbAuditAmt() {
        return dcbAuditAmt;
    }

    public void setDcbAuditAmt(BigDecimal dcbAuditAmt) {
        this.dcbAuditAmt = dcbAuditAmt;
    }

    public BigDecimal getDcbEstimateAmt() {
        return dcbEstimateAmt;
    }

    public void setDcbEstimateAmt(BigDecimal dcbEstimateAmt) {
        this.dcbEstimateAmt = dcbEstimateAmt;
    }

    public BigDecimal getDcbNotContainAmt() {
        return dcbNotContainAmt;
    }

    public void setDcbNotContainAmt(BigDecimal dcbNotContainAmt) {
        this.dcbNotContainAmt = dcbNotContainAmt;
    }

    public BigDecimal getDcbContainAmt() {
        return dcbContainAmt;
    }

    public void setDcbContainAmt(BigDecimal dcbContainAmt) {
        this.dcbContainAmt = dcbContainAmt;
    }

    public BigDecimal getDcbConRate() {
        return dcbConRate;
    }

    public void setDcbConRate(BigDecimal dcbConRate) {
        this.dcbConRate = dcbConRate;
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

    public String getDcoName() {
        return dcoName;
    }

    public void setDcoName(String dcoName) {
        this.dcoName = dcoName;
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

    public String getDcoSubCode() {
        return dcoSubCode;
    }

    public void setDcoSubCode(String dcoSubCode) {
        this.dcoSubCode = dcoSubCode;
    }
}
