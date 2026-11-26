package com.jpmsoft.common.model.response.proCp;

import java.util.Date;

/**
 * 工作办理记录
 */

public class ProCpWorkRecordResponse extends ProjectCpDtlResponse {


    private String cpwrId;
    private String cpdId;
    private String cpwrRemark;
    private String cpwrCreateUid;
    private String cpwrCreateUname;
    private Date cpwrCreateTime;
    private Boolean cpwrHomePush;
    private Date cpwrFillingTime;

    private int fileCount;
    private  int commentCount;
    public String getCpwrId() {
        return cpwrId;
    }

    public void setCpwrId(String cpwrId) {
        this.cpwrId = cpwrId;
    }

    public String getCpdId() {
        return cpdId;
    }

    public void setCpdId(String cpdId) {
        this.cpdId = cpdId;
    }

    public String getCpwrRemark() {
        return cpwrRemark;
    }

    public void setCpwrRemark(String cpwrRemark) {
        this.cpwrRemark = cpwrRemark;
    }

    public String getCpwrCreateUid() {
        return cpwrCreateUid;
    }

    public void setCpwrCreateUid(String cpwrCreateUid) {
        this.cpwrCreateUid = cpwrCreateUid;
    }

    public String getCpwrCreateUname() {
        return cpwrCreateUname;
    }

    public void setCpwrCreateUname(String cpwrCreateUname) {
        this.cpwrCreateUname = cpwrCreateUname;
    }

    public Date getCpwrCreateTime() {
        return cpwrCreateTime;
    }

    public void setCpwrCreateTime(Date cpwrCreateTime) {
        this.cpwrCreateTime = cpwrCreateTime;
    }

    public Boolean getCpwrHomePush() {
        return cpwrHomePush;
    }

    public void setCpwrHomePush(Boolean cpwrHomePush) {
        this.cpwrHomePush = cpwrHomePush;
    }

    public Date getCpwrFillingTime() {
        return cpwrFillingTime;
    }

    public void setCpwrFillingTime(Date cpwrFillingTime) {
        this.cpwrFillingTime = cpwrFillingTime;
    }

    public int getFileCount() {
        return fileCount;
    }

    public void setFileCount(int fileCount) {
        this.fileCount = fileCount;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }
}
