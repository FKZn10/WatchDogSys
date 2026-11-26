package com.jpmsoft.jpbpm.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

@TableName("SYS_PositionP_Role")
public class SysPositionPRole {
    @TableId(type = IdType.INPUT)
    private String  ppRId;
    private String  roleId;
    private String  ppId;
    private String teId;

    public String getPpRId() {
        return ppRId;
    }

    public void setPpRId(String ppRId) {
        this.ppRId = ppRId;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getPpId() {
        return ppId;
    }

    public void setPpId(String ppId) {
        this.ppId = ppId;
    }
}
