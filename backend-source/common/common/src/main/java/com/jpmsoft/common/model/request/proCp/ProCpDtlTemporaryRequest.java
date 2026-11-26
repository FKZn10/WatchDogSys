package com.jpmsoft.common.model.request.proCp;

import com.jpmsoft.common.model.BaseModeRequest;

import java.util.Date;

public class ProCpDtlTemporaryRequest extends BaseModeRequest {
    private Date temporaryTime;
    private  String cpdPersonId;
    private String pId;
    private Integer pending;
    public String cpdName;
    public String pName;

    public Date getTemporaryTime() {
        return temporaryTime;
    }

    public void setTemporaryTime(Date temporaryTime) {
        this.temporaryTime = temporaryTime;
    }

    public String getCpdPersonId() {
        return cpdPersonId;
    }

    public void setCpdPersonId(String cpdPersonId) {
        this.cpdPersonId = cpdPersonId;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public Integer getPending() {
        return pending;
    }

    public void setPending(Integer pending) {
        this.pending = pending;
    }

    public String getCpdName() {
        return cpdName;
    }

    public void setCpdName(String cpdName) {
        this.cpdName = cpdName;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }
}
