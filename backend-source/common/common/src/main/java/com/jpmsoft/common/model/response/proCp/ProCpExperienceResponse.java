package com.jpmsoft.common.model.response.proCp;

import java.util.Date;

/**
 * 经验分享
 */

public class ProCpExperienceResponse extends ProjectCpDtlResponse {

    private String cpeId;
    private String cpdId;
    private String cpeContent;
    private String cpeCreateUid;
    private String cpeCreateUname;
    private Date cpeCreateTime;

    public String getCpeId() {
        return cpeId;
    }

    public void setCpeId(String cpeId) {
        this.cpeId = cpeId;
    }

    public String getCpdId() {
        return cpdId;
    }

    public void setCpdId(String cpdId) {
        this.cpdId = cpdId;
    }

    public String getCpeContent() {
        return cpeContent;
    }

    public void setCpeContent(String cpeContent) {
        this.cpeContent = cpeContent;
    }

    public String getCpeCreateUid() {
        return cpeCreateUid;
    }

    public void setCpeCreateUid(String cpeCreateUid) {
        this.cpeCreateUid = cpeCreateUid;
    }

    public String getCpeCreateUname() {
        return cpeCreateUname;
    }

    public void setCpeCreateUname(String cpeCreateUname) {
        this.cpeCreateUname = cpeCreateUname;
    }

    public Date getCpeCreateTime() {
        return cpeCreateTime;
    }

    public void setCpeCreateTime(Date cpeCreateTime) {
        this.cpeCreateTime = cpeCreateTime;
    }
}
