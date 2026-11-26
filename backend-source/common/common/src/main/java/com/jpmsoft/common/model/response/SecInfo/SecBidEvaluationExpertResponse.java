package com.jpmsoft.common.model.response.SecInfo;

import com.jpmsoft.common.model.BaseFileResponse;

import java.util.Date;
import java.util.List;

/**
 * 评标专家构成
 */
public class SecBidEvaluationExpertResponse extends BaseFileResponse {

    private String beeId;
    private String secId;
    private String beeEvaluationMethod;
    private Date beeEvaluationTime;
    private String beeExpertApplication;
    private String beeExpertStatement;
    private String beeExpertGign;
    private String beeExpertFeeCollection;
    private String beeCreateUid;
    private Date beeCreateTime;
    private String beeCreateUname;
    private Integer beeState;
    private Date beeStateTime;

    private String secName;
    private String secType;
    private List<SecProjectResponse> secProjects;


    public String getSecType() {
        return secType;
    }

    public void setSecType(String secType) {
        this.secType = secType;
    }

    public List<SecProjectResponse> getSecProjects() {
        return secProjects;
    }

    public void setSecProjects(List<SecProjectResponse> secProjects) {
        this.secProjects = secProjects;
    }

    public String getBeeId() {
        return beeId;
    }

    public void setBeeId(String beeId) {
        this.beeId = beeId;
    }

    public String getSecId() {
        return secId;
    }

    public void setSecId(String secId) {
        this.secId = secId;
    }

    public String getBeeEvaluationMethod() {
        return beeEvaluationMethod;
    }

    public void setBeeEvaluationMethod(String beeEvaluationMethod) {
        this.beeEvaluationMethod = beeEvaluationMethod;
    }

    public Date getBeeEvaluationTime() {
        return beeEvaluationTime;
    }

    public void setBeeEvaluationTime(Date beeEvaluationTime) {
        this.beeEvaluationTime = beeEvaluationTime;
    }

    public String getBeeExpertApplication() {
        return beeExpertApplication;
    }

    public void setBeeExpertApplication(String beeExpertApplication) {
        this.beeExpertApplication = beeExpertApplication;
    }

    public String getBeeExpertStatement() {
        return beeExpertStatement;
    }

    public void setBeeExpertStatement(String beeExpertStatement) {
        this.beeExpertStatement = beeExpertStatement;
    }

    public String getBeeExpertGign() {
        return beeExpertGign;
    }

    public void setBeeExpertGign(String beeExpertGign) {
        this.beeExpertGign = beeExpertGign;
    }

    public String getBeeExpertFeeCollection() {
        return beeExpertFeeCollection;
    }

    public void setBeeExpertFeeCollection(String beeExpertFeeCollection) {
        this.beeExpertFeeCollection = beeExpertFeeCollection;
    }

    public String getBeeCreateUid() {
        return beeCreateUid;
    }

    public void setBeeCreateUid(String beeCreateUid) {
        this.beeCreateUid = beeCreateUid;
    }

    public Date getBeeCreateTime() {
        return beeCreateTime;
    }

    public void setBeeCreateTime(Date beeCreateTime) {
        this.beeCreateTime = beeCreateTime;
    }

    public String getBeeCreateUname() {
        return beeCreateUname;
    }

    public void setBeeCreateUname(String beeCreateUname) {
        this.beeCreateUname = beeCreateUname;
    }

    public Integer getBeeState() {
        return beeState;
    }

    public void setBeeState(Integer beeState) {
        this.beeState = beeState;
    }

    public Date getBeeStateTime() {
        return beeStateTime;
    }

    public void setBeeStateTime(Date beeStateTime) {
        this.beeStateTime = beeStateTime;
    }

    public String getSecName() {
        return secName;
    }

    public void setSecName(String secName) {
        this.secName = secName;
    }

}
