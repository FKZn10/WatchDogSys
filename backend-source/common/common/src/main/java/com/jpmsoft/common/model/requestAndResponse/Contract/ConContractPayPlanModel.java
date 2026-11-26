package com.jpmsoft.common.model.requestAndResponse.Contract;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 合同支付计划
 */

public class ConContractPayPlanModel {

    private String cppId;
    private String conId;
    private String cppName;
    private String cppCode;
    private String cppClause;
    private BigDecimal cppMoney;
    private Float cppRatio;
    private Float cppRatioPayTo;
    private String cppRemark;
    private Integer cppSort;
    private Date cppCreateTime;
    private String cppExplain;

    // 标段类型
    private String isbdSectionType;

    // 估算价
    private BigDecimal isbdAmount;

    private String cppConditionType;

    private String cppConditionValue;

    private String cppMode;

    private String cppBasics;
    private String cppZftjLx;
    private Boolean cppIsCompleted;
    private Integer cppZftjNumber;
    private String cppZftjUnit;
    private String cppConJdlx;
    private Boolean cppStatus;

    // ===================== 新增 3 个字段 =====================
    /**
     * 实际付款日期
     */
    private Date cppPayDate;

    /**
     * 实际付款金额
     */
    private BigDecimal cppPayAmount;

    /**
     * 资金来源
     */
    private String cppPaySource;

    public String getCppPaySource() {return cppPaySource;}

    public Date getCppPayDate() {return cppPayDate;}

    public void setCppPayDate(Date cppPayDate) {this.cppPayDate = cppPayDate;}

    public BigDecimal getCppPayAmount() {return cppPayAmount;}

    public String getCppConditionType() {
        return cppConditionType;
    }

    public void setCppConditionType(String cppConditionType) {
        this.cppConditionType = cppConditionType;
    }

    public String getCppConditionValue() {
        return cppConditionValue;
    }

    public void setCppConditionValue(String cppConditionValue) {
        this.cppConditionValue = cppConditionValue;
    }

    public String getCppMode() {
        return cppMode;
    }

    public void setCppMode(String cppMode) {
        this.cppMode = cppMode;
    }

    public String getCppBasics() {
        return cppBasics;
    }

    public void setCppBasics(String cppBasics) {
        this.cppBasics = cppBasics;
    }

    public String getCppZftjLx() {
        return cppZftjLx;
    }

    public void setCppZftjLx(String cppZftjLx) {
        this.cppZftjLx = cppZftjLx;
    }

    public Boolean getCppIsCompleted() {
        return cppIsCompleted;
    }

    public void setCppIsCompleted(Boolean cppIsCompleted) {
        this.cppIsCompleted = cppIsCompleted;
    }

    public Integer getCppZftjNumber() {
        return cppZftjNumber;
    }

    public void setCppZftjNumber(Integer cppZftjNumber) {
        this.cppZftjNumber = cppZftjNumber;
    }

    public String getCppZftjUnit() {
        return cppZftjUnit;
    }

    public void setCppZftjUnit(String cppZftjUnit) {
        this.cppZftjUnit = cppZftjUnit;
    }

    public String getCppConJdlx() {
        return cppConJdlx;
    }

    public void setCppConJdlx(String cppConJdlx) {
        this.cppConJdlx = cppConJdlx;
    }

    public Boolean getCppStatus() {
        return cppStatus;
    }

    public void setCppStatus(Boolean cppStatus) {
        this.cppStatus = cppStatus;
    }

    public String getIsbdSectionType() {
        return isbdSectionType;
    }

    public BigDecimal getIsbdAmount() {
        return isbdAmount;
    }

    public void setIsbdAmount(BigDecimal isbdAmount) {
        this.isbdAmount = isbdAmount;
    }

    public void setIsbdSectionType(String isbdSectionType) {
        this.isbdSectionType = isbdSectionType;
    }

    public String getCppId() {
        return cppId;
    }

    public void setCppId(String cppId) {
        this.cppId = cppId;
    }

    public String getConId() {
        return conId;
    }

    public void setConId(String conId) {
        this.conId = conId;
    }

    public String getCppName() {
        return cppName;
    }

    public void setCppName(String cppName) {
        this.cppName = cppName;
    }

    public String getCppCode() {
        return cppCode;
    }

    public void setCppCode(String cppCode) {
        this.cppCode = cppCode;
    }

    public String getCppClause() {
        return cppClause;
    }

    public void setCppClause(String cppClause) {
        this.cppClause = cppClause;
    }

    public BigDecimal getCppMoney() {
        return cppMoney;
    }

    public void setCppMoney(BigDecimal cppMoney) {
        this.cppMoney = cppMoney;
    }

    public Float getCppRatio() {
        return cppRatio;
    }

    public void setCppRatio(Float cppRatio) {
        this.cppRatio = cppRatio;
    }

    public Float getCppRatioPayTo() {
        return cppRatioPayTo;
    }

    public void setCppRatioPayTo(Float cppRatioPayTo) {
        this.cppRatioPayTo = cppRatioPayTo;
    }

    public String getCppRemark() {
        return cppRemark;
    }

    public void setCppRemark(String cppRemark) {
        this.cppRemark = cppRemark;
    }

    public Integer getCppSort() {
        return cppSort;
    }

    public void setCppSort(Integer cppSort) {
        this.cppSort = cppSort;
    }

    public Date getCppCreateTime() {
        return cppCreateTime;
    }

    public void setCppCreateTime(Date cppCreateTime) {
        this.cppCreateTime = cppCreateTime;
    }

    public String getCppExplain() {
        return cppExplain;
    }

    public void setCppExplain(String cppExplain) {
        this.cppExplain = cppExplain;
    }

    public void setCppPayAmount(BigDecimal cppPayAmount) {this.cppPayAmount = cppPayAmount;}

    public void setCppPaySource(String cppPaySource) {this.cppPaySource = cppPaySource;}

}
