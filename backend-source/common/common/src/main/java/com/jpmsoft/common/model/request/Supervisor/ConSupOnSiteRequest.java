package com.jpmsoft.common.model.request.Supervisor;

import com.jpmsoft.common.model.BaseFileRequest;

import java.util.Date;

/**
 * 旁站记录
 */
public class ConSupOnSiteRequest extends BaseFileRequest {

    private String osId;
    private String pId;
    private String conId;
    private Date osStartTime;
    private Date osEndTime;
    private String osContractorId;
    private String osKeyPart;
    private String osKeyPartExplain;
    private String osExplain;
    private String osCreateUid;
    private String osCreateUname;
    private Date osCreateTime;
    private Integer osState;
    private Date osStateTime;
    private String osCode;

    private String pName;
    private String conName;


    public String getOsId() {
        return osId;
    }

    public void setOsId(String osId) {
        this.osId = osId;
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

    public Date getOsStartTime() {
        return osStartTime;
    }

    public void setOsStartTime(Date osStartTime) {
        this.osStartTime = osStartTime;
    }

    public Date getOsEndTime() {
        return osEndTime;
    }

    public void setOsEndTime(Date osEndTime) {
        this.osEndTime = osEndTime;
    }

    public String getOsContractorId() {
        return osContractorId;
    }

    public void setOsContractorId(String osContractorId) {
        this.osContractorId = osContractorId;
    }

    public String getOsKeyPart() {
        return osKeyPart;
    }

    public void setOsKeyPart(String osKeyPart) {
        this.osKeyPart = osKeyPart;
    }

    public String getOsKeyPartExplain() {
        return osKeyPartExplain;
    }

    public void setOsKeyPartExplain(String osKeyPartExplain) {
        this.osKeyPartExplain = osKeyPartExplain;
    }

    public String getOsExplain() {
        return osExplain;
    }

    public void setOsExplain(String osExplain) {
        this.osExplain = osExplain;
    }

    public String getOsCreateUid() {
        return osCreateUid;
    }

    public void setOsCreateUid(String osCreateUid) {
        this.osCreateUid = osCreateUid;
    }

    public String getOsCreateUname() {
        return osCreateUname;
    }

    public void setOsCreateUname(String osCreateUname) {
        this.osCreateUname = osCreateUname;
    }

    public Date getOsCreateTime() {
        return osCreateTime;
    }

    public void setOsCreateTime(Date osCreateTime) {
        this.osCreateTime = osCreateTime;
    }

    public Integer getOsState() {
        return osState;
    }

    public void setOsState(Integer osState) {
        this.osState = osState;
    }

    public Date getOsStateTime() {
        return osStateTime;
    }

    public void setOsStateTime(Date osStateTime) {
        this.osStateTime = osStateTime;
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

    public String getOsCode() {
        return osCode;
    }

    public void setOsCode(String osCode) {
        this.osCode = osCode;
    }
}
