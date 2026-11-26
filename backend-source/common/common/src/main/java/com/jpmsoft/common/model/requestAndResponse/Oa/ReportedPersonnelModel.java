package com.jpmsoft.common.model.requestAndResponse.Oa;


import com.jpmsoft.common.model.ProjectCommonRes;

import java.util.Date;
import java.util.List;

/**
 * 项目人员报备
 */
public class ReportedPersonnelModel extends ProjectCommonRes {

    private String rpId;
    private String pId;
    private String rpType;
    private String rpRemark;
    private String rpCreateUid;
    private String rpCreateUname;
    private Date rpCreateTime;
    private Integer rpState;
    private Date rpStateTime;
    private String rpChangeType;
    private List<ReportedPersonnelDtlModel> repPersonnelDtls;
    private Boolean auth = true;

    private String pProjectStageOrganization;

    public String getpProjectStageOrganization() {
        return pProjectStageOrganization;
    }

    public void setpProjectStageOrganization(String pProjectStageOrganization) {
        this.pProjectStageOrganization = pProjectStageOrganization;
    }

    public Boolean getAuth() {
        return auth;
    }

    public void setAuth(Boolean auth) {
        this.auth = auth;
    }

    public String getRpId() {
        return rpId;
    }

    public void setRpId(String rpId) {
        this.rpId = rpId;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getRpType() {
        return rpType;
    }

    public void setRpType(String rpType) {
        this.rpType = rpType;
    }

    public String getRpRemark() {
        return rpRemark;
    }

    public void setRpRemark(String rpRemark) {
        this.rpRemark = rpRemark;
    }

    public String getRpCreateUid() {
        return rpCreateUid;
    }

    public void setRpCreateUid(String rpCreateUid) {
        this.rpCreateUid = rpCreateUid;
    }

    public String getRpCreateUname() {
        return rpCreateUname;
    }

    public void setRpCreateUname(String rpCreateUname) {
        this.rpCreateUname = rpCreateUname;
    }

    public Date getRpCreateTime() {
        return rpCreateTime;
    }

    public void setRpCreateTime(Date rpCreateTime) {
        this.rpCreateTime = rpCreateTime;
    }

    public Integer getRpState() {
        return rpState;
    }

    public void setRpState(Integer rpState) {
        this.rpState = rpState;
    }

    public Date getRpStateTime() {
        return rpStateTime;
    }

    public void setRpStateTime(Date rpStateTime) {
        this.rpStateTime = rpStateTime;
    }

    public String getRpChangeType() {
        return rpChangeType;
    }

    public void setRpChangeType(String rpChangeType) {
        this.rpChangeType = rpChangeType;
    }

    public List<ReportedPersonnelDtlModel> getRepPersonnelDtls() {
        return repPersonnelDtls;
    }

    public void setRepPersonnelDtls(List<ReportedPersonnelDtlModel> repPersonnelDtls) {
        this.repPersonnelDtls = repPersonnelDtls;
    }
}
