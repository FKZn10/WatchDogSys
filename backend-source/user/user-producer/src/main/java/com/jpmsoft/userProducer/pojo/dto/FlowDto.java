package com.jpmsoft.userProducer.pojo.dto;


import com.jpmsoft.common.model.BaseModeRequest;
import com.jpmsoft.userProducer.constants.BaseMsg;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class FlowDto extends BaseModeRequest implements Serializable {

    private static final long serialVersionUID = 3273592175682363985L;
    @NotBlank(message = BaseMsg.flowIdIsNull)
    public String flowid;
    public String flowname;
    public String flowxml;
    public String flowclient;
    public String flowproject;
    public String flowsolution;
    public String flowcompany;
    public Date flowcreatetime;
    public String flowcreateuId;
    public String flowcreateuName;
    public String flowlastedituId;
    public String flowlastedituName;
    public Date flowlastedittime;
    public String flowlasteditreason;
    public Integer flowversionnum;
    public String flowenable;
    public String tCode;
    public String tId;
    public String type;
    public List<String>  pIds;
    private String flowRecordRule;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

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

    public List<String> getpIds() {
        return pIds;
    }

    public void setpIds(List<String> pIds) {
        this.pIds = pIds;
    }

    public String getFlowRecordRule() {
        return flowRecordRule;
    }

    public void setFlowRecordRule(String flowRecordRule) {
        this.flowRecordRule = flowRecordRule;
    }
}
