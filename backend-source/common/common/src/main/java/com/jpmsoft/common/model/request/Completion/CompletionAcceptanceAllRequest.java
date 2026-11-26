package com.jpmsoft.common.model.request.Completion;

import com.jpmsoft.common.base.FileFields;
import com.jpmsoft.common.base.ProcessFields;
import com.jpmsoft.common.enums.ProcessFieldEnum;
import com.jpmsoft.common.model.BaseFileRequest;

import java.util.Date;

/**
 * 竣工验收（合并版）
 */
public class CompletionAcceptanceAllRequest extends BaseFileRequest {
    @ProcessFields(type = ProcessFieldEnum.id)
    private String caaId;
    private String pId;
    private String conId;
    private String caaTitle;
    private String caaPleaderUid;
    private Date caaStaringTime;
    private Date caaEndTime;
    private String caaSunitId;
    private String caaDunitId;
    private String caaCcunitId;
    private String caaOneSunitUid;
    private String caaOneExplorationUid;
    private String caaOneDesignUid;
    private String caaOneConstructionUid;
    private String caaOneTechnology;
    private String caaOneRtechnology;
    private String caaOneProperty;
    private String caaOneSupervision;
    private String caaOneRemark;
    @FileFields(index = 1)
    private String caaOneFile;
    private String caaTwoPleaderUid;
    private String caaTwoSunitUid;
    private String caaTwoExplorationUid;
    private String caaTwoDesignUid;
    private String caaTwoConstructionUid;
    private String caaTwoTechnology;
    private String caaTwoRtechnology;
    private String caaTwoProperty;
    private String caaTwoSupervision;
    private String caaTwoRemark;
    @FileFields(index = 2)
    private String caaTwoFile;
    private String caaThreeResponsibleUid;
    private String caaThreePleaderUid;
    private String caaThreeSunitUid;
    private String caaThreeExplorationUid;
    private String caaThreeDesignUid;
    private String caaThreeConstructionUid;
    private String caaThreeTechnology;
    private String caaThreeRtechnology;
    private String caaThreeProperty;
    private String caaThreeSupervision;
    private String caaThreeRemark;
    @FileFields(index = 3)
    private String caaThreeFile;
    @ProcessFields(type = ProcessFieldEnum.createUid)
    private String caaCreateUid;
    @ProcessFields(type = ProcessFieldEnum.createUname)
    private String caaCreateUname;
    @ProcessFields(type = ProcessFieldEnum.createTime)
    private Date caaCreateTime;
    @ProcessFields(type = ProcessFieldEnum.stateTime)
    private Date caaStateTime;
    @ProcessFields(type = ProcessFieldEnum.state)
    private Integer caaState;
    @FileFields(index = 4)
    private String caaAcceptanceDoc;
    @FileFields(index = 4, type = "caaAcceptanceDocIds")
    private String caaAcceptanceDocIds;
    private String caaConstructionUnit;
    private String  caaConstructionUnitName;
    private String  caaSurveyUnitName;
    private String caaSurveyUnit;
    private Integer caaDurationTotal;
    private Date caaArchiveTime;

    private String pName;
    private String conName;
    @FileFields(index = 1, type = "caaOneFileIds")
    private String caaOneFileIds;
    @FileFields(index = 2, type = "caaTwoFileIds")
    private String caaTwoFileIds;
    @FileFields(index = 3, type = "caaThreeFileIds")
    private String caaThreeFileIds;



    public String getCaaId() {
        return caaId;
    }

    public void setCaaId(String caaId) {
        this.caaId = caaId;
    }

    @Override
    public String getpId() {
        return pId;
    }

    @Override
    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getConId() {
        return conId;
    }

    public void setConId(String conId) {
        this.conId = conId;
    }

    public String getCaaTitle() {
        return caaTitle;
    }

    public void setCaaTitle(String caaTitle) {
        this.caaTitle = caaTitle;
    }

    public String getCaaPleaderUid() {
        return caaPleaderUid;
    }

