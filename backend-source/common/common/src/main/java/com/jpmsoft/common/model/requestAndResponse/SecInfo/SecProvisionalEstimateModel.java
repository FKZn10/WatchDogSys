package com.jpmsoft.common.model.requestAndResponse.SecInfo;

import com.jpmsoft.common.model.BaseFileRequest;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 暂估价
 */
public class SecProvisionalEstimateModel extends BaseFileRequest {

    private String peId;
    private String pId;
    private String conId;
    private String peType;
    private String peProfessionalName;
    private String peOverallName;
    private String peProType;
    private String peCode;
    private String peName;
    private String peModel;
    private String peMeasure;
    private BigDecimal peEstimatePrice;
    private Integer peEstimateNumber;
    private BigDecimal peEstimateCombine;
    private BigDecimal peOverallPrice;
    private BigDecimal peOverallCombine;
    private BigDecimal peSupervisorPrice;
    private BigDecimal peSupervisorCombine;
    private BigDecimal peCostPrice;
    private BigDecimal peCostCombine;
    private BigDecimal peBuyertPrice;
    private BigDecimal peBuyerCombine;
    private String peRegulationType;
    private BigDecimal peRegulationPrice;
    private String peRegulationMold;
    private BigDecimal peRegulationCombine;
    private String pePricingMode;
    private String peApprovalCompany;
    private Date peStateTime;
    private String peCreateUid;
    private String peCreateUname;
    private Integer state;
    private Date stateTime;
    private Date peCreateTime;
    private String peRemark;
    private Float peRate;

    private String pName;
    private String pCode;
    private String conName;
    private String conCode;
    private String peApprovalCompanyName;
    //单位简称
    private String peApprCompAbbName;

    public String getPeId() {
        return peId;
    }

