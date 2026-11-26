package com.jpmsoft.projectConsumer.pojo.dto.investment;

import com.jpmsoft.common.model.BaseFileRequest;

import java.io.Serializable;
import java.math.BigDecimal;

public class FundPlanModifyDto extends BaseFileRequest implements Serializable {

    private static final long serialVersionUID = 1213444751010308854L;

    private String fpmId;
    private String pId;
    private Integer fpmYear;
    private BigDecimal fpmYearCapitalScale;
    private BigDecimal fpmModify;
    private BigDecimal fpmModifyTotal;
    private BigDecimal fpmCapitalScale;
    private BigDecimal fpmNotFunds;
    private BigDecimal fpmReceivedFunds;
    private BigDecimal fpmReleaseAmountTotal;
    private BigDecimal fpmReleaseAmountTotalYear;
    private String fpmRemarks;
    private String pName;
    private String pProjectCode;
    private String pCode;
    private String pConstructionUnit;
    private String pConstructionUnitName;
    private String pUseUnit;
    private String pUseUnitName;
    private String pType;
    private String pCreateTime;
    private BigDecimal bdAuditAmt;
    private String fpmBudgetItemName;
    private BigDecimal actualPayment;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getFpmId() {
        return fpmId;
    }

    public void setFpmId(String fpmId) {
        this.fpmId = fpmId;
    }

    @Override
    public String getpId() {
        return pId;
    }

    @Override
    public void setpId(String pId) {
        this.pId = pId;
    }

    public Integer getFpmYear() {
        return fpmYear;
    }

    public void setFpmYear(Integer fpmYear) {
        this.fpmYear = fpmYear;
    }

    public BigDecimal getFpmYearCapitalScale() {
        return fpmYearCapitalScale;
    }

    public void setFpmYearCapitalScale(BigDecimal fpmYearCapitalScale) {
        this.fpmYearCapitalScale = fpmYearCapitalScale;
    }

    public BigDecimal getFpmModify() {
        return fpmModify;
    }

    public void setFpmModify(BigDecimal fpmModify) {
        this.fpmModify = fpmModify;
    }

    public BigDecimal getFpmModifyTotal() {
        return fpmModifyTotal;
    }

    public void setFpmModifyTotal(BigDecimal fpmModifyTotal) {
        this.fpmModifyTotal = fpmModifyTotal;
    }

    public BigDecimal getFpmCapitalScale() {
        return fpmCapitalScale;
    }

    public void setFpmCapitalScale(BigDecimal fpmCapitalScale) {
        this.fpmCapitalScale = fpmCapitalScale;
    }

    public BigDecimal getFpmNotFunds() {
        return fpmNotFunds;
    }

    public void setFpmNotFunds(BigDecimal fpmNotFunds) {
        this.fpmNotFunds = fpmNotFunds;
    }

    public BigDecimal getFpmReceivedFunds() {
        return fpmReceivedFunds;
    }

    public void setFpmReceivedFunds(BigDecimal fpmReceivedFunds) {
        this.fpmReceivedFunds = fpmReceivedFunds;
    }

    public BigDecimal getFpmReleaseAmountTotal() {
        return fpmReleaseAmountTotal;
    }

    public void setFpmReleaseAmountTotal(BigDecimal fpmReleaseAmountTotal) {
        this.fpmReleaseAmountTotal = fpmReleaseAmountTotal;
    }

    public String getFpmRemarks() {
        return fpmRemarks;
    }

    public void setFpmRemarks(String fpmRemarks) {
        this.fpmRemarks = fpmRemarks;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public String getpProjectCode() {
        return pProjectCode;
    }

    public void setpProjectCode(String pProjectCode) {
        this.pProjectCode = pProjectCode;
    }

    public BigDecimal getBdAuditAmt() {
        return bdAuditAmt;
    }

    public void setBdAuditAmt(BigDecimal bdAuditAmt) {
        this.bdAuditAmt = bdAuditAmt;
    }

    public String getpCode() {
        return pCode;
    }

    public void setpCode(String pCode) {
        this.pCode = pCode;
    }

    public String getpConstructionUnit() {
        return pConstructionUnit;
    }

    public void setpConstructionUnit(String pConstructionUnit) {
        this.pConstructionUnit = pConstructionUnit;
    }

    @Override
    public String getpConstructionUnitName() {
        return pConstructionUnitName;
    }

    @Override
    public void setpConstructionUnitName(String pConstructionUnitName) {
        this.pConstructionUnitName = pConstructionUnitName;
    }

    public String getpUseUnit() {
        return pUseUnit;
    }

    public void setpUseUnit(String pUseUnit) {
        this.pUseUnit = pUseUnit;
    }

    public String getpUseUnitName() {
        return pUseUnitName;
    }

    public void setpUseUnitName(String pUseUnitName) {
        this.pUseUnitName = pUseUnitName;
    }

    public String getpType() {
        return pType;
    }

    public void setpType(String pType) {
        this.pType = pType;
    }

    public String getpCreateTime() {
        return pCreateTime;
    }

    public void setpCreateTime(String pCreateTime) {
        this.pCreateTime = pCreateTime;
    }

    public String getFpmBudgetItemName() {
        return fpmBudgetItemName;
    }

    public void setFpmBudgetItemName(String fpmBudgetItemName) {
        this.fpmBudgetItemName = fpmBudgetItemName;
    }

    public BigDecimal getFpmReleaseAmountTotalYear() {
        return fpmReleaseAmountTotalYear;
    }

    public void setFpmReleaseAmountTotalYear(BigDecimal fpmReleaseAmountTotalYear) {
        this.fpmReleaseAmountTotalYear = fpmReleaseAmountTotalYear;
    }

    public BigDecimal getActualPayment() {
        return actualPayment;
    }

    public void setActualPayment(BigDecimal actualPayment) {
        this.actualPayment = actualPayment;
    }
}
