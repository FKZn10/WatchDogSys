package com.jpmsoft.userProducer.pojo.dto;

import com.jpmsoft.common.model.BaseModeRequest;

import java.io.Serializable;
import java.util.Date;

public class PositionDto extends BaseModeRequest implements Serializable {


    private static final long serialVersionUID = 5865077438618734219L;
    public String pId;
    // 部门Id
    public String dId;
    public String pName;
    public String pCode;
    public String pRemark;
    public String pSort;
    public String pStatus;
    public String pMdmNo;
    public Date pCreatetime;
    public String pCreateuId;
    public String pCreateuName;
    // 角色Id集合
    public String roleIds;
    public Boolean pDuty;

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getdId() {
        return dId;
    }

    public void setdId(String dId) {
        this.dId = dId;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public String getpCode() {
        return pCode;
    }

    public void setpCode(String pCode) {
        this.pCode = pCode;
    }

    public String getpRemark() {
        return pRemark;
    }

    public void setpRemark(String pRemark) {
        this.pRemark = pRemark;
    }

    public String getpSort() {
        return pSort;
    }

    public void setpSort(String pSort) {
        this.pSort = pSort;
    }

    public String getpStatus() {
        return pStatus;
    }

    public void setpStatus(String pStatus) {
        this.pStatus = pStatus;
    }

    public String getpMdmNo() {
        return pMdmNo;
    }

    public void setpMdmNo(String pMdmNo) {
        this.pMdmNo = pMdmNo;
    }

    public Date getpCreatetime() {
        return pCreatetime;
    }

    public void setpCreatetime(Date pCreatetime) {
        this.pCreatetime = pCreatetime;
    }

    public String getpCreateuId() {
        return pCreateuId;
    }

    public void setpCreateuId(String pCreateuId) {
        this.pCreateuId = pCreateuId;
    }

    public String getpCreateuName() {
        return pCreateuName;
    }

    public void setpCreateuName(String pCreateuName) {
        this.pCreateuName = pCreateuName;
    }

    public String getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(String roleIds) {
        this.roleIds = roleIds;
    }

    public Boolean getpDuty() {
        return pDuty;
    }

    public void setpDuty(Boolean pDuty) {
        this.pDuty = pDuty;
    }
}
