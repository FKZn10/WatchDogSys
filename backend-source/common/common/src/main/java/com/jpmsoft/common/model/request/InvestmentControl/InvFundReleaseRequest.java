package com.jpmsoft.common.model.request.InvestmentControl;

import com.jpmsoft.common.model.BaseFileRequest;

import java.util.Date;
import java.util.List;

/**
 * 资金计划下达
 */
public class InvFundReleaseRequest extends BaseFileRequest{

    private String ifrId;
    private String ifrFileName;
    private String ifrReplyCode;
    private Date ifrReplyTime;
    private String ifrCreateUid;
    private String ifrCreateUname;
    private Date ifrCreateTime;
    private String ifrFile;
    private String ifrFileIds;
    private Integer reqType;
    private Integer state;
    private Date stateTime;
    private List<InvProjectPlanRequest> invProjectPlans;

    public String getIfrId() {
        return ifrId;
    }

    public void setIfrId(String ifrId) {
        this.ifrId = ifrId;
    }

    public String getIfrFileName() {
        return ifrFileName;
    }

    public void setIfrFileName(String ifrFileName) {
        this.ifrFileName = ifrFileName;
    }

    public String getIfrReplyCode() {
        return ifrReplyCode;
    }

    public void setIfrReplyCode(String ifrReplyCode) {
        this.ifrReplyCode = ifrReplyCode;
    }

    public Date getIfrReplyTime() {
        return ifrReplyTime;
    }

    public void setIfrReplyTime(Date ifrReplyTime) {
        this.ifrReplyTime = ifrReplyTime;
    }

    public String getIfrCreateUid() {
        return ifrCreateUid;
    }

    public void setIfrCreateUid(String ifrCreateUid) {
        this.ifrCreateUid = ifrCreateUid;
    }

    public String getIfrCreateUname() {
        return ifrCreateUname;
    }

    public void setIfrCreateUname(String ifrCreateUname) {
        this.ifrCreateUname = ifrCreateUname;
    }

    public Date getIfrCreateTime() {
        return ifrCreateTime;
    }

    public void setIfrCreateTime(Date ifrCreateTime) {
        this.ifrCreateTime = ifrCreateTime;
    }

    public String getIfrFile() {
        return ifrFile;
    }

    public void setIfrFile(String ifrFile) {
        this.ifrFile = ifrFile;
    }

    public String getIfrFileIds() {
        return ifrFileIds;
    }

    public void setIfrFileIds(String ifrFileIds) {
        this.ifrFileIds = ifrFileIds;
    }

    public Integer getReqType() {
        return reqType;
    }

    public void setReqType(Integer reqType) {
        this.reqType = reqType;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Date getStateTime() {
        return stateTime;
    }

    public void setStateTime(Date stateTime) {
        this.stateTime = stateTime;
    }

    public List<InvProjectPlanRequest> getInvProjectPlans() {
        return invProjectPlans;
    }

    public void setInvProjectPlans(List<InvProjectPlanRequest> invProjectPlans) {
        this.invProjectPlans = invProjectPlans;
    }
}