    public void setCaaPleaderUid(String caaPleaderUid) {
        this.caaPleaderUid = caaPleaderUid;
    }

    public Date getCaaStaringTime() {
        return caaStaringTime;
    }

    public void setCaaStaringTime(Date caaStaringTime) {
        this.caaStaringTime = caaStaringTime;
    }

    public Date getCaaEndTime() {
        return caaEndTime;
    }

    public void setCaaEndTime(Date caaEndTime) {
        this.caaEndTime = caaEndTime;
    }

    public String getCaaSunitId() {
        return caaSunitId;
    }

    public void setCaaSunitId(String caaSunitId) {
        this.caaSunitId = caaSunitId;
    }

    public String getCaaDunitId() {
        return caaDunitId;
    }

    public void setCaaDunitId(String caaDunitId) {
        this.caaDunitId = caaDunitId;
    }

    public String getCaaCcunitId() {
        return caaCcunitId;
    }

    public void setCaaCcunitId(String caaCcunitId) {
        this.caaCcunitId = caaCcunitId;
    }

    public String getCaaOneSunitUid() {
        return caaOneSunitUid;
    }

    public void setCaaOneSunitUid(String caaOneSunitUid) {
        this.caaOneSunitUid = caaOneSunitUid;
    }

    public String getCaaOneExplorationUid() {
        return caaOneExplorationUid;
    }

    public void setCaaOneExplorationUid(String caaOneExplorationUid) {
        this.caaOneExplorationUid = caaOneExplorationUid;
    }

    public String getCaaOneDesignUid() {
        return caaOneDesignUid;
    }

    public void setCaaOneDesignUid(String caaOneDesignUid) {
        this.caaOneDesignUid = caaOneDesignUid;
    }

    public String getCaaOneConstructionUid() {
        return caaOneConstructionUid;
    }

    public void setCaaOneConstructionUid(String caaOneConstructionUid) {
        this.caaOneConstructionUid = caaOneConstructionUid;
    }

    public String getCaaOneTechnology() {
        return caaOneTechnology;
    }

    public void setCaaOneTechnology(String caaOneTechnology) {
        this.caaOneTechnology = caaOneTechnology;
    }

    public String getCaaOneRtechnology() {
        return caaOneRtechnology;
    }

    public void setCaaOneRtechnology(String caaOneRtechnology) {
        this.caaOneRtechnology = caaOneRtechnology;
    }

    public String getCaaOneProperty() {
        return caaOneProperty;
    }

    public void setCaaOneProperty(String caaOneProperty) {
        this.caaOneProperty = caaOneProperty;
    }

    public String getCaaOneSupervision() {
        return caaOneSupervision;
    }

    public void setCaaOneSupervision(String caaOneSupervision) {
        this.caaOneSupervision = caaOneSupervision;
    }

    public String getCaaOneRemark() {
        return caaOneRemark;
    }

    public void setCaaOneRemark(String caaOneRemark) {
        this.caaOneRemark = caaOneRemark;
    }

    public String getCaaOneFile() {
        return caaOneFile;
    }

    public void setCaaOneFile(String caaOneFile) {
        this.caaOneFile = caaOneFile;
    }

    public String getCaaTwoPleaderUid() {
        return caaTwoPleaderUid;
    }

    public void setCaaTwoPleaderUid(String caaTwoPleaderUid) {
        this.caaTwoPleaderUid = caaTwoPleaderUid;
    }

    public String getCaaTwoSunitUid() {
        return caaTwoSunitUid;
    }

    public void setCaaTwoSunitUid(String caaTwoSunitUid) {
        this.caaTwoSunitUid = caaTwoSunitUid;
    }

    public String getCaaTwoExplorationUid() {
        return caaTwoExplorationUid;
    }

    public void setCaaTwoExplorationUid(String caaTwoExplorationUid) {
        this.caaTwoExplorationUid = caaTwoExplorationUid;
    }

