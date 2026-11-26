package com.jpmsoft.jpbpm.pojo.dto;

public class WithdrawalProcessDto {

    public String taskid;
    public String explain;
    public String user_id;
    public String fileId;
    public String withdrawalSteps;
    public String requestSource;
    public String tCode;
    public String year;
    //public List<userselection> nextuser;

    public String getTaskid() {
        return taskid;
    }

    public void setTaskid(String taskid) {
        this.taskid = taskid;
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

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getWithdrawalSteps() {
        return withdrawalSteps;
    }

    public void setWithdrawalSteps(String withdrawalSteps) {
        this.withdrawalSteps = withdrawalSteps;
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
