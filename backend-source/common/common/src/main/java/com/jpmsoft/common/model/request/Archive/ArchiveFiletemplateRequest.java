package com.jpmsoft.common.model.request.Archive;


import java.util.Date;

/**
 * 归档文档模板
 */

public class ArchiveFiletemplateRequest {

    private String atId;
    private String adId;
    private String atTCode;
    private Integer atOrder;
    private String atType;
    private String atCreateUid;
    private String atCreateUname;
    private Date atCreateTime;
    private Integer atState;


    public String getAtId() {
        return atId;
    }

    public void setAtId(String atId) {
        this.atId = atId;
    }

    public String getAdId() {
        return adId;
    }

    public void setAdId(String adId) {
        this.adId = adId;
    }

    public String getAtTCode() {
        return atTCode;
    }

    public void setAtTCode(String atTCode) {
        this.atTCode = atTCode;
    }

    public Integer getAtOrder() {
        return atOrder;
    }

    public void setAtOrder(Integer atOrder) {
        this.atOrder = atOrder;
    }

    public String getAtType() {
        return atType;
    }

    public void setAtType(String atType) {
        this.atType = atType;
    }

    public String getAtCreateUid() {
        return atCreateUid;
    }

    public void setAtCreateUid(String atCreateUid) {
        this.atCreateUid = atCreateUid;
    }

    public String getAtCreateUname() {
        return atCreateUname;
    }

    public void setAtCreateUname(String atCreateUname) {
        this.atCreateUname = atCreateUname;
    }

    public Date getAtCreateTime() {
        return atCreateTime;
    }

    public void setAtCreateTime(Date atCreateTime) {
        this.atCreateTime = atCreateTime;
    }

    public Integer getAtState() {
        return atState;
    }

    public void setAtState(Integer atState) {
        this.atState = atState;
    }
}
