package com.jpmsoft.common.model.response.Contract;

import com.jpmsoft.common.model.ProjectCommonRes;

public class GuaranteeLetterResponseCommon extends ProjectCommonRes {

    private String conName;
    private String conCode;
    private String secName;
    //单位简称
    private String glsWarranteeAbbName;
    private String glbCompanyAbbName;

    public String getConName() {
        return conName;
    }

    public void setConName(String conName) {
        this.conName = conName;
    }

    public String getConCode() {
        return conCode;
    }

    public void setConCode(String conCode) {
        this.conCode = conCode;
    }

    public String getSecName() {
        return secName;
    }

    public void setSecName(String secName) {
        this.secName = secName;
    }

    public String getGlsWarranteeAbbName() {
        return glsWarranteeAbbName;
    }

    public void setGlsWarranteeAbbName(String glsWarranteeAbbName) {
        this.glsWarranteeAbbName = glsWarranteeAbbName;
    }

    public String getGlbCompanyAbbName() {
        return glbCompanyAbbName;
    }

    public void setGlbCompanyAbbName(String glbCompanyAbbName) {
        this.glbCompanyAbbName = glbCompanyAbbName;
    }
}
