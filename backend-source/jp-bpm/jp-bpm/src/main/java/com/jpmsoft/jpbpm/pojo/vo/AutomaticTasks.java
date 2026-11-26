package com.jpmsoft.jpbpm.pojo.vo;

import java.util.Date;

public class AutomaticTasks {
    private String taskid;
    private String taskflow;
    private String taskrule;
    private String taskentityid;
    private String taskclient;
    private String taskproject;
    private String taskcurrentactid;
    private String taskpreactid;
    private String taskuser;
    private Date tasksendtime;
    private String taskname;
    private String tasksenduser;
    private String taskparenttaskid;
    private String taskparentflowid;
    private String taskparentactid;
    /**
     * 未发起协同：null
     * 正在协同："YES"
     * 协同结束："NO"
     */
    private String taskcoordination;
    private Date taskstarttime;
    private Date taskendtime;
    private String taskrb;
    //未读
    //已读
    private String taskread;
    private Date taskreadtime;
    private String tasksection;
    private String taskcontract;
    //是否原路返回（是：否）
    private String taskgetflagstr;
    //退回发起人
    private String taskReturnType;
    //自动完成
    public String actid;
    public String actname;
    public String actAutoCompletion;
    public String actTriggertTime;
    public String actAutoMultiple;
    public String actAutoCompletionK;
    public String actAutoCompletionT;
    public String actAutoDefaultOpinion;
    public String actAutoTriggerField;
    public Double elapsedHours;

    public String getTaskid() {
        return taskid;
    }

    public void setTaskid(String taskid) {
        this.taskid = taskid;
    }

    public String getTaskflow() {
        return taskflow;
    }

    public void setTaskflow(String taskflow) {
        this.taskflow = taskflow;
    }

    public String getTaskrule() {
        return taskrule;
    }

    public void setTaskrule(String taskrule) {
        this.taskrule = taskrule;
    }

    public String getTaskentityid() {
        return taskentityid;
    }

    public void setTaskentityid(String taskentityid) {
        this.taskentityid = taskentityid;
    }

    public String getTaskclient() {
        return taskclient;
    }

    public void setTaskclient(String taskclient) {
        this.taskclient = taskclient;
    }

    public String getTaskproject() {
        return taskproject;
    }

    public void setTaskproject(String taskproject) {
        this.taskproject = taskproject;
    }

    public String getTaskcurrentactid() {
        return taskcurrentactid;
    }

    public void setTaskcurrentactid(String taskcurrentactid) {
        this.taskcurrentactid = taskcurrentactid;
    }

    public String getTaskpreactid() {
        return taskpreactid;
    }

    public void setTaskpreactid(String taskpreactid) {
        this.taskpreactid = taskpreactid;
    }

    public String getTaskuser() {
        return taskuser;
    }

    public void setTaskuser(String taskuser) {
        this.taskuser = taskuser;
    }

    public Date getTasksendtime() {
        return tasksendtime;
    }

    public void setTasksendtime(Date tasksendtime) {
        this.tasksendtime = tasksendtime;
    }

    public String getTaskname() {
        return taskname;
    }

    public void setTaskname(String taskname) {
        this.taskname = taskname;
    }

    public String getTasksenduser() {
        return tasksenduser;
    }

    public void setTasksenduser(String tasksenduser) {
        this.tasksenduser = tasksenduser;
    }

    public String getTaskparenttaskid() {
        return taskparenttaskid;
    }

    public void setTaskparenttaskid(String taskparenttaskid) {
        this.taskparenttaskid = taskparenttaskid;
    }

    public String getTaskparentflowid() {
        return taskparentflowid;
    }

    public void setTaskparentflowid(String taskparentflowid) {
        this.taskparentflowid = taskparentflowid;
    }

    public String getTaskparentactid() {
        return taskparentactid;
    }

    public void setTaskparentactid(String taskparentactid) {
        this.taskparentactid = taskparentactid;
    }

    public String getTaskcoordination() {
        return taskcoordination;
    }

    public void setTaskcoordination(String taskcoordination) {
        this.taskcoordination = taskcoordination;
    }

    public Date getTaskstarttime() {
        return taskstarttime;
    }

    public void setTaskstarttime(Date taskstarttime) {
        this.taskstarttime = taskstarttime;
    }

    public Date getTaskendtime() {
        return taskendtime;
    }

    public void setTaskendtime(Date taskendtime) {
        this.taskendtime = taskendtime;
    }

    public String getTaskrb() {
        return taskrb;
    }

    public void setTaskrb(String taskrb) {
        this.taskrb = taskrb;
    }

    public String getTaskread() {
        return taskread;
    }

    public void setTaskread(String taskread) {
        this.taskread = taskread;
    }

    public Date getTaskreadtime() {
        return taskreadtime;
    }

    public void setTaskreadtime(Date taskreadtime) {
        this.taskreadtime = taskreadtime;
    }

    public String getTasksection() {
        return tasksection;
    }

    public void setTasksection(String tasksection) {
        this.tasksection = tasksection;
    }

    public String getTaskcontract() {
        return taskcontract;
    }

    public void setTaskcontract(String taskcontract) {
        this.taskcontract = taskcontract;
    }

    public String getTaskgetflagstr() {
        return taskgetflagstr;
    }

    public void setTaskgetflagstr(String taskgetflagstr) {
        this.taskgetflagstr = taskgetflagstr;
    }

    public String getTaskReturnType() {
        return taskReturnType;
    }

    public void setTaskReturnType(String taskReturnType) {
        this.taskReturnType = taskReturnType;
    }

    public String getActid() {
        return actid;
    }

    public void setActid(String actid) {
        this.actid = actid;
    }

    public String getActAutoCompletion() {
        return actAutoCompletion;
    }

    public void setActAutoCompletion(String actAutoCompletion) {
        this.actAutoCompletion = actAutoCompletion;
    }

    public String getActTriggertTime() {
        return actTriggertTime;
    }

    public void setActTriggertTime(String actTriggertTime) {
        this.actTriggertTime = actTriggertTime;
    }

    public String getActAutoMultiple() {
        return actAutoMultiple;
    }

    public void setActAutoMultiple(String actAutoMultiple) {
        this.actAutoMultiple = actAutoMultiple;
    }

    public String getActAutoCompletionK() {
        return actAutoCompletionK;
    }

    public void setActAutoCompletionK(String actAutoCompletionK) {
        this.actAutoCompletionK = actAutoCompletionK;
    }

    public String getActAutoCompletionT() {
        return actAutoCompletionT;
    }

    public void setActAutoCompletionT(String actAutoCompletionT) {
        this.actAutoCompletionT = actAutoCompletionT;
    }

    public String getActAutoDefaultOpinion() {
        return actAutoDefaultOpinion;
    }

    public void setActAutoDefaultOpinion(String actAutoDefaultOpinion) {
        this.actAutoDefaultOpinion = actAutoDefaultOpinion;
    }

    public String getActAutoTriggerField() {
        return actAutoTriggerField;
    }

    public void setActAutoTriggerField(String actAutoTriggerField) {
        this.actAutoTriggerField = actAutoTriggerField;
    }

    public Double getElapsedHours() {
        return elapsedHours;
    }

    public void setElapsedHours(Double elapsedHours) {
        this.elapsedHours = elapsedHours;
    }

    public String getActname() {
        return actname;
    }

    public void setActname(String actname) {
        this.actname = actname;
    }
}
