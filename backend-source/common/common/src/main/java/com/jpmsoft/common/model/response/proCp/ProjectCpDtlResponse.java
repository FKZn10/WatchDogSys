package com.jpmsoft.common.model.response.proCp;

import com.jpmsoft.common.model.ProjectCommonRes;
import com.jpmsoft.common.model.response.FileResponse;

import java.util.Date;
import java.util.List;

/**
 * 项目建设程序详情
 */

public class ProjectCpDtlResponse extends ProjectCommonRes {

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
    private Integer cpdSort;
    private String cpdCplId;
    private String cpdAdministrationUrlH;
    private String tCode;
    private String cpdFileName;
    private String cpdFileCode;
    private Date cpdReplyTime;
    private Integer cpdStatus;
    private Date cpdCerEndTime;
    private Date stateTime;
    private String cpdFileType;

    private String cplFileExpiain;
    private String cplAchievementType;
    private List<ProjectCpDtlResponse> childNodes;
    private  List<FileResponse> files;
    private String cpdProName;
    private Integer cpdProState;
    /**
     * 正在审批过程中的项目建设程序取消
     */
    private Integer approveCpCancel;

    private String gsId;

    private String taskImportance;

    private List<ProjectCpLibraryRiskResponse> riskList;

    public List<ProjectCpLibraryRiskResponse> getRiskList() {
        return riskList;
    }

    public void setRiskList(List<ProjectCpLibraryRiskResponse> riskList) {
        this.riskList = riskList;
    }

    public String getTaskImportance() {
        return taskImportance;
    }

    public void setTaskImportance(String taskImportance) {
        this.taskImportance = taskImportance;
    }

    public String getGsId() {
        return gsId;
    }

    public void setGsId(String gsId) {
        this.gsId = gsId;
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

    public String getCpdOtherFile() {
        return cpdOtherFile;
    }

    public void setCpdOtherFile(String cpdOtherFile) {
        this.cpdOtherFile = cpdOtherFile;
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

    public Integer getCpdSort() {
        return cpdSort;
    }

    public void setCpdSort(Integer cpdSort) {
        this.cpdSort = cpdSort;
    }

    public String getCpdCplId() {
        return cpdCplId;
    }

    public void setCpdCplId(String cpdCplId) {
        this.cpdCplId = cpdCplId;
    }

    public String getCpdAdministrationUrlH() {
        return cpdAdministrationUrlH;
    }

    public void setCpdAdministrationUrlH(String cpdAdministrationUrlH) {
        this.cpdAdministrationUrlH = cpdAdministrationUrlH;
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

    public Date getCpdCerEndTime() {
        return cpdCerEndTime;
    }

    public void setCpdCerEndTime(Date cpdCerEndTime) {
        this.cpdCerEndTime = cpdCerEndTime;
    }

    public Date getStateTime() {
        return stateTime;
    }

    public void setStateTime(Date stateTime) {
        this.stateTime = stateTime;
    }

    public String getCpdFileType() {
        return cpdFileType;
    }

    public void setCpdFileType(String cpdFileType) {
        this.cpdFileType = cpdFileType;
    }

    public String getCplFileExpiain() {
        return cplFileExpiain;
    }

    public void setCplFileExpiain(String cplFileExpiain) {
        this.cplFileExpiain = cplFileExpiain;
    }

    public String getCplAchievementType() {
        return cplAchievementType;
    }

    public void setCplAchievementType(String cplAchievementType) {
        this.cplAchievementType = cplAchievementType;
    }

    public List<ProjectCpDtlResponse> getChildNodes() {
        return childNodes;
    }

    public void setChildNodes(List<ProjectCpDtlResponse> childNodes) {
        this.childNodes = childNodes;
    }

    public String gettCode() {
        return tCode;
    }

    public void settCode(String tCode) {
        this.tCode = tCode;
    }

    @Override
    public List<FileResponse> getFiles() {
        return files;
    }

    @Override
    public void setFiles(List<FileResponse> files) {
        this.files = files;
    }

    public String getCpdProName() {
        return cpdProName;
    }

    public void setCpdProName(String cpdProName) {
        this.cpdProName = cpdProName;
    }

    public Integer getCpdProState() {
        return cpdProState;
    }

    public void setCpdProState(Integer cpdProState) {
        this.cpdProState = cpdProState;
    }

    public Integer getApproveCpCancel() {
        return approveCpCancel;
    }

    public void setApproveCpCancel(Integer approveCpCancel) {
        this.approveCpCancel = approveCpCancel;
    }
}
