package com.jpmsoft.common.model.response.SecInfo;


/**
 * 中标单位
 */
public class SecWinUnitResponse {

    private String wuId;
    private String wnId;
    private String wuCompanyId;
    private String wuCompanyName;
    private String wuCompAbbName;


    public String getWuId() {
        return wuId;
    }

    public void setWuId(String wuId) {
        this.wuId = wuId;
    }

    public String getWnId() {
        return wnId;
    }

    public void setWnId(String wnId) {
        this.wnId = wnId;
    }

    public String getWuCompanyId() {
        return wuCompanyId;
    }

    public void setWuCompanyId(String wuCompanyId) {
        this.wuCompanyId = wuCompanyId;
    }

    public String getWuCompanyName() {
        return wuCompanyName;
    }

    public void setWuCompanyName(String wuCompanyName) {
        this.wuCompanyName = wuCompanyName;
    }

    public String getWuCompAbbName() {
        return wuCompAbbName;
    }

    public void setWuCompAbbName(String wuCompAbbName) {
        this.wuCompAbbName = wuCompAbbName;
    }
}
