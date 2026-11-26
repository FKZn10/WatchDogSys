package com.jpmsoft.userProducer.pojo.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

@TableName("Sys_Dictionary")
public class SysDictionary {

    @TableId(type = IdType.INPUT)
    private String dId;
    private String dName;
    private String dCode;
    private Integer dSort;
    private String dParentid;
    private String dRemark;
    private String dStatus;
    private String teId;
    private Integer dMold;

    public String getdId() {
        return dId;
    }

    public void setdId(String dId) {
        this.dId = dId;
    }

    public String getdName() {
        return dName;
    }

    public void setdName(String dName) {
        this.dName = dName;
    }

    public String getdCode() {
        return dCode;
    }

    public void setdCode(String dCode) {
        this.dCode = dCode;
    }

    public Integer getdSort() {
        return dSort;
    }

    public void setdSort(Integer dSort) {
        this.dSort = dSort;
    }

    public String getdParentid() {
        return dParentid;
    }

    public void setdParentid(String dParentid) {
        this.dParentid = dParentid;
    }

    public String getdRemark() {
        return dRemark;
    }

    public void setdRemark(String dRemark) {
        this.dRemark = dRemark;
    }

    public String getdStatus() {
        return dStatus;
    }

    public void setdStatus(String dStatus) {
        this.dStatus = dStatus;
    }

    public String getTeId() {
        return teId;
    }

    public void setTeId(String teId) {
        this.teId = teId;
    }

    public Integer getdMold() {
        return dMold;
    }

    public void setdMold(Integer dMold) {
        this.dMold = dMold;
    }
}
