package com.jpmsoft.common.model.response.InvestmentControl;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.Date;

public class ProjectPresetYearResponse {
    private String id;

    private String createUid;

    private String createUname;

    private Date createTime;

    private Integer state;

    private Date stateTime;

    @JsonProperty("pId")
    private String pId;

    private Integer pyEclareYear;



    private BigDecimal pyInvestmentAmount;

    private Integer page;

    private Integer size;

    private String pName;

    private String pRegion;

    private String pConstructionUnit;

    private String ProjectStage;

    private String pProjectStatus;

    private String pApproveBudget;


    public String getpRegion() {
        return pRegion;
    }

    public void setpRegion(String pRegion) {
        this.pRegion = pRegion;
    }

    public String getpConstructionUnit() {
        return pConstructionUnit;
    }

    public void setpConstructionUnit(String pConstructionUnit) {
        this.pConstructionUnit = pConstructionUnit;
    }

    public String getProjectStage() {
        return ProjectStage;
    }

    public void setProjectStage(String projectStage) {
        ProjectStage = projectStage;
    }

    public String getpProjectStatus() {
        return pProjectStatus;
    }

    public void setpProjectStatus(String pProjectStatus) {
        this.pProjectStatus = pProjectStatus;
    }

    public String getpApproveBudget() {
        return pApproveBudget;
    }

    public void setpApproveBudget(String pApproveBudget) {
        this.pApproveBudget = pApproveBudget;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public Integer getPyEclareYear() {
        return pyEclareYear;
    }

    public void setPyEclareYear(Integer pyEclareYear) {
        this.pyEclareYear = pyEclareYear;
    }

    public BigDecimal getPyInvestmentAmount() {
        return pyInvestmentAmount;
    }

    public void setPyInvestmentAmount(BigDecimal pyInvestmentAmount) {
        this.pyInvestmentAmount = pyInvestmentAmount;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }
}
