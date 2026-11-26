package com.jpmsoft.userProducer.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;

@TableName("SYS_Department_P")
public class SysDepartmentP {
    @TableId(type = IdType.INPUT)
    public String pdId;
    public String pId;
    public String pdName;
    public String pdCode;
    public String pdStatus;
    public String pdParentid;
    public String pdPath;
    public String pdType;
    public String pdMdmNo;
    public String pdRemark;
    public Integer pdSort;
    public String pdSolution;
    public Date pdCreatetime;
    public String pdCreateuId;
    public String pdCreateuName;
    public String ownerUnit;

    public String getOwnerUnit() {
        return ownerUnit;
    }

    public void setOwnerUnit(String ownerUnit) {
        this.ownerUnit = ownerUnit;
    }

    public String getPdId() {
        return pdId;
    }

    public void setPdId(String pdId) {
        this.pdId = pdId;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getPdName() {
        return pdName;
    }

    public void setPdName(String pdName) {
        this.pdName = pdName;
    }

    public String getPdCode() {
        return pdCode;
    }

    public void setPdCode(String pdCode) {
        this.pdCode = pdCode;
    }

    public String getPdStatus() {
        return pdStatus;
    }

    public void setPdStatus(String pdStatus) {
        this.pdStatus = pdStatus;
    }

    public String getPdParentid() {
        return pdParentid;
    }

    public void setPdParentid(String pdParentid) {
        this.pdParentid = pdParentid;
    }

    public String getPdPath() {
        return pdPath;
    }

    public void setPdPath(String pdPath) {
        this.pdPath = pdPath;
    }

    public String getPdType() {
        return pdType;
    }

    public void setPdType(String pdType) {
        this.pdType = pdType;
    }

    public String getPdMdmNo() {
        return pdMdmNo;
    }

    public void setPdMdmNo(String pdMdmNo) {
        this.pdMdmNo = pdMdmNo;
    }

    public String getPdRemark() {
        return pdRemark;
    }

    public void setPdRemark(String pdRemark) {
        this.pdRemark = pdRemark;
    }

    public Integer getPdSort() {
        return pdSort;
    }

    public void setPdSort(Integer pdSort) {
        this.pdSort = pdSort;
    }

    public String getPdSolution() {
        return pdSolution;
    }

    public void setPdSolution(String pdSolution) {
        this.pdSolution = pdSolution;
    }

    public Date getPdCreatetime() {
        return pdCreatetime;
    }

    public void setPdCreatetime(Date pdCreatetime) {
        this.pdCreatetime = pdCreatetime;
    }

    public String getPdCreateuId() {
        return pdCreateuId;
    }

    public void setPdCreateuId(String pdCreateuId) {
        this.pdCreateuId = pdCreateuId;
    }

    public String getPdCreateuName() {
        return pdCreateuName;
    }

    public void setPdCreateuName(String pdCreateuName) {
        this.pdCreateuName = pdCreateuName;
    }
}
