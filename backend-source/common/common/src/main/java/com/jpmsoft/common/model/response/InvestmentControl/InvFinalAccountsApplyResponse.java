package com.jpmsoft.common.model.response.InvestmentControl;


import com.jpmsoft.common.model.ProjectCommonRes;
import com.jpmsoft.common.model.request.InvestmentControl.InvFinalAccountsApplyDtlDto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 决算申请
 */
public class InvFinalAccountsApplyResponse extends ProjectCommonRes {

    private static final long serialVersionUID = -5018428917083133956L;
    private String faId;
    private String pId;
    private BigDecimal faTotalContract;
    private BigDecimal faTotalApproved;
    private BigDecimal faSubmitCost;
    private String faRemark;
    private String faCreateUid;
    private String faCreateUname;
    private Date faCreateTime;
    private Integer faState;
    private Date faStateTime;
    private BigDecimal faApprovedAmt;
    private Date faApprovedTime;
    private String faApprovalTitle;
    private String faApprovalNum;
    private Date faApprovalTime;
    private String faApprovalFile;
    private String faApprovalUnit;
    private String conId;


    private List<InvFinalAccountsApplyDtlDto> invFinalAccountsApplyDtls;

    public String getFaId() {
        return faId;
    }

    public void setFaId(String faId) {
        this.faId = faId;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public BigDecimal getFaTotalContract() {
        return faTotalContract;
    }

    public void setFaTotalContract(BigDecimal faTotalContract) {
        this.faTotalContract = faTotalContract;
    }

    public BigDecimal getFaTotalApproved() {
        return faTotalApproved;
    }

    public void setFaTotalApproved(BigDecimal faTotalApproved) {
        this.faTotalApproved = faTotalApproved;
    }

    public BigDecimal getFaSubmitCost() {
        return faSubmitCost;
    }

    public void setFaSubmitCost(BigDecimal faSubmitCost) {
        this.faSubmitCost = faSubmitCost;
    }

    public String getFaRemark() {
        return faRemark;
    }

    public void setFaRemark(String faRemark) {
        this.faRemark = faRemark;
    }

    public String getFaCreateUid() {
        return faCreateUid;
    }

    public void setFaCreateUid(String faCreateUid) {
        this.faCreateUid = faCreateUid;
    }

    public String getFaCreateUname() {
        return faCreateUname;
    }

    public void setFaCreateUname(String faCreateUname) {
        this.faCreateUname = faCreateUname;
    }

    public Date getFaCreateTime() {
        return faCreateTime;
    }

    public void setFaCreateTime(Date faCreateTime) {
        this.faCreateTime = faCreateTime;
    }

    public Integer getFaState() {
        return faState;
    }

    public void setFaState(Integer faState) {
        this.faState = faState;
    }

    public Date getFaStateTime() {
        return faStateTime;
    }

    public void setFaStateTime(Date faStateTime) {
        this.faStateTime = faStateTime;
    }


    public BigDecimal getFaApprovedAmt() {
        return faApprovedAmt;
    }

    public void setFaApprovedAmt(BigDecimal faApprovedAmt) {
        this.faApprovedAmt = faApprovedAmt;
    }

    public Date getFaApprovedTime() {
        return faApprovedTime;
    }

    public void setFaApprovedTime(Date faApprovedTime) {
        this.faApprovedTime = faApprovedTime;
    }

    public String getFaApprovalTitle() {
        return faApprovalTitle;
    }

    public void setFaApprovalTitle(String faApprovalTitle) {
        this.faApprovalTitle = faApprovalTitle;
    }

    public String getFaApprovalNum() {
        return faApprovalNum;
    }

    public void setFaApprovalNum(String faApprovalNum) {
        this.faApprovalNum = faApprovalNum;
    }

    public Date getFaApprovalTime() {
        return faApprovalTime;
    }

    public void setFaApprovalTime(Date faApprovalTime) {
        this.faApprovalTime = faApprovalTime;
    }

    public String getFaApprovalFile() {
        return faApprovalFile;
    }

    public void setFaApprovalFile(String faApprovalFile) {
        this.faApprovalFile = faApprovalFile;
    }

    public String getFaApprovalUnit() {
        return faApprovalUnit;
    }

    public void setFaApprovalUnit(String faApprovalUnit) {
        this.faApprovalUnit = faApprovalUnit;
    }

    public List<InvFinalAccountsApplyDtlDto> getInvFinalAccountsApplyDtls() {
        return invFinalAccountsApplyDtls;
    }

    public void setInvFinalAccountsApplyDtls(List<InvFinalAccountsApplyDtlDto> invFinalAccountsApplyDtls) {
        this.invFinalAccountsApplyDtls = invFinalAccountsApplyDtls;
    }
}
