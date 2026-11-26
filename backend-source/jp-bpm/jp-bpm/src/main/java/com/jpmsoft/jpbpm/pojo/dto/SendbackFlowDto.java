package com.jpmsoft.jpbpm.pojo.dto;

import java.util.List;

public class SendbackFlowDto {

    public String taskid;
    public List<String> cc_user;
    public String activityid;
    public String explain;
    public String taskname;
    public String user_id;
    public String file_id;
    public String operationtype;
    public String backtype;
    public String returntype;
    public String smstype;
    public String returnUser;
    public String  returnaActId;
    public String  requestSource;
    public String tCode;
    public String year;

    public String getReturntype() {
        return returntype;
    }

    public void setReturntype(String returntype) {
        this.returntype = returntype;
    }

    public String getTaskid() {
        return taskid;
    }

    public void setTaskid(String taskid) {
        this.taskid = taskid;
    }

    public List<String> getCc_user() {
        return cc_user;
    }

    public void setCc_user(List<String> cc_user) {
        this.cc_user = cc_user;
    }

    public String getActivityid() {
        return activityid;
    }

    public void setActivityid(String activityid) {
        this.activityid = activityid;
    }

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }

    public String getTaskname() {
        return taskname;
    }

    public void setTaskname(String taskname) {
        this.taskname = taskname;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getFile_id() {
        return file_id;
    }

    public void setFile_id(String file_id) {
        this.file_id = file_id;
    }

    public String getOperationtype() {
        return operationtype;
    }

    public void setOperationtype(String operationtype) {
        this.operationtype = operationtype;
    }

    public String getBacktype() {
        return backtype;
    }

    public void setBacktype(String backtype) {
        this.backtype = backtype;
    }

    public String getSmstype() {
        return smstype;
    }

    public void setSmstype(String smstype) {
        this.smstype = smstype;
    }

    public String getReturnUser() {
        return returnUser;
    }

    public void setReturnUser(String returnUser) {
        this.returnUser = returnUser;
    }


    public String getReturnaActId() {
        return returnaActId;
    }

    public void setReturnaActId(String returnaActId) {
        this.returnaActId = returnaActId;
    }

    public String getRequestSource() {
        return requestSource;
    }

    public void setRequestSource(String requestSource) {
        this.requestSource = requestSource;
    }

    public String gettCode() {
        return tCode;
    }

    public void settCode(String tCode) {
        this.tCode = tCode;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }
}
