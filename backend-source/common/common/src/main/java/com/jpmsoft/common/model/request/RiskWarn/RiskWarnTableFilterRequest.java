package com.jpmsoft.common.model.request.RiskWarn;

import java.util.Date;

public class RiskWarnTableFilterRequest {
    private String rwtfId;
    private String rwtId;
    private String rwiId;
    private String rwtfConditionType;
    private String rwtfComparisonType;
    private String rwtfTableField;
    private String rwtfComparisonProcessing;
    private Integer rwtfComparisonPercentage;
    private String rwtfComparator;
    private String rwtfAssociationTable;
    private String rwtfComparedValue;
    private String rwtfComparedProcessingCover;
    private Integer rwtfComparisonPercentageCover;
    private Date rwtfCreateTime;

    public String getRwtfId() {
        return rwtfId;
    }

    public void setRwtfId(String rwtfId) {
        this.rwtfId = rwtfId;
    }

    public String getRwtId() {
        return rwtId;
    }

    public void setRwtId(String rwtId) {
        this.rwtId = rwtId;
    }

    public String getRwiId() {
        return rwiId;
    }

    public void setRwiId(String rwiId) {
        this.rwiId = rwiId;
    }

    public String getRwtfConditionType() {
        return rwtfConditionType;
    }

    public void setRwtfConditionType(String rwtfConditionType) {
        this.rwtfConditionType = rwtfConditionType;
    }

    public String getRwtfComparisonType() {
        return rwtfComparisonType;
    }

    public void setRwtfComparisonType(String rwtfComparisonType) {
        this.rwtfComparisonType = rwtfComparisonType;
    }

    public String getRwtfTableField() {
        return rwtfTableField;
    }

    public void setRwtfTableField(String rwtfTableField) {
        this.rwtfTableField = rwtfTableField;
    }

    public String getRwtfComparisonProcessing() {
        return rwtfComparisonProcessing;
    }

    public void setRwtfComparisonProcessing(String rwtfComparisonProcessing) {
        this.rwtfComparisonProcessing = rwtfComparisonProcessing;
    }

    public Integer getRwtfComparisonPercentage() {
        return rwtfComparisonPercentage;
    }

    public void setRwtfComparisonPercentage(Integer rwtfComparisonPercentage) {
        this.rwtfComparisonPercentage = rwtfComparisonPercentage;
    }

    public String getRwtfComparator() {
        return rwtfComparator;
    }

    public void setRwtfComparator(String rwtfComparator) {
        this.rwtfComparator = rwtfComparator;
    }

    public String getRwtfAssociationTable() {
        return rwtfAssociationTable;
    }

    public void setRwtfAssociationTable(String rwtfAssociationTable) {
        this.rwtfAssociationTable = rwtfAssociationTable;
    }

    public String getRwtfComparedValue() {
        return rwtfComparedValue;
    }

    public void setRwtfComparedValue(String rwtfComparedValue) {
        this.rwtfComparedValue = rwtfComparedValue;
    }

    public String getRwtfComparedProcessingCover() {
        return rwtfComparedProcessingCover;
    }

    public void setRwtfComparedProcessingCover(String rwtfComparedProcessingCover) {
        this.rwtfComparedProcessingCover = rwtfComparedProcessingCover;
    }

    public Integer getRwtfComparisonPercentageCover() {
        return rwtfComparisonPercentageCover;
    }

    public void setRwtfComparisonPercentageCover(Integer rwtfComparisonPercentageCover) {
        this.rwtfComparisonPercentageCover = rwtfComparisonPercentageCover;
    }

    public Date getRwtfCreateTime() {
        return rwtfCreateTime;
    }

    public void setRwtfCreateTime(Date rwtfCreateTime) {
        this.rwtfCreateTime = rwtfCreateTime;
    }
}
