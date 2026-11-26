package com.jpmsoft.common.model.request.PeInfo;

import com.jpmsoft.common.model.BaseModeRequest;

import java.util.Date;

/**
 * 上会确认退回记录
 */

public class PeMeetingConfirmBackRequest extends BaseModeRequest {

    private String backId;
    private String peId;
    private String backUid;
    private String backUname;
    private Date backCreateTime;
    private String backType;
    private String backReason;

    private String pId;
    public String getBackId() {
        return backId;
    }

    public void setBackId(String backId) {
        this.backId = backId;
    }

    public String getPeId() {
        return peId;
    }

    public void setPeId(String peId) {
        this.peId = peId;
    }

    public String getBackUid() {
        return backUid;
    }

    public void setBackUid(String backUid) {
        this.backUid = backUid;
    }

    public String getBackUname() {
        return backUname;
    }

    public void setBackUname(String backUname) {
        this.backUname = backUname;
    }

    public Date getBackCreateTime() {
        return backCreateTime;
    }

    public void setBackCreateTime(Date backCreateTime) {
        this.backCreateTime = backCreateTime;
    }

    public String getBackType() {
        return backType;
    }

    public void setBackType(String backType) {
        this.backType = backType;
    }

    public String getBackReason() {
        return backReason;
    }

    public void setBackReason(String backReason) {
        this.backReason = backReason;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }
}