    public void setPeId(String peId) {
        this.peId = peId;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getConId() {
        return conId;
    }

    public void setConId(String conId) {
        this.conId = conId;
    }

    public String getPeType() {
        return peType;
    }

    public void setPeType(String peType) {
        this.peType = peType;
    }

    public String getPeProfessionalName() {
        return peProfessionalName;
    }

    public void setPeProfessionalName(String peProfessionalName) {
        this.peProfessionalName = peProfessionalName;
    }

    public String getPeOverallName() {
        return peOverallName;
    }

    public void setPeOverallName(String peOverallName) {
        this.peOverallName = peOverallName;
    }

    public String getPeProType() {
        return peProType;
    }

    public void setPeProType(String peProType) {
        this.peProType = peProType;
    }

    public String getPeCode() {
        return peCode;
    }

    public void setPeCode(String peCode) {
        this.peCode = peCode;
    }

    public String getPeName() {
        return peName;
    }

    public void setPeName(String peName) {
        this.peName = peName;
    }

    public String getPeModel() {
        return peModel;
    }

    public void setPeModel(String peModel) {
        this.peModel = peModel;
    }

    public String getPeMeasure() {
        return peMeasure;
    }

    public void setPeMeasure(String peMeasure) {
        this.peMeasure = peMeasure;
    }

    public BigDecimal getPeEstimatePrice() {
        return peEstimatePrice;
    }

    public void setPeEstimatePrice(BigDecimal peEstimatePrice) {
        this.peEstimatePrice = peEstimatePrice;
    }

    public Integer getPeEstimateNumber() {
        return peEstimateNumber;
    }

    public void setPeEstimateNumber(Integer peEstimateNumber) {
        this.peEstimateNumber = peEstimateNumber;
    }

    public BigDecimal getPeEstimateCombine() {
        return peEstimateCombine;
    }

    public void setPeEstimateCombine(BigDecimal peEstimateCombine) {
        this.peEstimateCombine = peEstimateCombine;
    }

    public BigDecimal getPeOverallPrice() {
        return peOverallPrice;
    }

    public void setPeOverallPrice(BigDecimal peOverallPrice) {
        this.peOverallPrice = peOverallPrice;
    }

    public BigDecimal getPeOverallCombine() {
        return peOverallCombine;
    }

    public void setPeOverallCombine(BigDecimal peOverallCombine) {
        this.peOverallCombine = peOverallCombine;
    }

    public BigDecimal getPeSupervisorPrice() {
        return peSupervisorPrice;
    }

    public void setPeSupervisorPrice(BigDecimal peSupervisorPrice) {
        this.peSupervisorPrice = peSupervisorPrice;
    }

    public BigDecimal getPeSupervisorCombine() {
        return peSupervisorCombine;
    }

    public void setPeSupervisorCombine(BigDecimal peSupervisorCombine) {
        this.peSupervisorCombine = peSupervisorCombine;
    }

    public BigDecimal getPeCostPrice() {
        return peCostPrice;
    }

    public void setPeCostPrice(BigDecimal peCostPrice) {
        this.peCostPrice = peCostPrice;
    }

    public BigDecimal getPeCostCombine() {
        return peCostCombine;
    }

    public void setPeCostCombine(BigDecimal peCostCombine) {
        this.peCostCombine = peCostCombine;
    }

    public BigDecimal getPeBuyertPrice() {
        return peBuyertPrice;
    }

    public void setPeBuyertPrice(BigDecimal peBuyertPrice) {
        this.peBuyertPrice = peBuyertPrice;
    }

    public BigDecimal getPeBuyerCombine() {
        return peBuyerCombine;
    }

    public void setPeBuyerCombine(BigDecimal peBuyerCombine) {
        this.peBuyerCombine = peBuyerCombine;
    }

    public String getPeRegulationType() {
        return peRegulationType;
    }

    public void setPeRegulationType(String peRegulationType) {
        this.peRegulationType = peRegulationType;
    }

    public BigDecimal getPeRegulationPrice() {
        return peRegulationPrice;
    }

    public void setPeRegulationPrice(BigDecimal peRegulationPrice) {
        this.peRegulationPrice = peRegulationPrice;
    }

    public String getPeRegulationMold() {
        return peRegulationMold;
    }

    public void setPeRegulationMold(String peRegulationMold) {
        this.peRegulationMold = peRegulationMold;
    }

    public BigDecimal getPeRegulationCombine() {
        return peRegulationCombine;
    }

    public void setPeRegulationCombine(BigDecimal peRegulationCombine) {
        this.peRegulationCombine = peRegulationCombine;
    }

    public String getPePricingMode() {
        return pePricingMode;
    }

    public void setPePricingMode(String pePricingMode) {
        this.pePricingMode = pePricingMode;
    }

    public String getPeApprovalCompany() {
        return peApprovalCompany;
    }

    public void setPeApprovalCompany(String peApprovalCompany) {
        this.peApprovalCompany = peApprovalCompany;
    }

    public Date getPeStateTime() {
        return peStateTime;
    }

    public void setPeStateTime(Date peStateTime) {
        this.peStateTime = peStateTime;
    }

    public String getPeCreateUid() {
        return peCreateUid;
    }

    public void setPeCreateUid(String peCreateUid) {
        this.peCreateUid = peCreateUid;
    }

    public String getPeCreateUname() {
        return peCreateUname;
    }

    public void setPeCreateUname(String peCreateUname) {
        this.peCreateUname = peCreateUname;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Date getStateTime() {
        return stateTime;
    }

    public void setStateTime(Date stateTime) {
        this.stateTime = stateTime;
    }

    public Date getPeCreateTime() {
        return peCreateTime;
    }

    public void setPeCreateTime(Date peCreateTime) {
        this.peCreateTime = peCreateTime;
    }

    public String getPeRemark() {
        return peRemark;
    }

    public void setPeRemark(String peRemark) {
        this.peRemark = peRemark;
    }

    public Float getPeRate() {
        return peRate;
    }

    public void setPeRate(Float peRate) {
        this.peRate = peRate;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public String getpCode() {
        return pCode;
    }

    public void setpCode(String pCode) {
        this.pCode = pCode;
    }

    public String getConName() {
        return conName;
    }

    public void setConName(String conName) {
        this.conName = conName;
    }

    public String getConCode() {
        return conCode;
    }

    public void setConCode(String conCode) {
        this.conCode = conCode;
    }

    public String getPeApprovalCompanyName() {
        return peApprovalCompanyName;
    }

    public void setPeApprovalCompanyName(String peApprovalCompanyName) {
        this.peApprovalCompanyName = peApprovalCompanyName;
    }

    public String getPeApprCompAbbName() {
        return peApprCompAbbName;
    }

    public void setPeApprCompAbbName(String peApprCompAbbName) {
        this.peApprCompAbbName = peApprCompAbbName;
    }
}
