package com.jpmsoft.common.model.request.Maintenance;

import com.jpmsoft.common.model.BaseFileRequest;

import java.util.Date;

/**
 * 维保联系人
 */
public class MaintenanceContactRequest extends BaseFileRequest {
    private String mcId;
    private String pId;
    private String secId;
    private String conId;
    private String mcCId;
    private String mcUnitType;
    private String mcUnitLeader;
    private String mcUnitLeaderTel;
    private String mcSiteLeader;
    private String mcSiteLeaderTel;
    private String mcCreateUid;
    private String mcCreateUname;
    private Date mcCreateTime;
    private Integer mcState;
    private Date mcStateTime;

    private String pName;
    private String conName;


    public String getMcId() {
        return mcId;
    }

    public void setMcId(String mcId) {
        this.mcId = mcId;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getSecId() {
        return secId;
    }

    public void setSecId(String secId) {
        this.secId = secId;
    }

    public String getConId() {
        return conId;
    }

    public void setConId(String conId) {
        this.conId = conId;
    }

    public String getMcCId() {
        return mcCId;
    }

    public void setMcCId(String mcCId) {
        this.mcCId = mcCId;
    }

    public String getMcUnitType() {
        return mcUnitType;
    }

    public void setMcUnitType(String mcUnitType) {
        this.mcUnitType = mcUnitType;
    }

    public String getMcUnitLeader() {
        return mcUnitLeader;
    }

    public void setMcUnitLeader(String mcUnitLeader) {
        this.mcUnitLeader = mcUnitLeader;
    }

    public String getMcUnitLeaderTel() {
        return mcUnitLeaderTel;
    }

    public void setMcUnitLeaderTel(String mcUnitLeaderTel) {
        this.mcUnitLeaderTel = mcUnitLeaderTel;
    }

    public String getMcSiteLeader() {
        return mcSiteLeader;
    }

    public void setMcSiteLeader(String mcSiteLeader) {
        this.mcSiteLeader = mcSiteLeader;
    }

    public String getMcSiteLeaderTel() {
        return mcSiteLeaderTel;
    }

    public void setMcSiteLeaderTel(String mcSiteLeaderTel) {
        this.mcSiteLeaderTel = mcSiteLeaderTel;
    }

    public String getMcCreateUid() {
        return mcCreateUid;
    }

    public void setMcCreateUid(String mcCreateUid) {
        this.mcCreateUid = mcCreateUid;
    }

    public String getMcCreateUname() {
        return mcCreateUname;
    }

    public void setMcCreateUname(String mcCreateUname) {
        this.mcCreateUname = mcCreateUname;
    }

    public Date getMcCreateTime() {
        return mcCreateTime;
    }

    public void setMcCreateTime(Date mcCreateTime) {
        this.mcCreateTime = mcCreateTime;
    }

    public Integer getMcState() {
        return mcState;
    }

    public void setMcState(Integer mcState) {
        this.mcState = mcState;
    }

    public Date getMcStateTime() {
        return mcStateTime;
    }

    public void setMcStateTime(Date mcStateTime) {
        this.mcStateTime = mcStateTime;
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
