package com.jpmsoft.common.model.request.proCp;

import com.jpmsoft.common.model.BaseFileRequest;

import java.util.Date;

/**
 * 项目建设程序详情
 */

public class ProjectCpDtlRequest extends BaseFileRequest {

    private String cpdId;
    private String cpId;
    private String cpdName;
    private String cpdCode;
    private String cpdParentId;
    private String cpdType;
    private Integer cpdDays;
    private String cpdProId;
    private String cpdAdministration;
    private String cpdAdministrationUrl;
    private String cpdRemark;
    private String cpdHtml;
    private Date cpdPlanStartTime;
    private Date cpdPlanEndTime;
    private String cpdPersonId;
    private String cpdPersonName;
    private Date cpdStartTime;
    private Date cpdEndTime;
    private Integer cpdState;
    private String cpdDoc;
    private String cpdOtherFile;
    private String cpdCreateUid;
    private String cpdCreateUname;
    private Date cpdCreateTime;
    private String cpdFileName;
    private String cpdFileCode;
    private Date cpdReplyTime;
    private Integer cpdStatus;
    private String cpdFileType;
    /**
     * 统计类型
     * 1：手续总数
     * 2：已完成
     * 3：未完成
     * 4：超时未完成
     */
    private Integer statisticalType;

    /**
     * 1:
     * 2：
     */
    private Integer queryType;

    private String pName;

    private Integer flowState;
    private String pType;
    private String pProjectStage;
    private String pConstructionUnit;
    private String pCompetentDepartment;
    private String pRegion;
    private String cpdDocIds;
    private String cpdOtherFileIds;
    private Date cpdCerEndTime;

    private Boolean haveDoc;

    private String cleanCreateTime;

    private String cprId;

    public String getCprId() {
        return cprId;
    }

    public void setCprId(String cprId) {
        this.cprId = cprId;
    }


    public String getCleanCreateTime() {
        return cleanCreateTime;
    }

    public void setCleanCreateTime(String cleanCreateTime) {
        this.cleanCreateTime = cleanCreateTime;
    }

    public Integer getQueryType() {
        return queryType;
    }

