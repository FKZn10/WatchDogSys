package com.jpmsoft.common.model.request.PeInfo;

import com.jpmsoft.common.model.BaseModeRequest;

public class PeInfoCollectDtlRequest extends BaseModeRequest {

    private String pecId;
    private Integer peYear;
    private Integer peQuarter;
    private String peType;
    //项目建设单位
    private String pConstructionUnit;
    //履约评价单位
    private String peUnitId;
    private Boolean approval;
    private String peCompanyId;
    private String peCompanyName;
    private String conType;
    private String pName;
    private String pId;
    private String conName;
    private String peState;
    //合同甲方单位
    private String conPartyAId;
    private String pecDepartment;


    public String getPecId() {
        return pecId;
    }

    public void setPecId(String pecId) {
        this.pecId = pecId;
    }

    public Integer getPeYear() {
        return peYear;
    }

    public void setPeYear(Integer peYear) {
        this.peYear = peYear;
    }

    public Integer getPeQuarter() {
        return peQuarter;
    }

    public void setPeQuarter(Integer peQuarter) {
        this.peQuarter = peQuarter;
    }

    public String getPeType() {
        return peType;
    }

    public void setPeType(String peType) {
        this.peType = peType;
    }

    public String getpConstructionUnit() {
        return pConstructionUnit;
    }

    public void setpConstructionUnit(String pConstructionUnit) {
        this.pConstructionUnit = pConstructionUnit;
    }

    public String getPeUnitId() {
        return peUnitId;
    }

    public void setPeUnitId(String peUnitId) {
        this.peUnitId = peUnitId;
    }

    public Boolean getApproval() {
        return approval;
    }

    public void setApproval(Boolean approval) {
        this.approval = approval;
    }

    public String getPeCompanyId() {
        return peCompanyId;
    }

    public void setPeCompanyId(String peCompanyId) {
        this.peCompanyId = peCompanyId;
    }

    public String getPeCompanyName() {
        return peCompanyName;
    }

    public void setPeCompanyName(String peCompanyName) {
        this.peCompanyName = peCompanyName;
    }

    public String getConType() {
        return conType;
    }

    public void setConType(String conType) {
        this.conType = conType;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getConName() {
        return conName;
    }

    public void setConName(String conName) {
        this.conName = conName;
    }

    public String getPeState() {
        return peState;
    }

    public void setPeState(String peState) {
        this.peState = peState;
    }

    public String getConPartyAId() {
        return conPartyAId;
    }

    public void setConPartyAId(String conPartyAId) {
        this.conPartyAId = conPartyAId;
    }

    public String getPecDepartment() {
        return pecDepartment;
    }

    public void setPecDepartment(String pecDepartment) {
        this.pecDepartment = pecDepartment;
    }
}
