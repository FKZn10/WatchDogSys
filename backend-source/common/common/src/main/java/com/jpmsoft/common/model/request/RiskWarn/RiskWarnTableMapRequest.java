package com.jpmsoft.common.model.request.RiskWarn;

import java.util.Date;

public class RiskWarnTableMapRequest {
    private String rwtmId;
    private String rwiId;
    private String rwtmField;
    private String rwtmType;
    private String rwtmMappingValue;
    private Date rwtmCreateTime;

    public String getRwtmId() {
        return rwtmId;
    }

    public void setRwtmId(String rwtmId) {
        this.rwtmId = rwtmId;
    }

    public String getRwiId() {
        return rwiId;
    }

    public void setRwiId(String rwiId) {
        this.rwiId = rwiId;
    }

    public String getRwtmField() {
        return rwtmField;
    }

    public void setRwtmField(String rwtmField) {
        this.rwtmField = rwtmField;
    }

    public String getRwtmType() {
        return rwtmType;
    }

    public void setRwtmType(String rwtmType) {
        this.rwtmType = rwtmType;
    }

    public String getRwtmMappingValue() {
        return rwtmMappingValue;
    }

    public void setRwtmMappingValue(String rwtmMappingValue) {
        this.rwtmMappingValue = rwtmMappingValue;
    }

    public Date getRwtmCreateTime() {
        return rwtmCreateTime;
    }

    public void setRwtmCreateTime(Date rwtmCreateTime) {
        this.rwtmCreateTime = rwtmCreateTime;
    }
}
