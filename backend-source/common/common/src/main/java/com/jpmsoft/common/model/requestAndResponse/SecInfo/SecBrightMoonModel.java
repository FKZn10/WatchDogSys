package com.jpmsoft.common.model.requestAndResponse.SecInfo;

import com.jpmsoft.common.model.BaseFileRequest;
import com.jpmsoft.common.model.response.SecInfo.SecProjectResponse;

import java.util.Date;
import java.util.List;

/**
 * 招标清标
 */
public class SecBrightMoonModel extends BaseFileRequest {

    private String sbmId;
    private String secId;
    private Date sbmStartTime;
    private Date sbmEntTime;
    private String sbmBidClearanceReport;
    private String sbmListBidClearingTeams;
    private String sbmContractorInformation;
    private String sbmCreateUid;
    private String sbmCreateUname;
    private Date sbmCreateTime;
    private Integer sbmState;
    private Date sbmStateTime;

    private String sbmBidClearanceReportIds;
    private String sbmListBidClearingTeamsIds;
    private String sbmContractorInformationIds;
    private String secName;
    private String secType;
    private List<SecProjectResponse> secProjects;

    public String getSbmId() {
        return sbmId;
    }

    public void setSbmId(String sbmId) {
        this.sbmId = sbmId;
    }

    public String getSecId() {
        return secId;
    }

    public void setSecId(String secId) {
        this.secId = secId;
    }

    public Date getSbmStartTime() {
        return sbmStartTime;
    }

    public void setSbmStartTime(Date sbmStartTime) {
        this.sbmStartTime = sbmStartTime;
    }

    public Date getSbmEntTime() {
        return sbmEntTime;
    }

    public void setSbmEntTime(Date sbmEntTime) {
        this.sbmEntTime = sbmEntTime;
    }

    public String getSbmBidClearanceReport() {
        return sbmBidClearanceReport;
    }

    public void setSbmBidClearanceReport(String sbmBidClearanceReport) {
        this.sbmBidClearanceReport = sbmBidClearanceReport;
    }

    public String getSbmListBidClearingTeams() {
        return sbmListBidClearingTeams;
    }

    public void setSbmListBidClearingTeams(String sbmListBidClearingTeams) {
        this.sbmListBidClearingTeams = sbmListBidClearingTeams;
    }

    public String getSbmContractorInformation() {
        return sbmContractorInformation;
    }

    public void setSbmContractorInformation(String sbmContractorInformation) {
        this.sbmContractorInformation = sbmContractorInformation;
    }

    public String getSbmCreateUid() {
        return sbmCreateUid;
    }

    public void setSbmCreateUid(String sbmCreateUid) {
        this.sbmCreateUid = sbmCreateUid;
    }

    public String getSbmCreateUname() {
        return sbmCreateUname;
    }

    public void setSbmCreateUname(String sbmCreateUname) {
        this.sbmCreateUname = sbmCreateUname;
    }

    public Date getSbmCreateTime() {
        return sbmCreateTime;
    }

    public void setSbmCreateTime(Date sbmCreateTime) {
        this.sbmCreateTime = sbmCreateTime;
    }

    public Integer getSbmState() {
        return sbmState;
    }

    public void setSbmState(Integer sbmState) {
        this.sbmState = sbmState;
    }

    public Date getSbmStateTime() {
        return sbmStateTime;
    }

    public void setSbmStateTime(Date sbmStateTime) {
        this.sbmStateTime = sbmStateTime;
    }

    public String getSbmBidClearanceReportIds() {
        return sbmBidClearanceReportIds;
    }

    public void setSbmBidClearanceReportIds(String sbmBidClearanceReportIds) {
        this.sbmBidClearanceReportIds = sbmBidClearanceReportIds;
    }

    public String getSbmListBidClearingTeamsIds() {
        return sbmListBidClearingTeamsIds;
    }

    public void setSbmListBidClearingTeamsIds(String sbmListBidClearingTeamsIds) {
        this.sbmListBidClearingTeamsIds = sbmListBidClearingTeamsIds;
    }

    public String getSbmContractorInformationIds() {
        return sbmContractorInformationIds;
    }

    public void setSbmContractorInformationIds(String sbmContractorInformationIds) {
        this.sbmContractorInformationIds = sbmContractorInformationIds;
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

    public List<SecProjectResponse> getSecProjects() {
        return secProjects;
    }

    public void setSecProjects(List<SecProjectResponse> secProjects) {
        this.secProjects = secProjects;
    }
}
