package com.jpmsoft.common.model.requestAndResponse.SecInfo;

import com.jpmsoft.common.model.BaseFileRequest;
import com.jpmsoft.common.model.response.SecInfo.SecProjectResponse;

import java.util.Date;
import java.util.List;

/**
 * 投标保证金
 */
public class SecCalibrationSchemeModel extends BaseFileRequest {

    private String scsId;
    private String secId;
    private Date scsBidClosingTime;
    private String scsApprovalDescription;
    private String scsMinutesMeeting;
    private String scsCalibrationScheme;
    private String scsCreateUid;
    private String scsCreateUname;
    private Date scsCreateTime;
    private Integer scsState;
    private Date scsStateTime;

    private String secName;
    private String secType;
    private String dScope;
    private String secBiddingType;
    private String dDeterminType;
    private Date secStartTime;
    private Date secEndTime;
    private String scsMinutesMeetingIds;
    private String scsCalibrationSchemeIds;
    private List<SecProjectResponse> secProjects;

    public String getScsId() {
        return scsId;
    }

    public void setScsId(String scsId) {
        this.scsId = scsId;
    }

    public String getSecId() {
        return secId;
    }

    public void setSecId(String secId) {
        this.secId = secId;
    }

    public Date getScsBidClosingTime() {
        return scsBidClosingTime;
    }

    public void setScsBidClosingTime(Date scsBidClosingTime) {
        this.scsBidClosingTime = scsBidClosingTime;
    }

    public String getScsApprovalDescription() {
        return scsApprovalDescription;
    }

    public void setScsApprovalDescription(String scsApprovalDescription) {
        this.scsApprovalDescription = scsApprovalDescription;
    }

    public String getScsMinutesMeeting() {
        return scsMinutesMeeting;
    }

    public void setScsMinutesMeeting(String scsMinutesMeeting) {
        this.scsMinutesMeeting = scsMinutesMeeting;
    }

    public String getScsCalibrationScheme() {
        return scsCalibrationScheme;
    }

    public void setScsCalibrationScheme(String scsCalibrationScheme) {
        this.scsCalibrationScheme = scsCalibrationScheme;
    }

    public String getScsCreateUid() {
        return scsCreateUid;
    }

    public void setScsCreateUid(String scsCreateUid) {
        this.scsCreateUid = scsCreateUid;
    }

    public String getScsCreateUname() {
        return scsCreateUname;
    }

    public void setScsCreateUname(String scsCreateUname) {
        this.scsCreateUname = scsCreateUname;
    }

    public Date getScsCreateTime() {
        return scsCreateTime;
    }

    public void setScsCreateTime(Date scsCreateTime) {
        this.scsCreateTime = scsCreateTime;
    }

    public Integer getScsState() {
        return scsState;
    }

    public void setScsState(Integer scsState) {
        this.scsState = scsState;
    }

    public Date getScsStateTime() {
        return scsStateTime;
    }

    public void setScsStateTime(Date scsStateTime) {
        this.scsStateTime = scsStateTime;
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

    public String getdScope() {
        return dScope;
    }

    public void setdScope(String dScope) {
        this.dScope = dScope;
    }

    public String getSecBiddingType() {
        return secBiddingType;
    }

    public void setSecBiddingType(String secBiddingType) {
        this.secBiddingType = secBiddingType;
    }

    public String getdDeterminType() {
        return dDeterminType;
    }

    public void setdDeterminType(String dDeterminType) {
        this.dDeterminType = dDeterminType;
    }

    public Date getSecStartTime() {
        return secStartTime;
    }

    public void setSecStartTime(Date secStartTime) {
        this.secStartTime = secStartTime;
    }

    public Date getSecEndTime() {
        return secEndTime;
    }

    public void setSecEndTime(Date secEndTime) {
        this.secEndTime = secEndTime;
    }

    public String getScsMinutesMeetingIds() {
        return scsMinutesMeetingIds;
    }

    public void setScsMinutesMeetingIds(String scsMinutesMeetingIds) {
        this.scsMinutesMeetingIds = scsMinutesMeetingIds;
    }

    public String getScsCalibrationSchemeIds() {
        return scsCalibrationSchemeIds;
    }

    public void setScsCalibrationSchemeIds(String scsCalibrationSchemeIds) {
        this.scsCalibrationSchemeIds = scsCalibrationSchemeIds;
    }

    public List<SecProjectResponse> getSecProjects() {
        return secProjects;
    }

    public void setSecProjects(List<SecProjectResponse> secProjects) {
        this.secProjects = secProjects;
    }
}
