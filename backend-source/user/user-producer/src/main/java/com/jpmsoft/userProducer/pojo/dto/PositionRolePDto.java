package com.jpmsoft.userProducer.pojo.dto;

import lombok.Data;


public class PositionRolePDto {
    private String ppRId;
    private String ppId;
    private String roleId;
    //项目id
    private String pId;
    //用户id
    private String uId;

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

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }
}
