package com.jpmsoft.common.model.request.InvestmentControl;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ProjectPresetYearRequest implements Serializable {
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


    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
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
}
