package com.jpmsoft.common.model.request.PeInfo;

import com.jpmsoft.common.model.BaseFileRequest;

import java.util.Date;
import java.util.List;

/**
 * 考核评分汇总表
 */
public class PeInfoCollectRequest extends BaseFileRequest {

    private String pecId;
    private Date pecDate;
    private String pecUid;
    private String pecUname;
    private String pecConstructionUnitId;
    private String pecUnitId;
    private Date pecFlowDate;
    private Integer pecFlowState;
    private String pecRemark;
    private Integer pecYear;
    private Integer pecQuarter;
    private String pecType;

    private String   pecConstructionUnitName;
    private List<String> peIds;
    private String pecDepartment;

    public String getPecId() {
        return pecId;
    }

    public void setPecId(String pecId) {
        this.pecId = pecId;
    }

    public Date getPecDate() {
        return pecDate;
    }

    public void setPecDate(Date pecDate) {
        this.pecDate = pecDate;
    }

    public String getPecUid() {
        return pecUid;
    }

    public void setPecUid(String pecUid) {
        this.pecUid = pecUid;
    }

    public String getPecUname() {
        return pecUname;
    }

    public void setPecUname(String pecUname) {
        this.pecUname = pecUname;
    }

    public String getPecConstructionUnitId() {
        return pecConstructionUnitId;
    }

    public void setPecConstructionUnitId(String pecConstructionUnitId) {
        this.pecConstructionUnitId = pecConstructionUnitId;
    }

    public String getPecUnitId() {
        return pecUnitId;
    }

    public void setPecUnitId(String pecUnitId) {
        this.pecUnitId = pecUnitId;
    }

    public Date getPecFlowDate() {
        return pecFlowDate;
    }

    public void setPecFlowDate(Date pecFlowDate) {
        this.pecFlowDate = pecFlowDate;
    }

    public Integer getPecFlowState() {
        return pecFlowState;
    }

    public void setPecFlowState(Integer pecFlowState) {
        this.pecFlowState = pecFlowState;
    }

    public String getPecRemark() {
        return pecRemark;
    }

    public void setPecRemark(String pecRemark) {
        this.pecRemark = pecRemark;
    }

    public Integer getPecYear() {
        return pecYear;
    }

    public void setPecYear(Integer pecYear) {
        this.pecYear = pecYear;
    }

    public Integer getPecQuarter() {
        return pecQuarter;
    }

    public void setPecQuarter(Integer pecQuarter) {
        this.pecQuarter = pecQuarter;
    }

    public String getPecType() {
        return pecType;
    }

    public void setPecType(String pecType) {
        this.pecType = pecType;
    }

    public String getPecConstructionUnitName() {
        return pecConstructionUnitName;
    }

    public void setPecConstructionUnitName(String pecConstructionUnitName) {
        this.pecConstructionUnitName = pecConstructionUnitName;
    }

    public List<String> getPeIds() {
        return peIds;
    }

    public void setPeIds(List<String> peIds) {
        this.peIds = peIds;
    }

    public String getPecDepartment() {
        return pecDepartment;
    }

    public void setPecDepartment(String pecDepartment) {
        this.pecDepartment = pecDepartment;
    }
}
