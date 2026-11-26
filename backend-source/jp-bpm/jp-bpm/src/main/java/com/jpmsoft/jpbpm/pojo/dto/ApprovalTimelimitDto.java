package com.jpmsoft.jpbpm.pojo.dto;

import com.jpmsoft.common.model.BaseModeRequest;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class ApprovalTimelimitDto extends BaseModeRequest {
    public List<String> tpTypes;
    public String workDay;
    public String workTimeType;
    public String workTimeStar;
    public String workTimeEnd;
    public Integer overtime;
    public List<String> dIds;
    public String uType;
    public String uName;
    public String starTime;
    public String endTime;
    public String completeEndTime;
    public String completeStarTime;
    public String userAccount;
    public String  tptFileCode;

    public List<String> getTpTypes() {
        return tpTypes;
    }

    public void setTpTypes(List<String> tpTypes) {
        this.tpTypes = tpTypes;
    }

    public String getWorkDay() {
        return workDay;
    }

    public void setWorkDay(String workDay) {
        this.workDay = workDay;
    }

    public String getWorkTimeType() {
        return workTimeType;
    }

    public void setWorkTimeType(String workTimeType) {
        this.workTimeType = workTimeType;
    }

    public String getWorkTimeStar() {
        return workTimeStar;
    }

    public void setWorkTimeStar(String workTimeStar) {
        this.workTimeStar = workTimeStar;
    }

    public String getWorkTimeEnd() {
        return workTimeEnd;
    }

    public void setWorkTimeEnd(String workTimeEnd) {
        this.workTimeEnd = workTimeEnd;
    }

    public Integer getOvertime() {
        return overtime;
    }

    public void setOvertime(Integer overtime) {
        this.overtime = overtime;
    }

    public List<String> getdIds() {
        return dIds;
    }

    public void setdIds(List<String> dIds) {
        this.dIds = dIds;
    }

    public String getuType() {
        return uType;
    }

    public void setuType(String uType) {
        this.uType = uType;
    }

    public String getuName() {
        return uName;
    }

    public void setuName(String uName) {
        this.uName = uName;
    }

    public String getStarTime() {
        return starTime;
    }

    public void setStarTime(String starTime) {
        this.starTime = starTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getCompleteEndTime() {
        return completeEndTime;
    }

    public void setCompleteEndTime(String completeEndTime) {
        this.completeEndTime = completeEndTime;
    }

    public String getCompleteStarTime() {
        return completeStarTime;
    }

    public void setCompleteStarTime(String completeStarTime) {
        this.completeStarTime = completeStarTime;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public String getTptFileCode() {
        return tptFileCode;
    }

    public void setTptFileCode(String tptFileCode) {
        this.tptFileCode = tptFileCode;
    }
}
