package com.jpmsoft.common.model.request.DesignChange;


import com.jpmsoft.common.model.BaseFileRequest;

import java.util.Date;

/**
 * 工程量核实
 */
public class ConEnVerifyRequest extends BaseFileRequest {

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

    private String pName;
    private String conName;

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
}
