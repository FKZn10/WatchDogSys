package com.jpmsoft.common.model.request.Completion;

import com.jpmsoft.common.base.FileFields;
import com.jpmsoft.common.base.ProcessFields;
import com.jpmsoft.common.enums.ProcessFieldEnum;
import com.jpmsoft.common.model.BaseFileRequest;

import java.util.Date;
import java.util.List;

/**
 * 竣工备案
 */
public class CompletionFilingRequest extends BaseFileRequest {
    @ProcessFields(type = ProcessFieldEnum.id)
    private String cfId;
    private String pId;
    private String secId;
    private String conId;
    private String cfType;
    private String cfContractorId;
    private String cfRemark;
    @FileFields(index = 1)
    private String cfDoc;
    @ProcessFields(type = ProcessFieldEnum.createUid)
    private String cfCreateUid;
    @ProcessFields(type = ProcessFieldEnum.createUname)
    private String cfCreateUname;
    @ProcessFields(type = ProcessFieldEnum.createTime)
    private Date cfCreateTime;
    @ProcessFields(type = ProcessFieldEnum.state)
    private Integer cfState;
    @ProcessFields(type = ProcessFieldEnum.stateTime)
    private Date cfStateTime;

    private String pName;
    private String conName;
    @FileFields(index = 1, type = "cfDocIds")
    private String cfDocIds;
    private List<String> cfCreateTimes;


    private List<CompletionFilingDetailsRequest> completionFilingDetails;

    public List<CompletionFilingDetailsRequest> getCompletionFilingDetails() {
        return completionFilingDetails;
    }

    public void setCompletionFilingDetails(List<CompletionFilingDetailsRequest> completionFilingDetails) {
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

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public String getConName() {
        return conName;
    }

    public void setConName(String conName) {
        this.conName = conName;
    }

    public String getCfDocIds() {
        return cfDocIds;
    }

    public void setCfDocIds(String cfDocIds) {
        this.cfDocIds = cfDocIds;
    }

    public List<String> getCfCreateTimes() {
        return cfCreateTimes;
    }

    public void setCfCreateTimes(List<String> cfCreateTimes) {
        this.cfCreateTimes = cfCreateTimes;
    }
}
