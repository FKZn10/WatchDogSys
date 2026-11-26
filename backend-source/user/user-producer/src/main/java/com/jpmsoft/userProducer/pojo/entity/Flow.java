package com.jpmsoft.userProducer.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;

@TableName("SYS_Flow")
public class Flow {
    @TableId(type = IdType.INPUT)
    private String flowid;
    private String flowname;
    private String flowxml;
    private String flowclient;
    private String flowproject;
    private String flowsolution;
    private String flowcompany;
    private Date flowcreatetime;
    private String flowcreateuId;
    private String flowcreateuName;
    private String flowlastedituId;
    private String flowlastedituName;
    private Date flowlastedittime;
    private String flowlasteditreason;
    private Integer flowversionnum;
    private String flowenable;
    private String tCode;
    private String tId;
    private String teId;
    private String flowRecordRule;

    public String getFlowid() {
        return flowid;
    }

    public void setFlowid(String flowid) {
        this.flowid = flowid;
    }

    public String getFlowname() {
        return flowname;
    }

    public void setFlowname(String flowname) {
        this.flowname = flowname;
    }

    public String getFlowxml() {
        return flowxml;
    }

    public void setFlowxml(String flowxml) {
        this.flowxml = flowxml;
    }

    public String getFlowclient() {
        return flowclient;
    }

    public void setFlowclient(String flowclient) {
        this.flowclient = flowclient;
    }

    public String getFlowproject() {
        return flowproject;
    }

    public void setFlowproject(String flowproject) {
        this.flowproject = flowproject;
    }

    public String getFlowsolution() {
        return flowsolution;
    }

    public void setFlowsolution(String flowsolution) {
        this.flowsolution = flowsolution;
    }

    public String getFlowcompany() {
        return flowcompany;
    }

    public void setFlowcompany(String flowcompany) {
        this.flowcompany = flowcompany;
    }

    public Date getFlowcreatetime() {
        return flowcreatetime;
    }

    public void setFlowcreatetime(Date flowcreatetime) {
        this.flowcreatetime = flowcreatetime;
    }

    public String getFlowcreateuId() {
        return flowcreateuId;
    }

    public void setFlowcreateuId(String flowcreateuId) {
        this.flowcreateuId = flowcreateuId;
    }

    public String getFlowcreateuName() {
        return flowcreateuName;
    }

    public void setFlowcreateuName(String flowcreateuName) {
        this.flowcreateuName = flowcreateuName;
    }

    public String getFlowlastedituId() {
        return flowlastedituId;
    }

    public void setFlowlastedituId(String flowlastedituId) {
        this.flowlastedituId = flowlastedituId;
    }

    public String getFlowlastedituName() {
        return flowlastedituName;
    }

    public void setFlowlastedituName(String flowlastedituName) {
        this.flowlastedituName = flowlastedituName;
    }

    public Date getFlowlastedittime() {
        return flowlastedittime;
    }

    public void setFlowlastedittime(Date flowlastedittime) {
        this.flowlastedittime = flowlastedittime;
    }

    public String getFlowlasteditreason() {
        return flowlasteditreason;
    }

    public void setFlowlasteditreason(String flowlasteditreason) {
        this.flowlasteditreason = flowlasteditreason;
    }

    public Integer getFlowversionnum() {
        return flowversionnum;
    }

    public void setFlowversionnum(Integer flowversionnum) {
        this.flowversionnum = flowversionnum;
    }

    public String getFlowenable() {
        return flowenable;
    }

    public void setFlowenable(String flowenable) {
        this.flowenable = flowenable;
    }

    public String gettCode() {
        return tCode;
    }

    public void settCode(String tCode) {
        this.tCode = tCode;
    }

    public String gettId() {
        return tId;
    }

    public void settId(String tId) {
        this.tId = tId;
    }

    public String getTeId() {
        return teId;
    }

    public void setTeId(String teId) {
        this.teId = teId;
    }

    public String getFlowRecordRule() {
        return flowRecordRule;
    }

    public void setFlowRecordRule(String flowRecordRule) {
        this.flowRecordRule = flowRecordRule;
    }
}
