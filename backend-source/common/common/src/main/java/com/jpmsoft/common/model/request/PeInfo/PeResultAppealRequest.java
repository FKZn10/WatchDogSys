package com.jpmsoft.common.model.request.PeInfo;

import com.jpmsoft.common.model.BaseModeRequest;

import java.util.Date;

/**
 * 履约评价结果送达
 */
public class PeResultAppealRequest extends BaseModeRequest {

    private String raId;
    private String rId;
    private String raRemark;
    private String raCreateUid;
    private String raCreateUname;
    private Date raCreateTime;

    private String pId;

    public String getRaId() {
        return raId;
    }

    public void setRaId(String raId) {
        this.raId = raId;
    }

    public String getrId() {
        return rId;
    }

    public void setrId(String rId) {
        this.rId = rId;
    }

    public String getRaRemark() {
        return raRemark;
    }

    public void setRaRemark(String raRemark) {
        this.raRemark = raRemark;
    }

    public String getRaCreateUid() {
        return raCreateUid;
    }

    public void setRaCreateUid(String raCreateUid) {
        this.raCreateUid = raCreateUid;
    }

    public String getRaCreateUname() {
        return raCreateUname;
    }

    public void setRaCreateUname(String raCreateUname) {
        this.raCreateUname = raCreateUname;
    }

    public Date getRaCreateTime() {
        return raCreateTime;
    }

    public void setRaCreateTime(Date raCreateTime) {
        this.raCreateTime = raCreateTime;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }
}