    public String getCaaTwoDesignUid() {
        return caaTwoDesignUid;
    }

    public void setCaaTwoDesignUid(String caaTwoDesignUid) {
        this.caaTwoDesignUid = caaTwoDesignUid;
    }

    public String getCaaTwoConstructionUid() {
        return caaTwoConstructionUid;
    }

    public void setCaaTwoConstructionUid(String caaTwoConstructionUid) {
        this.caaTwoConstructionUid = caaTwoConstructionUid;
    }

    public String getCaaTwoTechnology() {
        return caaTwoTechnology;
    }

    public void setCaaTwoTechnology(String caaTwoTechnology) {
        this.caaTwoTechnology = caaTwoTechnology;
    }

    public String getCaaTwoRtechnology() {
        return caaTwoRtechnology;
    }

    public void setCaaTwoRtechnology(String caaTwoRtechnology) {
        this.caaTwoRtechnology = caaTwoRtechnology;
    }

    public String getCaaTwoProperty() {
        return caaTwoProperty;
    }

    public void setCaaTwoProperty(String caaTwoProperty) {
        this.caaTwoProperty = caaTwoProperty;
    }

    public String getCaaTwoSupervision() {
        return caaTwoSupervision;
    }

    public void setCaaTwoSupervision(String caaTwoSupervision) {
        this.caaTwoSupervision = caaTwoSupervision;
    }

    public String getCaaTwoRemark() {
        return caaTwoRemark;
    }

    public void setCaaTwoRemark(String caaTwoRemark) {
        this.caaTwoRemark = caaTwoRemark;
    }

    public String getCaaTwoFile() {
        return caaTwoFile;
    }

    public void setCaaTwoFile(String caaTwoFile) {
        this.caaTwoFile = caaTwoFile;
    }

    public String getCaaThreeResponsibleUid() {
        return caaThreeResponsibleUid;
    }

    public void setCaaThreeResponsibleUid(String caaThreeResponsibleUid) {
        this.caaThreeResponsibleUid = caaThreeResponsibleUid;
    }

    public String getCaaThreePleaderUid() {
        return caaThreePleaderUid;
    }

    public void setCaaThreePleaderUid(String caaThreePleaderUid) {
        this.caaThreePleaderUid = caaThreePleaderUid;
    }

    public String getCaaThreeSunitUid() {
        return caaThreeSunitUid;
    }

    public void setCaaThreeSunitUid(String caaThreeSunitUid) {
        this.caaThreeSunitUid = caaThreeSunitUid;
    }

    public String getCaaThreeExplorationUid() {
        return caaThreeExplorationUid;
    }

    public void setCaaThreeExplorationUid(String caaThreeExplorationUid) {
        this.caaThreeExplorationUid = caaThreeExplorationUid;
    }

    public String getCaaThreeDesignUid() {
        return caaThreeDesignUid;
    }

    public void setCaaThreeDesignUid(String caaThreeDesignUid) {
        this.caaThreeDesignUid = caaThreeDesignUid;
    }

    public String getCaaThreeConstructionUid() {
        return caaThreeConstructionUid;
    }

    public void setCaaThreeConstructionUid(String caaThreeConstructionUid) {
        this.caaThreeConstructionUid = caaThreeConstructionUid;
    }

    public String getCaaThreeTechnology() {
        return caaThreeTechnology;
    }

    public void setCaaThreeTechnology(String caaThreeTechnology) {
        this.caaThreeTechnology = caaThreeTechnology;
    }

    public String getCaaThreeRtechnology() {
        return caaThreeRtechnology;
    }

    public void setCaaThreeRtechnology(String caaThreeRtechnology) {
        this.caaThreeRtechnology = caaThreeRtechnology;
    }

    public String getCaaThreeProperty() {
        return caaThreeProperty;
    }

    public void setCaaThreeProperty(String caaThreeProperty) {
        this.caaThreeProperty = caaThreeProperty;
    }

    public String getCaaThreeSupervision() {
        return caaThreeSupervision;
    }

