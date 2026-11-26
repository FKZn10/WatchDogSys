package com.jpmsoft.common.model.response.proCp;

import com.jpmsoft.common.model.ProjectCommonRes;

import java.util.Date;

/**
 * 项目建设程序
 */
public class ProjectCpResponse extends ProjectCommonRes {

    private String cpId;
    private String pId;
    private Date cpCreateTime;
    private String cpCreateUid;
    private String cpCreateUname;
    private String cpRemark;
    private String cpltId;
    private int cpDtlCount;
    private int completedCount;
    private int unfinishedCount;
    private int overTimeCount;
    private int totalDays;
    private Integer state;
    private Date stateTime;

    private String currentNode;
    private String currentNodeId;

    private Integer alreadyOverCount;

    private Integer underApprovalCount;

    private int milestoneCount;

    private int firstLevelCount;

    private int secondLevelCount;

    private String pUseUnitName;

    public String getpUseUnitName() {
        return pUseUnitName;
    }

    public void setpUseUnitName(String pUseUnitName) {
        this.pUseUnitName = pUseUnitName;
    }

    public int getMilestoneCount() {
        return milestoneCount;
    }

    public void setMilestoneCount(int milestoneCount) {
        this.milestoneCount = milestoneCount;
    }

    public int getFirstLevelCount() {
        return firstLevelCount;
    }

    public void setFirstLevelCount(int firstLevelCount) {
        this.firstLevelCount = firstLevelCount;
    }

    public int getSecondLevelCount() {
        return secondLevelCount;
    }

    public void setSecondLevelCount(int secondLevelCount) {
        this.secondLevelCount = secondLevelCount;
    }

    public Integer getAlreadyOverCount() {
        return alreadyOverCount;
    }

    public void setAlreadyOverCount(Integer alreadyOverCount) {
        this.alreadyOverCount = alreadyOverCount;
    }

    public Integer getUnderApprovalCount() {
        return underApprovalCount;
    }

    public void setUnderApprovalCount(Integer underApprovalCount) {
        this.underApprovalCount = underApprovalCount;
    }

    public String getCpId() {
        return cpId;
    }

    public void setCpId(String cpId) {
        this.cpId = cpId;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public Date getCpCreateTime() {
        return cpCreateTime;
    }

    public void setCpCreateTime(Date cpCreateTime) {
        this.cpCreateTime = cpCreateTime;
    }

    public String getCpCreateUid() {
        return cpCreateUid;
    }

    public void setCpCreateUid(String cpCreateUid) {
        this.cpCreateUid = cpCreateUid;
    }

    public String getCpCreateUname() {
        return cpCreateUname;
    }

    public void setCpCreateUname(String cpCreateUname) {
        this.cpCreateUname = cpCreateUname;
    }

    public String getCpRemark() {
        return cpRemark;
    }

    public void setCpRemark(String cpRemark) {
        this.cpRemark = cpRemark;
    }


    public Integer getCpDtlCount() {
        return cpDtlCount;
    }

    public void setCpDtlCount(Integer cpDtlCount) {
        this.cpDtlCount = cpDtlCount;
    }

    public Integer getCompletedCount() {
        return completedCount;
    }

    public void setCompletedCount(Integer completedCount) {
        this.completedCount = completedCount;
    }

    public Integer getUnfinishedCount() {
        return unfinishedCount;
    }

    public void setUnfinishedCount(Integer unfinishedCount) {
        this.unfinishedCount = unfinishedCount;
    }

    public Integer getOverTimeCount() {
        return overTimeCount;
    }

    public void setOverTimeCount(Integer overTimeCount) {
        this.overTimeCount = overTimeCount;
    }

    public Integer getTotalDays() {
        return totalDays;
    }

    public void setTotalDays(Integer totalDays) {
        this.totalDays = totalDays;
    }

    public String getCpltId() {
        return cpltId;
    }

    public void setCpltId(String cpltId) {
        this.cpltId = cpltId;
    }

    public void setCpDtlCount(int cpDtlCount) {
        this.cpDtlCount = cpDtlCount;
    }

    public void setCompletedCount(int completedCount) {
        this.completedCount = completedCount;
    }

    public void setUnfinishedCount(int unfinishedCount) {
        this.unfinishedCount = unfinishedCount;
    }

    public void setOverTimeCount(int overTimeCount) {
        this.overTimeCount = overTimeCount;
    }

    public void setTotalDays(int totalDays) {
        this.totalDays = totalDays;
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

    public String getCurrentNode() {
        return currentNode;
    }

    public void setCurrentNode(String currentNode) {
        this.currentNode = currentNode;
    }

    public String getCurrentNodeId() {
        return currentNodeId;
    }

    public void setCurrentNodeId(String currentNodeId) {
        this.currentNodeId = currentNodeId;
    }
}
