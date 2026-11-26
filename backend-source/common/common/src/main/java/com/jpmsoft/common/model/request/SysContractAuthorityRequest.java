package com.jpmsoft.common.model.request;

import com.jpmsoft.common.model.BaseFileRequest;

/**
 * 合同权限配置
 */
public class SysContractAuthorityRequest extends BaseFileRequest {

    private String conId;
    private String caCId;
    private String caCName;
    private String conName;
    private String pName;
    private String caCreateUid;
    private String caCreateUname;
    private String caType;
    private String caUserId;
    private String caUserName;

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

    public String getCaCName() {
        return caCName;
    }

    public void setCaCName(String caCName) {
        this.caCName = caCName;
    }

    public String getConName() {
        return conName;
    }

    public void setConName(String conName) {
        this.conName = conName;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
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
