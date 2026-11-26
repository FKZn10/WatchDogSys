package com.jpmsoft.common.model.response.milestones;

import java.util.Date;
import java.util.List;

public class ProjectMilestonesResponse {
    private String pmsId;
    // 项目id
    private String pId;
    private String pName;
    private String pCode;
    private String pConstructionUnitName;
    private String createUid;
    private String createUname;
    private Date createTime;
    // 节点
    private List<ProjectMilestoneResponse> milestones;


    public String getPmsId() {
        return pmsId;
    }

    public void setPmsId(String pmsId) {
        this.pmsId = pmsId;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public List<ProjectMilestoneResponse> getMilestones() {
        return milestones;
    }

    public void setMilestones(List<ProjectMilestoneResponse> milestones) {
        this.milestones = milestones;
    }

    public String getCreateUid() {
        return createUid;
    }

    public void setCreateUid(String createUid) {
        this.createUid = createUid;
    }

    public String getCreateUname() {
        return createUname;
    }

    public void setCreateUname(String createUname) {
        this.createUname = createUname;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public String getpCode() {
        return pCode;
    }

    public void setpCode(String pCode) {
        this.pCode = pCode;
    }

    public String getpConstructionUnitName() {
        return pConstructionUnitName;
    }

    public void setpConstructionUnitName(String pConstructionUnitName) {
        this.pConstructionUnitName = pConstructionUnitName;
    }
}
