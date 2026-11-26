package com.jpmsoft.common.model.response.Contract;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 保函办退
 */

public class GuaranteeLetterBackResponse extends GuaranteeLetterResponseCommon {

    private String glbId;
    private String glsId;
    private String glbCompanyId;
    private Date glbDate;
    private String glbBase;
    private String glbRemark;
    private String glbCreateUid;
    private String glbCreateUname;
    private Date glbCreateTime;
    private Integer glbState;
    private Date glbStateTime;
    private Boolean glbCompletion;
    /************************************/

    private String glsCode;
    private String glsType;
    private String glbCompanyName;
    private String glsWarranteeNameHistory;
    private String pConstructionUnitNameHistory;
    private String glbCompanyNameHistory;

    private BigDecimal conMoneyTotal;

    private String companyPartyAName;

    private String companyPartybName;


    public BigDecimal getConMoneyTotal() {
        return conMoneyTotal;
    }

    public void setConMoneyTotal(BigDecimal conMoneyTotal) {
        this.conMoneyTotal = conMoneyTotal;
    }

    public String getCompanyPartyAName() {
        return companyPartyAName;
    }

    public void setCompanyPartyAName(String companyPartyAName) {
        this.companyPartyAName = companyPartyAName;
    }

    public String getCompanyPartybName() {
        return companyPartybName;
    }

    public void setCompanyPartybName(String companyPartybName) {
        this.companyPartybName = companyPartybName;
    }

    public String getGlbId() {
        return glbId;
    }

    public void setGlbId(String glbId) {
        this.glbId = glbId;
    }

    public String getGlsId() {
        return glsId;
    }

    public void setGlsId(String glsId) {
        this.glsId = glsId;
    }

    public String getGlbCompanyId() {
        return glbCompanyId;
    }

    public void setGlbCompanyId(String glbCompanyId) {
        this.glbCompanyId = glbCompanyId;
    }

    public Date getGlbDate() {
        return glbDate;
    }

    public void setGlbDate(Date glbDate) {
        this.glbDate = glbDate;
    }

    public String getGlbBase() {
        return glbBase;
    }

    public void setGlbBase(String glbBase) {
        this.glbBase = glbBase;
    }

    public String getGlbRemark() {
        return glbRemark;
    }

    public void setGlbRemark(String glbRemark) {
        this.glbRemark = glbRemark;
    }

    public String getGlbCreateUid() {
        return glbCreateUid;
    }

    public void setGlbCreateUid(String glbCreateUid) {
        this.glbCreateUid = glbCreateUid;
    }

    public String getGlbCreateUname() {
        return glbCreateUname;
    }

    public void setGlbCreateUname(String glbCreateUname) {
        this.glbCreateUname = glbCreateUname;
    }

    public Date getGlbCreateTime() {
        return glbCreateTime;
    }

    public void setGlbCreateTime(Date glbCreateTime) {
        this.glbCreateTime = glbCreateTime;
    }

    public Integer getGlbState() {
        return glbState;
    }

    public void setGlbState(Integer glbState) {
        this.glbState = glbState;
    }

    public Date getGlbStateTime() {
        return glbStateTime;
    }

    public void setGlbStateTime(Date glbStateTime) {
        this.glbStateTime = glbStateTime;
    }

    public Boolean getGlbCompletion() {
        return glbCompletion;
    }

    public void setGlbCompletion(Boolean glbCompletion) {
        this.glbCompletion = glbCompletion;
    }

    public String getGlsCode() {
        return glsCode;
    }

    public void setGlsCode(String glsCode) {
        this.glsCode = glsCode;
    }

    public String getGlsType() {
        return glsType;
    }

    public void setGlsType(String glsType) {
        this.glsType = glsType;
    }

    public String getGlbCompanyName() {
        return glbCompanyName;
    }

    public void setGlbCompanyName(String glbCompanyName) {
        this.glbCompanyName = glbCompanyName;
    }

    public String getGlsWarranteeNameHistory() {
        return glsWarranteeNameHistory;
    }

    public void setGlsWarranteeNameHistory(String glsWarranteeNameHistory) {
        this.glsWarranteeNameHistory = glsWarranteeNameHistory;
    }

    public String getpConstructionUnitNameHistory() {
        return pConstructionUnitNameHistory;
    }

    public void setpConstructionUnitNameHistory(String pConstructionUnitNameHistory) {
        this.pConstructionUnitNameHistory = pConstructionUnitNameHistory;
    }

    public String getGlbCompanyNameHistory() {
        return glbCompanyNameHistory;
    }

    public void setGlbCompanyNameHistory(String glbCompanyNameHistory) {
        this.glbCompanyNameHistory = glbCompanyNameHistory;
    }
}
