package com.jpmsoft.common.model.response;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class SysFileResponse {

    private String fileid;
    private String tId;
    private String filetitle;
    private String filecode;
    private String filekeyword;
    private String fileemergencylevel;
    private String filedescription;
    private Integer fileflowstatus;
    private Date filecreatedate;
    private String filecreateUser;
    private String filecreateusername;
    private String filecreatecompanyid;
    private String fileproject;
    private String filesectionid;
    private String filecontract;
    /****************************/
    //模板名称
    private String tName;
    private String pName;
    private String pBuildModel;
    private String pMajorType;
    private String pType;
    private String secName;
    private String conName;
    private String cName;
    private String tCode;
    private String flowId;
    private List<Map<String,String>> taskUserAndActList;

    public String getcName() {
        return cName;
    }

    public void setcName(String cName) {
        this.cName = cName;
    }

    public String getFileid() {
        return fileid;
    }

    public void setFileid(String fileid) {
        this.fileid = fileid;
    }

    public String gettId() {
        return tId;
    }

    public void settId(String tId) {
        this.tId = tId;
    }

    public String getFiletitle() {
        return filetitle;
    }

    public void setFiletitle(String filetitle) {
        this.filetitle = filetitle;
    }

    public String getFilecode() {
        return filecode;
    }

    public void setFilecode(String filecode) {
        this.filecode = filecode;
    }

    public String getFilekeyword() {
        return filekeyword;
    }

    public void setFilekeyword(String filekeyword) {
        this.filekeyword = filekeyword;
    }

    public String getFileemergencylevel() {
        return fileemergencylevel;
    }

    public void setFileemergencylevel(String fileemergencylevel) {
        this.fileemergencylevel = fileemergencylevel;
    }

    public String getFiledescription() {
        return filedescription;
    }

    public void setFiledescription(String filedescription) {
        this.filedescription = filedescription;
    }

    public Integer getFileflowstatus() {
        return fileflowstatus;
    }

    public void setFileflowstatus(Integer fileflowstatus) {
        this.fileflowstatus = fileflowstatus;
    }

    public Date getFilecreatedate() {
        return filecreatedate;
    }

    public void setFilecreatedate(Date filecreatedate) {
        this.filecreatedate = filecreatedate;
    }

    public String getFilecreateUser() {
        return filecreateUser;
    }

    public void setFilecreateUser(String filecreateUser) {
        this.filecreateUser = filecreateUser;
    }

    public String getFilecreateusername() {
        return filecreateusername;
    }

    public void setFilecreateusername(String filecreateusername) {
        this.filecreateusername = filecreateusername;
    }

    public String getFilecreatecompanyid() {
        return filecreatecompanyid;
    }

    public void setFilecreatecompanyid(String filecreatecompanyid) {
        this.filecreatecompanyid = filecreatecompanyid;
    }

    public String getFileproject() {
        return fileproject;
    }

    public void setFileproject(String fileproject) {
        this.fileproject = fileproject;
    }

    public String getFilesectionid() {
        return filesectionid;
    }

    public void setFilesectionid(String filesectionid) {
        this.filesectionid = filesectionid;
    }

    public String getFilecontract() {
        return filecontract;
    }

    public void setFilecontract(String filecontract) {
        this.filecontract = filecontract;
    }

    public String gettName() {
        return tName;
    }

    public void settName(String tName) {
        this.tName = tName;
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

    public String gettCode() {
        return tCode;
    }

    public void settCode(String tCode) {
        this.tCode = tCode;
    }

    public String getFlowId() {
        return flowId;
    }

    public void setFlowId(String flowId) {
        this.flowId = flowId;
    }

    public String getpBuildModel() {
        return pBuildModel;
    }

    public void setpBuildModel(String pBuildModel) {
        this.pBuildModel = pBuildModel;
    }

    public String getpMajorType() {
        return pMajorType;
    }

    public void setpMajorType(String pMajorType) {
        this.pMajorType = pMajorType;
    }

    public String getpType() {
        return pType;
    }

    public void setpType(String pType) {
        this.pType = pType;
    }

    public List<Map<String, String>> getTaskUserAndActList() {
        return taskUserAndActList;
    }

    public void setTaskUserAndActList(List<Map<String, String>> taskUserAndActList) {
        this.taskUserAndActList = taskUserAndActList;
    }
}
