package com.jpmsoft.common.model.request.Contract;

import com.jpmsoft.common.model.BaseFileRequest;

import java.util.Date;

/**
 * 保函办退
 */

public class GuaranteeLetterBackRequest extends BaseFileRequest {

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


    public String pName;
    public String conName;
    public String secName;
    public String glbCompanyName;
    private String glsCode;



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

    public String getSecName() {
        return secName;
    }

    public void setSecName(String secName) {
        this.secName = secName;
    }

    public String getGlbCompanyName() {
        return glbCompanyName;
    }

    public void setGlbCompanyName(String glbCompanyName) {
        this.glbCompanyName = glbCompanyName;
    }

    public String getGlsCode() {
        return glsCode;
    }

    public void setGlsCode(String glsCode) {
        this.glsCode = glsCode;
    }
}
