package com.jpmsoft.common.model.response.SecInfo;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.jpmsoft.common.model.ProjectCommonRes;

import java.math.BigDecimal;

/**
 * 标段项目关系表
 */
public class SecProjectResponse extends ProjectCommonRes {

    private String spId;
    private String secId;
    private String pId;
    //招采计划id
    private String ppId;
    //招采计划名称
    private String ppName;
    private String pConstructionUnitNameHistory;
    private BigDecimal pEstimateAmount;
    private BigDecimal pApproveBudget;
    @JsonProperty("pCampus")
    private String pCampus;

    @JsonProperty("pUseUnitName")
    private String pUseUnitName;

    @JsonProperty("pCategory")
    private String pCategory;

    private Integer state;

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getpCategory() {
        return pCategory;
    }

    public void setpCategory(String pCategory) {
        this.pCategory = pCategory;
    }

    public String getpUseUnitName() {
        return pUseUnitName;
    }

    public void setpUseUnitName(String pUseUnitName) {
        this.pUseUnitName = pUseUnitName;
    }

    public String getpCampus() {
        return pCampus;
    }

    public void setpCampus(String pCampus) {
        this.pCampus = pCampus;
    }

    public String getSpId() {
        return spId;
    }

    public void setSpId(String spId) {
        this.spId = spId;
    }

    public String getSecId() {
        return secId;
    }

    public void setSecId(String secId) {
        this.secId = secId;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getPpId() {
        return ppId;
    }

    public void setPpId(String ppId) {
        this.ppId = ppId;
    }

    public String getPpName() {
        return ppName;
    }

    public void setPpName(String ppName) {
        this.ppName = ppName;
    }

    public String getpConstructionUnitNameHistory() {
        return pConstructionUnitNameHistory;
    }

    public void setpConstructionUnitNameHistory(String pConstructionUnitNameHistory) {
        this.pConstructionUnitNameHistory = pConstructionUnitNameHistory;
    }

    public BigDecimal getpEstimateAmount() {
        return pEstimateAmount;
    }

    public void setpEstimateAmount(BigDecimal pEstimateAmount) {
        this.pEstimateAmount = pEstimateAmount;
    }

    public BigDecimal getpApproveBudget() {
        return pApproveBudget;
    }

    public void setpApproveBudget(BigDecimal pApproveBudget) {
        this.pApproveBudget = pApproveBudget;
    }

}
