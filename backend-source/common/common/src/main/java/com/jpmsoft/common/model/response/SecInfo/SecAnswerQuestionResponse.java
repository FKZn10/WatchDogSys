package com.jpmsoft.common.model.response.SecInfo;


import com.jpmsoft.common.model.requestAndResponse.SecCommonModel;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecAnswerQuestionDtlModel;

import java.util.Date;
import java.util.List;

/**
 * 答疑补遗
 */
public class SecAnswerQuestionResponse extends SecCommonModel {
    private String aqId;
    private String secId;
    private String aqTitle;
    private String aqContent;
    private String aqDocType;
    private String aqItemType;
    private String aqOtherDoc;
    private Integer aqNoticeTimeChange;
    private String aqCreateUid;
    private String aqCreateUname;
    private Date aqCreateTime;
    private Integer aqState;
    private Date aqStateTime;
    private String aqRemark;
    private String aqClarificationNotice;
    private String aqClarification;
    private Integer aqSurveyWhether;
    private Date aqSurveyTime;
    private String aqSurveyPlace;
    private String aqSurveyRemark;
    private Integer aqMeetingWhether;
    private Date aqMeetingTime;
    private String aqMeetingPlace;
    private String aqMeetingRemark;
    private Boolean aqIsChangeTime;
    private Boolean aqIsChangeLocation;
    private Date aqBidTime;
    private String aqLocation;
    private Integer aqDelayStopBids;
    private Boolean aqIsThere;
    private String aqApprovalMatters;
    private Date aqTime;
    private String aqStatus;
    private Date aqAnswerTime;
    private String aqAnswerContent;
    private String aqAnswerUname;
    private String aqFile;
    private String aqAnswerFile;
    private String aqFinalRes;

    private List<SecProjectResponse> secProjects;
    private List<SecAnswerQuestionDtlModel> answerQuestionDtlModels;
    private String pName;
    private String secName;
    private String secCode;
    private String secType;
    private String secOrganizeForm;
    private String secBiddingType;
    private Date secStartTime;
    private Integer secBiddingCycle;
    private String secPlace;
    private String tendereeName;
    private String tendereeAbbName;
    private String pCompetentDepartmentName;

    private String pBuildModel;

    private String pBusinessUnit;

    private String pCategory;

    //答疑发布日期
    private Date aqPublishTime;

    //答疑发布网址
    private String aqPublishUrl;

    // 计划开标时间
    private Date aqPlanOpenTime;
    //计划开标地点
    private String aqPlanOpenLocation;
    /*
     * 项目规模
     */
    private String projectCategory;
    /*
     * 发起单位
     */
    private String projectInitiatingUnit;
    private String projectOwner;

    public String getProjectOwner() {
        return projectOwner;
    }

    public void setProjectOwner(String projectOwner) {
        this.projectOwner = projectOwner;
    }

    public Date getAqPlanOpenTime() {
        return aqPlanOpenTime;
    }

    public void setAqPlanOpenTime(Date aqPlanOpenTime) {
        this.aqPlanOpenTime = aqPlanOpenTime;
    }

    public String getAqPlanOpenLocation() {
        return aqPlanOpenLocation;
    }

    public void setAqPlanOpenLocation(String aqPlanOpenLocation) {
        this.aqPlanOpenLocation = aqPlanOpenLocation;
    }

    public Date getAqPublishTime() {
        return aqPublishTime;
    }

    public void setAqPublishTime(Date aqPublishTime) {
        this.aqPublishTime = aqPublishTime;
    }

    public String getAqPublishUrl() {
        return aqPublishUrl;
    }

    public void setAqPublishUrl(String aqPublishUrl) {
        this.aqPublishUrl = aqPublishUrl;
    }

    public String getpCategory() {
        return pCategory;
    }

    public void setpCategory(String pCategory) {
        this.pCategory = pCategory;
    }

    public String getpBusinessUnit() {
        return pBusinessUnit;
    }

    public void setpBusinessUnit(String pBusinessUnit) {
        this.pBusinessUnit = pBusinessUnit;
    }

    public String getpBuildModel() {
        return pBuildModel;
    }

    public void setpBuildModel(String pBuildModel) {
        this.pBuildModel = pBuildModel;
    }

    public String getAqId() {
        return aqId;
    }

    public void setAqId(String aqId) {
        this.aqId = aqId;
    }

    public String getSecId() {
        return secId;
    }

    public void setSecId(String secId) {
        this.secId = secId;
    }

    public String getAqTitle() {
        return aqTitle;
    }

    public void setAqTitle(String aqTitle) {
        this.aqTitle = aqTitle;
    }

    public String getAqContent() {
        return aqContent;
    }

    public void setAqContent(String aqContent) {
        this.aqContent = aqContent;
    }

    public String getAqDocType() {
        return aqDocType;
    }

