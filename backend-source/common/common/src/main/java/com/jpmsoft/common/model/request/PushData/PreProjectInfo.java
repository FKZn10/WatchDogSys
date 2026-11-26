package com.jpmsoft.common.model.request.PushData;

/**
 * 北京工行
 * 推送项目信息请求报文
 */
public class PreProjectInfo {

    private String projectNo;
    private String projectName;
    private String projectBeginDate;
    private String projectEndDate;
    private Long projectAmt;
    private String agentCode;
    private String corporationCode;

    public String getProjectNo() {
        return projectNo;
    }

    public void setProjectNo(String projectNo) {
        this.projectNo = projectNo;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectBeginDate() {
        return projectBeginDate;
    }

    public void setProjectBeginDate(String projectBeginDate) {
        this.projectBeginDate = projectBeginDate;
    }

    public String getProjectEndDate() {
        return projectEndDate;
    }

    public void setProjectEndDate(String projectEndDate) {
        this.projectEndDate = projectEndDate;
    }


    public Long getProjectAmt() {
        return projectAmt;
    }

    public void setProjectAmt(Long projectAmt) {
        this.projectAmt = projectAmt;
    }

    public String getAgentCode() {
        return agentCode;
    }

    public void setAgentCode(String agentCode) {
        this.agentCode = agentCode;
    }

    public String getCorporationCode() {
        return corporationCode;
    }

    public void setCorporationCode(String corporationCode) {
        this.corporationCode = corporationCode;
    }
}
