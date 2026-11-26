package com.jpmsoft.common.model.response.SecInfo;


import com.jpmsoft.common.model.requestAndResponse.SecCommonModel;

import java.util.Date;
import java.util.List;

/**
 * 资格审查小组
 */

public class SecQeGroupResponse extends SecCommonModel {
    private String qegId;
    private String secId;
    private String qegRemark;
    private String qegCreateUid;
    private String qegCreateUname;
    private Date qegCreateTime;
    private Integer qegState;
    private Date qegStateTime;
    private String qegScope;
    private String nScope;
    private String nBiddingType;
    private String nBiddingAgencyName;
    private String secBiddingType;
    private String tendereeName;

    private List<SecQeGroupDtlResponse> sqGroupDtls;
    private List<SecProjectResponse> secProjects;

    //单位简称
    private String nBidAgeAbbName;


    public String getQegId() {
        return qegId;
    }

    public void setQegId(String qegId) {
        this.qegId = qegId;
    }

    public String getSecId() {
        return secId;
    }

    public void setSecId(String secId) {
        this.secId = secId;
    }

    public String getQegRemark() {
        return qegRemark;
    }

    public void setQegRemark(String qegRemark) {
        this.qegRemark = qegRemark;
    }

    public String getQegCreateUid() {
        return qegCreateUid;
    }

    public void setQegCreateUid(String qegCreateUid) {
        this.qegCreateUid = qegCreateUid;
    }

    public String getQegCreateUname() {
        return qegCreateUname;
    }

    public void setQegCreateUname(String qegCreateUname) {
        this.qegCreateUname = qegCreateUname;
    }

    public Date getQegCreateTime() {
        return qegCreateTime;
    }

    public void setQegCreateTime(Date qegCreateTime) {
        this.qegCreateTime = qegCreateTime;
    }

    public Integer getQegState() {
        return qegState;
    }

    public void setQegState(Integer qegState) {
        this.qegState = qegState;
    }

    public Date getQegStateTime() {
        return qegStateTime;
    }

    public void setQegStateTime(Date qegStateTime) {
        this.qegStateTime = qegStateTime;
    }

    public String getQegScope() {
        return qegScope;
    }

    public void setQegScope(String qegScope) {
        this.qegScope = qegScope;
    }

    public String getnScope() {
        return nScope;
    }

    public void setnScope(String nScope) {
        this.nScope = nScope;
    }

    public String getnBiddingType() {
        return nBiddingType;
    }

    public void setnBiddingType(String nBiddingType) {
        this.nBiddingType = nBiddingType;
    }

    public String getnBiddingAgencyName() {
        return nBiddingAgencyName;
    }

    public void setnBiddingAgencyName(String nBiddingAgencyName) {
        this.nBiddingAgencyName = nBiddingAgencyName;
    }

    public String getSecBiddingType() {
        return secBiddingType;
    }

    public void setSecBiddingType(String secBiddingType) {
        this.secBiddingType = secBiddingType;
    }

    public String getTendereeName() {
        return tendereeName;
    }

    public void setTendereeName(String tendereeName) {
        this.tendereeName = tendereeName;
    }

    public List<SecQeGroupDtlResponse> getSqGroupDtls() {
        return sqGroupDtls;
    }

    public void setSqGroupDtls(List<SecQeGroupDtlResponse> sqGroupDtls) {
        this.sqGroupDtls = sqGroupDtls;
    }

    public List<SecProjectResponse> getSecProjects() {
        return secProjects;
    }

    public void setSecProjects(List<SecProjectResponse> secProjects) {
        this.secProjects = secProjects;
    }

    public String getnBidAgeAbbName() {
        return nBidAgeAbbName;
    }

    public void setnBidAgeAbbName(String nBidAgeAbbName) {
        this.nBidAgeAbbName = nBidAgeAbbName;
    }
}