    public void setCaaThreeSupervision(String caaThreeSupervision) {
        this.caaThreeSupervision = caaThreeSupervision;
    }

    public String getCaaThreeRemark() {
        return caaThreeRemark;
    }

    public void setCaaThreeRemark(String caaThreeRemark) {
        this.caaThreeRemark = caaThreeRemark;
    }

    public String getCaaThreeFile() {
        return caaThreeFile;
    }

    public void setCaaThreeFile(String caaThreeFile) {
        this.caaThreeFile = caaThreeFile;
    }

    public String getCaaCreateUid() {
        return caaCreateUid;
    }

    public void setCaaCreateUid(String caaCreateUid) {
        this.caaCreateUid = caaCreateUid;
    }

    public String getCaaCreateUname() {
        return caaCreateUname;
    }

    public void setCaaCreateUname(String caaCreateUname) {
        this.caaCreateUname = caaCreateUname;
    }

    public Date getCaaCreateTime() {
        return caaCreateTime;
    }

    public void setCaaCreateTime(Date caaCreateTime) {
        this.caaCreateTime = caaCreateTime;
    }

    public Date getCaaStateTime() {
        return caaStateTime;
    }

    public void setCaaStateTime(Date caaStateTime) {
        this.caaStateTime = caaStateTime;
    }

    public Integer getCaaState() {
        return caaState;
    }

    public void setCaaState(Integer caaState) {
        this.caaState = caaState;
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

    public String getCaaOneFileIds() {
        return caaOneFileIds;
    }

    public void setCaaOneFileIds(String caaOneFileIds) {
        this.caaOneFileIds = caaOneFileIds;
    }

    public String getCaaTwoFileIds() {
        return caaTwoFileIds;
    }

    public void setCaaTwoFileIds(String caaTwoFileIds) {
        this.caaTwoFileIds = caaTwoFileIds;
    }

    public String getCaaThreeFileIds() {
        return caaThreeFileIds;
    }

    public void setCaaThreeFileIds(String caaThreeFileIds) {
        this.caaThreeFileIds = caaThreeFileIds;
    }

    public String getCaaAcceptanceDoc() {
        return caaAcceptanceDoc;
    }

    public void setCaaAcceptanceDoc(String caaAcceptanceDoc) {
        this.caaAcceptanceDoc = caaAcceptanceDoc;
    }

    public String getCaaAcceptanceDocIds() {
        return caaAcceptanceDocIds;
    }

    public void setCaaAcceptanceDocIds(String caaAcceptanceDocIds) {
        this.caaAcceptanceDocIds = caaAcceptanceDocIds;
    }

    public String getCaaConstructionUnit() {
        return caaConstructionUnit;
    }

    public void setCaaConstructionUnit(String caaConstructionUnit) {
        this.caaConstructionUnit = caaConstructionUnit;
    }

    public String getCaaSurveyUnit() {
        return caaSurveyUnit;
    }

    public void setCaaSurveyUnit(String caaSurveyUnit) {
        this.caaSurveyUnit = caaSurveyUnit;
    }

    public Integer getCaaDurationTotal() {
        return caaDurationTotal;
    }

    public void setCaaDurationTotal(Integer caaDurationTotal) {
        this.caaDurationTotal = caaDurationTotal;
    }

    public Date getCaaArchiveTime() {
        return caaArchiveTime;
    }

    public void setCaaArchiveTime(Date caaArchiveTime) {
        this.caaArchiveTime = caaArchiveTime;
    }

    public String getCaaConstructionUnitName() {
        return caaConstructionUnitName;
    }

    public void setCaaConstructionUnitName(String caaConstructionUnitName) {
        this.caaConstructionUnitName = caaConstructionUnitName;
    }

    public String getCaaSurveyUnitName() {
        return caaSurveyUnitName;
    }

    public void setCaaSurveyUnitName(String caaSurveyUnitName) {
        this.caaSurveyUnitName = caaSurveyUnitName;
    }
}
