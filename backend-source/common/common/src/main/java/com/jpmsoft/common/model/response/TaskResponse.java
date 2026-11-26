package com.jpmsoft.common.model.response;

import java.math.BigDecimal;
import java.util.Date;


public class TaskResponse {


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
    private String projectId;
    private String contractId;
    private String flowId;
    private String secId;


    //是否原路返回（是：否）
    private String taskgetflagstr;
    private String sendUserName;
    private String fileTitle;
    private String fileCode;
    private String flowName;
    private String fileflowstatus;
    private String filecreatedate;
    private String filecreateuser;
    private String filecreateusername;
    private String  fileproject;

    private String pName;
    private String secName;
    private String conName;
    private String taskReturnType;
    private String taskUserName;
    private String tCode;
    private String taskType;
    private String taskSource;
    private BigDecimal remainder;
    private String cId;
    public String getTaskUserName() {
        return taskUserName;
    }

    public void setTaskUserName(String taskUserName) {
        this.taskUserName = taskUserName;
    }

    public String getTaskReturnType() {
        return taskReturnType;
    }

    public void setTaskReturnType(String taskReturnType) {
        this.taskReturnType = taskReturnType;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public String getSecName() {
        return secName;
    }

    public void setSecName(String secName) {
        this.secName = secName;
    }

    public String getConName() {
        return conName;
    }

    public void setConName(String conName) {
        this.conName = conName;
    }

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

    public String getSendUserName() {
        return sendUserName;
    }

    public void setSendUserName(String sendUserName) {
        this.sendUserName = sendUserName;
    }

    public String getFileTitle() {
        return fileTitle;
    }

    public void setFileTitle(String fileTitle) {
        this.fileTitle = fileTitle;
    }

    public String getFlowName() {
        return flowName;
    }

    public void setFlowName(String flowName) {
        this.flowName = flowName;
    }

    public String gettCode() {
        return tCode;
    }

    public void settCode(String tCode) {
        this.tCode = tCode;
    }

    public String getFileflowstatus() {
        return fileflowstatus;
    }

    public void setFileflowstatus(String fileflowstatus) {
        this.fileflowstatus = fileflowstatus;
    }

    public String getFilecreatedate() {
        return filecreatedate;
    }

    public void setFilecreatedate(String filecreatedate) {
        this.filecreatedate = filecreatedate;
    }

    public String getFilecreateuser() {
        return filecreateuser;
    }

    public void setFilecreateuser(String filecreateuser) {
        this.filecreateuser = filecreateuser;
    }

    public String getFilecreateusername() {
        return filecreateusername;
    }

    public void setFilecreateusername(String filecreateusername) {
        this.filecreateusername = filecreateusername;
    }

    public String getFileCode() {
        return fileCode;
    }

    public void setFileCode(String fileCode) {
        this.fileCode = fileCode;
    }

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public BigDecimal getRemainder() {
        return remainder;
    }

    public void setRemainder(BigDecimal remainder) {
        this.remainder = remainder;
    }

    public String getTaskSource() {
        return taskSource;
    }

    public void setTaskSource(String taskSource) {
        this.taskSource = taskSource;
    }

    public String getFileproject() {
        return fileproject;
    }

    public void setFileproject(String fileproject) {
        this.fileproject = fileproject;
    }

    public String getcId() {
        return cId;
    }

    public void setcId(String cId) {
        this.cId = cId;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    public String getFlowId() {
        return flowId;
    }

    public void setFlowId(String flowId) {
        this.flowId = flowId;
    }

    public String getSecId() {
        return secId;
    }

    public void setSecId(String secId) {
        this.secId = secId;
    }
}
