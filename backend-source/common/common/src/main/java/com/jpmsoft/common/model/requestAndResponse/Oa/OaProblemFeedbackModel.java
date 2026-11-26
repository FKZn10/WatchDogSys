package com.jpmsoft.common.model.requestAndResponse.Oa;

import com.jpmsoft.common.model.ProjectCommonRes;

import java.util.Date;

/**
 * 问题反馈
 */
public class OaProblemFeedbackModel extends ProjectCommonRes {

    private String pmfId;
    private String pmdId;
    private String pmfContent;
    private String pmfUid;
    private String pmfUname;
    private Date pmfTime;
    private String pmfCreateUid;
    private String pmfCreateUname;
    private Date pmfCreateTime;
    private Integer pmfState;
    private Date pmfStateTime;
    private String pmfCode;
    private Integer pmfStatus;

    private String secName;
    private String conName;
    private String pmdTitle;
    private String pmdType;
    private String pmdContent;
    private String conId;
    private String secId;
    private String pmdCode;
    private String pmdSolution;

    public String getPmdSolution() {
        return pmdSolution;
    }

    public void setPmdSolution(String pmdSolution) {
        this.pmdSolution = pmdSolution;
    }

    public String getPmdCode() {
        return pmdCode;
    }

    public void setPmdCode(String pmdCode) {
        this.pmdCode = pmdCode;
    }

    public String getPmfId() {
        return pmfId;
    }

    public void setPmfId(String pmfId) {
        this.pmfId = pmfId;
    }

    public String getPmdId() {
        return pmdId;
    }

    public void setPmdId(String pmdId) {
        this.pmdId = pmdId;
    }

    public String getPmfContent() {
        return pmfContent;
    }

    public void setPmfContent(String pmfContent) {
        this.pmfContent = pmfContent;
    }

    public String getPmfUid() {
        return pmfUid;
    }

    public void setPmfUid(String pmfUid) {
        this.pmfUid = pmfUid;
    }

    public String getPmfUname() {
        return pmfUname;
    }

    public void setPmfUname(String pmfUname) {
        this.pmfUname = pmfUname;
    }

    public Date getPmfTime() {
        return pmfTime;
    }

    public void setPmfTime(Date pmfTime) {
        this.pmfTime = pmfTime;
    }

    public String getPmfCreateUid() {
        return pmfCreateUid;
    }

    public void setPmfCreateUid(String pmfCreateUid) {
        this.pmfCreateUid = pmfCreateUid;
    }

    public String getPmfCreateUname() {
        return pmfCreateUname;
    }

    public void setPmfCreateUname(String pmfCreateUname) {
        this.pmfCreateUname = pmfCreateUname;
    }

    public Date getPmfCreateTime() {
        return pmfCreateTime;
    }

    public void setPmfCreateTime(Date pmfCreateTime) {
        this.pmfCreateTime = pmfCreateTime;
    }

    public Integer getPmfState() {
        return pmfState;
    }

    public void setPmfState(Integer pmfState) {
        this.pmfState = pmfState;
    }

    public Date getPmfStateTime() {
        return pmfStateTime;
    }

    public void setPmfStateTime(Date pmfStateTime) {
        this.pmfStateTime = pmfStateTime;
    }

    public String getPmfCode() {
        return pmfCode;
    }

    public void setPmfCode(String pmfCode) {
        this.pmfCode = pmfCode;
    }

    public Integer getPmfStatus() {
        return pmfStatus;
    }

    public void setPmfStatus(Integer pmfStatus) {
        this.pmfStatus = pmfStatus;
    }

    public String getSecName() {
        return secName;
    }

    public void setSecName(String secName) {
        this.secName = secName;
    }

    public String getConName() {
        return conName;
    }

    public void setConName(String conName) {
        this.conName = conName;
    }

    public String getPmdTitle() {
        return pmdTitle;
    }

    public void setPmdTitle(String pmdTitle) {
        this.pmdTitle = pmdTitle;
    }

    public String getPmdType() {
        return pmdType;
    }

    public void setPmdType(String pmdType) {
        this.pmdType = pmdType;
    }

    public String getPmdContent() {
        return pmdContent;
    }

    public void setPmdContent(String pmdContent) {
        this.pmdContent = pmdContent;
    }

    public String getConId() {
        return conId;
    }

    public void setConId(String conId) {
        this.conId = conId;
    }

    public String getSecId() {
        return secId;
    }

    public void setSecId(String secId) {
        this.secId = secId;
    }
}
