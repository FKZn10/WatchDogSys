package com.jpmsoft.common.model.requestAndResponse.SecInfo;


import java.math.BigDecimal;

/**
 * 工程概算
 */
public class SecInfoBudgetModel {
    
    private String sibId;
    private String secId;
    private String sibCost;
    private String sibCostName;
    private String sibMainCost;
    private String sibMainCostName;
    private String sibCompany;
    private String sibQuantities;
    private BigDecimal sibUnitPrice;
    private BigDecimal sibInvestment;
    private String sibRemarks;
    private BigDecimal sibTotalExpenses;
    private Integer sibExpenseType;
    //阶段类型（01、概算 02、估算 03、匡算）
    private String sibStageType;

    public String getSibId() {
        return sibId;
    }

    public void setSibId(String sibId) {
        this.sibId = sibId;
    }

    public String getSecId() {
        return secId;
    }

    public void setSecId(String secId) {
        this.secId = secId;
    }

    public String getSibCost() {
        return sibCost;
    }

    public void setSibCost(String sibCost) {
        this.sibCost = sibCost;
    }

    public String getSibCostName() {
        return sibCostName;
    }

    public void setSibCostName(String sibCostName) {
        this.sibCostName = sibCostName;
    }

    public String getSibMainCost() {
        return sibMainCost;
    }

    public void setSibMainCost(String sibMainCost) {
        this.sibMainCost = sibMainCost;
    }

    public String getSibMainCostName() {
        return sibMainCostName;
    }

    public void setSibMainCostName(String sibMainCostName) {
        this.sibMainCostName = sibMainCostName;
    }

    public String getSibCompany() {
        return sibCompany;
    }

    public void setSibCompany(String sibCompany) {
        this.sibCompany = sibCompany;
    }

    public String getSibQuantities() {
        return sibQuantities;
    }

    public void setSibQuantities(String sibQuantities) {
        this.sibQuantities = sibQuantities;
    }

    public BigDecimal getSibUnitPrice() {
        return sibUnitPrice;
    }

    public void setSibUnitPrice(BigDecimal sibUnitPrice) {
        this.sibUnitPrice = sibUnitPrice;
    }

    public BigDecimal getSibInvestment() {
        return sibInvestment;
    }

    public void setSibInvestment(BigDecimal sibInvestment) {
        this.sibInvestment = sibInvestment;
    }

    public String getSibRemarks() {
        return sibRemarks;
    }

    public void setSibRemarks(String sibRemarks) {
        this.sibRemarks = sibRemarks;
    }

    public BigDecimal getSibTotalExpenses() {
        return sibTotalExpenses;
    }

    public void setSibTotalExpenses(BigDecimal sibTotalExpenses) {
        this.sibTotalExpenses = sibTotalExpenses;
    }

    public Integer getSibExpenseType() {
        return sibExpenseType;
    }

    public void setSibExpenseType(Integer sibExpenseType) {
        this.sibExpenseType = sibExpenseType;
    }

    public String getSibStageType() {
        return sibStageType;
    }

    public void setSibStageType(String sibStageType) {
        this.sibStageType = sibStageType;
    }
}
