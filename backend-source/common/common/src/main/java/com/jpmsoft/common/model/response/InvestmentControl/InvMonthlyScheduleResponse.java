package com.jpmsoft.common.model.response.InvestmentControl;

import java.math.BigDecimal;

/**
 * 月进度计划
 *
 */
public class InvMonthlyScheduleResponse {

    private String imsId;
    private String ipvId;
    private Integer imsMonth;
    private BigDecimal imsPlannedInv;
    private Float imsAnnualProportion;
    private BigDecimal imsCumulativeInv;
    private Float imsCumulativeInvProportion;
    private BigDecimal imsPlannedPay;
    private Float imsPayProportion;
    private BigDecimal imsCumulativePay;
    private Float imsCumulativePayProportion;
    private String imsImgProgress;

    private String ipvDeclareYear;

    private Integer imsYear;


    public Integer getImsYear() {
        return imsYear;
    }

    public void setImsYear(Integer imsYear) {
        this.imsYear = imsYear;
    }

    public String getIpvDeclareYear() {
        return ipvDeclareYear;
    }

    public void setIpvDeclareYear(String ipvDeclareYear) {
        this.ipvDeclareYear = ipvDeclareYear;
    }

    public String getImsId() {
        return imsId;
    }

    public void setImsId(String imsId) {
        this.imsId = imsId;
    }

    public String getIpvId() {
        return ipvId;
    }

    public void setIpvId(String ipvId) {
        this.ipvId = ipvId;
    }

    public Integer getImsMonth() {
        return imsMonth;
    }

    public void setImsMonth(Integer imsMonth) {
        this.imsMonth = imsMonth;
    }

    public BigDecimal getImsPlannedInv() {
        return imsPlannedInv;
    }

    public void setImsPlannedInv(BigDecimal imsPlannedInv) {
        this.imsPlannedInv = imsPlannedInv;
    }

    public Float getImsAnnualProportion() {
        return imsAnnualProportion;
    }

    public void setImsAnnualProportion(Float imsAnnualProportion) {
        this.imsAnnualProportion = imsAnnualProportion;
    }

    public BigDecimal getImsCumulativeInv() {
        return imsCumulativeInv;
    }

    public void setImsCumulativeInv(BigDecimal imsCumulativeInv) {
        this.imsCumulativeInv = imsCumulativeInv;
    }

    public Float getImsCumulativeInvProportion() {
        return imsCumulativeInvProportion;
    }

    public void setImsCumulativeInvProportion(Float imsCumulativeInvProportion) {
        this.imsCumulativeInvProportion = imsCumulativeInvProportion;
    }

    public BigDecimal getImsPlannedPay() {
        return imsPlannedPay;
    }

    public void setImsPlannedPay(BigDecimal imsPlannedPay) {
        this.imsPlannedPay = imsPlannedPay;
    }

    public Float getImsPayProportion() {
        return imsPayProportion;
    }

    public void setImsPayProportion(Float imsPayProportion) {
        this.imsPayProportion = imsPayProportion;
    }

    public BigDecimal getImsCumulativePay() {
        return imsCumulativePay;
    }

    public void setImsCumulativePay(BigDecimal imsCumulativePay) {
        this.imsCumulativePay = imsCumulativePay;
    }

    public Float getImsCumulativePayProportion() {
        return imsCumulativePayProportion;
    }

    public void setImsCumulativePayProportion(Float imsCumulativePayProportion) {
        this.imsCumulativePayProportion = imsCumulativePayProportion;
    }

    public String getImsImgProgress() {
        return imsImgProgress;
    }

    public void setImsImgProgress(String imsImgProgress) {
        this.imsImgProgress = imsImgProgress;
    }
}
