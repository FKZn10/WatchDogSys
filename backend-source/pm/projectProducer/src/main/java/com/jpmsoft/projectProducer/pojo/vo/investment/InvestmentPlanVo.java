package com.jpmsoft.projectProducer.pojo.vo.investment;

import java.math.BigDecimal;

public  class InvestmentPlanVo{
    public  String  title;
    public  Integer number;
    public  String  ippDocNumber;
    public  String  ippReleaseType;
    public  String  ippPlanDocNo;
    public  String  ippSourceFunds;

    public BigDecimal ippReleaseFunds;
    public  BigDecimal  paidMoney;
    public  BigDecimal  remainingAmount;
    public  BigDecimal  cumulativeAmount;
    public  String  ippRemarks;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getIppDocNumber() {
        return ippDocNumber;
    }

    public void setIppDocNumber(String ippDocNumber) {
        this.ippDocNumber = ippDocNumber;
    }

    public String getIppReleaseType() {
        return ippReleaseType;
    }

    public void setIppReleaseType(String ippReleaseType) {
        this.ippReleaseType = ippReleaseType;
    }

    public String getIppPlanDocNo() {
        return ippPlanDocNo;
    }

    public void setIppPlanDocNo(String ippPlanDocNo) {
        this.ippPlanDocNo = ippPlanDocNo;
    }

    public BigDecimal getIppReleaseFunds() {
        return ippReleaseFunds;
    }

    public void setIppReleaseFunds(BigDecimal ippReleaseFunds) {
        this.ippReleaseFunds = ippReleaseFunds;
    }

    public BigDecimal getPaidMoney() {
        return paidMoney;
    }

    public void setPaidMoney(BigDecimal paidMoney) {
        this.paidMoney = paidMoney;
    }

    public BigDecimal getRemainingAmount() {
        return remainingAmount;
    }

    public void setRemainingAmount(BigDecimal remainingAmount) {
        this.remainingAmount = remainingAmount;
    }

    public BigDecimal getCumulativeAmount() {
        return cumulativeAmount;
    }

    public void setCumulativeAmount(BigDecimal cumulativeAmount) {
        this.cumulativeAmount = cumulativeAmount;
    }

    public String getIppRemarks() {
        return ippRemarks;
    }

    public void setIppRemarks(String ippRemarks) {
        this.ippRemarks = ippRemarks;
    }

    public String getIppSourceFunds() {
        return ippSourceFunds;
    }

    public void setIppSourceFunds(String ippSourceFunds) {
        this.ippSourceFunds = ippSourceFunds;
    }
}