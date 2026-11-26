package com.jpmsoft.common.model.request.MeEnter;

import com.jpmsoft.common.model.BaseModeRequest;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 见证送检清单
 */
public class MeWitnessDtlRequest extends BaseModeRequest {

    private String wdId;
    private String wiId;
    private String eadId;
    private String wdCode;
    private Integer wdSampleGroups;
    private String wdItem;
    private String wdTestBasis;
    private String wdEntrustedTime;
    private String wdStipulateSampleSize;
    private String wdActualSampleSize;
    private String wdActualPlace;
    private String wdActualInspectionLot;
    private String wdRemark;
    private Date wdCreateTime;
    private String wdName;
    private String wdSupplierName;
    private String wdBrandName;
    private String wdStandardType;
    private Float wdCount;
    private String wdUnit;
    private Integer wdQuantity;
    private String wdFormula;
    private BigDecimal wdAmount;
    private String wdResult;
    private String wdResRemark;

    /***************************************/
    private String conId;
    private String pId;
    private String metName;
    private String wiUnitId;
    private String eaCode;
    /**
     * 是否有检测报告
     * 0：全部
     * 1：有检测报告
     * 2：没有检测报告
     */
    private Integer haveReport;
    private Integer wiState;


    public String getWdId() {
        return wdId;
    }

    public void setWdId(String wdId) {
        this.wdId = wdId;
    }

    public String getWiId() {
        return wiId;
    }

    public void setWiId(String wiId) {
        this.wiId = wiId;
    }

    public String getEadId() {
        return eadId;
    }

    public void setEadId(String eadId) {
        this.eadId = eadId;
    }

    public String getWdCode() {
        return wdCode;
    }

    public void setWdCode(String wdCode) {
        this.wdCode = wdCode;
    }

    public Integer getWdSampleGroups() {
        return wdSampleGroups;
    }

    public void setWdSampleGroups(Integer wdSampleGroups) {
        this.wdSampleGroups = wdSampleGroups;
    }

    public String getWdItem() {
        return wdItem;
    }

    public void setWdItem(String wdItem) {
        this.wdItem = wdItem;
    }

    public String getWdTestBasis() {
        return wdTestBasis;
    }

    public void setWdTestBasis(String wdTestBasis) {
        this.wdTestBasis = wdTestBasis;
    }

    public String getWdEntrustedTime() {
        return wdEntrustedTime;
    }

    public void setWdEntrustedTime(String wdEntrustedTime) {
        this.wdEntrustedTime = wdEntrustedTime;
    }

    public String getWdStipulateSampleSize() {
        return wdStipulateSampleSize;
    }

    public void setWdStipulateSampleSize(String wdStipulateSampleSize) {
        this.wdStipulateSampleSize = wdStipulateSampleSize;
    }

    public String getWdActualSampleSize() {
        return wdActualSampleSize;
    }

    public void setWdActualSampleSize(String wdActualSampleSize) {
        this.wdActualSampleSize = wdActualSampleSize;
    }

    public String getWdActualPlace() {
        return wdActualPlace;
    }

    public void setWdActualPlace(String wdActualPlace) {
        this.wdActualPlace = wdActualPlace;
    }

    public String getWdActualInspectionLot() {
        return wdActualInspectionLot;
    }

    public void setWdActualInspectionLot(String wdActualInspectionLot) {
        this.wdActualInspectionLot = wdActualInspectionLot;
    }

    public String getWdRemark() {
        return wdRemark;
    }

    public void setWdRemark(String wdRemark) {
        this.wdRemark = wdRemark;
    }

    public Date getWdCreateTime() {
        return wdCreateTime;
    }

    public void setWdCreateTime(Date wdCreateTime) {
        this.wdCreateTime = wdCreateTime;
    }

    public String getWdName() {
        return wdName;
    }

    public void setWdName(String wdName) {
        this.wdName = wdName;
    }

    public String getWdSupplierName() {
        return wdSupplierName;
    }

    public void setWdSupplierName(String wdSupplierName) {
        this.wdSupplierName = wdSupplierName;
    }

    public String getWdBrandName() {
        return wdBrandName;
    }

    public void setWdBrandName(String wdBrandName) {
        this.wdBrandName = wdBrandName;
    }

    public String getWdStandardType() {
        return wdStandardType;
    }

    public void setWdStandardType(String wdStandardType) {
        this.wdStandardType = wdStandardType;
    }

    public Float getWdCount() {
        return wdCount;
    }

    public void setWdCount(Float wdCount) {
        this.wdCount = wdCount;
    }

    public String getWdUnit() {
        return wdUnit;
    }

    public void setWdUnit(String wdUnit) {
        this.wdUnit = wdUnit;
    }

    public Integer getWdQuantity() {
        return wdQuantity;
    }

    public void setWdQuantity(Integer wdQuantity) {
        this.wdQuantity = wdQuantity;
    }

    public String getWdFormula() {
        return wdFormula;
    }

    public void setWdFormula(String wdFormula) {
        this.wdFormula = wdFormula;
    }

    public BigDecimal getWdAmount() {
        return wdAmount;
    }

    public void setWdAmount(BigDecimal wdAmount) {
        this.wdAmount = wdAmount;
    }

    public String getWdResult() {
        return wdResult;
    }

    public void setWdResult(String wdResult) {
        this.wdResult = wdResult;
    }

    public String getWdResRemark() {
        return wdResRemark;
    }

    public void setWdResRemark(String wdResRemark) {
        this.wdResRemark = wdResRemark;
    }

    public String getConId() {
        return conId;
    }

    public void setConId(String conId) {
        this.conId = conId;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getMetName() {
        return metName;
    }

    public void setMetName(String metName) {
        this.metName = metName;
    }

    public String getWiUnitId() {
        return wiUnitId;
    }

    public void setWiUnitId(String wiUnitId) {
        this.wiUnitId = wiUnitId;
    }

    public String getEaCode() {
        return eaCode;
    }

    public void setEaCode(String eaCode) {
        this.eaCode = eaCode;
    }

    public Integer getHaveReport() {
        return haveReport;
    }

    public void setHaveReport(Integer haveReport) {
        this.haveReport = haveReport;
    }

    public Integer getWiState() {
        return wiState;
    }

    public void setWiState(Integer wiState) {
        this.wiState = wiState;
    }
}
