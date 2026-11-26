package com.jpmsoft.common.model.request.SecInfo;


/**
 * 中标单位
 */
public class SecWinUnitRequest {

    private String wuId;
    private String wnId;
    private String wuCompanyId;

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
}
