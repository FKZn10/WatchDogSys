package com.jpmsoft.jpbpm.pojo.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ApprovalDetailsVo implements Serializable {
    public String  dId;
    public String  dName;
    public String  uName;
    public String  tpName;
    public String   actname;
    public String   tpId ;
    public String  tpEntityid;
    public String  tpUser;
    public String  tpType;
    public String  tpStarttime;
    public String  tCode;
    public Date tpDotime;
    public BigDecimal handlingTime;

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

    public String getTpName() {
        return tpName;
    }

    public void setTpName(String tpName) {
        this.tpName = tpName;
    }

    public String getActname() {
        return actname;
    }

    public void setActname(String actname) {
        this.actname = actname;
    }

    public String getTpId() {
        return tpId;
    }

    public void setTpId(String tpId) {
        this.tpId = tpId;
    }

    public String getTpEntityid() {
        return tpEntityid;
    }

    public void setTpEntityid(String tpEntityid) {
        this.tpEntityid = tpEntityid;
    }

    public String getTpUser() {
        return tpUser;
    }

    public void setTpUser(String tpUser) {
        this.tpUser = tpUser;
    }

    public String getTpType() {
        return tpType;
    }

    public void setTpType(String tpType) {
        this.tpType = tpType;
    }

    public String getTpStarttime() {
        return tpStarttime;
    }

    public void setTpStarttime(String tpStarttime) {
        this.tpStarttime = tpStarttime;
    }

    public Date getTpDotime() {
        return tpDotime;
    }

    public void setTpDotime(Date tpDotime) {
        this.tpDotime = tpDotime;
    }

    public BigDecimal getHandlingTime() {
        return handlingTime;
    }

    public void setHandlingTime(BigDecimal handlingTime) {
        this.handlingTime = handlingTime;
    }

    public String gettCode() {
        return tCode;
    }

    public void settCode(String tCode) {
        this.tCode = tCode;
    }
}
