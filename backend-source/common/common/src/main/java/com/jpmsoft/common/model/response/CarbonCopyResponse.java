package com.jpmsoft.common.model.response;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class CarbonCopyResponse {
    private String ccId;
    private String ccActid;
    private String ccUser;
    private String ccFlowid;
    private Date ccTime;
    private String ccCreateuser;
    private String ccRead;
    private Date ccReadtime;
    private String ccTitle;
    private String ccFileid;
    private String ccProcessid;
    private String ccExplain;
    private Date ccExplaintime;
    /*************************/
    private String ccFlowName;
    private String ccActName;
    private String ccCreateName;
    private String ccUserName;
    private String ccUserType;
    private String ccFileTitle;
private String  isSystem;
    private String pName;
    private String secName;
    private String conName;
    private String tCode;
    public String filecreateuser;
    public String filecreateusername;
    private String fileflowstatus;
    private List<Map<String, Object>> taskUsetAndActList;

    public String getCcUserName() {
        return ccUserName;
    }

    public void setCcUserName(String ccUserName) {
        this.ccUserName = ccUserName;
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

    public String getCcId() {
        return ccId;
    }

    public void setCcId(String ccId) {
        this.ccId = ccId;
    }

    public String getCcActid() {
        return ccActid;
    }

    public void setCcActid(String ccActid) {
        this.ccActid = ccActid;
    }

    public String getCcUser() {
        return ccUser;
    }

    public void setCcUser(String ccUser) {
        this.ccUser = ccUser;
    }

    public String getCcFlowid() {
        return ccFlowid;
    }

    public void setCcFlowid(String ccFlowid) {
        this.ccFlowid = ccFlowid;
    }

    public Date getCcTime() {
        return ccTime;
    }

    public void setCcTime(Date ccTime) {
        this.ccTime = ccTime;
    }

    public String getCcCreateuser() {
        return ccCreateuser;
    }

    public void setCcCreateuser(String ccCreateuser) {
        this.ccCreateuser = ccCreateuser;
    }

    public String getCcRead() {
        return ccRead;
    }

    public void setCcRead(String ccRead) {
        this.ccRead = ccRead;
    }

    public Date getCcReadtime() {
        return ccReadtime;
    }

    public void setCcReadtime(Date ccReadtime) {
        this.ccReadtime = ccReadtime;
    }

    public String getCcTitle() {
        return ccTitle;
    }

    public void setCcTitle(String ccTitle) {
        this.ccTitle = ccTitle;
    }

    public String getCcFileid() {
        return ccFileid;
    }

    public void setCcFileid(String ccFileid) {
        this.ccFileid = ccFileid;
    }

    public String getCcProcessid() {
        return ccProcessid;
    }

    public void setCcProcessid(String ccProcessid) {
        this.ccProcessid = ccProcessid;
    }

    public String getCcExplain() {
        return ccExplain;
    }

    public void setCcExplain(String ccExplain) {
        this.ccExplain = ccExplain;
    }

    public Date getCcExplaintime() {
        return ccExplaintime;
    }

    public void setCcExplaintime(Date ccExplaintime) {
        this.ccExplaintime = ccExplaintime;
    }

    public String getCcFlowName() {
        return ccFlowName;
    }

    public void setCcFlowName(String ccFlowName) {
        this.ccFlowName = ccFlowName;
    }

    public String getCcActName() {
        return ccActName;
    }

    public void setCcActName(String ccActName) {
        this.ccActName = ccActName;
    }

    public String getCcCreateName() {
        return ccCreateName;
    }

    public void setCcCreateName(String ccCreateName) {
        this.ccCreateName = ccCreateName;
    }

    public String getCcFileTitle() {
        return ccFileTitle;
    }

    public void setCcFileTitle(String ccFileTitle) {
        this.ccFileTitle = ccFileTitle;
    }

    public String gettCode() {
        return tCode;
    }

    public void settCode(String tCode) {
        this.tCode = tCode;
    }

    public String getCcUserType() {
        return ccUserType;
    }

    public void setCcUserType(String ccUserType) {
        this.ccUserType = ccUserType;
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

    public String getFileflowstatus() {
        return fileflowstatus;
    }

    public void setFileflowstatus(String fileflowstatus) {
        this.fileflowstatus = fileflowstatus;
    }

    public List<Map<String, Object>> getTaskUsetAndActList() {
        return taskUsetAndActList;
    }

    public void setTaskUsetAndActList(List<Map<String, Object>> taskUsetAndActList) {
        this.taskUsetAndActList = taskUsetAndActList;
    }

    public String getIsSystem() {
        return isSystem;
    }

    public void setIsSystem(String isSystem) {
        this.isSystem = isSystem;
    }
}
