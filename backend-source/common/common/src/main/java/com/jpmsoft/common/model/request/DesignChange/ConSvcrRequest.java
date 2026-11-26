package com.jpmsoft.common.model.request.DesignChange;

import com.jpmsoft.common.model.BaseFileRequest;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 现场签证变更记录
 */
public class ConSvcrRequest extends BaseFileRequest {

    private String svcrId;
    private String svvId;
    private String pId;
    private String conId;
    private String svcrCode;
    private String svcrProjectName;
    private String svcrMajor;
    private String svcrProposeId;
    private Date svcrDate;
    private String svcrContentSummary;
    private String svcrContent;
    private String svcrSchemeBefore;
    private String svcrSchemeAfter;
    private BigDecimal svcrTotalBefore;
    private BigDecimal svcrTotalAfter;
    private BigDecimal svcrTotalFirstTrial;
    private String svcrCreateUid;
    private String svcrCreateUname;
    private Date svcrCreateTime;
    private Integer svcrState;
    private Date svcrStateTime;
    private String svisId;
    private String svisProjectName;
    private String pName;
    private String conName;

    public String getSvcrId() {
        return svcrId;
    }

    public void setSvcrId(String svcrId) {
        this.svcrId = svcrId;
    }

    public String getSvvId() {
        return svvId;
    }

    public void setSvvId(String svvId) {
        this.svvId = svvId;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getConId() {
        return conId;
    }

    public void setConId(String conId) {
        this.conId = conId;
    }

    public String getSvcrCode() {
        return svcrCode;
    }

    public void setSvcrCode(String svcrCode) {
        this.svcrCode = svcrCode;
    }

    public String getSvcrProjectName() {
        return svcrProjectName;
    }

    public void setSvcrProjectName(String svcrProjectName) {
        this.svcrProjectName = svcrProjectName;
    }

    public String getSvcrMajor() {
        return svcrMajor;
    }

    public void setSvcrMajor(String svcrMajor) {
        this.svcrMajor = svcrMajor;
    }

    public String getSvcrProposeId() {
        return svcrProposeId;
    }

    public void setSvcrProposeId(String svcrProposeId) {
        this.svcrProposeId = svcrProposeId;
    }

    public Date getSvcrDate() {
        return svcrDate;
    }

    public void setSvcrDate(Date svcrDate) {
        this.svcrDate = svcrDate;
    }

    public String getSvcrContentSummary() {
        return svcrContentSummary;
    }

    public void setSvcrContentSummary(String svcrContentSummary) {
        this.svcrContentSummary = svcrContentSummary;
    }

    public String getSvcrContent() {
        return svcrContent;
    }

    public void setSvcrContent(String svcrContent) {
        this.svcrContent = svcrContent;
    }

    public String getSvcrSchemeBefore() {
        return svcrSchemeBefore;
    }

    public void setSvcrSchemeBefore(String svcrSchemeBefore) {
        this.svcrSchemeBefore = svcrSchemeBefore;
    }

    public String getSvcrSchemeAfter() {
        return svcrSchemeAfter;
    }

    public void setSvcrSchemeAfter(String svcrSchemeAfter) {
        this.svcrSchemeAfter = svcrSchemeAfter;
    }

    public BigDecimal getSvcrTotalBefore() {
        return svcrTotalBefore;
    }

    public void setSvcrTotalBefore(BigDecimal svcrTotalBefore) {
        this.svcrTotalBefore = svcrTotalBefore;
    }

    public BigDecimal getSvcrTotalAfter() {
        return svcrTotalAfter;
    }

    public void setSvcrTotalAfter(BigDecimal svcrTotalAfter) {
        this.svcrTotalAfter = svcrTotalAfter;
    }

    public BigDecimal getSvcrTotalFirstTrial() {
        return svcrTotalFirstTrial;
    }

    public void setSvcrTotalFirstTrial(BigDecimal svcrTotalFirstTrial) {
        this.svcrTotalFirstTrial = svcrTotalFirstTrial;
    }

    public String getSvcrCreateUid() {
        return svcrCreateUid;
    }

    public void setSvcrCreateUid(String svcrCreateUid) {
        this.svcrCreateUid = svcrCreateUid;
    }

    public String getSvcrCreateUname() {
        return svcrCreateUname;
    }

    public void setSvcrCreateUname(String svcrCreateUname) {
        this.svcrCreateUname = svcrCreateUname;
    }

    public Date getSvcrCreateTime() {
        return svcrCreateTime;
    }

    public void setSvcrCreateTime(Date svcrCreateTime) {
        this.svcrCreateTime = svcrCreateTime;
    }

    public Integer getSvcrState() {
        return svcrState;
    }

    public void setSvcrState(Integer svcrState) {
        this.svcrState = svcrState;
    }

    public Date getSvcrStateTime() {
        return svcrStateTime;
    }

    public void setSvcrStateTime(Date svcrStateTime) {
        this.svcrStateTime = svcrStateTime;
    }

    public String getSvisId() {
        return svisId;
    }

    public void setSvisId(String svisId) {
        this.svisId = svisId;
    }

    public String getSvisProjectName() {
        return svisProjectName;
    }

    public void setSvisProjectName(String svisProjectName) {
        this.svisProjectName = svisProjectName;
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
}
