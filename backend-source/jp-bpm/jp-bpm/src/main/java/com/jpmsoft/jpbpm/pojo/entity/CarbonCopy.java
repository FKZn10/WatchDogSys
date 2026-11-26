package com.jpmsoft.jpbpm.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;

/**
 * 流程设置活动处理后自动抄送的用户
 */
@TableName("SYS_CarbonCopy")
public class CarbonCopy {

    @TableId(type = IdType.INPUT)
    private String ccId;
    private String ccActid;
    private String ccUser;
    private String ccFlowid;
    private Date ccTime;
    private String ccCreateuser;
    private String ccRead;
    private Date ccReadtime;
    private String ccTitle;
    private String ccFileid;
    private String ccProcessid;
    private String ccExplain;
    private Date ccExplaintime;
    private String teId;
    private String isSystem;
    public String getCcId() {
        return ccId;
    }

    public void setCcId(String ccId) {
        this.ccId = ccId;
    }

    public String getCcActid() {
        return ccActid;
    }

    public void setCcActid(String ccActid) {
        this.ccActid = ccActid;
    }

    public String getCcUser() {
        return ccUser;
    }

    public void setCcUser(String ccUser) {
        this.ccUser = ccUser;
    }

    public String getCcFlowid() {
        return ccFlowid;
    }

    public void setCcFlowid(String ccFlowid) {
        this.ccFlowid = ccFlowid;
    }

    public Date getCcTime() {
        return ccTime;
    }

    public void setCcTime(Date ccTime) {
        this.ccTime = ccTime;
    }

    public String getCcCreateuser() {
        return ccCreateuser;
    }

    public void setCcCreateuser(String ccCreateuser) {
        this.ccCreateuser = ccCreateuser;
    }

    public String getCcRead() {
        return ccRead;
    }

    public void setCcRead(String ccRead) {
        this.ccRead = ccRead;
    }

    public Date getCcReadtime() {
        return ccReadtime;
    }

    public void setCcReadtime(Date ccReadtime) {
        this.ccReadtime = ccReadtime;
    }

    public String getCcTitle() {
        return ccTitle;
    }

    public void setCcTitle(String ccTitle) {
        this.ccTitle = ccTitle;
    }

    public String getCcFileid() {
        return ccFileid;
    }

    public void setCcFileid(String ccFileid) {
        this.ccFileid = ccFileid;
    }

    public String getCcProcessid() {
        return ccProcessid;
    }

    public void setCcProcessid(String ccProcessid) {
        this.ccProcessid = ccProcessid;
    }

    public String getCcExplain() {
        return ccExplain;
    }

    public void setCcExplain(String ccExplain) {
        this.ccExplain = ccExplain;
    }

    public Date getCcExplaintime() {
        return ccExplaintime;
    }

    public void setCcExplaintime(Date ccExplaintime) {
        this.ccExplaintime = ccExplaintime;
    }

    public String getTeId() {
        return teId;
    }

    public void setTeId(String teId) {
        this.teId = teId;
    }

    public String getIsSystem() {
        return isSystem;
    }

    public void setIsSystem(String isSystem) {
        this.isSystem = isSystem;
    }
}