    public void setQueryType(Integer queryType) {
        this.queryType = queryType;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public String getCpdId() {
        return cpdId;
    }

    public void setCpdId(String cpdId) {
        this.cpdId = cpdId;
    }

    public String getCpId() {
        return cpId;
    }

    public void setCpId(String cpId) {
        this.cpId = cpId;
    }

    public String getCpdName() {
        return cpdName;
    }

    public void setCpdName(String cpdName) {
        this.cpdName = cpdName;
    }

    public String getCpdCode() {
        return cpdCode;
    }

    public void setCpdCode(String cpdCode) {
        this.cpdCode = cpdCode;
    }

    public String getCpdParentId() {
        return cpdParentId;
    }

    public void setCpdParentId(String cpdParentId) {
        this.cpdParentId = cpdParentId;
    }

    public String getCpdType() {
        return cpdType;
    }

    public void setCpdType(String cpdType) {
        this.cpdType = cpdType;
    }

    public Integer getCpdDays() {
        return cpdDays;
    }

    public void setCpdDays(Integer cpdDays) {
        this.cpdDays = cpdDays;
    }

    public String getCpdProId() {
        return cpdProId;
    }

    public void setCpdProId(String cpdProId) {
        this.cpdProId = cpdProId;
    }

    public String getCpdAdministration() {
        return cpdAdministration;
    }

    public void setCpdAdministration(String cpdAdministration) {
        this.cpdAdministration = cpdAdministration;
    }

    public String getCpdAdministrationUrl() {
        return cpdAdministrationUrl;
    }

    public void setCpdAdministrationUrl(String cpdAdministrationUrl) {
        this.cpdAdministrationUrl = cpdAdministrationUrl;
    }

    public String getCpdRemark() {
        return cpdRemark;
    }

    public void setCpdRemark(String cpdRemark) {
        this.cpdRemark = cpdRemark;
    }

    public String getCpdHtml() {
        return cpdHtml;
    }

    public void setCpdHtml(String cpdHtml) {
        this.cpdHtml = cpdHtml;
    }

    public Date getCpdPlanStartTime() {
        return cpdPlanStartTime;
    }

    public void setCpdPlanStartTime(Date cpdPlanStartTime) {
        this.cpdPlanStartTime = cpdPlanStartTime;
    }

    public Date getCpdPlanEndTime() {
        return cpdPlanEndTime;
    }

    public void setCpdPlanEndTime(Date cpdPlanEndTime) {
        this.cpdPlanEndTime = cpdPlanEndTime;
    }

    public String getCpdPersonId() {
        return cpdPersonId;
    }

    public void setCpdPersonId(String cpdPersonId) {
        this.cpdPersonId = cpdPersonId;
    }

    public String getCpdPersonName() {
        return cpdPersonName;
    }

    public void setCpdPersonName(String cpdPersonName) {
        this.cpdPersonName = cpdPersonName;
    }

    public Date getCpdStartTime() {
        return cpdStartTime;
    }

    public void setCpdStartTime(Date cpdStartTime) {
        this.cpdStartTime = cpdStartTime;
    }

    public Date getCpdEndTime() {
        return cpdEndTime;
    }

    public void setCpdEndTime(Date cpdEndTime) {
        this.cpdEndTime = cpdEndTime;
    }

    public Integer getCpdState() {
        return cpdState;
    }

    public void setCpdState(Integer cpdState) {
        this.cpdState = cpdState;
    }

    public String getCpdDoc() {
        return cpdDoc;
    }

    public void setCpdDoc(String cpdDoc) {
        this.cpdDoc = cpdDoc;
    }

    public String getCpdCreateUid() {
        return cpdCreateUid;
    }

    public void setCpdCreateUid(String cpdCreateUid) {
        this.cpdCreateUid = cpdCreateUid;
    }

    public String getCpdCreateUname() {
        return cpdCreateUname;
    }

    public void setCpdCreateUname(String cpdCreateUname) {
        this.cpdCreateUname = cpdCreateUname;
    }

    public Date getCpdCreateTime() {
        return cpdCreateTime;
    }

    public void setCpdCreateTime(Date cpdCreateTime) {
        this.cpdCreateTime = cpdCreateTime;
    }

    public String getCpdFileName() {
        return cpdFileName;
    }

    public void setCpdFileName(String cpdFileName) {
        this.cpdFileName = cpdFileName;
    }

    public String getCpdFileCode() {
        return cpdFileCode;
    }

    public void setCpdFileCode(String cpdFileCode) {
        this.cpdFileCode = cpdFileCode;
    }

    public Date getCpdReplyTime() {
        return cpdReplyTime;
    }

    public void setCpdReplyTime(Date cpdReplyTime) {
        this.cpdReplyTime = cpdReplyTime;
    }

    public Integer getCpdStatus() {
        return cpdStatus;
    }

    public void setCpdStatus(Integer cpdStatus) {
        this.cpdStatus = cpdStatus;
    }

    public String getCpdFileType() {
        return cpdFileType;
    }

    public void setCpdFileType(String cpdFileType) {
        this.cpdFileType = cpdFileType;
    }

    public Integer getStatisticalType() {
        return statisticalType;
    }

    public void setStatisticalType(Integer statisticalType) {
        this.statisticalType = statisticalType;
    }

    public Integer getFlowState() {
        return flowState;
    }

    public void setFlowState(Integer flowState) {
        this.flowState = flowState;
    }

    public String getpType() {
        return pType;
    }

    public void setpType(String pType) {
        this.pType = pType;
    }

    public String getpProjectStage() {
        return pProjectStage;
    }

    public void setpProjectStage(String pProjectStage) {
        this.pProjectStage = pProjectStage;
    }

    public String getpConstructionUnit() {
        return pConstructionUnit;
    }

    public void setpConstructionUnit(String pConstructionUnit) {
        this.pConstructionUnit = pConstructionUnit;
    }

    public String getpCompetentDepartment() {
        return pCompetentDepartment;
    }

    public void setpCompetentDepartment(String pCompetentDepartment) {
        this.pCompetentDepartment = pCompetentDepartment;
    }

    public String getpRegion() {
        return pRegion;
    }

    public void setpRegion(String pRegion) {
        this.pRegion = pRegion;
    }

    public String getCpdDocIds() {
        return cpdDocIds;
    }

    public void setCpdDocIds(String cpdDocIds) {
        this.cpdDocIds = cpdDocIds;
    }

    public Date getCpdCerEndTime() {
        return cpdCerEndTime;
    }

    public void setCpdCerEndTime(Date cpdCerEndTime) {
        this.cpdCerEndTime = cpdCerEndTime;
    }

    public Boolean getHaveDoc() {
        return haveDoc;
    }

    public void setHaveDoc(Boolean haveDoc) {
        this.haveDoc = haveDoc;
    }

    public String getCpdOtherFile() {
        return cpdOtherFile;
    }

    public void setCpdOtherFile(String cpdOtherFile) {
        this.cpdOtherFile = cpdOtherFile;
    }

    public String getCpdOtherFileIds() {
        return cpdOtherFileIds;
    }

    public void setCpdOtherFileIds(String cpdOtherFileIds) {
        this.cpdOtherFileIds = cpdOtherFileIds;
    }
}
