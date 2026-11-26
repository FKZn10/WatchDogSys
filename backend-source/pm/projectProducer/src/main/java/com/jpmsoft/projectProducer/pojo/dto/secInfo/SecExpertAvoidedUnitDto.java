package com.jpmsoft.projectProducer.pojo.dto.secInfo;

/**
 * 配置需要回避的单位
 */
public class SecExpertAvoidedUnitDto {

    //申请单位
    private String unit;

    //需要回避的单位（申请单位是unit值时）
    private String avoidedUnit;

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getAvoidedUnit() {
        return avoidedUnit;
    }

    public void setAvoidedUnit(String avoidedUnit) {
        this.avoidedUnit = avoidedUnit;
    }
}
