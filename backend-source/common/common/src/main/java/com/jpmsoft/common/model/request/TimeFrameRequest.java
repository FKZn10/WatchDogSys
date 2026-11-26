package com.jpmsoft.common.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jpmsoft.common.model.BaseModeRequest;

/**
 * 时间范围请求参数
 */

public class TimeFrameRequest extends BaseModeRequest {

    private String startTime;
    private String endTime;
    private String pType;
    private Integer state;
    private String pStatus;
    private String pProjectStage;
    private String pConstructionUnit;
    private String pCompetentDepartment;
    private String pRegion;
    private Integer cpdState;
    @JsonProperty("pName")
    private String pName;

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public Integer getCpdState() {
        return cpdState;
    }

    public void setCpdState(Integer cpdState) {
        this.cpdState = cpdState;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getpType() {
        return pType;
    }

    public void setpType(String pType) {
        this.pType = pType;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getpStatus() {
        return pStatus;
    }

    public void setpStatus(String pStatus) {
        this.pStatus = pStatus;
    }

    public String getpProjectStage() {
        return pProjectStage;
    }

    public void setpProjectStage(String pProjectStage) {
        this.pProjectStage = pProjectStage;
    }

    public String getpConstructionUnit() {
        return pConstructionUnit;
    }

    public void setpConstructionUnit(String pConstructionUnit) {
        this.pConstructionUnit = pConstructionUnit;
    }

    public String getpCompetentDepartment() {
        return pCompetentDepartment;
    }

    public void setpCompetentDepartment(String pCompetentDepartment) {
        this.pCompetentDepartment = pCompetentDepartment;
    }

    public String getpRegion() {
        return pRegion;
    }

    public void setpRegion(String pRegion) {
        this.pRegion = pRegion;
    }
}
