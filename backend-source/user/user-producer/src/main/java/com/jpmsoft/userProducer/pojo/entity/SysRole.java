package com.jpmsoft.userProducer.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;

@TableName("SYS_Role")
public class SysRole {

    @TableId(type = IdType.INPUT)
    private String roleId;
    private String roleName;
    private String roleCode;
    private String roleType;
    private String roleRemark;
    private Integer roleSort;
    private Date roleCreatetime;
    private String roleCreateuId;
    private String roleCreateuName;
    private String roleSolution;
    /**
     * 本人：0 本单位：1全部：2
     */
    private String roleDataauthority;
    private String roleCpy;
    private String teId;
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

    public String getRoleCpy() {
        return roleCpy;
    }

    public void setRoleCpy(String roleCpy) {
        this.roleCpy = roleCpy;
    }

    public String getTeId() {
        return teId;
    }

    public void setTeId(String teId) {
        this.teId = teId;
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
