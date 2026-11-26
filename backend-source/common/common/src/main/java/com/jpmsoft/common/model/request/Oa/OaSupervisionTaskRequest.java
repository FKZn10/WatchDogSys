package com.jpmsoft.common.model.request.Oa;


import com.jpmsoft.common.model.BaseFileRequest;

import java.util.Date;

/**
 * 督办任务
 */
public class OaSupervisionTaskRequest extends BaseFileRequest {
    private String stId;
    private String stDId;
    private String pId;
    private String stCreateUid;
    private String stCreateUname;
    private Date stCreateTime;
    private Integer stState;
    private Date stStateTime;
    private String stCode;
    private Date stTerm;
    private String stTitle;
    private String stType;
    private String stHandleType;
    private String stContent;
    private String stRemark;
    private String stPerson;
    private String stPersonName;


    private String pName;
    private String year;

    public String getStId() {
        return stId;
    }

    public void setStId(String stId) {
        this.stId = stId;
    }

    public String getStDId() {
        return stDId;
    }

    public void setStDId(String stDId) {
        this.stDId = stDId;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getStCreateUid() {
        return stCreateUid;
    }

    public void setStCreateUid(String stCreateUid) {
        this.stCreateUid = stCreateUid;
    }

    public String getStCreateUname() {
        return stCreateUname;
    }

    public void setStCreateUname(String stCreateUname) {
        this.stCreateUname = stCreateUname;
    }

    public Date getStCreateTime() {
        return stCreateTime;
    }

    public void setStCreateTime(Date stCreateTime) {
        this.stCreateTime = stCreateTime;
    }

    public Integer getStState() {
        return stState;
    }

    public void setStState(Integer stState) {
        this.stState = stState;
    }

    public Date getStStateTime() {
        return stStateTime;
    }

    public void setStStateTime(Date stStateTime) {
        this.stStateTime = stStateTime;
    }

    public String getStCode() {
        return stCode;
    }

    public void setStCode(String stCode) {
        this.stCode = stCode;
    }

    public Date getStTerm() {
        return stTerm;
    }

    public void setStTerm(Date stTerm) {
        this.stTerm = stTerm;
    }

    public String getStTitle() {
        return stTitle;
    }

    public void setStTitle(String stTitle) {
        this.stTitle = stTitle;
    }

    public String getStType() {
        return stType;
    }

    public void setStType(String stType) {
        this.stType = stType;
    }

    public String getStHandleType() {
        return stHandleType;
    }

    public void setStHandleType(String stHandleType) {
        this.stHandleType = stHandleType;
    }

    public String getStContent() {
        return stContent;
    }

    public void setStContent(String stContent) {
        this.stContent = stContent;
    }

    public String getStRemark() {
        return stRemark;
    }

    public void setStRemark(String stRemark) {
        this.stRemark = stRemark;
    }

    public String getStPerson() {
        return stPerson;
    }

    public void setStPerson(String stPerson) {
        this.stPerson = stPerson;
    }

    public String getStPersonName() {
        return stPersonName;
    }

    public void setStPersonName(String stPersonName) {
        this.stPersonName = stPersonName;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }


    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }
}
