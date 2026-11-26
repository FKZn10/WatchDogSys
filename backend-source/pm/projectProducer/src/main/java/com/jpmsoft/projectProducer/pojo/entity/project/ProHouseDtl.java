package com.jpmsoft.projectProducer.pojo.entity.project;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * 规划房屋详细
 */
@TableName("Pro_House_Dtl")
public class ProHouseDtl {

    @TableId(type = IdType.INPUT)
    private String houseId;
    private String pId;
    private Float houseArea;
    private Integer houseNumber;
    private String houseRemark;

    public String getHouseId() {
        return houseId;
    }

    public void setHouseId(String houseId) {
        this.houseId = houseId;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public Float getHouseArea() {
        return houseArea;
    }

    public void setHouseArea(Float houseArea) {
        this.houseArea = houseArea;
    }

    public Integer getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(Integer houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getHouseRemark() {
        return houseRemark;
    }

    public void setHouseRemark(String houseRemark) {
        this.houseRemark = houseRemark;
    }
}
