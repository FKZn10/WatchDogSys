package com.jpmsoft.projectProducer.pojo.dto.departmentP;

import com.jpmsoft.common.model.BaseModeRequest;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

public class PositionPDto extends BaseModeRequest implements Serializable {
    private static final long serialVersionUID = -7198824290377016157L;
    //项目岗位ID
    @NotBlank(message = "项目岗位ID不能为空")
    public String ppId;
    //项目部门ID
    public String pdId;
    public String ppName;
    public String ppCode;
    public String ppRemark;
    public String ppSort;
    public String ppStatus;
    public String ppMdmNo;
    public Date ppCreatetime;
    public String ppCreateuId;
    public String ppCreateuName;
    /**
     * 角色id集合
     */
    public String roleIds;

    public String getPpId() {
        return ppId;
    }

    public void setPpId(String ppId) {
        this.ppId = ppId;
    }

    public String getPdId() {
        return pdId;
    }

    public void setPdId(String pdId) {
        this.pdId = pdId;
    }

    public String getPpName() {
        return ppName;
    }

    public void setPpName(String ppName) {
        this.ppName = ppName;
    }

    public String getPpCode() {
        return ppCode;
    }

    public void setPpCode(String ppCode) {
        this.ppCode = ppCode;
    }

    public String getPpRemark() {
        return ppRemark;
    }

    public void setPpRemark(String ppRemark) {
        this.ppRemark = ppRemark;
    }

    public String getPpSort() {
        return ppSort;
    }

    public void setPpSort(String ppSort) {
        this.ppSort = ppSort;
    }

    public String getPpStatus() {
        return ppStatus;
    }

    public void setPpStatus(String ppStatus) {
        this.ppStatus = ppStatus;
    }

    public String getPpMdmNo() {
        return ppMdmNo;
    }

    public void setPpMdmNo(String ppMdmNo) {
        this.ppMdmNo = ppMdmNo;
    }

    public Date getPpCreatetime() {
        return ppCreatetime;
    }

    public void setPpCreatetime(Date ppCreatetime) {
        this.ppCreatetime = ppCreatetime;
    }

    public String getPpCreateuId() {
        return ppCreateuId;
    }

    public void setPpCreateuId(String ppCreateuId) {
        this.ppCreateuId = ppCreateuId;
    }

    public String getPpCreateuName() {
        return ppCreateuName;
    }

    public void setPpCreateuName(String ppCreateuName) {
        this.ppCreateuName = ppCreateuName;
    }

    public String getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(String roleIds) {
        this.roleIds = roleIds;
    }
}
