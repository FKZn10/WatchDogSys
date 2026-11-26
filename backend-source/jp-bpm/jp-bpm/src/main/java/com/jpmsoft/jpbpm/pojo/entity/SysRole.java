package com.jpmsoft.jpbpm.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;

@TableName("SYS_Role")
public class SysRole {

    @TableId(type = IdType.INPUT)
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
     * 本人：0 本单位：1全部：2
     */
    public String roleDataauthority;
    private String teId;

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

    public String getTeId() {
        return teId;
    }

    public void setTeId(String teId) {
        this.teId = teId;
    }
}
