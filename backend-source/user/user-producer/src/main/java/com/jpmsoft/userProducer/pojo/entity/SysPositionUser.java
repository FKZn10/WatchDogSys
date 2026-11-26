package com.jpmsoft.userProducer.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

@TableName("SYS_Position_User")
public class SysPositionUser {

    @TableId(type = IdType.INPUT)
    private String puId;
    private String pId;
    private String uId;
    private Integer puSort;

    private Boolean puMainDuty;
    private Boolean puDuty;
    private Boolean puMainDep;

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

    public Integer getPuSort() {
        return puSort;
    }

    public void setPuSort(Integer puSort) {
        this.puSort = puSort;
    }

    public Boolean getPuMainDuty() {
        return puMainDuty;
    }

    public void setPuMainDuty(Boolean puMainDuty) {
        this.puMainDuty = puMainDuty;
    }

    public Boolean getPuDuty() {
        return puDuty;
    }

    public void setPuDuty(Boolean puDuty) {
        this.puDuty = puDuty;
    }

    public Boolean getPuMainDep() {
        return puMainDep;
    }

    public void setPuMainDep(Boolean puMainDep) {
        this.puMainDep = puMainDep;
    }
}
