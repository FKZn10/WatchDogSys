package com.jpmsoft.jpbpm.pojo.vo;

import java.io.Serializable;
import java.math.BigDecimal;

public class ApprovalTimelimitVo implements Serializable {
    public String tptPDid;
    public String tptPDname;
    public String dId;
    public String dName;
    public String uName;
    public String userId;
    public String uType;
    public Integer totalTimes;
    public Integer tpCount;
    public Integer rejectCount;
    public BigDecimal rejectionRate;
    public Integer overtimeCount;
    public BigDecimal overtimeRate;
    public BigDecimal handlingTime;
    public String  userAccount;

    public String getdId() {
        return dId;
    }

    public void setdId(String dId) {
        this.dId = dId;
    }

    public String getdName() {
        return dName;
    }

    public void setdName(String dName) {
        this.dName = dName;
    }

    public String getuName() {
        return uName;
    }

    public void setuName(String uName) {
        this.uName = uName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getuType() {
        return uType;
    }

    public void setuType(String uType) {
        this.uType = uType;
    }

    public Integer getTotalTimes() {
        return totalTimes;
    }

    public void setTotalTimes(Integer totalTimes) {
        this.totalTimes = totalTimes;
    }

    public Integer getTpCount() {
        return tpCount;
    }

    public void setTpCount(Integer tpCount) {
        this.tpCount = tpCount;
    }

    public Integer getRejectCount() {
        return rejectCount;
    }

    public void setRejectCount(Integer rejectCount) {
        this.rejectCount = rejectCount;
    }

    public BigDecimal getRejectionRate() {
        return rejectionRate;
    }

    public void setRejectionRate(BigDecimal rejectionRate) {
        this.rejectionRate = rejectionRate;
    }

    public Integer getOvertimeCount() {
        return overtimeCount;
    }

    public void setOvertimeCount(Integer overtimeCount) {
        this.overtimeCount = overtimeCount;
    }

    public BigDecimal getOvertimeRate() {
        return overtimeRate;
    }

    public void setOvertimeRate(BigDecimal overtimeRate) {
        this.overtimeRate = overtimeRate;
    }

    public BigDecimal getHandlingTime() {
        return handlingTime;
    }

    public void setHandlingTime(BigDecimal handlingTime) {
        this.handlingTime = handlingTime;
    }

    public String getTptPDid() {
        return tptPDid;
    }

    public void setTptPDid(String tptPDid) {
        this.tptPDid = tptPDid;
    }

    public String getTptPDname() {
        return tptPDname;
    }

    public void setTptPDname(String tptPDname) {
        this.tptPDname = tptPDname;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }
}