    public void setAqDocType(String aqDocType) {
        this.aqDocType = aqDocType;
    }

    public String getAqItemType() {
        return aqItemType;
    }

    public void setAqItemType(String aqItemType) {
        this.aqItemType = aqItemType;
    }

    public String getAqOtherDoc() {
        return aqOtherDoc;
    }

    public void setAqOtherDoc(String aqOtherDoc) {
        this.aqOtherDoc = aqOtherDoc;
    }

    public Integer getAqNoticeTimeChange() {
        return aqNoticeTimeChange;
    }

    public void setAqNoticeTimeChange(Integer aqNoticeTimeChange) {
        this.aqNoticeTimeChange = aqNoticeTimeChange;
    }

    public String getAqCreateUid() {
        return aqCreateUid;
    }

    public void setAqCreateUid(String aqCreateUid) {
        this.aqCreateUid = aqCreateUid;
    }

    public String getAqCreateUname() {
        return aqCreateUname;
    }

    public void setAqCreateUname(String aqCreateUname) {
        this.aqCreateUname = aqCreateUname;
    }

    public Date getAqCreateTime() {
        return aqCreateTime;
    }

    public void setAqCreateTime(Date aqCreateTime) {
        this.aqCreateTime = aqCreateTime;
    }

    public Integer getAqState() {
        return aqState;
    }

    public void setAqState(Integer aqState) {
        this.aqState = aqState;
    }

    public Date getAqStateTime() {
        return aqStateTime;
    }

    public void setAqStateTime(Date aqStateTime) {
        this.aqStateTime = aqStateTime;
    }

    public String getAqRemark() {
        return aqRemark;
    }

    public void setAqRemark(String aqRemark) {
        this.aqRemark = aqRemark;
    }


    public String getAqClarificationNotice() {
        return aqClarificationNotice;
    }

    public void setAqClarificationNotice(String aqClarificationNotice) {
        this.aqClarificationNotice = aqClarificationNotice;
    }

    public String getAqClarification() {
        return aqClarification;
    }

    public void setAqClarification(String aqClarification) {
        this.aqClarification = aqClarification;
    }

    public Integer getAqSurveyWhether() {
        return aqSurveyWhether;
    }

    public void setAqSurveyWhether(Integer aqSurveyWhether) {
        this.aqSurveyWhether = aqSurveyWhether;
    }

    public Date getAqSurveyTime() {
        return aqSurveyTime;
    }

    public void setAqSurveyTime(Date aqSurveyTime) {
        this.aqSurveyTime = aqSurveyTime;
    }

    public String getAqSurveyPlace() {
        return aqSurveyPlace;
    }

    public void setAqSurveyPlace(String aqSurveyPlace) {
        this.aqSurveyPlace = aqSurveyPlace;
    }

    public String getAqSurveyRemark() {
        return aqSurveyRemark;
    }

    public void setAqSurveyRemark(String aqSurveyRemark) {
        this.aqSurveyRemark = aqSurveyRemark;
    }

    public Integer getAqMeetingWhether() {
        return aqMeetingWhether;
    }

    public void setAqMeetingWhether(Integer aqMeetingWhether) {
        this.aqMeetingWhether = aqMeetingWhether;
    }

    public Date getAqMeetingTime() {
        return aqMeetingTime;
    }

    public void setAqMeetingTime(Date aqMeetingTime) {
        this.aqMeetingTime = aqMeetingTime;
    }

    public String getAqMeetingPlace() {
        return aqMeetingPlace;
    }

    public void setAqMeetingPlace(String aqMeetingPlace) {
        this.aqMeetingPlace = aqMeetingPlace;
    }

    public String getAqMeetingRemark() {
        return aqMeetingRemark;
    }

    public void setAqMeetingRemark(String aqMeetingRemark) {
        this.aqMeetingRemark = aqMeetingRemark;
    }

    public Boolean getAqIsChangeTime() {
        return aqIsChangeTime;
    }

    public void setAqIsChangeTime(Boolean aqIsChangeTime) {
        this.aqIsChangeTime = aqIsChangeTime;
    }

    public Boolean getAqIsChangeLocation() {
        return aqIsChangeLocation;
    }

    public void setAqIsChangeLocation(Boolean aqIsChangeLocation) {
        this.aqIsChangeLocation = aqIsChangeLocation;
    }

    public Date getAqBidTime() {
        return aqBidTime;
    }

    public void setAqBidTime(Date aqBidTime) {
        this.aqBidTime = aqBidTime;
    }

    public String getAqLocation() {
        return aqLocation;
    }

    public void setAqLocation(String aqLocation) {
        this.aqLocation = aqLocation;
    }

    public List<SecProjectResponse> getSecProjects() {
        return secProjects;
    }

