package com.jpmsoft.jpbpm.pojo.dto;

import java.util.List;

public class InitiateCommunicationDto {

    public String taskid;

    public String clientid;
    public List<userselectionDto> nextuser;

    public String explain;
    public String taskname;
    public String user_id;
    public List<String> ccuser;
    public String fileid;
    public String com_id;
    public String tCode;
    public String year;

    public String getCom_id() {
        return com_id;
    }

    public void setCom_id(String com_id) {
        this.com_id = com_id;
    }

    public String getFileid() {
        return fileid;
    }

    public void setFileid(String fileid) {
        this.fileid = fileid;
    }

    public List<String> getCcuser() {
        return ccuser;
    }

    public void setCcuser(List<String> ccuser) {
        this.ccuser = ccuser;
    }

    public String getTaskid() {
        return taskid;
    }

    public void setTaskid(String taskid) {
        this.taskid = taskid;
    }

    public String getClientid() {
        return clientid;
    }

    public void setClientid(String clientid) {
        this.clientid = clientid;
    }

    public List<userselectionDto> getNextuser() {
        return nextuser;
    }

    public void setNextuser(List<userselectionDto> nextuser) {
        this.nextuser = nextuser;
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
