package com.jpmsoft.fileUpload.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;

@TableName("SYS_Config")
public class SysConfig {

    @TableId(type = IdType.INPUT)
    public String conId;
    private Integer conType;
    private String conCategory;
    public String conName;
    public String conCode;
    public String conValue;
    public String conDescription;
    public String conCreateuId;
    public String conCreateuName;
    public Date conCreatetime;
    /**
     * 启停状态 1 启用 0 停用
     */
    private Integer conStatus;
    private Integer conMold;

    public String getConId() {
        return conId;
    }

    public void setConId(String conId) {
        this.conId = conId;
    }

    public Integer getConType() {
        return conType;
    }

    public void setConType(Integer conType) {
        this.conType = conType;
    }

    public String getConCategory() {
        return conCategory;
    }

    public void setConCategory(String conCategory) {
        this.conCategory = conCategory;
    }

    public String getConName() {
        return conName;
    }

    public void setConName(String conName) {
        this.conName = conName;
    }

    public String getConCode() {
        return conCode;
    }

    public void setConCode(String conCode) {
        this.conCode = conCode;
    }

    public String getConValue() {
        return conValue;
    }

    public void setConValue(String conValue) {
        this.conValue = conValue;
    }

    public String getConDescription() {
        return conDescription;
    }

    public void setConDescription(String conDescription) {
        this.conDescription = conDescription;
    }

    public String getConCreateuId() {
        return conCreateuId;
    }

    public void setConCreateuId(String conCreateuId) {
        this.conCreateuId = conCreateuId;
    }

    public String getConCreateuName() {
        return conCreateuName;
    }

    public void setConCreateuName(String conCreateuName) {
        this.conCreateuName = conCreateuName;
    }

    public Date getConCreatetime() {
        return conCreatetime;
    }

    public void setConCreatetime(Date conCreatetime) {
        this.conCreatetime = conCreatetime;
    }

    public Integer getConStatus() {
        return conStatus;
    }

    public void setConStatus(Integer conStatus) {
        this.conStatus = conStatus;
    }

    public Integer getConMold() {
        return conMold;
    }

    public void setConMold(Integer conMold) {
        this.conMold = conMold;
    }
}

