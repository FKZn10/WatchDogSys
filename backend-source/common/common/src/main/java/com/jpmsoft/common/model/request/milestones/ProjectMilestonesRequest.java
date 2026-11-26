package com.jpmsoft.common.model.request.milestones;

import com.jpmsoft.common.model.BaseFileRequest;

import java.util.List;

public class ProjectMilestonesRequest extends BaseFileRequest {
    private String pmsId;
    // 项目id
    private String pId;
    // 节点
    private List<ProjectMilestoneRequest> milestones;

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

    public List<ProjectMilestoneRequest> getMilestones() {
        return milestones;
    }

    public void setMilestones(List<ProjectMilestoneRequest> milestones) {
        this.milestones = milestones;
    }
}
