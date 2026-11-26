package com.jpmsoft.userProducer.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;

@TableName("Sys_Positionlv")
public class SysPositionlv {
    @TableId(type = IdType.INPUT)
    public String plId;
    public String plName;
    public String plCode;
    public String positionPStatus;
    public Integer plSort;
    public String plMdmNo;
    public String plRemark;
    public String plSolution;
    public Date plCreatetime;
    public String plCreateuId;
    public String plCreateuName;
    private String teId;

    public String getPlId() {
        return plId;
    }

    public void setPlId(String plId) {
        this.plId = plId;
    }

    public String getPlName() {
        return plName;
    }

    public void setPlName(String plName) {
        this.plName = plName;
    }

    public String getPlCode() {
        return plCode;
    }

    public void setPlCode(String plCode) {
        this.plCode = plCode;
    }

    public String getPositionPStatus() {
        return positionPStatus;
    }

    public void setPositionPStatus(String positionPStatus) {
        this.positionPStatus = positionPStatus;
    }

    public Integer getPlSort() {
        return plSort;
    }

    public void setPlSort(Integer plSort) {
        this.plSort = plSort;
    }

    public String getPlMdmNo() {
        return plMdmNo;
    }

    public void setPlMdmNo(String plMdmNo) {
        this.plMdmNo = plMdmNo;
    }

    public String getPlRemark() {
        return plRemark;
    }

    public void setPlRemark(String plRemark) {
        this.plRemark = plRemark;
    }

    public String getPlSolution() {
        return plSolution;
    }

    public void setPlSolution(String plSolution) {
        this.plSolution = plSolution;
    }

    public Date getPlCreatetime() {
        return plCreatetime;
    }

    public void setPlCreatetime(Date plCreatetime) {
        this.plCreatetime = plCreatetime;
    }

    public String getPlCreateuId() {
        return plCreateuId;
    }

    public void setPlCreateuId(String plCreateuId) {
        this.plCreateuId = plCreateuId;
    }

    public String getPlCreateuName() {
        return plCreateuName;
    }

    public void setPlCreateuName(String plCreateuName) {
        this.plCreateuName = plCreateuName;
    }

    public String getTeId() {
        return teId;
    }

    public void setTeId(String teId) {
        this.teId = teId;
    }
}
