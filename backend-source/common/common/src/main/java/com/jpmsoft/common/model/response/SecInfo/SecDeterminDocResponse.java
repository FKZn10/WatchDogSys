package com.jpmsoft.common.model.response.SecInfo;

import com.jpmsoft.common.model.requestAndResponse.SecCommonModel;

import java.util.Date;
import java.util.List;

/**
 * 定标资料
 */
public class SecDeterminDocResponse extends SecCommonModel {

    private String bdId;
    private String secId;
    private Integer bdRecommend;
    private String bdRecommendUnit;
    private Integer bdExcellentTeam;
    private String bdExcellentTeamUnit;
    private String bdRemark;
    private String bdCreateUid;
    private String bdCreateUname;
    private String bdCreateTime;
    private Integer bdState;
    private Date bdStateTime;

    /*****************/
    private String bdRecommendUnitName;
    private String bdExcellentTeamUnitName;
    private List<SecProjectResponse> secProjects;
    //单位简称
    private String  bdRecomAbbName;
    private String  bdExcellTeamAbbName;

    public String getBdId() {
        return bdId;
    }

    public void setBdId(String bdId) {
        this.bdId = bdId;
    }

    public String getSecId() {
        return secId;
    }

    public void setSecId(String secId) {
        this.secId = secId;
    }

    public Integer getBdRecommend() {
        return bdRecommend;
    }

    public void setBdRecommend(Integer bdRecommend) {
        this.bdRecommend = bdRecommend;
    }

    public String getBdRecommendUnit() {
        return bdRecommendUnit;
    }

    public void setBdRecommendUnit(String bdRecommendUnit) {
        this.bdRecommendUnit = bdRecommendUnit;
    }

    public Integer getBdExcellentTeam() {
        return bdExcellentTeam;
    }

    public void setBdExcellentTeam(Integer bdExcellentTeam) {
        this.bdExcellentTeam = bdExcellentTeam;
    }

    public String getBdExcellentTeamUnit() {
        return bdExcellentTeamUnit;
    }

    public void setBdExcellentTeamUnit(String bdExcellentTeamUnit) {
        this.bdExcellentTeamUnit = bdExcellentTeamUnit;
    }

    public String getBdRemark() {
        return bdRemark;
    }

    public void setBdRemark(String bdRemark) {
        this.bdRemark = bdRemark;
    }

    public String getBdCreateUid() {
        return bdCreateUid;
    }

    public void setBdCreateUid(String bdCreateUid) {
        this.bdCreateUid = bdCreateUid;
    }

    public String getBdCreateUname() {
        return bdCreateUname;
    }

    public void setBdCreateUname(String bdCreateUname) {
        this.bdCreateUname = bdCreateUname;
    }

    public Integer getBdState() {
        return bdState;
    }

    public void setBdState(Integer bdState) {
        this.bdState = bdState;
    }

    public Date getBdStateTime() {
        return bdStateTime;
    }

    public void setBdStateTime(Date bdStateTime) {
        this.bdStateTime = bdStateTime;
    }

    public String getBdCreateTime() {
        return bdCreateTime;
    }

    public void setBdCreateTime(String bdCreateTime) {
        this.bdCreateTime = bdCreateTime;
    }

    public String getBdRecommendUnitName() {
        return bdRecommendUnitName;
    }

    public void setBdRecommendUnitName(String bdRecommendUnitName) {
        this.bdRecommendUnitName = bdRecommendUnitName;
    }

    public String getBdExcellentTeamUnitName() {
        return bdExcellentTeamUnitName;
    }

    public void setBdExcellentTeamUnitName(String bdExcellentTeamUnitName) {
        this.bdExcellentTeamUnitName = bdExcellentTeamUnitName;
    }

    public List<SecProjectResponse> getSecProjects() {
        return secProjects;
    }

    public void setSecProjects(List<SecProjectResponse> secProjects) {
        this.secProjects = secProjects;
    }

    public String getBdRecomAbbName() {
        return bdRecomAbbName;
    }

    public void setBdRecomAbbName(String bdRecomAbbName) {
        this.bdRecomAbbName = bdRecomAbbName;
    }

    public String getBdExcellTeamAbbName() {
        return bdExcellTeamAbbName;
    }

    public void setBdExcellTeamAbbName(String bdExcellTeamAbbName) {
        this.bdExcellTeamAbbName = bdExcellTeamAbbName;
    }
}
