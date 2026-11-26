package com.jpmsoft.common.model.requestAndResponse;


import com.jpmsoft.common.model.BaseFileResponse;

import java.math.BigDecimal;

/**
 * 招标相关Response实体公共模块
 */
public class SecCommonModel extends BaseFileResponse {
    private String secName;
    private String secType;
    private String dScope;
    private BigDecimal secMoney;
    private BigDecimal dAmount;
    private String pId;
    private String secPricingMethod;
    private String secOtherValuation;
    private String secAgencyName;
    private String secContractingDepartment;
    private String secContractingDepartmentName;
    private String secDeterminType;
    private String secPlace;


    public String getSecName() {
        return secName;
    }

    public void setSecName(String secName) {
        this.secName = secName;
    }

    public String getSecType() {
        return secType;
    }

    public void setSecType(String secType) {
        this.secType = secType;
    }

    public String getdScope() {
        return dScope;
    }

    public void setdScope(String dScope) {
        this.dScope = dScope;
    }

    public BigDecimal getSecMoney() {
        return secMoney;
    }

    public void setSecMoney(BigDecimal secMoney) {
        this.secMoney = secMoney;
    }

    public BigDecimal getdAmount() {
        return dAmount;
    }

    public void setdAmount(BigDecimal dAmount) {
        this.dAmount = dAmount;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getSecPricingMethod() {
        return secPricingMethod;
    }

    public void setSecPricingMethod(String secPricingMethod) {
        this.secPricingMethod = secPricingMethod;
    }

    public String getSecOtherValuation() {
        return secOtherValuation;
    }

    public void setSecOtherValuation(String secOtherValuation) {
        this.secOtherValuation = secOtherValuation;
    }

    public String getSecAgencyName() {
        return secAgencyName;
    }

    public void setSecAgencyName(String secAgencyName) {
        this.secAgencyName = secAgencyName;
    }

    public String getSecContractingDepartment() {
        return secContractingDepartment;
    }

    public void setSecContractingDepartment(String secContractingDepartment) {
        this.secContractingDepartment = secContractingDepartment;
    }

    public String getSecContractingDepartmentName() {
        return secContractingDepartmentName;
    }

    public void setSecContractingDepartmentName(String secContractingDepartmentName) {
        this.secContractingDepartmentName = secContractingDepartmentName;
    }

    public String getSecDeterminType() {
        return secDeterminType;
    }

    public void setSecDeterminType(String secDeterminType) {
        this.secDeterminType = secDeterminType;
    }

    public String getSecPlace() {
        return secPlace;
    }

    public void setSecPlace(String secPlace) {
        this.secPlace = secPlace;
    }
}
