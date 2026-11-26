package com.jpmsoft.userProducer.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

@TableName("SYS_T_Position")
public class SysTPosition {

    @TableId(type = IdType.INPUT)
    public String tpId;
    public String tdId;
    public String tpName;
    public String tpCode;
    public String tpRemark;
    public Integer tpSort;
    public String tpStatus;

    public String getTpId() {
        return tpId;
    }

    public void setTpId(String tpId) {
        this.tpId = tpId;
    }

    public String getTdId() {
        return tdId;
    }

    public void setTdId(String tdId) {
        this.tdId = tdId;
    }

    public String getTpName() {
        return tpName;
    }

    public void setTpName(String tpName) {
        this.tpName = tpName;
    }

    public String getTpCode() {
        return tpCode;
    }

    public void setTpCode(String tpCode) {
        this.tpCode = tpCode;
    }

    public String getTpRemark() {
        return tpRemark;
    }

    public void setTpRemark(String tpRemark) {
        this.tpRemark = tpRemark;
    }

    public Integer getTpSort() {
        return tpSort;
    }

    public void setTpSort(Integer tpSort) {
        this.tpSort = tpSort;
    }

    public String getTpStatus() {
        return tpStatus;
    }

    public void setTpStatus(String tpStatus) {
        this.tpStatus = tpStatus;
    }
}
