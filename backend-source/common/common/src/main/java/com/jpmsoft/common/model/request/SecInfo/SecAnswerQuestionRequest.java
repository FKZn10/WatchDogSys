package com.jpmsoft.common.model.request.SecInfo;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.jpmsoft.common.annotations.ClassInfo;
import com.jpmsoft.common.model.BaseFileRequest;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecAnswerQuestionDtlModel;
import com.jpmsoft.common.model.response.SecInfo.SecProjectResponse;

import java.util.Date;
import java.util.List;

/**
 * 答疑补遗
 */
public class SecAnswerQuestionRequest extends BaseFileRequest {

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

    //其他文件 ids

    private String aqFileIds;
    private String aqAnswerFileIds;
    private String aqOtherDocIds;
    private String aqClarificationNoticeIds;
    private String aqClarificationIds;
    private String secName;
    private String secType;
    private String secBiddingType;
    private String pName;
    //创建时间（时间范围筛选）、
    private List<String> aqCreateTimes;
    private List<String> aqBidTimes;
    private List<SecAnswerQuestionDtlModel> answerQuestionDtlModels;


    //答疑发布日期
    private Date aqPublishTime;

    //答疑发布网址
    private String aqPublishUrl;

    // 计划开标时间
    private Date aqPlanOpenTime;
    //计划开标地点
    private String aqPlanOpenLocation;


    @JsonProperty("pNameList")
    @ClassInfo(title = "项目名称", type = "string", value = "pNameList", dbValue = "case when pre.P_Name is null then sp.P_Name else pre.P_Name end", no = 1)
    private List<String> pNameList;

    @ClassInfo(title = "标段名称", type = "string", value = "secNameList", dbValue = "sec.sec_Name", no = 2)
    private List<String> secNameList;

    @JsonProperty("pCategoryList")
    @ClassInfo(title = "项目分类", type = "dictionary", value = "pCategoryList", dbValue = "case when pre.P_Category is null then sp.p_Category else pre.P_Category end",dictionary = "JPMsoft_NDTZXMFL", no = 3)
    private List<String> pCategoryList;

    @JsonProperty("aqCreateUnameList")
    @ClassInfo(title = "发起人", type = "string", value = "aqCreateUnameList", dbValue = "mn.Aq_Create_Uname", no = 4)
    private List<String> aqCreateUnameList;

    @JsonProperty("aqCreateTimeStr")
    @ClassInfo(title = "发起时间", type = "date", value = "aqCreateTimeStr", dbValue = "mn.Aq_Create_Time", no = 5)
    private String aqCreateTimeStr;


    @JsonProperty("aqStateList")
    @ClassInfo(title = "流程状态", type = "dictionary", value = "aqStateList", dbValue = "mn.Aq_State",dictionary = "JPMsoft_LCZT", no = 6)
    private List<Integer> aqStateList;
    /*
     * 项目规模
     */
    private String projectCategory;
    /*
     * 发起单位
     */
    private String projectInitiatingUnit;
    private String projectOwner;

    private String isNew;
    private List<String> participationOrFinished;

    private List<SecProjectResponse> secProjects;

    public List<SecProjectResponse> getSecProjects() {
        return secProjects;
    }

    public void setSecProjects(List<SecProjectResponse> secProjects) {
        this.secProjects = secProjects;
    }

    public String getIsNew() {
        return isNew;
    }

    public void setIsNew(String isNew) {
        this.isNew = isNew;
    }

    public List<String> getParticipationOrFinished() {
        return participationOrFinished;
    }

    public void setParticipationOrFinished(List<String> participationOrFinished) {
        this.participationOrFinished = participationOrFinished;
    }

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

    public List<String> getpNameList() {
        return pNameList;
    }

    public void setpNameList(List<String> pNameList) {
        this.pNameList = pNameList;
    }

    public List<String> getSecNameList() {
        return secNameList;
    }

    public void setSecNameList(List<String> secNameList) {
        this.secNameList = secNameList;
    }

    public List<String> getpCategoryList() {
        return pCategoryList;
    }

    public void setpCategoryList(List<String> pCategoryList) {
        this.pCategoryList = pCategoryList;
    }

    public List<String> getAqCreateUnameList() {
        return aqCreateUnameList;
    }

    public void setAqCreateUnameList(List<String> aqCreateUnameList) {
        this.aqCreateUnameList = aqCreateUnameList;
    }

    public String getAqCreateTimeStr() {
        return aqCreateTimeStr;
    }

    public void setAqCreateTimeStr(String aqCreateTimeStr) {
        this.aqCreateTimeStr = aqCreateTimeStr;
    }

    public List<Integer> getAqStateList() {
        return aqStateList;
    }

    public void setAqStateList(List<Integer> aqStateList) {
        this.aqStateList = aqStateList;
    }

    public String getAqId() {
        return aqId;
    }

    public void setAqId(String aqId) {
        this.aqId = aqId;
    }

    @Override
    public String getSecId() {
        return secId;
    }

    @Override
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

    public Integer getAqDelayStopBids() {
        return aqDelayStopBids;
    }

    public void setAqDelayStopBids(Integer aqDelayStopBids) {
        this.aqDelayStopBids = aqDelayStopBids;
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

    public String getAqFileIds() {
        return aqFileIds;
    }

    public void setAqFileIds(String aqFileIds) {
        this.aqFileIds = aqFileIds;
    }

    public String getAqAnswerFileIds() {
        return aqAnswerFileIds;
    }

    public void setAqAnswerFileIds(String aqAnswerFileIds) {
        this.aqAnswerFileIds = aqAnswerFileIds;
    }

    public String getAqOtherDocIds() {
        return aqOtherDocIds;
    }

    public void setAqOtherDocIds(String aqOtherDocIds) {
        this.aqOtherDocIds = aqOtherDocIds;
    }

    public String getAqClarificationNoticeIds() {
        return aqClarificationNoticeIds;
    }

    public void setAqClarificationNoticeIds(String aqClarificationNoticeIds) {
        this.aqClarificationNoticeIds = aqClarificationNoticeIds;
    }

    public String getAqClarificationIds() {
        return aqClarificationIds;
    }

    public void setAqClarificationIds(String aqClarificationIds) {
        this.aqClarificationIds = aqClarificationIds;
    }

    public String getSecName() {
        return secName;
    }

    public void setSecName(String secName) {
        this.secName = secName;
    }

    public String getSecType() {
        return secType;
    }

    public void setSecType(String secType) {
        this.secType = secType;
    }

    public String getSecBiddingType() {
        return secBiddingType;
    }

    public void setSecBiddingType(String secBiddingType) {
        this.secBiddingType = secBiddingType;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public List<String> getAqCreateTimes() {
        return aqCreateTimes;
    }

    public void setAqCreateTimes(List<String> aqCreateTimes) {
        this.aqCreateTimes = aqCreateTimes;
    }

    public List<String> getAqBidTimes() {
        return aqBidTimes;
    }

    public void setAqBidTimes(List<String> aqBidTimes) {
        this.aqBidTimes = aqBidTimes;
    }

    public List<SecAnswerQuestionDtlModel> getAnswerQuestionDtlModels() {
        return answerQuestionDtlModels;
    }

    public void setAnswerQuestionDtlModels(List<SecAnswerQuestionDtlModel> answerQuestionDtlModels) {
        this.answerQuestionDtlModels = answerQuestionDtlModels;
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
