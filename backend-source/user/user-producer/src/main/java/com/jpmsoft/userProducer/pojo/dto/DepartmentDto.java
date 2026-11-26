package com.jpmsoft.userProducer.pojo.dto;

import com.jpmsoft.common.model.BaseModeRequest;
import com.jpmsoft.userProducer.constants.BaseMsg;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

public class DepartmentDto extends BaseModeRequest implements Serializable {

    private static final long serialVersionUID = -1321377946798300353L;
    public String dId;
    public String dName;
    public String dCode;
    public String dStatus;
    public String dParentid;
    public String dPath;
    public String dType;
    public String dMdmNo;
    public String dSolution;
    public String dRemark;
    public String dSort;
    public String dCreateuId;
    public String dCreateuName;
    @NotBlank(message = BaseMsg.noOperationTypeSelected)
    public String type;
    public String newdId;
    public String newdSort;
    private String dProjectAuth;
    private Integer dHierarchy;
    private String dCompanyId;
    private Integer level;
    private Boolean hideDep=true;
    public String getdId() {
        return dId;
    }

    public void setdId(String dId) {
        this.dId = dId;
    }

    public String getdName() {
        return dName;
    }

    public void setdName(String dName) {
        this.dName = dName;
    }

    public String getdCode() {
        return dCode;
    }

    public void setdCode(String dCode) {
        this.dCode = dCode;
    }

    public String getdStatus() {
        return dStatus;
    }

    public void setdStatus(String dStatus) {
        this.dStatus = dStatus;
    }

    public String getdParentid() {
        return dParentid;
    }

    public void setdParentid(String dParentid) {
        this.dParentid = dParentid;
    }

    public String getdPath() {
        return dPath;
    }

    public void setdPath(String dPath) {
        this.dPath = dPath;
    }

    public String getdType() {
        return dType;
    }

    public void setdType(String dType) {
        this.dType = dType;
    }

    public String getdMdmNo() {
        return dMdmNo;
    }

    public void setdMdmNo(String dMdmNo) {
        this.dMdmNo = dMdmNo;
    }

    public String getdSolution() {
        return dSolution;
    }

    public void setdSolution(String dSolution) {
        this.dSolution = dSolution;
    }

    public String getdRemark() {
        return dRemark;
    }

    public void setdRemark(String dRemark) {
        this.dRemark = dRemark;
    }

    public String getdSort() {
        return dSort;
    }

    public void setdSort(String dSort) {
        this.dSort = dSort;
    }

    public String getdCreateuId() {
        return dCreateuId;
    }

    public void setdCreateuId(String dCreateuId) {
        this.dCreateuId = dCreateuId;
    }

    public String getdCreateuName() {
        return dCreateuName;
    }

    public void setdCreateuName(String dCreateuName) {
        this.dCreateuName = dCreateuName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNewdId() {
        return newdId;
    }

    public void setNewdId(String newdId) {
        this.newdId = newdId;
    }

    public String getNewdSort() {
        return newdSort;
    }

    public void setNewdSort(String newdSort) {
        this.newdSort = newdSort;
    }

    public String getdProjectAuth() {
        return dProjectAuth;
    }

    public void setdProjectAuth(String dProjectAuth) {
        this.dProjectAuth = dProjectAuth;
    }

    public Integer getdHierarchy() {
        return dHierarchy;
    }

    public void setdHierarchy(Integer dHierarchy) {
        this.dHierarchy = dHierarchy;
    }

    public String getdCompanyId() {
        return dCompanyId;
    }

    public void setdCompanyId(String dCompanyId) {
        this.dCompanyId = dCompanyId;
    }
    public Integer getLevel() {
        return level;
    }
    public void setLevel(Integer level) {
        this.level = level;
    }

    public Boolean getHideDep() {
        return hideDep;
    }

    public void setHideDep(Boolean hideDep) {
        this.hideDep = hideDep;
    }
}
