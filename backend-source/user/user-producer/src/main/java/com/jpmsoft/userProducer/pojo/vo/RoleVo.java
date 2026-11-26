package com.jpmsoft.userProducer.pojo.vo;

import java.io.Serializable;

public class RoleVo implements Serializable {
    private static final long serialVersionUID = 8142571248713938819L;
    public String roleId;
    public String roleName;
    public String code;
    public String roleCpy;
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getRoleCpy() {
        return roleCpy;
    }

    public void setRoleCpy(String roleCpy) {
        this.roleCpy = roleCpy;
    }
}
