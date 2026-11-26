package com.jpmsoft.common.model.export.response;

import java.math.BigDecimal;

public class InvMonthlyScheduleModel {

    private Integer imsMonth;
    private BigDecimal imsCumulativeInv;
    private String imsImgProgress;
    private String pId;

    public Integer getImsMonth() {
        return imsMonth;
    }

    public void setImsMonth(Integer imsMonth) {
        this.imsMonth = imsMonth;
    }

    public BigDecimal getImsCumulativeInv() {
        return imsCumulativeInv;
    }

    public void setImsCumulativeInv(BigDecimal imsCumulativeInv) {
        this.imsCumulativeInv = imsCumulativeInv;
    }

    public String getImsImgProgress() {
        return imsImgProgress;
    }

    public void setImsImgProgress(String imsImgProgress) {
        this.imsImgProgress = imsImgProgress;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }
}
