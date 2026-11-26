package com.jpmsoft.jpbpm.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;

/**
 *
 */
@TableName("SYS_CoordinationProcess")
public class SysCoordinationProcess {

    @TableId(type = IdType.INPUT)
    private String cpId;
    private String cpName;
    private String cpTaskid;
    private String cpSenduser;
    private String cpUser;
    private String cpExplain;
    private Date cpSendtime;
    private Date cpTime;
    private String cpActid;
    private String cpFlowid;
    private String cpFileid;
    private String teId;
    public String getCpId() {
        return cpId;
    }

    public void setCpId(String cpId) {
        this.cpId = cpId;
    }

    public String getCpName() {
        return cpName;
    }

    public void setCpName(String cpName) {
        this.cpName = cpName;
    }

    public String getCpTaskid() {
        return cpTaskid;
    }

    public void setCpTaskid(String cpTaskid) {
        this.cpTaskid = cpTaskid;
    }

    public String getCpSenduser() {
        return cpSenduser;
    }

    public void setCpSenduser(String cpSenduser) {
        this.cpSenduser = cpSenduser;
    }

    public String getCpUser() {
        return cpUser;
    }

    public void setCpUser(String cpUser) {
        this.cpUser = cpUser;
    }

    public String getCpExplain() {
        return cpExplain;
    }

    public void setCpExplain(String cpExplain) {
        this.cpExplain = cpExplain;
    }

    public Date getCpSendtime() {
        return cpSendtime;
    }

    public void setCpSendtime(Date cpSendtime) {
        this.cpSendtime = cpSendtime;
    }

    public Date getCpTime() {
        return cpTime;
    }

    public void setCpTime(Date cpTime) {
        this.cpTime = cpTime;
    }

    public String getCpActid() {
        return cpActid;
    }

    public void setCpActid(String cpActid) {
        this.cpActid = cpActid;
    }

    public String getCpFlowid() {
        return cpFlowid;
    }

    public void setCpFlowid(String cpFlowid) {
        this.cpFlowid = cpFlowid;
    }

    public String getCpFileid() {
        return cpFileid;
    }

    public void setCpFileid(String cpFileid) {
        this.cpFileid = cpFileid;
    }

    public String getTeId() {
        return teId;
    }

    public void setTeId(String teId) {
        this.teId = teId;
    }
}
