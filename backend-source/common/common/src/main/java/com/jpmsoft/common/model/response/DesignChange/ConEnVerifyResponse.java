package com.jpmsoft.common.model.response.DesignChange;


import com.jpmsoft.common.model.ProjectCommonRes;

import java.util.Date;

/**
 * 工程量核实
 */
public class ConEnVerifyResponse extends ProjectCommonRes {

    private String envId;
    private String enbId;
    private String pId;
    private String conId;
    private String envCode;
    private String envProjectName;
    private String envContractorId;
    private Date envDate;
    private String envRemark;
    private String envCreateUid;
    private String envCreateUname;
    private Date envCreateTime;
    private Integer envState;
    private Date envStateTime;
    private String enisId;

    private String enisProjectName;
    private String enisName;
    private String conName;
    private String envContractorName;
    private String enbContractorName;
    private String envContractorNameHistory;
    //单位简称
    private String envContrAbbName;
    private String enbContrAbbName;

    public String getEnvId() {
        return envId;
    }

    public void setEnvId(String envId) {
        this.envId = envId;
    }

    public String getEnbId() {
        return enbId;
    }

    public void setEnbId(String enbId) {
        this.enbId = enbId;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getConId() {
        return conId;
    }

    public void setConId(String conId) {
        this.conId = conId;
    }

    public String getEnvCode() {
        return envCode;
    }

    public void setEnvCode(String envCode) {
        this.envCode = envCode;
    }

    public String getEnvProjectName() {
        return envProjectName;
    }

    public void setEnvProjectName(String envProjectName) {
        this.envProjectName = envProjectName;
    }

    public String getEnvContractorId() {
        return envContractorId;
    }

    public void setEnvContractorId(String envContractorId) {
        this.envContractorId = envContractorId;
    }

    public Date getEnvDate() {
        return envDate;
    }

    public void setEnvDate(Date envDate) {
        this.envDate = envDate;
    }

    public String getEnvRemark() {
        return envRemark;
    }

    public void setEnvRemark(String envRemark) {
        this.envRemark = envRemark;
    }

    public String getEnvCreateUid() {
        return envCreateUid;
    }

    public void setEnvCreateUid(String envCreateUid) {
        this.envCreateUid = envCreateUid;
    }

    public String getEnvCreateUname() {
        return envCreateUname;
    }

    public void setEnvCreateUname(String envCreateUname) {
        this.envCreateUname = envCreateUname;
    }

    public Date getEnvCreateTime() {
        return envCreateTime;
    }

    public void setEnvCreateTime(Date envCreateTime) {
        this.envCreateTime = envCreateTime;
    }

    public Integer getEnvState() {
        return envState;
    }

    public void setEnvState(Integer envState) {
        this.envState = envState;
    }

    public Date getEnvStateTime() {
        return envStateTime;
    }

    public void setEnvStateTime(Date envStateTime) {
        this.envStateTime = envStateTime;
    }

    public String getEnisId() {
        return enisId;
    }

    public void setEnisId(String enisId) {
        this.enisId = enisId;
    }

    public String getEnisProjectName() {
        return enisProjectName;
    }

    public void setEnisProjectName(String enisProjectName) {
        this.enisProjectName = enisProjectName;
    }

    public String getEnisName() {
        return enisName;
    }

    public void setEnisName(String enisName) {
        this.enisName = enisName;
    }

    public String getConName() {
        return conName;
    }

    public void setConName(String conName) {
        this.conName = conName;
    }

    public String getEnvContractorName() {
        return envContractorName;
    }

    public void setEnvContractorName(String envContractorName) {
        this.envContractorName = envContractorName;
    }

    public String getEnbContractorName() {
        return enbContractorName;
    }

    public void setEnbContractorName(String enbContractorName) {
        this.enbContractorName = enbContractorName;
    }

    public String getEnvContractorNameHistory() {
        return envContractorNameHistory;
    }

    public void setEnvContractorNameHistory(String envContractorNameHistory) {
        this.envContractorNameHistory = envContractorNameHistory;
    }

    public String getEnvContrAbbName() {
        return envContrAbbName;
    }

    public void setEnvContrAbbName(String envContrAbbName) {
        this.envContrAbbName = envContrAbbName;
    }

    public String getEnbContrAbbName() {
        return enbContrAbbName;
    }

    public void setEnbContrAbbName(String enbContrAbbName) {
        this.enbContrAbbName = enbContrAbbName;
    }
}
