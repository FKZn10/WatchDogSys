package com.jpmsoft.common.model.requestAndResponse.InvestmentControl;

import com.jpmsoft.common.model.ProjectCommonRes;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 资金计划申请
 */
public class FundSchemeApplyModel extends ProjectCommonRes {

    private String fsaId;
    private BigDecimal fsaBudgetApproval;
    private BigDecimal fsaHaveApplied;
    private BigDecimal fsaCurrentApplied;
    private BigDecimal fsaRemaining;
    private String fsaApplyDepartment;
    private String fsaApplyBatch;
    private Date fsaApplyDate;
    private Date fsaUseDate;
    private String fsaCreateUid;
    private String fsaCreateUname;
    private Date fsaCreateTime;
    private Integer fsaState;
    private Date fsaStateTime;

    private String fsaApplyDepName;
    private List<FundSchemeApplyDtlModel> schemeApplyDtlModels;


    public String getFsaId() {
        return fsaId;
    }

    public void setFsaId(String fsaId) {
        this.fsaId = fsaId;
    }

    public BigDecimal getFsaBudgetApproval() {
        return fsaBudgetApproval;
    }

    public void setFsaBudgetApproval(BigDecimal fsaBudgetApproval) {
        this.fsaBudgetApproval = fsaBudgetApproval;
    }

    public BigDecimal getFsaHaveApplied() {
        return fsaHaveApplied;
    }

    public void setFsaHaveApplied(BigDecimal fsaHaveApplied) {
        this.fsaHaveApplied = fsaHaveApplied;
    }

    public BigDecimal getFsaCurrentApplied() {
        return fsaCurrentApplied;
    }

    public void setFsaCurrentApplied(BigDecimal fsaCurrentApplied) {
        this.fsaCurrentApplied = fsaCurrentApplied;
    }

    public BigDecimal getFsaRemaining() {
        return fsaRemaining;
    }

    public void setFsaRemaining(BigDecimal fsaRemaining) {
        this.fsaRemaining = fsaRemaining;
    }

    public String getFsaApplyDepartment() {
        return fsaApplyDepartment;
    }

    public void setFsaApplyDepartment(String fsaApplyDepartment) {
        this.fsaApplyDepartment = fsaApplyDepartment;
    }

    public String getFsaApplyBatch() {
        return fsaApplyBatch;
    }

    public void setFsaApplyBatch(String fsaApplyBatch) {
        this.fsaApplyBatch = fsaApplyBatch;
    }

    public Date getFsaApplyDate() {
        return fsaApplyDate;
    }

    public void setFsaApplyDate(Date fsaApplyDate) {
        this.fsaApplyDate = fsaApplyDate;
    }

    public Date getFsaUseDate() {
        return fsaUseDate;
    }

    public void setFsaUseDate(Date fsaUseDate) {
        this.fsaUseDate = fsaUseDate;
    }

    public String getFsaCreateUid() {
        return fsaCreateUid;
    }

    public void setFsaCreateUid(String fsaCreateUid) {
        this.fsaCreateUid = fsaCreateUid;
    }

    public String getFsaCreateUname() {
        return fsaCreateUname;
    }

    public void setFsaCreateUname(String fsaCreateUname) {
        this.fsaCreateUname = fsaCreateUname;
    }

    public Date getFsaCreateTime() {
        return fsaCreateTime;
    }

    public void setFsaCreateTime(Date fsaCreateTime) {
        this.fsaCreateTime = fsaCreateTime;
    }

    public Integer getFsaState() {
        return fsaState;
    }

    public void setFsaState(Integer fsaState) {
        this.fsaState = fsaState;
    }

    public Date getFsaStateTime() {
        return fsaStateTime;
    }

    public void setFsaStateTime(Date fsaStateTime) {
        this.fsaStateTime = fsaStateTime;
    }

    public String getFsaApplyDepName() {
        return fsaApplyDepName;
    }

    public void setFsaApplyDepName(String fsaApplyDepName) {
        this.fsaApplyDepName = fsaApplyDepName;
    }

    public List<FundSchemeApplyDtlModel> getSchemeApplyDtlModels() {
        return schemeApplyDtlModels;
    }

    public void setSchemeApplyDtlModels(List<FundSchemeApplyDtlModel> schemeApplyDtlModels) {
        this.schemeApplyDtlModels = schemeApplyDtlModels;
    }
}
