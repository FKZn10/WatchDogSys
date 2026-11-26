package com.jpmsoft.common.model.requestAndResponse.Design;

import com.jpmsoft.common.base.ProcessFields;
import com.jpmsoft.common.enums.ProcessFieldEnum;
import com.jpmsoft.common.model.ProjectCommonRes;

import java.util.Date;

/**
 * 初步设计
 */
public class DesignGeologyModel extends ProjectCommonRes {
    @ProcessFields(type = ProcessFieldEnum.id)
    private String dgId;
    private String pId;
    private String conId;
    private String secId;
    private String dgSurveyContent;
    private Date dgStart;
    private Date dgEnd;
    @ProcessFields(type = ProcessFieldEnum.state)
    private Integer State;
    @ProcessFields(type = ProcessFieldEnum.stateTime)
    private Date stateTime;
    @ProcessFields(type = ProcessFieldEnum.createTime)
    private Date dgCreateTime;
    @ProcessFields(type = ProcessFieldEnum.createUid)
    private String dgCreateUid;
    @ProcessFields(type = ProcessFieldEnum.createUname)
    private String dgCreateUname;
    private String dgName;
    private String dgCode;
    private String dgExplorationUnit;
    private Integer dgDesignOrder;
    private String  dgExplorationUnitTel;

    private String conName;
    private String conCode;
    private String conPartyBAgent;
    private String conPartyBName;
    private String secName;
    private String conPartyBId;
    //单位简称
    private String conPartyBAbbName;
    private String dgExplName;
    private String dgExplAbbName;
    private String conPartyBPm;


    public String getDgId() {
        return dgId;
    }

    public void setDgId(String dgId) {
        this.dgId = dgId;
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

    public String getSecId() {
        return secId;
    }

    public void setSecId(String secId) {
        this.secId = secId;
    }

    public String getDgSurveyContent() {
        return dgSurveyContent;
    }

    public void setDgSurveyContent(String dgSurveyContent) {
        this.dgSurveyContent = dgSurveyContent;
    }

    public Date getDgStart() {
        return dgStart;
    }

    public void setDgStart(Date dgStart) {
        this.dgStart = dgStart;
    }

    public Date getDgEnd() {
        return dgEnd;
    }

    public void setDgEnd(Date dgEnd) {
        this.dgEnd = dgEnd;
    }

    public Integer getState() {
        return State;
    }

    public void setState(Integer state) {
        State = state;
    }

    public Date getStateTime() {
        return stateTime;
    }

    public void setStateTime(Date stateTime) {
        this.stateTime = stateTime;
    }

    public Date getDgCreateTime() {
        return dgCreateTime;
    }

    public void setDgCreateTime(Date dgCreateTime) {
        this.dgCreateTime = dgCreateTime;
    }

    public String getDgCreateUid() {
        return dgCreateUid;
    }

    public void setDgCreateUid(String dgCreateUid) {
        this.dgCreateUid = dgCreateUid;
    }

    public String getDgCreateUname() {
        return dgCreateUname;
    }

    public void setDgCreateUname(String dgCreateUname) {
        this.dgCreateUname = dgCreateUname;
    }

    public String getDgName() {
        return dgName;
    }

    public void setDgName(String dgName) {
        this.dgName = dgName;
    }

    public String getDgCode() {
        return dgCode;
    }

    public void setDgCode(String dgCode) {
        this.dgCode = dgCode;
    }

    public String getDgExplorationUnit() {
        return dgExplorationUnit;
    }

    public void setDgExplorationUnit(String dgExplorationUnit) {
        this.dgExplorationUnit = dgExplorationUnit;
    }

    public Integer getDgDesignOrder() {
        return dgDesignOrder;
    }

    public void setDgDesignOrder(Integer dgDesignOrder) {
        this.dgDesignOrder = dgDesignOrder;
    }

    public String getDgExplorationUnitTel() {
        return dgExplorationUnitTel;
    }

    public void setDgExplorationUnitTel(String dgExplorationUnitTel) {
        this.dgExplorationUnitTel = dgExplorationUnitTel;
    }

    public String getConName() {
        return conName;
    }

    public void setConName(String conName) {
        this.conName = conName;
    }

    public String getConCode() {
        return conCode;
    }

    public void setConCode(String conCode) {
        this.conCode = conCode;
    }

    public String getConPartyBAgent() {
        return conPartyBAgent;
    }

    public void setConPartyBAgent(String conPartyBAgent) {
        this.conPartyBAgent = conPartyBAgent;
    }

    public String getConPartyBName() {
        return conPartyBName;
    }

    public void setConPartyBName(String conPartyBName) {
        this.conPartyBName = conPartyBName;
    }

    public String getSecName() {
        return secName;
    }

    public void setSecName(String secName) {
        this.secName = secName;
    }

    public String getConPartyBId() {
        return conPartyBId;
    }

    public void setConPartyBId(String conPartyBId) {
        this.conPartyBId = conPartyBId;
    }

    public String getConPartyBAbbName() {
        return conPartyBAbbName;
    }

    public void setConPartyBAbbName(String conPartyBAbbName) {
        this.conPartyBAbbName = conPartyBAbbName;
    }

    public String getDgExplName() {
        return dgExplName;
    }

    public void setDgExplName(String dgExplName) {
        this.dgExplName = dgExplName;
    }

    public String getDgExplAbbName() {
        return dgExplAbbName;
    }

    public void setDgExplAbbName(String dgExplAbbName) {
        this.dgExplAbbName = dgExplAbbName;
    }

    public String getConPartyBPm() {
        return conPartyBPm;
    }

    public void setConPartyBPm(String conPartyBPm) {
        this.conPartyBPm = conPartyBPm;
    }
}
