package com.jpmsoft.jpbpm.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;

@TableName("SYS_CoordinationTask_Backups")
public class SysCoordinationTaskBackups {

    @TableId(type = IdType.INPUT)
    private String ctId;
    private String ctName;
    private String ctUser;
    private String ctSenduser;
    private Date ctTime;
    private String ctTaskid;
    private String ctActid;
    private String ctFlowid;
    private String ctFileid;
    private String ctCountersign;
    private String ctActname;
    private String teId;

    public String getCtId() {
        return ctId;
    }

    public void setCtId(String ctId) {
        this.ctId = ctId;
    }

    public String getCtName() {
        return ctName;
    }

    public void setCtName(String ctName) {
        this.ctName = ctName;
    }

    public String getCtUser() {
        return ctUser;
    }

    public void setCtUser(String ctUser) {
        this.ctUser = ctUser;
    }

    public String getCtSenduser() {
        return ctSenduser;
    }

    public void setCtSenduser(String ctSenduser) {
        this.ctSenduser = ctSenduser;
    }

    public Date getCtTime() {
        return ctTime;
    }

    public void setCtTime(Date ctTime) {
        this.ctTime = ctTime;
    }

    public String getCtTaskid() {
        return ctTaskid;
    }

    public void setCtTaskid(String ctTaskid) {
        this.ctTaskid = ctTaskid;
    }

    public String getCtActid() {
        return ctActid;
    }

    public void setCtActid(String ctActid) {
        this.ctActid = ctActid;
    }

    public String getCtFlowid() {
        return ctFlowid;
    }

    public void setCtFlowid(String ctFlowid) {
        this.ctFlowid = ctFlowid;
    }

    public String getCtFileid() {
        return ctFileid;
    }

    public void setCtFileid(String ctFileid) {
        this.ctFileid = ctFileid;
    }

    public String getCtCountersign() {
        return ctCountersign;
    }

    public void setCtCountersign(String ctCountersign) {
        this.ctCountersign = ctCountersign;
    }

    public String getCtActname() {
        return ctActname;
    }

    public void setCtActname(String ctActname) {
        this.ctActname = ctActname;
    }

    public String getTeId() {
        return teId;
    }

    public void setTeId(String teId) {
        this.teId = teId;
    }
}

