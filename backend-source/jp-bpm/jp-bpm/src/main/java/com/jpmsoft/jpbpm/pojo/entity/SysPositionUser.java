package com.jpmsoft.jpbpm.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

@TableName("SYS_Position_User")
public class SysPositionUser {

    @TableId(type = IdType.INPUT)
    private String puId;
    private String pId;
    private String uId;
    private String teId;
    private String puMainDuty;
    private String puDuty;


    public String getPuId() {
        return puId;
    }

    public void setPuId(String puId) {
        this.puId = puId;
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

    public String getPuMainDuty() {
        return puMainDuty;
    }

    public void setPuMainDuty(String puMainDuty) {
        this.puMainDuty = puMainDuty;
    }

    public String getPuDuty() {
        return puDuty;
    }

    public void setPuDuty(String puDuty) {
        this.puDuty = puDuty;
    }
}
