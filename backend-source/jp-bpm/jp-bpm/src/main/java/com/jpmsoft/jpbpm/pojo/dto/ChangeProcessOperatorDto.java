package com.jpmsoft.jpbpm.pojo.dto;

import java.util.List;

public class ChangeProcessOperatorDto {
    public String taskid;
    public List<userselectionDto> nextuser;
    public String explain;
    public String user_id;
     public String  requestSource;
     public String  tCode;
     public String year;

    public String getTaskid() {
        return taskid;
    }

    public void setTaskid(String taskid) {
        this.taskid = taskid;
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

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
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
