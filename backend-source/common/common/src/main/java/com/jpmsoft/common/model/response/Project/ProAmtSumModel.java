package com.jpmsoft.common.model.response.Project;

import java.math.BigDecimal;

public class ProAmtSumModel {

    private BigDecimal pInvTotalSum = BigDecimal.ZERO;
    private BigDecimal pCalculateRoughlyAmountSum = BigDecimal.ZERO;
    private BigDecimal pEstimateAmountSum = BigDecimal.ZERO;
    private BigDecimal pApproveBudgetSum = BigDecimal.ZERO;
    private BigDecimal conMoneyTotalSum = BigDecimal.ZERO;
    private BigDecimal archiveFileTotalSum = BigDecimal.ZERO;
    private Integer contractSum = 0;
    private Object data;

    public BigDecimal getpInvTotalSum() {
        return pInvTotalSum;
    }

    public void setpInvTotalSum(BigDecimal pInvTotalSum) {
        this.pInvTotalSum = pInvTotalSum;
    }

    public BigDecimal getpCalculateRoughlyAmountSum() {
        return pCalculateRoughlyAmountSum;
    }

    public void setpCalculateRoughlyAmountSum(BigDecimal pCalculateRoughlyAmountSum) {
        this.pCalculateRoughlyAmountSum = pCalculateRoughlyAmountSum;
    }

    public BigDecimal getpEstimateAmountSum() {
        return pEstimateAmountSum;
    }

    public void setpEstimateAmountSum(BigDecimal pEstimateAmountSum) {
        this.pEstimateAmountSum = pEstimateAmountSum;
    }

    public BigDecimal getpApproveBudgetSum() {
        return pApproveBudgetSum;
    }

    public void setpApproveBudgetSum(BigDecimal pApproveBudgetSum) {
        this.pApproveBudgetSum = pApproveBudgetSum;
    }

    public BigDecimal getConMoneyTotalSum() {
        return conMoneyTotalSum;
    }

    public void setConMoneyTotalSum(BigDecimal conMoneyTotalSum) {
        this.conMoneyTotalSum = conMoneyTotalSum;
    }

    public BigDecimal getArchiveFileTotalSum() {
        return archiveFileTotalSum;
    }

    public void setArchiveFileTotalSum(BigDecimal archiveFileTotalSum) {
        this.archiveFileTotalSum = archiveFileTotalSum;
    }

    public Integer getContractSum() {
        return contractSum;
    }

    public void setContractSum(Integer contractSum) {
        this.contractSum = contractSum;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
