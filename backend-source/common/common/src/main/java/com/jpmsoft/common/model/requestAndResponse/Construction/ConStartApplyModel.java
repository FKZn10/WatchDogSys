package com.jpmsoft.common.model.requestAndResponse.Construction;

import com.jpmsoft.common.base.ProcessFields;
import com.jpmsoft.common.enums.ProcessFieldEnum;
import com.jpmsoft.common.model.ProjectCommonRes;

import java.util.Date;

/**
 * 开工报审
 */
public class ConStartApplyModel extends ProjectCommonRes {

    @ProcessFields(type = ProcessFieldEnum.id)
    private String saId;
    private String pId;
    private String saContractorId;
    private String saSupervisorId;
    private String saRemark;
    @ProcessFields(type = ProcessFieldEnum.createUid)
    private String saCreateUid;
    @ProcessFields(type = ProcessFieldEnum.createUname)
    private String saCreateUname;
    @ProcessFields(type = ProcessFieldEnum.createTime)
    private Date saCreateTime;
    @ProcessFields(type = ProcessFieldEnum.state)
    private Integer saState;
    @ProcessFields(type = ProcessFieldEnum.stateTime)
    private Date saStateTime;
    private String saTitle;

    private String saContractorName;
    private String saSupervisorName;
    private String saContractorNameHistory;
    private String saSupervisorNameHistory;
    private String exContractorAbbName;
    private String exSupervisorAbbName;
    private Date saDate;

    public Date getSaDate() {
        return saDate;
    }

    public void setSaDate(Date saDate) {
        this.saDate = saDate;
    }

    public String getSaId() {
        return saId;
    }

    public void setSaId(String saId) {
        this.saId = saId;
    }

    @Override
    public String getpId() {
        return pId;
    }

    @Override
    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getSaContractorId() {
        return saContractorId;
    }

    public void setSaContractorId(String saContractorId) {
        this.saContractorId = saContractorId;
    }

    public String getSaSupervisorId() {
        return saSupervisorId;
    }

    public void setSaSupervisorId(String saSupervisorId) {
        this.saSupervisorId = saSupervisorId;
    }

    public String getSaRemark() {
        return saRemark;
    }

    public void setSaRemark(String saRemark) {
        this.saRemark = saRemark;
    }

    public String getSaCreateUid() {
        return saCreateUid;
    }

    public void setSaCreateUid(String saCreateUid) {
        this.saCreateUid = saCreateUid;
    }

    public String getSaCreateUname() {
        return saCreateUname;
    }

    public void setSaCreateUname(String saCreateUname) {
        this.saCreateUname = saCreateUname;
    }

    public Date getSaCreateTime() {
        return saCreateTime;
    }

    public void setSaCreateTime(Date saCreateTime) {
        this.saCreateTime = saCreateTime;
    }

    public Integer getSaState() {
        return saState;
    }

    public void setSaState(Integer saState) {
        this.saState = saState;
    }

    public Date getSaStateTime() {
        return saStateTime;
    }

    public void setSaStateTime(Date saStateTime) {
        this.saStateTime = saStateTime;
    }

    public String getSaTitle() {
        return saTitle;
    }

    public void setSaTitle(String saTitle) {
        this.saTitle = saTitle;
    }

    public String getSaContractorName() {
        return saContractorName;
    }

    public void setSaContractorName(String saContractorName) {
        this.saContractorName = saContractorName;
    }

    public String getSaSupervisorName() {
        return saSupervisorName;
    }

    public void setSaSupervisorName(String saSupervisorName) {
        this.saSupervisorName = saSupervisorName;
    }

    public String getSaContractorNameHistory() {
        return saContractorNameHistory;
    }

    public void setSaContractorNameHistory(String saContractorNameHistory) {
        this.saContractorNameHistory = saContractorNameHistory;
    }

    public String getSaSupervisorNameHistory() {
        return saSupervisorNameHistory;
    }

    public void setSaSupervisorNameHistory(String saSupervisorNameHistory) {
        this.saSupervisorNameHistory = saSupervisorNameHistory;
    }

    public String getExContractorAbbName() {
        return exContractorAbbName;
    }

    public void setExContractorAbbName(String exContractorAbbName) {
        this.exContractorAbbName = exContractorAbbName;
    }

    public String getExSupervisorAbbName() {
        return exSupervisorAbbName;
    }

    public void setExSupervisorAbbName(String exSupervisorAbbName) {
        this.exSupervisorAbbName = exSupervisorAbbName;
    }
}
