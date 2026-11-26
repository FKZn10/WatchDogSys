package com.jpmsoft.common.model.requestAndResponse.Archive;


import com.jpmsoft.common.base.ProcessFields;
import com.jpmsoft.common.enums.ProcessFieldEnum;
import com.jpmsoft.common.model.ProjectCommonRes;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 档案备案申请
 */

public class ArchiveFilingApplyModel extends ProjectCommonRes implements Serializable {

    private static final long serialVersionUID = -526760329167530939L;
    @ProcessFields(type = ProcessFieldEnum.id)
    private String faId;
    private String pId;
    private String conId;
    private String faType;
    private String faLocation;
    private String faSourceFunds;
    private String faManageUnitId;
    private String faPmId;
    private String faPmName;
    private String faAgentConstructionUnitName;
    private String faConstructionUnitName;
    private String faSurveyUnitName;
    private String faDesignUnitName;
    private String faConsultUnitName;
    private String faSupervisorUnitName;
    private BigDecimal faConstructionContractPrice;
    private BigDecimal faConstructionSettlementPrice;
    private Date faStartDate;
    private Date faEndDate;
    private String faRemark;
    @ProcessFields(type = ProcessFieldEnum.createUid)
    private String faCreateUid;
    @ProcessFields(type = ProcessFieldEnum.createUname)
    private String faCreateUname;
    @ProcessFields(type = ProcessFieldEnum.createTime)
    private Date faCreateTime;
    @ProcessFields(type = ProcessFieldEnum.state)
    private Integer faState;
    @ProcessFields(type = ProcessFieldEnum.stateTime)
    private Date faStateTime;

    private String conName;
    private String faManageUnitName;
    private String faManageUnitNameHistory;
    private String faManageUnitAbbName;

    public String getFaId() {
        return faId;
    }

    public void setFaId(String faId) {
        this.faId = faId;
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

    public String getFaType() {
        return faType;
    }

    public void setFaType(String faType) {
        this.faType = faType;
    }

    public String getFaLocation() {
        return faLocation;
    }

    public void setFaLocation(String faLocation) {
        this.faLocation = faLocation;
    }

    public String getFaSourceFunds() {
        return faSourceFunds;
    }

    public void setFaSourceFunds(String faSourceFunds) {
        this.faSourceFunds = faSourceFunds;
    }

    public String getFaManageUnitId() {
        return faManageUnitId;
    }

    public void setFaManageUnitId(String faManageUnitId) {
        this.faManageUnitId = faManageUnitId;
    }

    public String getFaPmId() {
        return faPmId;
    }

    public void setFaPmId(String faPmId) {
        this.faPmId = faPmId;
    }

    public String getFaPmName() {
        return faPmName;
    }

    public void setFaPmName(String faPmName) {
        this.faPmName = faPmName;
    }

    public String getFaAgentConstructionUnitName() {
        return faAgentConstructionUnitName;
    }

    public void setFaAgentConstructionUnitName(String faAgentConstructionUnitName) {
        this.faAgentConstructionUnitName = faAgentConstructionUnitName;
    }

    public String getFaConstructionUnitName() {
        return faConstructionUnitName;
    }

    public void setFaConstructionUnitName(String faConstructionUnitName) {
        this.faConstructionUnitName = faConstructionUnitName;
    }

    public String getFaSurveyUnitName() {
        return faSurveyUnitName;
    }

    public void setFaSurveyUnitName(String faSurveyUnitName) {
        this.faSurveyUnitName = faSurveyUnitName;
    }

    public String getFaDesignUnitName() {
        return faDesignUnitName;
    }

    public void setFaDesignUnitName(String faDesignUnitName) {
        this.faDesignUnitName = faDesignUnitName;
    }

    public String getFaConsultUnitName() {
        return faConsultUnitName;
    }

    public void setFaConsultUnitName(String faConsultUnitName) {
        this.faConsultUnitName = faConsultUnitName;
    }

    public String getFaSupervisorUnitName() {
        return faSupervisorUnitName;
    }

    public void setFaSupervisorUnitName(String faSupervisorUnitName) {
        this.faSupervisorUnitName = faSupervisorUnitName;
    }

    public BigDecimal getFaConstructionContractPrice() {
        return faConstructionContractPrice;
    }

    public void setFaConstructionContractPrice(BigDecimal faConstructionContractPrice) {
        this.faConstructionContractPrice = faConstructionContractPrice;
    }

    public BigDecimal getFaConstructionSettlementPrice() {
        return faConstructionSettlementPrice;
    }

    public void setFaConstructionSettlementPrice(BigDecimal faConstructionSettlementPrice) {
        this.faConstructionSettlementPrice = faConstructionSettlementPrice;
    }

    public Date getFaStartDate() {
        return faStartDate;
    }

    public void setFaStartDate(Date faStartDate) {
        this.faStartDate = faStartDate;
    }

    public Date getFaEndDate() {
        return faEndDate;
    }

    public void setFaEndDate(Date faEndDate) {
        this.faEndDate = faEndDate;
    }

    public String getFaRemark() {
        return faRemark;
    }

    public void setFaRemark(String faRemark) {
        this.faRemark = faRemark;
    }

    public String getFaCreateUid() {
        return faCreateUid;
    }

    public void setFaCreateUid(String faCreateUid) {
        this.faCreateUid = faCreateUid;
    }

    public String getFaCreateUname() {
        return faCreateUname;
    }

    public void setFaCreateUname(String faCreateUname) {
        this.faCreateUname = faCreateUname;
    }

    public Date getFaCreateTime() {
        return faCreateTime;
    }

    public void setFaCreateTime(Date faCreateTime) {
        this.faCreateTime = faCreateTime;
    }

    public Integer getFaState() {
        return faState;
    }

    public void setFaState(Integer faState) {
        this.faState = faState;
    }

    public Date getFaStateTime() {
        return faStateTime;
    }

    public void setFaStateTime(Date faStateTime) {
        this.faStateTime = faStateTime;
    }

    public String getConName() {
        return conName;
    }

    public void setConName(String conName) {
        this.conName = conName;
    }

    public String getFaManageUnitName() {
        return faManageUnitName;
    }

    public void setFaManageUnitName(String faManageUnitName) {
        this.faManageUnitName = faManageUnitName;
    }

    public String getFaManageUnitNameHistory() {
        return faManageUnitNameHistory;
    }

    public void setFaManageUnitNameHistory(String faManageUnitNameHistory) {
        this.faManageUnitNameHistory = faManageUnitNameHistory;
    }

    public String getFaManageUnitAbbName() {
        return faManageUnitAbbName;
    }

    public void setFaManageUnitAbbName(String faManageUnitAbbName) {
        this.faManageUnitAbbName = faManageUnitAbbName;
    }
}
