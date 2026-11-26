package com.jpmsoft.userProducer.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;

@TableName("SYS_Position_P")
public class SysPositionP {
    @TableId(type = IdType.INPUT)
    public String ppId;
    public String pdId;
    public String ppName;
    public String ppCode;
    public String ppRemark;
    public Integer ppSort;
    public String ppStatus;
    public String ppMdmNo;
    public Date ppCreatetime;
    public String ppCreateuId;
    public String ppCreateuName;

    public String getPpId() {
        return ppId;
    }

    public void setPpId(String ppId) {
        this.ppId = ppId;
    }

    public String getPdId() {
        return pdId;
    }

    public void setPdId(String pdId) {
        this.pdId = pdId;
    }

    public String getPpName() {
        return ppName;
    }

    public void setPpName(String ppName) {
        this.ppName = ppName;
    }

    public String getPpCode() {
        return ppCode;
    }

    public void setPpCode(String ppCode) {
        this.ppCode = ppCode;
    }

    public String getPpRemark() {
        return ppRemark;
    }

    public void setPpRemark(String ppRemark) {
        this.ppRemark = ppRemark;
    }

    public Integer getPpSort() {
        return ppSort;
    }

    public void setPpSort(Integer ppSort) {
        this.ppSort = ppSort;
    }

    public String getPpStatus() {
        return ppStatus;
    }

    public void setPpStatus(String ppStatus) {
        this.ppStatus = ppStatus;
    }

    public String getPpMdmNo() {
        return ppMdmNo;
    }

    public void setPpMdmNo(String ppMdmNo) {
        this.ppMdmNo = ppMdmNo;
    }

    public Date getPpCreatetime() {
        return ppCreatetime;
    }

    public void setPpCreatetime(Date ppCreatetime) {
        this.ppCreatetime = ppCreatetime;
    }

    public String getPpCreateuId() {
        return ppCreateuId;
    }

    public void setPpCreateuId(String ppCreateuId) {
        this.ppCreateuId = ppCreateuId;
    }

    public String getPpCreateuName() {
        return ppCreateuName;
    }

    public void setPpCreateuName(String ppCreateuName) {
        this.ppCreateuName = ppCreateuName;
    }
}
