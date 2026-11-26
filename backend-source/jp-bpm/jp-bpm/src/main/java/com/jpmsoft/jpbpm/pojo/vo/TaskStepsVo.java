package com.jpmsoft.jpbpm.pojo.vo;

public class TaskStepsVo {
    private  String ActID;
    private  String actname;
    private  String uName;
    private String uId;
    private  String tasksendtime;

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getActname() {
        return actname;
    }

    public void setActname(String actname) {
        this.actname = actname;
    }

    public String getuName() {
        return uName;
    }

    public void setuName(String uName) {
        this.uName = uName;
    }

    public String getTasksendtime() {
        return tasksendtime;
    }

    public void setTasksendtime(String tasksendtime) {
        this.tasksendtime = tasksendtime;
    }

    public String getActID() {
        return ActID;
    }

    public void setActID(String actID) {
        ActID = actID;
    }
}
