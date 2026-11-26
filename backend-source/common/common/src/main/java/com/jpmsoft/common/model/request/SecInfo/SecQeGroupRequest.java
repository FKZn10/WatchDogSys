package com.jpmsoft.common.model.request.SecInfo;


import com.jpmsoft.common.model.BaseFileRequest;

import java.util.Date;
import java.util.List;

/**
 * 资格审查小组
 */

public class SecQeGroupRequest extends BaseFileRequest {


    private String qegId;
    private String secId;
    private String qegRemark;
    private String qegCreateUid;
    private String qegCreateUname;
    private Date qegCreateTime;
    private Integer qegState;
    private Date qegStateTime;
    private String qegScope;
    private List<SecQeGroupDtlRequest> sqGroupDtls;

    public String getQegId() {
        return qegId;
    }

    public void setQegId(String qegId) {
        this.qegId = qegId;
    }

    public String getSecId() {
        return secId;
    }

    public void setSecId(String secId) {
        this.secId = secId;
    }

    public String getQegRemark() {
        return qegRemark;
    }

    public void setQegRemark(String qegRemark) {
        this.qegRemark = qegRemark;
    }

    public String getQegCreateUid() {
        return qegCreateUid;
    }

    public void setQegCreateUid(String qegCreateUid) {
        this.qegCreateUid = qegCreateUid;
    }

    public String getQegCreateUname() {
        return qegCreateUname;
    }

    public void setQegCreateUname(String qegCreateUname) {
        this.qegCreateUname = qegCreateUname;
    }

    public Date getQegCreateTime() {
        return qegCreateTime;
    }

    public void setQegCreateTime(Date qegCreateTime) {
        this.qegCreateTime = qegCreateTime;
    }

    public Integer getQegState() {
        return qegState;
    }

    public void setQegState(Integer qegState) {
        this.qegState = qegState;
    }

    public Date getQegStateTime() {
        return qegStateTime;
    }

    public void setQegStateTime(Date qegStateTime) {
        this.qegStateTime = qegStateTime;
    }

    public String getQegScope() {
        return qegScope;
    }

    public void setQegScope(String qegScope) {
        this.qegScope = qegScope;
    }

    public List<SecQeGroupDtlRequest> getSqGroupDtls() {
        return sqGroupDtls;
    }

    public void setSqGroupDtls(List<SecQeGroupDtlRequest> sqGroupDtls) {
        this.sqGroupDtls = sqGroupDtls;
    }
}
