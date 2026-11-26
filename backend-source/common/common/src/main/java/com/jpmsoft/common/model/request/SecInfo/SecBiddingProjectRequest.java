package com.jpmsoft.common.model.request.SecInfo;


/**
 * 招标计划项目关系表
 */
public class SecBiddingProjectRequest {

    private String sbpId;
    //招标计划id
    private String bpId;
    //项目id
    private String pId;
    //招采计划id
    private String ppId;
    private String sbpCreateUid;

    public String getSbpId() {
        return sbpId;
    }

    public void setSbpId(String sbpId) {
        this.sbpId = sbpId;
    }

    public String getBpId() {
        return bpId;
    }

    public void setBpId(String bpId) {
        this.bpId = bpId;
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

    public String getSbpCreateUid() {
        return sbpCreateUid;
    }

    public void setSbpCreateUid(String sbpCreateUid) {
        this.sbpCreateUid = sbpCreateUid;
    }
}
