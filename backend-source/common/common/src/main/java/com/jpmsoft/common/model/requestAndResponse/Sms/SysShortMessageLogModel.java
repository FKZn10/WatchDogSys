package com.jpmsoft.common.model.requestAndResponse.Sms;


import com.jpmsoft.common.model.BaseModeRequest;

import java.util.Date;

/**
 * 短信待发池
 */
public class SysShortMessageLogModel extends BaseModeRequest {

    private String smlId;
    private String smId;
    private String smlMobilePhone;
    private String smlUid;
    private String smlUname;
    private String smlContent;
    private String smlFileid;
    private String smlFlowid;
    private String smlActid;
    private Date smlCreateTime;
    private String smlCreateMethod;
    private String smlCreateUid;
    private String smlCreateUname;
    private String smlFrom;
    private Date smlSendTime;
    private String smlSendUrl;
    private String smlSendResult;
    private String smlSendResultMsg;

    private boolean unSuccess;

    public String getSmlId() {
        return smlId;
    }

    public void setSmlId(String smlId) {
        this.smlId = smlId;
    }

    public String getSmId() {
        return smId;
    }

    public void setSmId(String smId) {
        this.smId = smId;
    }

    public String getSmlMobilePhone() {
        return smlMobilePhone;
    }

    public void setSmlMobilePhone(String smlMobilePhone) {
        this.smlMobilePhone = smlMobilePhone;
    }

    public String getSmlUid() {
        return smlUid;
    }

    public void setSmlUid(String smlUid) {
        this.smlUid = smlUid;
    }

    public String getSmlUname() {
        return smlUname;
    }

    public void setSmlUname(String smlUname) {
        this.smlUname = smlUname;
    }

    public String getSmlContent() {
        return smlContent;
    }

    public void setSmlContent(String smlContent) {
        this.smlContent = smlContent;
    }

    public String getSmlFileid() {
        return smlFileid;
    }

    public void setSmlFileid(String smlFileid) {
        this.smlFileid = smlFileid;
    }

    public String getSmlFlowid() {
        return smlFlowid;
    }

    public void setSmlFlowid(String smlFlowid) {
        this.smlFlowid = smlFlowid;
    }

    public String getSmlActid() {
        return smlActid;
    }

    public void setSmlActid(String smlActid) {
        this.smlActid = smlActid;
    }

    public Date getSmlCreateTime() {
        return smlCreateTime;
    }

    public void setSmlCreateTime(Date smlCreateTime) {
        this.smlCreateTime = smlCreateTime;
    }

    public String getSmlCreateMethod() {
        return smlCreateMethod;
    }

    public void setSmlCreateMethod(String smlCreateMethod) {
        this.smlCreateMethod = smlCreateMethod;
    }

    public String getSmlCreateUid() {
        return smlCreateUid;
    }

    public void setSmlCreateUid(String smlCreateUid) {
        this.smlCreateUid = smlCreateUid;
    }

    public String getSmlCreateUname() {
        return smlCreateUname;
    }

    public void setSmlCreateUname(String smlCreateUname) {
        this.smlCreateUname = smlCreateUname;
    }

    public String getSmlFrom() {
        return smlFrom;
    }

    public void setSmlFrom(String smlFrom) {
        this.smlFrom = smlFrom;
    }

    public Date getSmlSendTime() {
        return smlSendTime;
    }

    public void setSmlSendTime(Date smlSendTime) {
        this.smlSendTime = smlSendTime;
    }

    public String getSmlSendUrl() {
        return smlSendUrl;
    }

    public void setSmlSendUrl(String smlSendUrl) {
        this.smlSendUrl = smlSendUrl;
    }

    public String getSmlSendResult() {
        return smlSendResult;
    }

    public void setSmlSendResult(String smlSendResult) {
        this.smlSendResult = smlSendResult;
    }

    public String getSmlSendResultMsg() {
        return smlSendResultMsg;
    }

    public void setSmlSendResultMsg(String smlSendResultMsg) {
        this.smlSendResultMsg = smlSendResultMsg;
    }

    public boolean isUnSuccess() {
        return unSuccess;
    }

    public void setUnSuccess(boolean unSuccess) {
        this.unSuccess = unSuccess;
    }
}
