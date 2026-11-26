package com.jpmsoft.common.model.requestAndResponse.Construction;


import com.jpmsoft.common.base.ProcessFields;
import com.jpmsoft.common.enums.ProcessFieldEnum;
import com.jpmsoft.common.model.ProjectCommonRes;

import java.util.Date;

/**
 * 施工（准备）审核
 */
public class ConExamineModel extends ProjectCommonRes {

    @ProcessFields(type = ProcessFieldEnum.id)
    private String exId;
    private String pId;
    private String exContractorId;
    private String exSupervisorId;
    private String exRemark;
    @ProcessFields(type = ProcessFieldEnum.createUid)
    private String exCreateUid;
    @ProcessFields(type = ProcessFieldEnum.createUname)
    private String exCreateUname;
    @ProcessFields(type = ProcessFieldEnum.createTime)
    private Date exCreateTime;
    @ProcessFields(type = ProcessFieldEnum.state)
    private Integer exState;
    @ProcessFields(type = ProcessFieldEnum.stateTime)
    private Date exStateTime;
    private String exTitle;

    private String exContractorName;
    private String exSupervisorName;
    private String exContractorNameHistory;
    private String exSupervisorNameHistory;
    private String exContractorAbbName;
    private String exSupervisorAbbName;

    public String getExId() {
        return exId;
    }

    public void setExId(String exId) {
        this.exId = exId;
    }

    @Override
    public String getpId() {
        return pId;
    }

    @Override
    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getExContractorId() {
        return exContractorId;
    }

    public void setExContractorId(String exContractorId) {
        this.exContractorId = exContractorId;
    }

    public String getExSupervisorId() {
        return exSupervisorId;
    }

    public void setExSupervisorId(String exSupervisorId) {
        this.exSupervisorId = exSupervisorId;
    }

    public String getExRemark() {
        return exRemark;
    }

    public void setExRemark(String exRemark) {
        this.exRemark = exRemark;
    }

    public String getExCreateUid() {
        return exCreateUid;
    }

    public void setExCreateUid(String exCreateUid) {
        this.exCreateUid = exCreateUid;
    }

    public String getExCreateUname() {
        return exCreateUname;
    }

    public void setExCreateUname(String exCreateUname) {
        this.exCreateUname = exCreateUname;
    }

    public Date getExCreateTime() {
        return exCreateTime;
    }

    public void setExCreateTime(Date exCreateTime) {
        this.exCreateTime = exCreateTime;
    }

    public Integer getExState() {
        return exState;
    }

    public void setExState(Integer exState) {
        this.exState = exState;
    }

    public Date getExStateTime() {
        return exStateTime;
    }

    public void setExStateTime(Date exStateTime) {
        this.exStateTime = exStateTime;
    }

    public String getExTitle() {
        return exTitle;
    }

    public void setExTitle(String exTitle) {
        this.exTitle = exTitle;
    }


    public String getExContractorName() {
        return exContractorName;
    }

    public void setExContractorName(String exContractorName) {
        this.exContractorName = exContractorName;
    }

    public String getExSupervisorName() {
        return exSupervisorName;
    }

    public void setExSupervisorName(String exSupervisorName) {
        this.exSupervisorName = exSupervisorName;
    }

    public String getExContractorNameHistory() {
        return exContractorNameHistory;
    }

    public void setExContractorNameHistory(String exContractorNameHistory) {
        this.exContractorNameHistory = exContractorNameHistory;
    }

    public String getExSupervisorNameHistory() {
        return exSupervisorNameHistory;
    }

    public void setExSupervisorNameHistory(String exSupervisorNameHistory) {
        this.exSupervisorNameHistory = exSupervisorNameHistory;
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
