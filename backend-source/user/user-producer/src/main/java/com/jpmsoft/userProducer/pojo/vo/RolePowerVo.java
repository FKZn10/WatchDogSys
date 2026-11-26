package com.jpmsoft.userProducer.pojo.vo;

import java.io.Serializable;

public class RolePowerVo implements Serializable {

    private static final long serialVersionUID = -4538778502436418947L;
    private  String roleName;
    private String powerId;

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getPowerId() {
        return powerId;
    }

    public void setPowerId(String powerId) {
        this.powerId = powerId;
    }
}
