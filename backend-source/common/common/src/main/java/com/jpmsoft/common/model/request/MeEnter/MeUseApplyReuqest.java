package com.jpmsoft.common.model.request.MeEnter;


import com.jpmsoft.common.model.BaseFileRequest;

import java.util.Date;
import java.util.List;

/**
 * 使用报审
 */
public class MeUseApplyReuqest extends BaseFileRequest {


    private String uaId;
    private String eaId;
    private String pId;
    private String conId;
    private String uaRemark;
    private String uaCreateUid;
    private String uaCreateUname;
    private Date uaCreateTime;
    private Integer uaState;
    private Date uaStateTime;
    private List<MeEnterApplyDtlRequest> applyDtls;

    public String getUaId() {
        return uaId;
    }

    public void setUaId(String uaId) {
        this.uaId = uaId;
    }

    public String getEaId() {
        return eaId;
    }

    public void setEaId(String eaId) {
        this.eaId = eaId;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getConId() {
        return conId;
    }

    public void setConId(String conId) {
        this.conId = conId;
    }

    public String getUaRemark() {
        return uaRemark;
    }

    public void setUaRemark(String uaRemark) {
        this.uaRemark = uaRemark;
    }

    public String getUaCreateUid() {
        return uaCreateUid;
    }

    public void setUaCreateUid(String uaCreateUid) {
        this.uaCreateUid = uaCreateUid;
    }

    public String getUaCreateUname() {
        return uaCreateUname;
    }

    public void setUaCreateUname(String uaCreateUname) {
        this.uaCreateUname = uaCreateUname;
    }

    public Date getUaCreateTime() {
        return uaCreateTime;
    }

    public void setUaCreateTime(Date uaCreateTime) {
        this.uaCreateTime = uaCreateTime;
    }

    public Integer getUaState() {
        return uaState;
    }

    public void setUaState(Integer uaState) {
        this.uaState = uaState;
    }

    public Date getUaStateTime() {
        return uaStateTime;
    }

    public void setUaStateTime(Date uaStateTime) {
        this.uaStateTime = uaStateTime;
    }

    public List<MeEnterApplyDtlRequest> getApplyDtls() {
        return applyDtls;
    }

    public void setApplyDtls(List<MeEnterApplyDtlRequest> applyDtls) {
        this.applyDtls = applyDtls;
    }
}
