package com.jpmsoft.common.model.request.SecInfo;


/**
 * 标段项目关系表
 */
public class SecProjectRequest {

    private String spId;
    private String secId;
    private String pId;
    //招采计划id
    private String ppId;

    public String getSpId() {
        return spId;
    }

    public void setSpId(String spId) {
        this.spId = spId;
    }

    public String getSecId() {
        return secId;
    }

    public void setSecId(String secId) {
        this.secId = secId;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getPpId() {
        return ppId;
    }

    public void setPpId(String ppId) {
        this.ppId = ppId;
    }
}
