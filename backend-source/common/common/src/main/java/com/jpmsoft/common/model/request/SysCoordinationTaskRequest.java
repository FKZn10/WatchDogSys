package com.jpmsoft.common.model.request;

import com.jpmsoft.common.model.BaseModeRequest;

/**
 * 流程管理 沟通任务
 */
public class SysCoordinationTaskRequest extends BaseModeRequest {

    private String ctName;
    private String pName;
    private String conName;
    private String ctSendUser;
    private String ctUser;
    private String fileId;
    public String getCtName() {
        return ctName;
    }

    public void setCtName(String ctName) {
        this.ctName = ctName;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public String getConName() {
        return conName;
    }

    public void setConName(String conName) {
        this.conName = conName;
    }

    public String getCtSendUser() {
        return ctSendUser;
    }

    public void setCtSendUser(String ctSendUser) {
        this.ctSendUser = ctSendUser;
    }

    public String getCtUser() {
        return ctUser;
    }

    public void setCtUser(String ctUser) {
        this.ctUser = ctUser;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }
}
