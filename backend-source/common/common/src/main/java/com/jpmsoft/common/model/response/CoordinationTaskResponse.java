package com.jpmsoft.common.model.response;

import java.util.Date;

public class CoordinationTaskResponse {

    private String ctId;
    private String ctName;
    private String ctUser;
    private String ctSenduser;
    private Date ctTime;
    private String ctTaskid;
    private String ctActid;
    private String ctFlowid;
    private String ctFileid;
    private String ctCountersign;
    private String ctActname;
    /*******************/
    private String ctFileTitle;
    private String ctSendName;
    private String ctUserName;
    private String ctTaskName;
    private String ctFlowName;

    private String pName;
    private String secName;
    private String conName;
    private String tCode;

    public String getCtUserName() {
        return ctUserName;
    }

    public void setCtUserName(String ctUserName) {
        this.ctUserName = ctUserName;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public String getSecName() {
        return secName;
    }

    public void setSecName(String secName) {
        this.secName = secName;
    }

    public String getConName() {
        return conName;
    }

    public void setConName(String conName) {
        this.conName = conName;
    }

    public String getCtId() {
        return ctId;
    }

    public void setCtId(String ctId) {
        this.ctId = ctId;
    }

    public String getCtName() {
        return ctName;
    }

    public void setCtName(String ctName) {
        this.ctName = ctName;
    }

    public String getCtUser() {
        return ctUser;
    }

    public void setCtUser(String ctUser) {
        this.ctUser = ctUser;
    }

    public String getCtSenduser() {
        return ctSenduser;
    }

    public void setCtSenduser(String ctSenduser) {
        this.ctSenduser = ctSenduser;
    }

    public Date getCtTime() {
        return ctTime;
    }

    public void setCtTime(Date ctTime) {
        this.ctTime = ctTime;
    }

    public String getCtTaskid() {
        return ctTaskid;
    }

    public void setCtTaskid(String ctTaskid) {
        this.ctTaskid = ctTaskid;
    }

    public String getCtActid() {
        return ctActid;
    }

    public void setCtActid(String ctActid) {
        this.ctActid = ctActid;
    }

    public String getCtFlowid() {
        return ctFlowid;
    }

    public void setCtFlowid(String ctFlowid) {
        this.ctFlowid = ctFlowid;
    }

    public String getCtFileid() {
        return ctFileid;
    }

    public void setCtFileid(String ctFileid) {
        this.ctFileid = ctFileid;
    }

    public String getCtCountersign() {
        return ctCountersign;
    }

    public void setCtCountersign(String ctCountersign) {
        this.ctCountersign = ctCountersign;
    }

    public String getCtActname() {
        return ctActname;
    }

    public void setCtActname(String ctActname) {
        this.ctActname = ctActname;
    }

    public String getCtFileTitle() {
        return ctFileTitle;
    }

    public void setCtFileTitle(String ctFileTitle) {
        this.ctFileTitle = ctFileTitle;
    }

    public String getCtSendName() {
        return ctSendName;
    }

    public void setCtSendName(String ctSendName) {
        this.ctSendName = ctSendName;
    }

    public String getCtTaskName() {
        return ctTaskName;
    }

    public void setCtTaskName(String ctTaskName) {
        this.ctTaskName = ctTaskName;
    }

    public String getCtFlowName() {
        return ctFlowName;
    }

    public void setCtFlowName(String ctFlowName) {
        this.ctFlowName = ctFlowName;
    }

    public String gettCode() {
        return tCode;
    }

    public void settCode(String tCode) {
        this.tCode = tCode;
    }
}
