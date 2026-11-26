package com.jpmsoft.userProducer.pojo.dto;

import com.jpmsoft.common.model.BaseModeRequest;
import com.jpmsoft.userProducer.constants.BaseMsg;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

public class TPositionDto extends BaseModeRequest implements Serializable {


    private static final long serialVersionUID = -4687796830208392437L;
    @NotBlank(message = BaseMsg.tpIdIsNull)
    private String tpId;
    private String tdId;
    private String tpName;
    private String tpCode;
    private String tpRemark;
    private String tpSort;
    private String tpStatus;
    private String roleIds;
    private String toId;

    public String getToId() {
        return toId;
    }

    public void setToId(String toId) {
        this.toId = toId;
    }

    public String getTpId() {
        return tpId;
    }

    public void setTpId(String tpId) {
        this.tpId = tpId;
    }

    public String getTdId() {
        return tdId;
    }

    public void setTdId(String tdId) {
        this.tdId = tdId;
    }

    public String getTpName() {
        return tpName;
    }

    public void setTpName(String tpName) {
        this.tpName = tpName;
    }

    public String getTpCode() {
        return tpCode;
    }

    public void setTpCode(String tpCode) {
        this.tpCode = tpCode;
    }

    public String getTpRemark() {
        return tpRemark;
    }

    public void setTpRemark(String tpRemark) {
        this.tpRemark = tpRemark;
    }

    public String getTpSort() {
        return tpSort;
    }

    public void setTpSort(String tpSort) {
        this.tpSort = tpSort;
    }

    public String getTpStatus() {
        return tpStatus;
    }

    public void setTpStatus(String tpStatus) {
        this.tpStatus = tpStatus;
    }

    public String getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(String roleIds) {
        this.roleIds = roleIds;
    }
}
