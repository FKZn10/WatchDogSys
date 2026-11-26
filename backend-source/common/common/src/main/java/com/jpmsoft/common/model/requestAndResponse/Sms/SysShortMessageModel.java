package com.jpmsoft.common.model.requestAndResponse.Sms;

import com.jpmsoft.common.model.BaseModeRequest;

import java.util.Date;

/**
 * 短信待发池
 */
public class SysShortMessageModel extends BaseModeRequest {

    private String smId;
    private String smMobilePhone;
    private String smUid;
    private String smUname;
    private String smContent;
    private String smFileid;
    private String smFlowid;
    private String smActid;
    private Date smCreateTime;
    private String smCreateMethod;
    private String smCreateUid;
    private String smCreateUname;
    private String smFrom;
    private String smtCode;
    private String smText;

    public String getSmId() {
        return smId;
    }

    public void setSmId(String smId) {
        this.smId = smId;
    }

    public String getSmMobilePhone() {
        return smMobilePhone;
    }

    public void setSmMobilePhone(String smMobilePhone) {
        this.smMobilePhone = smMobilePhone;
    }

    public String getSmUid() {
        return smUid;
    }

    public void setSmUid(String smUid) {
        this.smUid = smUid;
    }

    public String getSmUname() {
        return smUname;
    }

    public void setSmUname(String smUname) {
        this.smUname = smUname;
    }

    public String getSmContent() {
        return smContent;
    }

    public void setSmContent(String smContent) {
        this.smContent = smContent;
    }

    public String getSmFileid() {
        return smFileid;
    }

    public void setSmFileid(String smFileid) {
        this.smFileid = smFileid;
    }

    public String getSmFlowid() {
        return smFlowid;
    }

    public void setSmFlowid(String smFlowid) {
        this.smFlowid = smFlowid;
    }

    public String getSmActid() {
        return smActid;
    }

    public void setSmActid(String smActid) {
        this.smActid = smActid;
    }

    public Date getSmCreateTime() {
        return smCreateTime;
    }

    public void setSmCreateTime(Date smCreateTime) {
        this.smCreateTime = smCreateTime;
    }

    public String getSmCreateMethod() {
        return smCreateMethod;
    }

    public void setSmCreateMethod(String smCreateMethod) {
        this.smCreateMethod = smCreateMethod;
    }

    public String getSmCreateUid() {
        return smCreateUid;
    }

    public void setSmCreateUid(String smCreateUid) {
        this.smCreateUid = smCreateUid;
    }

    public String getSmCreateUname() {
        return smCreateUname;
    }

    public void setSmCreateUname(String smCreateUname) {
        this.smCreateUname = smCreateUname;
    }

    public String getSmFrom() {
        return smFrom;
    }

    public void setSmFrom(String smFrom) {
        this.smFrom = smFrom;
    }

    public String getSmtCode() {
        return smtCode;
    }

    public void setSmtCode(String smtCode) {
        this.smtCode = smtCode;
    }

    public String getSmText() {
        return smText;
    }

    public void setSmText(String smText) {
        this.smText = smText;
    }
}
