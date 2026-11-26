package com.jpmsoft.common.model.requestAndResponse.SecInfo;

import com.jpmsoft.common.model.BaseFileRequest;
import com.jpmsoft.common.model.response.SecInfo.SecProjectResponse;

import java.util.Date;
import java.util.List;

/**
 * 询价小组
 */
public class SecInquiryTeamModel extends BaseFileRequest {

    private String sitId;
    private String secId;
    private String sitScope;
    private String sitRemarks;
    private String sitCreateUid;
    private Date sitCreateTime;
    private String sitCreateUname;
    private Integer state;
    private Date stateTime;

    private String secName;
    private String secType;
    private String secBiddingType;
    private String secTendereeName;
    private List<SecInquiryTeamGroupModel>  secInquiryTeamGroupModels;
    private List<SecProjectResponse> secProjects;
    //单位简称
    private String secTendereeAbbName;


    public String getSitId() {
        return sitId;
    }

    public void setSitId(String sitId) {
        this.sitId = sitId;
    }

    public String getSecId() {
        return secId;
    }

    public void setSecId(String secId) {
        this.secId = secId;
    }

    public String getSitScope() {
        return sitScope;
    }

    public void setSitScope(String sitScope) {
        this.sitScope = sitScope;
    }

    public String getSitRemarks() {
        return sitRemarks;
    }

    public void setSitRemarks(String sitRemarks) {
        this.sitRemarks = sitRemarks;
    }

    public String getSitCreateUid() {
        return sitCreateUid;
    }

    public void setSitCreateUid(String sitCreateUid) {
        this.sitCreateUid = sitCreateUid;
    }

    public Date getSitCreateTime() {
        return sitCreateTime;
    }

    public void setSitCreateTime(Date sitCreateTime) {
        this.sitCreateTime = sitCreateTime;
    }

    public String getSitCreateUname() {
        return sitCreateUname;
    }

    public void setSitCreateUname(String sitCreateUname) {
        this.sitCreateUname = sitCreateUname;
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

    public String getSecTendereeName() {
        return secTendereeName;
    }

    public void setSecTendereeName(String secTendereeName) {
        this.secTendereeName = secTendereeName;
    }

    public List<SecInquiryTeamGroupModel> getSecInquiryTeamGroupModels() {
        return secInquiryTeamGroupModels;
    }

    public void setSecInquiryTeamGroupModels(List<SecInquiryTeamGroupModel> secInquiryTeamGroupModels) {
        this.secInquiryTeamGroupModels = secInquiryTeamGroupModels;
    }

    public List<SecProjectResponse> getSecProjects() {
        return secProjects;
    }

    public void setSecProjects(List<SecProjectResponse> secProjects) {
        this.secProjects = secProjects;
    }

    public String getSecTendereeAbbName() {
        return secTendereeAbbName;
    }

    public void setSecTendereeAbbName(String secTendereeAbbName) {
        this.secTendereeAbbName = secTendereeAbbName;
    }
}
