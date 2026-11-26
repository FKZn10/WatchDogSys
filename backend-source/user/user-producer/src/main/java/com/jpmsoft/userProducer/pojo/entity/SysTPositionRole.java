package com.jpmsoft.userProducer.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

@TableName("SYS_T_Position_Role")
public class SysTPositionRole {
    @TableId(type = IdType.INPUT)
    private String tprId;
    private String tpId;
    private String roleId;

    public String getTprId() {
        return tprId;
    }

    public void setTprId(String tprId) {
        this.tprId = tprId;
    }

    public String getTpId() {
        return tpId;
    }

    public void setTpId(String tpId) {
        this.tpId = tpId;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }
}
