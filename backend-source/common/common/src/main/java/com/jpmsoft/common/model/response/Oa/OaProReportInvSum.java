package com.jpmsoft.common.model.response.Oa;

import java.math.BigDecimal;

public class OaProReportInvSum {

    private BigDecimal prCompleteInvSum;
    private BigDecimal prYearInvSum;
    private BigDecimal prMonthInvSum;


    public BigDecimal getPrCompleteInvSum() {
        return prCompleteInvSum;
    }

    public void setPrCompleteInvSum(BigDecimal prCompleteInvSum) {
        this.prCompleteInvSum = prCompleteInvSum;
    }

    public BigDecimal getPrYearInvSum() {
        return prYearInvSum;
    }

    public void setPrYearInvSum(BigDecimal prYearInvSum) {
        this.prYearInvSum = prYearInvSum;
    }

    public BigDecimal getPrMonthInvSum() {
        return prMonthInvSum;
    }

    public void setPrMonthInvSum(BigDecimal prMonthInvSum) {
        this.prMonthInvSum = prMonthInvSum;
    }
}
