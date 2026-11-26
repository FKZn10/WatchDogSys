package com.jpmsoft.common.model.request.SecInfo;

import com.jpmsoft.common.model.BaseFileRequest;

import java.util.Date;
import java.util.List;

/**
 * 资格审查报告
 */
public class SecQeReportRequest extends BaseFileRequest {
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
    private String qerReportIds;

    private String secName;
    //创建时间（范围筛选）、
    private List<String>  qerCreateTimes;
    public String getQerReportIds() {
        return qerReportIds;
    }

    public void setQerReportIds(String qerReportIds) {
        this.qerReportIds = qerReportIds;
    }

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

    public String getSecName() {
        return secName;
    }

    public void setSecName(String secName) {
        this.secName = secName;
    }

    public List<String> getQerCreateTimes() {
        return qerCreateTimes;
    }

    public void setQerCreateTimes(List<String> qerCreateTimes) {
        this.qerCreateTimes = qerCreateTimes;
    }
}
