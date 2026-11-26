package com.jpmsoft.common.model.requestAndResponse.SecInfo;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 项目资金来源
 */
public class SecFundingSourceModel {

    private String sfsId;
    private String sfsForeignId;
    private String sfsCode;
    private Date sfsCreateTime;
    private Float sfsScale;
    private BigDecimal sfsMoney;
    private String sfsRemark;
    private Integer sfsSort;


    public String getSfsId() {
        return sfsId;
    }

    public void setSfsId(String sfsId) {
        this.sfsId = sfsId;
    }

    public String getSfsForeignId() {
        return sfsForeignId;
    }

    public void setSfsForeignId(String sfsForeignId) {
        this.sfsForeignId = sfsForeignId;
    }

    public String getSfsCode() {
        return sfsCode;
    }

    public void setSfsCode(String sfsCode) {
        this.sfsCode = sfsCode;
    }

    public Date getSfsCreateTime() {
        return sfsCreateTime;
    }

    public void setSfsCreateTime(Date sfsCreateTime) {
        this.sfsCreateTime = sfsCreateTime;
    }

    public Float getSfsScale() {
        return sfsScale;
    }

    public void setSfsScale(Float sfsScale) {
        this.sfsScale = sfsScale;
    }

    public BigDecimal getSfsMoney() {
        return sfsMoney;
    }

    public void setSfsMoney(BigDecimal sfsMoney) {
        this.sfsMoney = sfsMoney;
    }

    public String getSfsRemark() {
        return sfsRemark;
    }

    public void setSfsRemark(String sfsRemark) {
        this.sfsRemark = sfsRemark;
    }

    public Integer getSfsSort() {
        return sfsSort;
    }

    public void setSfsSort(Integer sfsSort) {
        this.sfsSort = sfsSort;
    }
}
