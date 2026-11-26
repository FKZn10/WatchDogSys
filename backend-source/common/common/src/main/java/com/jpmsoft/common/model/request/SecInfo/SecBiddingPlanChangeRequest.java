package com.jpmsoft.common.model.request.SecInfo;

import com.jpmsoft.common.model.BaseFileRequest;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecInfoSubjectRemarkDTO;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 招标计划变更
 * Created By:李文涛 aaron
 * Created Time:2022/6/16 16:10
 */
public class SecBiddingPlanChangeRequest extends BaseFileRequest implements Serializable {
    private static final long serialVersionUID = -4833554597545102815L;

    private String bpcId;
    private String bpId;
    private String bpName;
    private Integer bpYear;
    private Integer bpMonth;
    private String bpRemark;
    private String bpCreateUid;
    private String bpCreateUname;
    private Date bpCreateTime;
    private Date stateTime;
    private Integer state;
    private String bpTenderee;
    private String bpApprovalTitle;
    private String bpApprovalNum;
    private Date bpApprovalTime;
    private String bpApprovalFile;

    private String bpApprovalFileIds;
    private String pName;
    private List<SecInfoChangeRequest> secInfos;

    /**
     * 计划阶段
     */
    private String bpPlanPhase;

    /**
     * 原计划阶段
     */
    private String oldBpPlanPhase;

    private String isNew;

    private String syncOriginal;

    private List<SecInfoSubjectRemarkDTO> remarkList;

    private String responsibleLeader;

    private String responsibleLeaderName;

    /**
     * 是否储备项目申请
     */
    private String reserveItems;

    /**
     * 是否录入估算
     */
    private String enterEstimate;

    public String getEnterEstimate() {
        return enterEstimate;
    }

    public void setEnterEstimate(String enterEstimate) {
        this.enterEstimate = enterEstimate;
    }

    public String getReserveItems() {
        return reserveItems;
    }

    public void setReserveItems(String reserveItems) {
        this.reserveItems = reserveItems;
    }

    public String getResponsibleLeader() {
        return responsibleLeader;
    }

    public void setResponsibleLeader(String responsibleLeader) {
        this.responsibleLeader = responsibleLeader;
    }

    public String getResponsibleLeaderName() {
        return responsibleLeaderName;
    }

    public void setResponsibleLeaderName(String responsibleLeaderName) {
        this.responsibleLeaderName = responsibleLeaderName;
    }

    public List<SecInfoSubjectRemarkDTO> getRemarkList() {
        return remarkList;
    }

    public void setRemarkList(List<SecInfoSubjectRemarkDTO> remarkList) {
        this.remarkList = remarkList;
    }

    public String getSyncOriginal() {
        return syncOriginal;
    }

    public void setSyncOriginal(String syncOriginal) {
        this.syncOriginal = syncOriginal;
    }

    public String getOldBpPlanPhase() {
        return oldBpPlanPhase;
    }

    public void setOldBpPlanPhase(String oldBpPlanPhase) {
        this.oldBpPlanPhase = oldBpPlanPhase;
    }

    public String getIsNew() {
        return isNew;
    }

    public void setIsNew(String isNew) {
        this.isNew = isNew;
    }

    public String getBpPlanPhase() {
        return bpPlanPhase;
    }

    public void setBpPlanPhase(String bpPlanPhase) {
        this.bpPlanPhase = bpPlanPhase;
    }

    public String getBpcId() {
        return bpcId;
    }

    public void setBpcId(String bpcId) {
        this.bpcId = bpcId;
    }

    public String getBpId() {
        return bpId;
    }

    public void setBpId(String bpId) {
        this.bpId = bpId;
    }

    public String getBpName() {
        return bpName;
    }

    public void setBpName(String bpName) {
        this.bpName = bpName;
    }

    public Integer getBpYear() {
        return bpYear;
    }

    public void setBpYear(Integer bpYear) {
        this.bpYear = bpYear;
    }

    public Integer getBpMonth() {
        return bpMonth;
    }

    public void setBpMonth(Integer bpMonth) {
        this.bpMonth = bpMonth;
    }

    public String getBpRemark() {
        return bpRemark;
    }

    public void setBpRemark(String bpRemark) {
        this.bpRemark = bpRemark;
    }

    public String getBpCreateUid() {
        return bpCreateUid;
    }

    public void setBpCreateUid(String bpCreateUid) {
        this.bpCreateUid = bpCreateUid;
    }

    public String getBpCreateUname() {
        return bpCreateUname;
    }

    public void setBpCreateUname(String bpCreateUname) {
        this.bpCreateUname = bpCreateUname;
    }

    public Date getBpCreateTime() {
        return bpCreateTime;
    }

    public void setBpCreateTime(Date bpCreateTime) {
        this.bpCreateTime = bpCreateTime;
    }

    public Date getStateTime() {
        return stateTime;
    }

    public void setStateTime(Date stateTime) {
        this.stateTime = stateTime;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getBpTenderee() {
        return bpTenderee;
    }

    public void setBpTenderee(String bpTenderee) {
        this.bpTenderee = bpTenderee;
    }

    public String getBpApprovalTitle() {
        return bpApprovalTitle;
    }

    public void setBpApprovalTitle(String bpApprovalTitle) {
        this.bpApprovalTitle = bpApprovalTitle;
    }

    public String getBpApprovalNum() {
        return bpApprovalNum;
    }

    public void setBpApprovalNum(String bpApprovalNum) {
        this.bpApprovalNum = bpApprovalNum;
    }

    public Date getBpApprovalTime() {
        return bpApprovalTime;
    }

    public void setBpApprovalTime(Date bpApprovalTime) {
        this.bpApprovalTime = bpApprovalTime;
    }

    public String getBpApprovalFile() {
        return bpApprovalFile;
    }

    public void setBpApprovalFile(String bpApprovalFile) {
        this.bpApprovalFile = bpApprovalFile;
    }

    public String getBpApprovalFileIds() {
        return bpApprovalFileIds;
    }

    public void setBpApprovalFileIds(String bpApprovalFileIds) {
        this.bpApprovalFileIds = bpApprovalFileIds;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public List<SecInfoChangeRequest> getSecInfos() {
        return secInfos;
    }

    public void setSecInfos(List<SecInfoChangeRequest> secInfos) {
        this.secInfos = secInfos;
    }
}
