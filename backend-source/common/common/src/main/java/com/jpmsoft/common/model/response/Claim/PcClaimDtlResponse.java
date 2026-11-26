package com.jpmsoft.common.model.response.Claim;

import java.math.BigDecimal;

/**
 * 索赔清单
 */
public class PcClaimDtlResponse {

    private String cdId;
    private String claimId;
    private String cdCId;
    private Integer cdType;
    private Integer cdDuration;
    private BigDecimal cdCost;
    private String cdRemark;
    private String cdCName;
    private String cdCAbbName;

    public String getCdId() {
        return cdId;
    }

    public void setCdId(String cdId) {
        this.cdId = cdId;
    }

    public String getClaimId() {
        return claimId;
    }

    public void setClaimId(String claimId) {
        this.claimId = claimId;
    }

    public String getCdCId() {
        return cdCId;
    }

    public void setCdCId(String cdCId) {
        this.cdCId = cdCId;
    }

    public Integer getCdType() {
        return cdType;
    }

    public void setCdType(Integer cdType) {
        this.cdType = cdType;
    }

    public Integer getCdDuration() {
        return cdDuration;
    }

    public void setCdDuration(Integer cdDuration) {
        this.cdDuration = cdDuration;
    }

    public BigDecimal getCdCost() {
        return cdCost;
    }

    public void setCdCost(BigDecimal cdCost) {
        this.cdCost = cdCost;
    }

    public String getCdRemark() {
        return cdRemark;
    }

    public void setCdRemark(String cdRemark) {
        this.cdRemark = cdRemark;
    }

    public String getCdCName() {
        return cdCName;
    }

    public void setCdCName(String cdCName) {
        this.cdCName = cdCName;
    }

    public String getCdCAbbName() {
        return cdCAbbName;
    }

    public void setCdCAbbName(String cdCAbbName) {
        this.cdCAbbName = cdCAbbName;
    }
}
