package com.jpmsoft.userProducer.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

@TableName("SYS_PositionP_Role")
public class SysPositionPRole {
    @TableId(type = IdType.INPUT)
    private String ppRId;
    private String ppId;
    private String roleId;

    public String getPpRId() {
        return ppRId;
    }

    public void setPpRId(String ppRId) {
        this.ppRId = ppRId;
    }

    public String getPpId() {
        return ppId;
    }

    public void setPpId(String ppId) {
        this.ppId = ppId;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }
}
