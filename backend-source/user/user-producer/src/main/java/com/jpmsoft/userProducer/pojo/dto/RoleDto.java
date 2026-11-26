package com.jpmsoft.userProducer.pojo.dto;

import com.jpmsoft.common.model.BaseModeRequest;
import com.jpmsoft.userProducer.constants.BaseMsg;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

public class RoleDto extends BaseModeRequest implements Serializable {

    private static final long serialVersionUID = 3132289376500317398L;

    @NotBlank(message = BaseMsg.roleIdIsNull)
    public String roleId;
    public String roleName;
    public String roleCode;
    public String roleType;
    public String roleRemark;
    public Integer roleSort;
    public Date roleCreatetime;
    public String roleCreateuId;
    public String roleCreateuName;
    public String roleSolution;
    /**
     *     本人：0 本单位：1全部：2
     */
    public String roleDataauthority;
    public String powerIds;
    private String roleCpy;
    private String roleCpyName;
    private String sign;
    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    public String getRoleType() {
        return roleType;
    }

    public void setRoleType(String roleType) {
        this.roleType = roleType;
    }

    public String getRoleRemark() {
        return roleRemark;
    }

    public void setRoleRemark(String roleRemark) {
        this.roleRemark = roleRemark;
    }

    public Integer getRoleSort() {
        return roleSort;
    }

    public void setRoleSort(Integer roleSort) {
        this.roleSort = roleSort;
    }

    public Date getRoleCreatetime() {
        return roleCreatetime;
    }

    public void setRoleCreatetime(Date roleCreatetime) {
        this.roleCreatetime = roleCreatetime;
    }

    public String getRoleCreateuId() {
        return roleCreateuId;
    }

    public void setRoleCreateuId(String roleCreateuId) {
        this.roleCreateuId = roleCreateuId;
    }

    public String getRoleCreateuName() {
        return roleCreateuName;
    }

    public void setRoleCreateuName(String roleCreateuName) {
        this.roleCreateuName = roleCreateuName;
    }

    public String getRoleSolution() {
        return roleSolution;
    }

    public void setRoleSolution(String roleSolution) {
        this.roleSolution = roleSolution;
    }

    public String getRoleDataauthority() {
        return roleDataauthority;
    }

    public void setRoleDataauthority(String roleDataauthority) {
        this.roleDataauthority = roleDataauthority;
    }

    public String getPowerIds() {
        return powerIds;
    }

    public void setPowerIds(String powerIds) {
        this.powerIds = powerIds;
    }

    public String getRoleCpy() {
        return roleCpy;
    }

    public void setRoleCpy(String roleCpy) {
        this.roleCpy = roleCpy;
    }

    public String getRoleCpyName() {
        return roleCpyName;
    }

    public void setRoleCpyName(String roleCpyName) {
        this.roleCpyName = roleCpyName;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    /**
     * 加签正文
     *
     * @return
     */
    public String signContent() {
        return this.roleType + this.roleName + this.roleCode + this.roleCpy + this.roleDataauthority + this.roleSolution;
    }
}
