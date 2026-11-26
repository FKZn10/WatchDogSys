package com.jpmsoft.common.model.response.SecInfo;

import com.jpmsoft.common.model.BaseFileResponse;

import java.util.Date;
import java.util.List;

/**
 * 中标质疑投诉处理
 */
public class SecChallengeComplaintsResponse extends BaseFileResponse {

    private String sccId;
    private String secId;
    private Integer sccQuestion;
    private String sccQuestioner;
    private String sccQuestionerTelephone;
    private String sccQuestionQuestion;
    private String sccRespondent;
    private String sccReplyContent;
    private String sccRemarks;
    private String sccCreateUid;
    private String sccCreateUname;
    private Date sccCreateTime;
    private Integer sccState;
    private Date sccStateTime;

    //中标质疑文件
    private String sccHandleDoc;
    private String sccHandleDocFileIds;
    //开评标结果是否有更新
    private Integer sccResultUpdate;

    private String secName;
    private String secType;
    private String secBiddingType;
    private String pId;
    private String pName;
    private List<SecProjectResponse> secProjects;
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

    public String getSccId() {
        return sccId;
    }

    public void setSccId(String sccId) {
        this.sccId = sccId;
    }

    public String getSecId() {
        return secId;
    }

    public void setSecId(String secId) {
        this.secId = secId;
    }

    public Integer getSccQuestion() {
        return sccQuestion;
    }

    public void setSccQuestion(Integer sccQuestion) {
        this.sccQuestion = sccQuestion;
    }

    public String getSccQuestioner() {
        return sccQuestioner;
    }

    public void setSccQuestioner(String sccQuestioner) {
        this.sccQuestioner = sccQuestioner;
    }

    public String getSccQuestionerTelephone() {
        return sccQuestionerTelephone;
    }

    public void setSccQuestionerTelephone(String sccQuestionerTelephone) {
        this.sccQuestionerTelephone = sccQuestionerTelephone;
    }

    public String getSccQuestionQuestion() {
        return sccQuestionQuestion;
    }

    public void setSccQuestionQuestion(String sccQuestionQuestion) {
        this.sccQuestionQuestion = sccQuestionQuestion;
    }

    public String getSccRespondent() {
        return sccRespondent;
    }

    public void setSccRespondent(String sccRespondent) {
        this.sccRespondent = sccRespondent;
    }

    public String getSccReplyContent() {
        return sccReplyContent;
    }

    public void setSccReplyContent(String sccReplyContent) {
        this.sccReplyContent = sccReplyContent;
    }

    public String getSccRemarks() {
        return sccRemarks;
    }

    public void setSccRemarks(String sccRemarks) {
        this.sccRemarks = sccRemarks;
    }

    public String getSccCreateUid() {
        return sccCreateUid;
    }

    public void setSccCreateUid(String sccCreateUid) {
        this.sccCreateUid = sccCreateUid;
    }

    public String getSccCreateUname() {
        return sccCreateUname;
    }

    public void setSccCreateUname(String sccCreateUname) {
        this.sccCreateUname = sccCreateUname;
    }

    public Date getSccCreateTime() {
        return sccCreateTime;
    }

    public void setSccCreateTime(Date sccCreateTime) {
        this.sccCreateTime = sccCreateTime;
    }

    public Integer getSccState() {
        return sccState;
    }

    public void setSccState(Integer sccState) {
        this.sccState = sccState;
    }

    public Date getSccStateTime() {
        return sccStateTime;
    }

    public void setSccStateTime(Date sccStateTime) {
        this.sccStateTime = sccStateTime;
    }

    public String getSccHandleDoc() {
        return sccHandleDoc;
    }

    public void setSccHandleDoc(String sccHandleDoc) {
        this.sccHandleDoc = sccHandleDoc;
    }

    public String getSccHandleDocFileIds() {
        return sccHandleDocFileIds;
    }

    public void setSccHandleDocFileIds(String sccHandleDocFileIds) {
        this.sccHandleDocFileIds = sccHandleDocFileIds;
    }

    public Integer getSccResultUpdate() {
        return sccResultUpdate;
    }

    public void setSccResultUpdate(Integer sccResultUpdate) {
        this.sccResultUpdate = sccResultUpdate;
    }

    public String getSecBiddingType() {
        return secBiddingType;
    }

    public void setSecBiddingType(String secBiddingType) {
        this.secBiddingType = secBiddingType;
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

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public List<SecProjectResponse> getSecProjects() {
        return secProjects;
    }

    public void setSecProjects(List<SecProjectResponse> secProjects) {
        this.secProjects = secProjects;
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