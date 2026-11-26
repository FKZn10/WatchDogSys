package com.jpmsoft.userProducer.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;

@TableName("SYS_Config")
public class SysConfigEntity {

    @TableId(type = IdType.INPUT)
    private String conId;
    private String conCode;
    private String conName;
    private Date conCreatetime;
    private String conDescription;
    private String conValue;
    private String conCreateuId;
    private String conCreateuName;
    private Integer conType;
    private String conCategory;
    /**
     * 启停状态 1 启用 0 停用
     */
    private Integer conStatus;
    private String teId;
    /**
     * 配置项类别(区别多租户与系统默认)
     * 1：系统默认
     */
    private Integer conMold;
    /**
     * 修改时间
     */
    private Date updateTime;
    private String updateUname;
    private String updateUid;

    public String getConId() {
        return conId;
    }

    public void setConId(String conId) {
        this.conId = conId;
    }

    public String getConCode() {
        return conCode;
    }

    public void setConCode(String conCode) {
        this.conCode = conCode;
    }

    public String getConName() {
        return conName;
    }

    public void setConName(String conName) {
        this.conName = conName;
    }

    public Date getConCreatetime() {
        return conCreatetime;
    }

    public void setConCreatetime(Date conCreatetime) {
        this.conCreatetime = conCreatetime;
    }

    public String getConDescription() {
        return conDescription;
    }

    public void setConDescription(String conDescription) {
        this.conDescription = conDescription;
    }

    public String getConValue() {
        return conValue;
    }

    public void setConValue(String conValue) {
        this.conValue = conValue;
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

    public Integer getConStatus() {
        return conStatus;
    }

    public void setConStatus(Integer conStatus) {
        this.conStatus = conStatus;
    }

    public String getTeId() {
        return teId;
    }

    public void setTeId(String teId) {
        this.teId = teId;
    }

    public Integer getConMold() {
        return conMold;
    }

    public void setConMold(Integer conMold) {
        this.conMold = conMold;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateUname() {
        return updateUname;
    }

    public void setUpdateUname(String updateUname) {
        this.updateUname = updateUname;
    }

    public String getUpdateUid() {
        return updateUid;
    }

    public void setUpdateUid(String updateUid) {
        this.updateUid = updateUid;
    }
}
