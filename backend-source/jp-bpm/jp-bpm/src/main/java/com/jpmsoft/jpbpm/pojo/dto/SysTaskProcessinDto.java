package com.jpmsoft.jpbpm.pojo.dto;

import com.jpmsoft.common.model.BaseModeRequest;

import java.io.Serializable;

public class SysTaskProcessinDto extends BaseModeRequest {
    private String tpName;
    private String pName;
    private String conName;
    private String secName;
    private String tpFileTitle;
    private String tpSendName;
    private String tpStarttime;
    private String tpEndtime;
    private String tpDoUserName;
    private String tpExplain;
    private String actName;
    private String flowName;
    private String tName;
    private String fileId;
    private String starTime;
    private String endTime;
    private String tCode;
    private String year;

    public String getTpName() {
        return tpName;
    }

    public void setTpName(String tpName) {
        this.tpName = tpName;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public String getConName() {
        return conName;
    }

    public void setConName(String conName) {
        this.conName = conName;
    }

    public String getSecName() {
        return secName;
    }

    public void setSecName(String secName) {
        this.secName = secName;
    }

    public String getTpFileTitle() {
        return tpFileTitle;
    }

    public void setTpFileTitle(String tpFileTitle) {
        this.tpFileTitle = tpFileTitle;
    }

    public String getTpSendName() {
        return tpSendName;
    }

    public void setTpSendName(String tpSendName) {
        this.tpSendName = tpSendName;
    }

    public String getTpStarttime() {
        return tpStarttime;
    }

    public void setTpStarttime(String tpStarttime) {
        this.tpStarttime = tpStarttime;
    }

    public String getTpEndtime() {
        return tpEndtime;
    }

    public void setTpEndtime(String tpEndtime) {
        this.tpEndtime = tpEndtime;
    }

    public String getTpDoUserName() {
        return tpDoUserName;
    }

    public void setTpDoUserName(String tpDoUserName) {
        this.tpDoUserName = tpDoUserName;
    }

    public String getTpExplain() {
        return tpExplain;
    }

    public void setTpExplain(String tpExplain) {
        this.tpExplain = tpExplain;
    }

    public String getActName() {
        return actName;
    }

    public void setActName(String actName) {
        this.actName = actName;
    }

    public String getFlowName() {
        return flowName;
    }

    public void setFlowName(String flowName) {
        this.flowName = flowName;
    }

    public String gettName() {
        return tName;
    }

    public void settName(String tName) {
        this.tName = tName;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
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
