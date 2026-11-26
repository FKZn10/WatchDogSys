package com.jpmsoft.projectProducer.pojo.entity.investment;

import java.math.BigDecimal;

public class InvestmentAccount {
    private String pId;
    private String pNmae;
    private BigDecimal bdMoney;
    private String bdDocNun;
    private String ippId;
    private String ippDocNumber;
    private BigDecimal ippReleaseFunds;
    private String sourceFunds;
    private String conName;
    private String conId;
    private String conCode;
    private BigDecimal conMoneyTotal;
    private String payId;
    private BigDecimal payApply;
    private String payType;
    private String payTypeDetailed;
    private BigDecimal payApplyOwner;
    private BigDecimal payApprovedSettlementPrice;

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getpNmae() {
        return pNmae;
    }

    public void setpNmae(String pNmae) {
        this.pNmae = pNmae;
    }

    public BigDecimal getBdMoney() {
        return bdMoney;
    }

    public void setBdMoney(BigDecimal bdMoney) {
        this.bdMoney = bdMoney;
    }

    public String getBdDocNun() {
        return bdDocNun;
    }

    public void setBdDocNun(String bdDocNun) {
        this.bdDocNun = bdDocNun;
    }


    public String getIppDocNumber() {
        return ippDocNumber;
    }

    public void setIppDocNumber(String ippDocNumber) {
        this.ippDocNumber = ippDocNumber;
    }

    public BigDecimal getIppReleaseFunds() {
        return ippReleaseFunds;
    }

    public void setIppReleaseFunds(BigDecimal ippReleaseFunds) {
        this.ippReleaseFunds = ippReleaseFunds;
    }

    public String getSourceFunds() {
        return sourceFunds;
    }

    public void setSourceFunds(String sourceFunds) {
        this.sourceFunds = sourceFunds;
    }

    public String getConName() {
        return conName;
    }

    public void setConName(String conName) {
        this.conName = conName;
    }

    public String getConId() {
        return conId;
    }

    public void setConId(String conId) {
        this.conId = conId;
    }

    public String getConCode() {
        return conCode;
    }

    public void setConCode(String conCode) {
        this.conCode = conCode;
    }

    public BigDecimal getConMoneyTotal() {
        return conMoneyTotal;
    }

    public void setConMoneyTotal(BigDecimal conMoneyTotal) {
        this.conMoneyTotal = conMoneyTotal;
    }

    public String getPayId() {
        return payId;
    }

    public void setPayId(String payId) {
        this.payId = payId;
    }

    public BigDecimal getPayApply() {
        return payApply;
    }

    public void setPayApply(BigDecimal payApply) {
        this.payApply = payApply;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getPayTypeDetailed() {
        return payTypeDetailed;
    }

    public void setPayTypeDetailed(String payTypeDetailed) {
        this.payTypeDetailed = payTypeDetailed;
    }

    public BigDecimal getPayApplyOwner() {
        return payApplyOwner;
    }

    public void setPayApplyOwner(BigDecimal payApplyOwner) {
        this.payApplyOwner = payApplyOwner;
    }

    public BigDecimal getPayApprovedSettlementPrice() {
        return payApprovedSettlementPrice;
    }

    public void setPayApprovedSettlementPrice(BigDecimal payApprovedSettlementPrice) {
        this.payApprovedSettlementPrice = payApprovedSettlementPrice;
    }

    public String getIppId() {
        return ippId;
    }

    public void setIppId(String ippId) {
        this.ippId = ippId;
    }
}
