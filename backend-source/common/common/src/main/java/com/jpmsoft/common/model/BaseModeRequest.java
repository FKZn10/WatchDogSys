package com.jpmsoft.common.model;


import java.util.List;

public class BaseModeRequest  extends  BasePageEntity {

    /**
     * 鉴权条件
     */
    private String cId;
    private Boolean constructionControl;
    private Boolean constructionUnit;
    private List<String> departments;


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
}
