package com.jpmsoft.common.model.response.Plan;

/**
 * 任务关联线
 */
public class GanttLinksResponse {

    private String linkId;
    private String planId;
    private String linkSource;
    private String linkSourceName;
    private String linkTarget;
    private Integer linkType;

    public String getLinkId() {
        return linkId;
    }

    public void setLinkId(String linkId) {
        this.linkId = linkId;
    }

    public String getPlanId() {
        return planId;
    }

    public void setPlanId(String planId) {
        this.planId = planId;
    }

    public String getLinkSource() {
        return linkSource;
    }

    public void setLinkSource(String linkSource) {
        this.linkSource = linkSource;
    }

    public String getLinkSourceName() {
        return linkSourceName;
    }

    public void setLinkSourceName(String linkSourceName) {
        this.linkSourceName = linkSourceName;
    }

    public String getLinkTarget() {
        return linkTarget;
    }

    public void setLinkTarget(String linkTarget) {
        this.linkTarget = linkTarget;
    }

    public Integer getLinkType() {
        return linkType;
    }

    public void setLinkType(Integer linkType) {
        this.linkType = linkType;
    }
}
