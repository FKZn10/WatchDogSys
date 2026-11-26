package com.jpmsoft.common.model.response.Oa;

import com.jpmsoft.common.model.ProjectCommonRes;

import java.util.Date;

/**
 * 整改反馈记录
 */
public class OaSupervisionFeedbackResponse extends ProjectCommonRes {

    private String sfId;
    private String stId;
    private String sfRemark;
    private Date sfTime;
    private String sfCreateUid;
    private String sfCreateUname;
    private Date sfCreateTime;
    private Integer sfState;
    private Date sfStateTime;
    private String sfDId;
    private String sfDPerson;
    private String sfDPhone;
    private String sfEvaluate;
    private String sfSituation;
    private String sfProblem;
    private String sfMeasures;
    private String sfRectification;

    private String sfDName;
    private String stCode;
    private String stTitle;
    private String stType;
    private String stHandleType;

    public String getSfId() {
        return sfId;
    }

    public void setSfId(String sfId) {
        this.sfId = sfId;
    }

    public String getStId() {
        return stId;
    }

    public void setStId(String stId) {
        this.stId = stId;
    }

    public String getSfRemark() {
        return sfRemark;
    }

    public void setSfRemark(String sfRemark) {
        this.sfRemark = sfRemark;
    }

    public Date getSfTime() {
        return sfTime;
    }

    public void setSfTime(Date sfTime) {
        this.sfTime = sfTime;
    }

    public String getSfCreateUid() {
        return sfCreateUid;
    }

    public void setSfCreateUid(String sfCreateUid) {
        this.sfCreateUid = sfCreateUid;
    }

    public String getSfCreateUname() {
        return sfCreateUname;
    }

    public void setSfCreateUname(String sfCreateUname) {
        this.sfCreateUname = sfCreateUname;
    }

    public Date getSfCreateTime() {
        return sfCreateTime;
    }

    public void setSfCreateTime(Date sfCreateTime) {
        this.sfCreateTime = sfCreateTime;
    }

    public Integer getSfState() {
        return sfState;
    }

    public void setSfState(Integer sfState) {
        this.sfState = sfState;
    }

    public Date getSfStateTime() {
        return sfStateTime;
    }

    public void setSfStateTime(Date sfStateTime) {
        this.sfStateTime = sfStateTime;
    }

    public String getSfDId() {
        return sfDId;
    }

    public void setSfDId(String sfDId) {
        this.sfDId = sfDId;
    }

    public String getSfDPerson() {
        return sfDPerson;
    }

    public void setSfDPerson(String sfDPerson) {
        this.sfDPerson = sfDPerson;
    }

    public String getSfDPhone() {
        return sfDPhone;
    }

    public void setSfDPhone(String sfDPhone) {
        this.sfDPhone = sfDPhone;
    }

    public String getSfDName() {
        return sfDName;
    }

    public void setSfDName(String sfDName) {
        this.sfDName = sfDName;
    }

    public String getSfEvaluate() {
        return sfEvaluate;
    }

    public void setSfEvaluate(String sfEvaluate) {
        this.sfEvaluate = sfEvaluate;
    }

    public String getSfSituation() {
        return sfSituation;
    }

    public void setSfSituation(String sfSituation) {
        this.sfSituation = sfSituation;
    }

    public String getSfProblem() {
        return sfProblem;
    }

    public void setSfProblem(String sfProblem) {
        this.sfProblem = sfProblem;
    }

    public String getSfMeasures() {
        return sfMeasures;
    }

    public void setSfMeasures(String sfMeasures) {
        this.sfMeasures = sfMeasures;
    }

    public String getSfRectification() {
        return sfRectification;
    }

    public void setSfRectification(String sfRectification) {
        this.sfRectification = sfRectification;
    }

    public String getStCode() {
        return stCode;
    }

    public void setStCode(String stCode) {
        this.stCode = stCode;
    }

    public String getStTitle() {
        return stTitle;
    }

    public void setStTitle(String stTitle) {
        this.stTitle = stTitle;
    }

    public String getStType() {
        return stType;
    }

    public void setStType(String stType) {
        this.stType = stType;
    }

    public String getStHandleType() {
        return stHandleType;
    }

    public void setStHandleType(String stHandleType) {
        this.stHandleType = stHandleType;
    }
}
