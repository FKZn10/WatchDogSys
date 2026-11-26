package com.jpmsoft.common.model.response.Completion;

import com.jpmsoft.common.model.ProjectCommonRes;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 竣工备案
 */
public class CompletionFilingResponse extends ProjectCommonRes implements Serializable {

    private static final long serialVersionUID = -167544472452012937L;
    private String cfId;
    private String pId;
    private String secId;
    private String conId;
    private String cfType;
    private String cfContractorId;
    private String cfRemark;
    private String cfDoc;
    private String cfCreateUid;
    private String cfCreateUname;
    private Date cfCreateTime;
    private Integer cfState;
    private Date cfStateTime;

    private String conName;
    private String secName;
    private String cfContractorName;
    private String cfContractorNameHistory;
    private String cfContractorAbbName;
    private List<CompletionFilingDetailsResponse> completionFilingDetails;

    public List<CompletionFilingDetailsResponse> getCompletionFilingDetails() {
        return completionFilingDetails;
    }

    public void setCompletionFilingDetails(List<CompletionFilingDetailsResponse> completionFilingDetails) {
        this.completionFilingDetails = completionFilingDetails;
    }

    public String getCfId() {
        return cfId;
    }

    public void setCfId(String cfId) {
        this.cfId = cfId;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getSecId() {
        return secId;
    }

    public void setSecId(String secId) {
        this.secId = secId;
    }

    public String getConId() {
        return conId;
    }

    public void setConId(String conId) {
        this.conId = conId;
    }

    public String getCfType() {
        return cfType;
    }

    public void setCfType(String cfType) {
        this.cfType = cfType;
    }

    public String getCfContractorId() {
        return cfContractorId;
    }

    public void setCfContractorId(String cfContractorId) {
        this.cfContractorId = cfContractorId;
    }

    public String getCfRemark() {
        return cfRemark;
    }

    public void setCfRemark(String cfRemark) {
        this.cfRemark = cfRemark;
    }

    public String getCfDoc() {
        return cfDoc;
    }

    public void setCfDoc(String cfDoc) {
        this.cfDoc = cfDoc;
    }

    public String getCfCreateUid() {
        return cfCreateUid;
    }

    public void setCfCreateUid(String cfCreateUid) {
        this.cfCreateUid = cfCreateUid;
    }

    public String getCfCreateUname() {
        return cfCreateUname;
    }

    public void setCfCreateUname(String cfCreateUname) {
        this.cfCreateUname = cfCreateUname;
    }

    public Date getCfCreateTime() {
        return cfCreateTime;
    }

    public void setCfCreateTime(Date cfCreateTime) {
        this.cfCreateTime = cfCreateTime;
    }

    public Integer getCfState() {
        return cfState;
    }

    public void setCfState(Integer cfState) {
        this.cfState = cfState;
    }

    public Date getCfStateTime() {
        return cfStateTime;
    }

    public void setCfStateTime(Date cfStateTime) {
        this.cfStateTime = cfStateTime;
    }

    public String getConName() {
        return conName;
    }

    public void setConName(String conName) {
        this.conName = conName;
    }

    public String getSecName() {
        return secName;
    }

    public void setSecName(String secName) {
        this.secName = secName;
    }

    public String getCfContractorName() {
        return cfContractorName;
    }

    public void setCfContractorName(String cfContractorName) {
        this.cfContractorName = cfContractorName;
    }

    public String getCfContractorNameHistory() {
        return cfContractorNameHistory;
    }

    public void setCfContractorNameHistory(String cfContractorNameHistory) {
        this.cfContractorNameHistory = cfContractorNameHistory;
    }

    public String getCfContractorAbbName() {
        return cfContractorAbbName;
    }

    public void setCfContractorAbbName(String cfContractorAbbName) {
        this.cfContractorAbbName = cfContractorAbbName;
    }
}
