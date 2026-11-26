package com.jpmsoft.common.model.response.SecInfo;

import com.jpmsoft.common.model.requestAndResponse.SecCommonModel;

import java.util.Date;
import java.util.List;

/**
 * 资格审查报告
 */
public class SecQeReportResponse extends SecCommonModel {
    private String qerId;
    private String secId;
    private Date qerDate;
    private Integer qerBidderNum;
    private Integer qerBidderNumAdopt;
    private String qerResult;
    private String qerReport;
    private String qerCreateUid;
    private String qerCreateUname;
    private Date qerCreateTime;
    private Integer qerState;
    private Date qerStateTime;
    private String qerRemark;
    private List<SecQeGroupDtlResponse> sqGroupDtls;
    private List<SecProjectResponse> secProjects;
    public String getQerId() {
        return qerId;
    }

    public void setQerId(String qerId) {
        this.qerId = qerId;
    }

    public String getSecId() {
        return secId;
    }

    public void setSecId(String secId) {
        this.secId = secId;
    }

    public Date getQerDate() {
        return qerDate;
    }

    public void setQerDate(Date qerDate) {
        this.qerDate = qerDate;
    }

    public Integer getQerBidderNum() {
        return qerBidderNum;
    }

    public void setQerBidderNum(Integer qerBidderNum) {
        this.qerBidderNum = qerBidderNum;
    }

    public Integer getQerBidderNumAdopt() {
        return qerBidderNumAdopt;
    }

    public void setQerBidderNumAdopt(Integer qerBidderNumAdopt) {
        this.qerBidderNumAdopt = qerBidderNumAdopt;
    }

    public String getQerResult() {
        return qerResult;
    }

    public void setQerResult(String qerResult) {
        this.qerResult = qerResult;
    }

    public String getQerReport() {
        return qerReport;
    }

    public void setQerReport(String qerReport) {
        this.qerReport = qerReport;
    }

    public String getQerCreateUid() {
        return qerCreateUid;
    }

    public void setQerCreateUid(String qerCreateUid) {
        this.qerCreateUid = qerCreateUid;
    }

    public String getQerCreateUname() {
        return qerCreateUname;
    }

    public void setQerCreateUname(String qerCreateUname) {
        this.qerCreateUname = qerCreateUname;
    }

    public Date getQerCreateTime() {
        return qerCreateTime;
    }

    public void setQerCreateTime(Date qerCreateTime) {
        this.qerCreateTime = qerCreateTime;
    }

    public Integer getQerState() {
        return qerState;
    }

    public void setQerState(Integer qerState) {
        this.qerState = qerState;
    }

    public Date getQerStateTime() {
        return qerStateTime;
    }

    public void setQerStateTime(Date qerStateTime) {
        this.qerStateTime = qerStateTime;
    }

    public String getQerRemark() {
        return qerRemark;
    }

    public void setQerRemark(String qerRemark) {
        this.qerRemark = qerRemark;
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
}
