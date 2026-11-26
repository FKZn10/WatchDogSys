package com.jpmsoft.common.model.requestAndResponse.SecInfo;

import com.jpmsoft.common.model.BaseFileRequest;
import com.jpmsoft.common.model.response.SecInfo.SecProjectResponse;

import java.util.Date;
import java.util.List;

/**
 * 招标公告发布
 */
public class SecAnnouncementBiddingModel extends BaseFileRequest {

    private String sabId;
    private String secId;
    private Date sabAnnouncementTime;
    private Date sabClosBidTime;
    private String sabAnnouncement;
    private String sabRemarks;
    private String sabCreateUid;
    private String sabCreateUname;
    private Date sabCreateTime;
    private Integer state;
    private Date stateTime;


    private String secName;
    private String secCode;
    private List<String> sabAnnouncementTimes;
    private List<String> sabClosBidTimes;
    private List<SecProjectResponse> secProjects;


    public String getSabId() {
        return sabId;
    }

    public void setSabId(String sabId) {
        this.sabId = sabId;
    }

    public Date getSabAnnouncementTime() {
        return sabAnnouncementTime;
    }

    public void setSabAnnouncementTime(Date sabAnnouncementTime) {
        this.sabAnnouncementTime = sabAnnouncementTime;
    }

    public Date getSabClosBidTime() {
        return sabClosBidTime;
    }

    public void setSabClosBidTime(Date sabClosBidTime) {
        this.sabClosBidTime = sabClosBidTime;
    }

    public String getSabAnnouncement() {
        return sabAnnouncement;
    }

    public void setSabAnnouncement(String sabAnnouncement) {
        this.sabAnnouncement = sabAnnouncement;
    }

    public String getSabRemarks() {
        return sabRemarks;
    }

    public void setSabRemarks(String sabRemarks) {
        this.sabRemarks = sabRemarks;
    }

    public String getSabCreateUid() {
        return sabCreateUid;
    }

    public void setSabCreateUid(String sabCreateUid) {
        this.sabCreateUid = sabCreateUid;
    }

    public String getSabCreateUname() {
        return sabCreateUname;
    }

    public void setSabCreateUname(String sabCreateUname) {
        this.sabCreateUname = sabCreateUname;
    }

    public Date getSabCreateTime() {
        return sabCreateTime;
    }

    public void setSabCreateTime(Date sabCreateTime) {
        this.sabCreateTime = sabCreateTime;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Date getStateTime() {
        return stateTime;
    }

    public void setStateTime(Date stateTime) {
        this.stateTime = stateTime;
    }

    public String getSecName() {
        return secName;
    }

    public void setSecName(String secName) {
        this.secName = secName;
    }

    public String getSecCode() {
        return secCode;
    }

    public void setSecCode(String secCode) {
        this.secCode = secCode;
    }

    public List<String> getSabAnnouncementTimes() {
        return sabAnnouncementTimes;
    }

    public void setSabAnnouncementTimes(List<String> sabAnnouncementTimes) {
        this.sabAnnouncementTimes = sabAnnouncementTimes;
    }

    public List<String> getSabClosBidTimes() {
        return sabClosBidTimes;
    }

    public void setSabClosBidTimes(List<String> sabClosBidTimes) {
        this.sabClosBidTimes = sabClosBidTimes;
    }

    public String getSecId() {
        return secId;
    }

    public void setSecId(String secId) {
        this.secId = secId;
    }

    public List<SecProjectResponse> getSecProjects() {
        return secProjects;
    }

    public void setSecProjects(List<SecProjectResponse> secProjects) {
        this.secProjects = secProjects;
    }
}
