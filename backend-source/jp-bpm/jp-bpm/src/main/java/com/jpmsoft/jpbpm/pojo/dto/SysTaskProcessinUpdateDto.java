package com.jpmsoft.jpbpm.pojo.dto;

import java.util.Date;

public class SysTaskProcessinUpdateDto {
    public String tpId;
    public String tpUser;
    public String tpactid;
    public String actName;
    public Date tpDotime;
    public String  tpExplain;
    public String tpType;
    public Date tpStarttime;
    public String tpResult;
    public String tCode;
    public String year;


    public String getTpId() {
        return tpId;
    }

    public void setTpId(String tpId) {
        this.tpId = tpId;
    }

    public String getTpUser() {
        return tpUser;
    }

    public void setTpUser(String tpUser) {
        this.tpUser = tpUser;
    }

    public String getTpactid() {
        return tpactid;
    }

    public void setTpactid(String tpactid) {
        this.tpactid = tpactid;
    }

    public String getActName() {
        return actName;
    }

    public void setActName(String actName) {
        this.actName = actName;
    }

    public Date getTpDotime() {
        return tpDotime;
    }

    public void setTpDotime(Date tpDotime) {
        this.tpDotime = tpDotime;
    }

    public String getTpExplain() {
        return tpExplain;
    }

    public void setTpExplain(String tpExplain) {
        this.tpExplain = tpExplain;
    }

    public String getTpType() {
        return tpType;
    }

    public void setTpType(String tpType) {
        this.tpType = tpType;
    }

    public Date getTpStarttime() {
        return tpStarttime;
    }

    public void setTpStarttime(Date tpStarttime) {
        this.tpStarttime = tpStarttime;
    }

    public String getTpResult() {
        return tpResult;
    }

    public void setTpResult(String tpResult) {
        this.tpResult = tpResult;
    }

    public String gettCode() {
        return tCode;
    }

    public void settCode(String tCode) {
        this.tCode = tCode;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }
}
