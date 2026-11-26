package com.jpmsoft.common.model.requestAndResponse.InvestmentControl;

import com.jpmsoft.common.model.ProjectCommonRes;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 投资控制
 */
public class InvControlManageModel extends ProjectCommonRes implements Serializable {

    private static final long serialVersionUID = -366066882523341230L;
    
    private String icmId;
    private String icmYear;
    private String icmLegalPerson;
    private String icmLegalPersonName;
    private String icmLegalPersonCid;
    private String icmGenerationUnit;
    private String icmPlanType;
    private String icmMajorType;
    private String icmSourceFunds;
    private BigDecimal icmInvestmentEstimation;
    private BigDecimal icmCompleteYnvestmentY;
    private BigDecimal icmInvestmentFundsY;
    private Date icmOpenDate;
    private Date icmEndDate;
    private String icmPlanSource;
    private BigDecimal icmLength;
    private BigDecimal icmWidth;
    private BigDecimal icmMeasureArea;
    private String icmCreateUid;
    private String icmCreateUname;
    private Date icmCreateTime;
    private Integer icmState;
    private Date icmStateTime;
    private String icmPName;

    private String icmLegalPersonCName;
    private String icmGenerationUnitName;
    private String legalPersonCNameHistory;
    private String generationUnitNameHistory;
    //单位简称
    private String icmLegalPersonAbbName;
    private String icmGenerationAbbName;

    public String getIcmId() {
        return icmId;
    }

    public void setIcmId(String icmId) {
        this.icmId = icmId;
    }

    public String getIcmYear() {
        return icmYear;
    }

    public void setIcmYear(String icmYear) {
        this.icmYear = icmYear;
    }

    public String getIcmLegalPerson() {
        return icmLegalPerson;
    }

    public void setIcmLegalPerson(String icmLegalPerson) {
        this.icmLegalPerson = icmLegalPerson;
    }

    public String getIcmLegalPersonName() {
        return icmLegalPersonName;
    }

    public void setIcmLegalPersonName(String icmLegalPersonName) {
        this.icmLegalPersonName = icmLegalPersonName;
    }

    public String getIcmLegalPersonCid() {
        return icmLegalPersonCid;
    }

    public void setIcmLegalPersonCid(String icmLegalPersonCid) {
        this.icmLegalPersonCid = icmLegalPersonCid;
    }

    public String getIcmGenerationUnit() {
        return icmGenerationUnit;
    }

    public void setIcmGenerationUnit(String icmGenerationUnit) {
        this.icmGenerationUnit = icmGenerationUnit;
    }

    public String getIcmPlanType() {
        return icmPlanType;
    }

    public void setIcmPlanType(String icmPlanType) {
        this.icmPlanType = icmPlanType;
    }

    public String getIcmMajorType() {
        return icmMajorType;
    }

    public void setIcmMajorType(String icmMajorType) {
        this.icmMajorType = icmMajorType;
    }

    public String getIcmSourceFunds() {
        return icmSourceFunds;
    }

    public void setIcmSourceFunds(String icmSourceFunds) {
        this.icmSourceFunds = icmSourceFunds;
    }

    public BigDecimal getIcmInvestmentEstimation() {
        return icmInvestmentEstimation;
    }

    public void setIcmInvestmentEstimation(BigDecimal icmInvestmentEstimation) {
        this.icmInvestmentEstimation = icmInvestmentEstimation;
    }

    public BigDecimal getIcmCompleteYnvestmentY() {
        return icmCompleteYnvestmentY;
    }

    public void setIcmCompleteYnvestmentY(BigDecimal icmCompleteYnvestmentY) {
        this.icmCompleteYnvestmentY = icmCompleteYnvestmentY;
    }

    public BigDecimal getIcmInvestmentFundsY() {
        return icmInvestmentFundsY;
    }

    public void setIcmInvestmentFundsY(BigDecimal icmInvestmentFundsY) {
        this.icmInvestmentFundsY = icmInvestmentFundsY;
    }

    public Date getIcmOpenDate() {
        return icmOpenDate;
    }

    public void setIcmOpenDate(Date icmOpenDate) {
        this.icmOpenDate = icmOpenDate;
    }

    public Date getIcmEndDate() {
        return icmEndDate;
    }

    public void setIcmEndDate(Date icmEndDate) {
        this.icmEndDate = icmEndDate;
    }

    public String getIcmPlanSource() {
        return icmPlanSource;
    }

    public void setIcmPlanSource(String icmPlanSource) {
        this.icmPlanSource = icmPlanSource;
    }

    public BigDecimal getIcmLength() {
        return icmLength;
    }

    public void setIcmLength(BigDecimal icmLength) {
        this.icmLength = icmLength;
    }

    public BigDecimal getIcmWidth() {
        return icmWidth;
    }

    public void setIcmWidth(BigDecimal icmWidth) {
        this.icmWidth = icmWidth;
    }

    public BigDecimal getIcmMeasureArea() {
        return icmMeasureArea;
    }

    public void setIcmMeasureArea(BigDecimal icmMeasureArea) {
        this.icmMeasureArea = icmMeasureArea;
    }

    public String getIcmCreateUid() {
        return icmCreateUid;
    }

    public void setIcmCreateUid(String icmCreateUid) {
        this.icmCreateUid = icmCreateUid;
    }

    public String getIcmCreateUname() {
        return icmCreateUname;
    }

    public void setIcmCreateUname(String icmCreateUname) {
        this.icmCreateUname = icmCreateUname;
    }

    public Date getIcmCreateTime() {
        return icmCreateTime;
    }

    public void setIcmCreateTime(Date icmCreateTime) {
        this.icmCreateTime = icmCreateTime;
    }

    public Integer getIcmState() {
        return icmState;
    }

    public void setIcmState(Integer icmState) {
        this.icmState = icmState;
    }

    public Date getIcmStateTime() {
        return icmStateTime;
    }

    public void setIcmStateTime(Date icmStateTime) {
        this.icmStateTime = icmStateTime;
    }

    public String getIcmPName() {
        return icmPName;
    }

    public void setIcmPName(String icmPName) {
        this.icmPName = icmPName;
    }

    public String getIcmLegalPersonCName() {
        return icmLegalPersonCName;
    }

    public void setIcmLegalPersonCName(String icmLegalPersonCName) {
        this.icmLegalPersonCName = icmLegalPersonCName;
    }

    public String getIcmGenerationUnitName() {
        return icmGenerationUnitName;
    }

    public void setIcmGenerationUnitName(String icmGenerationUnitName) {
        this.icmGenerationUnitName = icmGenerationUnitName;
    }

    public String getLegalPersonCNameHistory() {
        return legalPersonCNameHistory;
    }

    public void setLegalPersonCNameHistory(String legalPersonCNameHistory) {
        this.legalPersonCNameHistory = legalPersonCNameHistory;
    }

    public String getGenerationUnitNameHistory() {
        return generationUnitNameHistory;
    }

    public void setGenerationUnitNameHistory(String generationUnitNameHistory) {
        this.generationUnitNameHistory = generationUnitNameHistory;
    }

    public String getIcmLegalPersonAbbName() {
        return icmLegalPersonAbbName;
    }

    public void setIcmLegalPersonAbbName(String icmLegalPersonAbbName) {
        this.icmLegalPersonAbbName = icmLegalPersonAbbName;
    }

    public String getIcmGenerationAbbName() {
        return icmGenerationAbbName;
    }

    public void setIcmGenerationAbbName(String icmGenerationAbbName) {
        this.icmGenerationAbbName = icmGenerationAbbName;
    }
}
