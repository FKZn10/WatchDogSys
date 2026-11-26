package com.jpmsoft.common.model.response.DesignChange;


import com.jpmsoft.common.model.ProjectCommonRes;

import java.util.Date;

/**
 * 现场签证工程量核实
 */
public class ConSvVerifyResponse extends ProjectCommonRes {

    private String svvId;
    private String svisId;
    private String pId;
    private String conId;
    private String svvCode;
    private String svvProjectName;
    private String svvContractorId;
    private Date svvDate;
    private String svvRemark;
    private String svvCreateUid;
    private String svvCreateUname;
    private Date svvCreateTime;
    private Integer svvState;
    private Date svvStateTime;

    private String conName;
    private String svvContractorName;
    private String svvContractorNameHistory;
    private String svisProjectName;
    private String svisName;
    //单位简称
    private String svvContrAbbName;

    public String getSvvId() {
        return svvId;
    }

    public void setSvvId(String svvId) {
        this.svvId = svvId;
    }

    public String getSvisId() {
        return svisId;
    }

    public void setSvisId(String svisId) {
        this.svisId = svisId;
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

    public String getSvvCode() {
        return svvCode;
    }

    public void setSvvCode(String svvCode) {
        this.svvCode = svvCode;
    }

    public String getSvvProjectName() {
        return svvProjectName;
    }

    public void setSvvProjectName(String svvProjectName) {
        this.svvProjectName = svvProjectName;
    }

    public String getSvvContractorId() {
        return svvContractorId;
    }

    public void setSvvContractorId(String svvContractorId) {
        this.svvContractorId = svvContractorId;
    }

    public Date getSvvDate() {
        return svvDate;
    }

    public void setSvvDate(Date svvDate) {
        this.svvDate = svvDate;
    }

    public String getSvvRemark() {
        return svvRemark;
    }

    public void setSvvRemark(String svvRemark) {
        this.svvRemark = svvRemark;
    }

    public String getSvvCreateUid() {
        return svvCreateUid;
    }

    public void setSvvCreateUid(String svvCreateUid) {
        this.svvCreateUid = svvCreateUid;
    }

    public String getSvvCreateUname() {
        return svvCreateUname;
    }

    public void setSvvCreateUname(String svvCreateUname) {
        this.svvCreateUname = svvCreateUname;
    }

    public Date getSvvCreateTime() {
        return svvCreateTime;
    }

    public void setSvvCreateTime(Date svvCreateTime) {
        this.svvCreateTime = svvCreateTime;
    }

    public Integer getSvvState() {
        return svvState;
    }

    public void setSvvState(Integer svvState) {
        this.svvState = svvState;
    }

    public Date getSvvStateTime() {
        return svvStateTime;
    }

    public void setSvvStateTime(Date svvStateTime) {
        this.svvStateTime = svvStateTime;
    }

    public String getConName() {
        return conName;
    }

    public void setConName(String conName) {
        this.conName = conName;
    }

    public String getSvvContractorName() {
        return svvContractorName;
    }

    public void setSvvContractorName(String svvContractorName) {
        this.svvContractorName = svvContractorName;
    }

    public String getSvvContractorNameHistory() {
        return svvContractorNameHistory;
    }

    public void setSvvContractorNameHistory(String svvContractorNameHistory) {
        this.svvContractorNameHistory = svvContractorNameHistory;
    }

    public String getSvisProjectName() {
        return svisProjectName;
    }

    public void setSvisProjectName(String svisProjectName) {
        this.svisProjectName = svisProjectName;
    }

    public String getSvisName() {
        return svisName;
    }

    public void setSvisName(String svisName) {
        this.svisName = svisName;
    }

    public String getSvvContrAbbName() {
        return svvContrAbbName;
    }

    public void setSvvContrAbbName(String svvContrAbbName) {
        this.svvContrAbbName = svvContrAbbName;
    }
}