    public Integer getAqDelayStopBids() {
        return aqDelayStopBids;
    }

    public Boolean getAqIsThere() {
        return aqIsThere;
    }

    public void setAqIsThere(Boolean aqIsThere) {
        this.aqIsThere = aqIsThere;
    }

    public String getAqApprovalMatters() {
        return aqApprovalMatters;
    }

    public void setAqApprovalMatters(String aqApprovalMatters) {
        this.aqApprovalMatters = aqApprovalMatters;
    }

    public Date getAqTime() {
        return aqTime;
    }

    public void setAqTime(Date aqTime) {
        this.aqTime = aqTime;
    }

    public String getAqStatus() {
        return aqStatus;
    }

    public void setAqStatus(String aqStatus) {
        this.aqStatus = aqStatus;
    }

    public Date getAqAnswerTime() {
        return aqAnswerTime;
    }

    public void setAqAnswerTime(Date aqAnswerTime) {
        this.aqAnswerTime = aqAnswerTime;
    }

    public String getAqAnswerContent() {
        return aqAnswerContent;
    }

    public void setAqAnswerContent(String aqAnswerContent) {
        this.aqAnswerContent = aqAnswerContent;
    }

    public String getAqAnswerUname() {
        return aqAnswerUname;
    }

    public void setAqAnswerUname(String aqAnswerUname) {
        this.aqAnswerUname = aqAnswerUname;
    }

    public String getAqFile() {
        return aqFile;
    }

    public void setAqFile(String aqFile) {
        this.aqFile = aqFile;
    }

    public String getAqAnswerFile() {
        return aqAnswerFile;
    }

    public void setAqAnswerFile(String aqAnswerFile) {
        this.aqAnswerFile = aqAnswerFile;
    }

    public String getAqFinalRes() {
        return aqFinalRes;
    }

    public void setAqFinalRes(String aqFinalRes) {
        this.aqFinalRes = aqFinalRes;
    }

    public void setAqDelayStopBids(Integer aqDelayStopBids) {
        this.aqDelayStopBids = aqDelayStopBids;
    }

    public void setSecProjects(List<SecProjectResponse> secProjects) {
        this.secProjects = secProjects;
    }

    public List<SecAnswerQuestionDtlModel> getAnswerQuestionDtlModels() {
        return answerQuestionDtlModels;
    }

    public void setAnswerQuestionDtlModels(List<SecAnswerQuestionDtlModel> answerQuestionDtlModels) {
        this.answerQuestionDtlModels = answerQuestionDtlModels;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    @Override
    public String getSecName() {
        return secName;
    }

    @Override
    public void setSecName(String secName) {
        this.secName = secName;
    }

    public String getSecCode() {
        return secCode;
    }

    public void setSecCode(String secCode) {
        this.secCode = secCode;
    }

    @Override
    public String getSecType() {
        return secType;
    }

    @Override
    public void setSecType(String secType) {
        this.secType = secType;
    }

    public String getSecOrganizeForm() {
        return secOrganizeForm;
    }

    public void setSecOrganizeForm(String secOrganizeForm) {
        this.secOrganizeForm = secOrganizeForm;
    }

    public String getSecBiddingType() {
        return secBiddingType;
    }

    public void setSecBiddingType(String secBiddingType) {
        this.secBiddingType = secBiddingType;
    }

    public Date getSecStartTime() {
        return secStartTime;
    }

    public void setSecStartTime(Date secStartTime) {
        this.secStartTime = secStartTime;
    }

    public Integer getSecBiddingCycle() {
        return secBiddingCycle;
    }

    public void setSecBiddingCycle(Integer secBiddingCycle) {
        this.secBiddingCycle = secBiddingCycle;
    }

    public String getSecPlace() {
        return secPlace;
    }

    public void setSecPlace(String secPlace) {
        this.secPlace = secPlace;
    }

    public String getTendereeName() {
        return tendereeName;
    }

    public void setTendereeName(String tendereeName) {
        this.tendereeName = tendereeName;
    }

    public String getTendereeAbbName() {
        return tendereeAbbName;
    }

    public void setTendereeAbbName(String tendereeAbbName) {
        this.tendereeAbbName = tendereeAbbName;
    }

    public String getpCompetentDepartmentName() {
        return pCompetentDepartmentName;
    }

    public void setpCompetentDepartmentName(String pCompetentDepartmentName) {
        this.pCompetentDepartmentName = pCompetentDepartmentName;
    }

    public String getProjectCategory() {
        return projectCategory;
    }

    public void setProjectCategory(String projectCategory) {
        this.projectCategory = projectCategory;
    }

    public String getProjectInitiatingUnit() {
        return projectInitiatingUnit;
    }

    public void setProjectInitiatingUnit(String projectInitiatingUnit) {
        this.projectInitiatingUnit = projectInitiatingUnit;
    }
}