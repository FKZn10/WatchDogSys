package com.jpmsoft.common.model.response;

import com.jpmsoft.common.model.ProjectCommonRes;

import java.util.Date;

/**
 * 合同权限配置
 */
public class SysContractAuthorityResponse extends ProjectCommonRes {

    private String caId;
    private String conId;
    private String caCId;
    private String caCreateUid;
    private String caCreateUname;
    private Date caCreateTime;
    private String caType;
    private String caUserId;

    /******************/
    private String conName;
    private String caCName;
    private String caUserName;

    public String getCaId() {
        return caId;
    }

    public void setCaId(String caId) {
        this.caId = caId;
    }

    public String getConId() {
        return conId;
    }

    public void setConId(String conId) {
        this.conId = conId;
    }

    public String getCaCId() {
        return caCId;
    }

    public void setCaCId(String caCId) {
        this.caCId = caCId;
    }

    public String getCaCreateUid() {
        return caCreateUid;
    }

    public void setCaCreateUid(String caCreateUid) {
        this.caCreateUid = caCreateUid;
    }

    public String getCaCreateUname() {
        return caCreateUname;
    }

    public void setCaCreateUname(String caCreateUname) {
        this.caCreateUname = caCreateUname;
    }

    public Date getCaCreateTime() {
        return caCreateTime;
    }

    public void setCaCreateTime(Date caCreateTime) {
        this.caCreateTime = caCreateTime;
    }

    public String getConName() {
        return conName;
    }

    public void setConName(String conName) {
        this.conName = conName;
    }

    public String getCaCName() {
        return caCName;
    }

    public void setCaCName(String caCName) {
        this.caCName = caCName;
    }

    public String getCaType() {
        return caType;
    }

    public void setCaType(String caType) {
        this.caType = caType;
    }

    public String getCaUserId() {
        return caUserId;
    }

    public void setCaUserId(String caUserId) {
        this.caUserId = caUserId;
    }

    public String getCaUserName() {
        return caUserName;
    }

    public void setCaUserName(String caUserName) {
        this.caUserName = caUserName;
    }
}
