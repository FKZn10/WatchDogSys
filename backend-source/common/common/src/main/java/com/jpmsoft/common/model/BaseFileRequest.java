package com.jpmsoft.common.model;


import com.jpmsoft.common.base.FileFields;

import java.util.List;

public class BaseFileRequest extends BasePageEntity {

    @FileFields(index = 999)
    private String fileRelationId;
    @FileFields(index = 999,type = "fileIds")
    private String fileIds;
    private String pId;
    private String fileTitle;
    private String fileCode;
    private String tId;
    private String tCode;

    /**
     * 鉴权条件
     */
    private String cId;
    private Boolean constructionControl;
    private Boolean constructionUnit;
    private List<String> departments;

    //项目主管单位主管部门
    private String pConstructionUnitName;
    private String pConsAbbName;
    private String pCompetentDepartmentName;

    private String secId;
    private String conId;

    public String getFileRelationId() {
        return fileRelationId;
    }

    public void setFileRelationId(String fileRelationId) {
        this.fileRelationId = fileRelationId;
    }

    public String getFileIds() {
        return fileIds;
    }

    public void setFileIds(String fileIds) {
        this.fileIds = fileIds;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getFileTitle() {
        return fileTitle;
    }

    public void setFileTitle(String fileTitle) {
        this.fileTitle = fileTitle;
    }

    public String getFileCode() {
        return fileCode;
    }

    public void setFileCode(String fileCode) {
        this.fileCode = fileCode;
    }

    public String gettId() {
        return tId;
    }

    public void settId(String tId) {
        this.tId = tId;
    }

    public String gettCode() {
        return tCode;
    }

    public void settCode(String tCode) {
        this.tCode = tCode;
    }

    public String getcId() {
        return cId;
    }

    public void setcId(String cId) {
        this.cId = cId;
    }

    public Boolean getConstructionControl() {
        return constructionControl;
    }

    public void setConstructionControl(Boolean constructionControl) {
        this.constructionControl = constructionControl;
    }

    public Boolean getConstructionUnit() {
        return constructionUnit;
    }

    public void setConstructionUnit(Boolean constructionUnit) {
        this.constructionUnit = constructionUnit;
    }

    public List<String> getDepartments() {
        return departments;
    }

    public void setDepartments(List<String> departments) {
        this.departments = departments;
    }

    public String getpConstructionUnitName() {
        return pConstructionUnitName;
    }

    public void setpConstructionUnitName(String pConstructionUnitName) {
        this.pConstructionUnitName = pConstructionUnitName;
    }

    public String getpConsAbbName() {
        return pConsAbbName;
    }

    public void setpConsAbbName(String pConsAbbName) {
        this.pConsAbbName = pConsAbbName;
    }

    public String getpCompetentDepartmentName() {
        return pCompetentDepartmentName;
    }

    public void setpCompetentDepartmentName(String pCompetentDepartmentName) {
        this.pCompetentDepartmentName = pCompetentDepartmentName;
    }

    public String getSecId() {
        return secId;
    }

    public void setSecId(String secId) {
        this.secId = secId;
    }

    public String getConId() {
        return conId;
    }

    public void setConId(String conId) {
        this.conId = conId;
    }

}
