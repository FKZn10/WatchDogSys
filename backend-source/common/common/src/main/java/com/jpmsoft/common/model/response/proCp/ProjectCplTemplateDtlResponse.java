package com.jpmsoft.common.model.response.proCp;

import java.util.List;

/**
 * 建设程序项目模板详情
 */
public class ProjectCplTemplateDtlResponse {

    private String cpltdId;
    private String cpltId;
    private String cpltdName;
    private String cpltdCode;
    private String cpltdParentId;
    private String cpltdType;
    private Integer cpltdDays;
    private String cpltdProId;
    private String cpltdAdministration;
    private String cpltdAdministrationUrl;
    private String cpltdRemark;
    private String cpltdHtml;
    private String cpltdCplId;
    private Integer cpltdSort;
    private String cpltdAdministrationUrlH;
    private List<ProjectCplTemplateDtlResponse> childNodes;

    public String getCpltdId() {
        return cpltdId;
    }

    public void setCpltdId(String cpltdId) {
        this.cpltdId = cpltdId;
    }

    public String getCpltId() {
        return cpltId;
    }

    public void setCpltId(String cpltId) {
        this.cpltId = cpltId;
    }

    public String getCpltdName() {
        return cpltdName;
    }

    public void setCpltdName(String cpltdName) {
        this.cpltdName = cpltdName;
    }

    public String getCpltdCode() {
        return cpltdCode;
    }

    public void setCpltdCode(String cpltdCode) {
        this.cpltdCode = cpltdCode;
    }

    public String getCpltdParentId() {
        return cpltdParentId;
    }

    public void setCpltdParentId(String cpltdParentId) {
        this.cpltdParentId = cpltdParentId;
    }

    public String getCpltdType() {
        return cpltdType;
    }

    public void setCpltdType(String cpltdType) {
        this.cpltdType = cpltdType;
    }

    public Integer getCpltdDays() {
        return cpltdDays;
    }

    public void setCpltdDays(Integer cpltdDays) {
        this.cpltdDays = cpltdDays;
    }

    public String getCpltdProId() {
        return cpltdProId;
    }

    public void setCpltdProId(String cpltdProId) {
        this.cpltdProId = cpltdProId;
    }

    public String getCpltdAdministration() {
        return cpltdAdministration;
    }

    public void setCpltdAdministration(String cpltdAdministration) {
        this.cpltdAdministration = cpltdAdministration;
    }

    public String getCpltdAdministrationUrl() {
        return cpltdAdministrationUrl;
    }

    public void setCpltdAdministrationUrl(String cpltdAdministrationUrl) {
        this.cpltdAdministrationUrl = cpltdAdministrationUrl;
    }

    public String getCpltdRemark() {
        return cpltdRemark;
    }

    public void setCpltdRemark(String cpltdRemark) {
        this.cpltdRemark = cpltdRemark;
    }

    public String getCpltdHtml() {
        return cpltdHtml;
    }

    public void setCpltdHtml(String cpltdHtml) {
        this.cpltdHtml = cpltdHtml;
    }

    public String getCpltdCplId() {
        return cpltdCplId;
    }

    public void setCpltdCplId(String cpltdCplId) {
        this.cpltdCplId = cpltdCplId;
    }

    public Integer getCpltdSort() {
        return cpltdSort;
    }

    public void setCpltdSort(Integer cpltdSort) {
        this.cpltdSort = cpltdSort;
    }

    public String getCpltdAdministrationUrlH() {
        return cpltdAdministrationUrlH;
    }

    public void setCpltdAdministrationUrlH(String cpltdAdministrationUrlH) {
        this.cpltdAdministrationUrlH = cpltdAdministrationUrlH;
    }

    public List<ProjectCplTemplateDtlResponse> getChildNodes() {
        return childNodes;
    }

    public void setChildNodes(List<ProjectCplTemplateDtlResponse> childNodes) {
        this.childNodes = childNodes;
    }
}
